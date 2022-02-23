<template>
    <div class="container-wrap chart">
        <div v-show="data && data.length > 0" class="container chart">
            <div class="info">
                <div>
                    <h3>기준 지점</h3>
                    <h2 v-if="data && data.length > 0">{{ data[0].stdStnNm }}</h2>
                </div>
                <div>
                    <h3>비교 지점</h3>
                    <h2 v-if="data && data.length > 0">{{ data[0].compStnNm }}</h2>
                </div>
                <div>
                    <h3>비교 분석 결과</h3>
                    <h2>Y = {{ r }}X {{ b >= 0 ? `+ ${b}` : `- ${-b}` }}&emsp;R<sup>2</sup> = ???</h2>
                </div>
            </div>
            <div class="timeseries">
                <div ref="timeseries"></div>
            </div>
            <div class="comparison">
                <div ref="comparison"></div>
            </div>
        </div>
        <div v-show="(!data || (data && data.length === 0)) && !loading" id="no-data" class="container">
            <div>
                <h1>데이터가 없습니다.</h1>
            </div>
        </div>
        <loading />
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    import { init, registerTransform } from "echarts";
    import { transform } from "echarts-stat";
    import dayjs from "dayjs";
    import customParseFormat from "dayjs/plugin/customParseFormat";

    dayjs.extend(customParseFormat);

    import Loading from "../../../common/loading/Loading";

    export default {
        name: "ContentChart",
        components: {
            Loading
        },
        data: () => ({
            timeseriesChart: null,
            comparisonChart: null,
            r: 0,
            b: 0
        }),
        computed: {
            ...mapState({
                windowWidth: state => state.common.windowWidth,
                loading: state => state.common.loading,
                selectedStandardStation: state => state.verification.selectedStandardStation,
                selectedCompareStation: state => state.verification.selectedCompareStation,
                selectedDateType: state => state.verification.selectedDateType,
                selectedItem: state => state.verification.selectedItem,
                data: state => state.verification.data
            }),

            xAxisLabels() {
                if (this.selectedDateType == "hour")
                    return this.data.map(obj => dayjs(obj.datetime, "YYYYMMDDHH").format("YYYY년 MM월 DD일 HH시"));
                else return this.data.map(obj => dayjs(obj.datetime, "YYYYMMDD").format("YYYY년 MM월 DD일"));
            },

            correlationData() {
                if (this.selectedItem.value == "pm10")
                    return this.data.map(obj => [obj.compPm10, obj.stdPm10]);
                else return this.data.map(obj => [obj.compPm25, obj.stdPm25]);
            }
        },
        watch: {
            selectedItem() {
                this.initChart();
            },

            data() {
                this.initChart();
            }
        },
        methods: {
            ...mapActions({
                addResizeEvent: "ADD_RESIZE_EVENT",
                clearResizeEvent: "CLEAR_RESIZE_EVENT"
            }),

            async initChart() {
                console.log("initChart");
                if (this.timeseriesChart != null) this.timeseriesChart.clear();
                else this.timeseriesChart = init(this.$refs["timeseries"]);
                if (this.comparisonChart != null) this.comparisonChart.clear();
                else this.comparisonChart = init(this.$refs["comparison"]);

                this.timeseriesChart.setOption({
                    title: {
                        text: "시계열 차트",
                        left: 50,
                        textStyle: {
                            fontFamily: "NanumSquare",
                            fontWeight: "bold"
                        }
                    },
                    tooltip: {
                        formatter: data => `
                            <strong style="font-size: 16px; margin-bottom: 5px; transform: rotate(0.03deg)">${data.componentIndex === 0 ? "관측" : "비교"} 지점</strong><br />
                            지점명: ${data.seriesName}<br />
                            측정 시간: ${data.name}<br />
                            ${this.selectedItem.label}: ${data.value}<br />`,
                        textStyle: {
                            fontFamily: "NanumSquare"
                        }
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {
                                type: "png"
                            }
                        },
                        right: 40
                    },
                    dataZoom: [
                        {
                            type: "slider"
                        }
                    ],
                    legend: {
                        data: this.data && this.data.length > 0 ?
                            [
                                this.data[0].stdStnNm,
                                this.data[0].compStnNm
                            ] : [],
                        textStyle: {
                            fontFamily: "NanumSquare"
                        }
                    },
                    xAxis: {
                        type: "category",
                        name: "측정 시간",
                        data: this.xAxisLabels,
                        splitLine: {
                            show: true
                        },
                        nameTextStyle: {
                            fontFamily: "NanumSquare"
                        },
                        axisLabel: {
                            fontFamily: "NanumSquare"
                        }
                    },
                    yAxis: {
                        type: "value",
                        name: `${this.selectedItem.label} ${this.selectedItem.unit}`,
                        min: 0,
                        splitLine: {
                            show: true
                        },
                        nameTextStyle: {
                            align: "left",
                            fontFamily: "NanumSquare",
                            fontWeight: "bold"
                        },
                        axisLabel: {
                            formatter: `{value}`,
                            fontFamily: "NanumSquare"
                        }
                    },
                    series: [
                        {
                            name: this.data && this.data.length > 0 ? this.data[0].stdStnNm : "",
                            // name: "standard",
                            type: "line",
                            data: this.selectedItem.value == "pm10" ?
                                this.data.map(obj => obj.stdPm10) :
                                this.data.map(obj => obj.stdPm25),
                            itemStyle: {
                                normal: {
                                    color: "#2279b5"
                                }
                            },
                            showSymbol: true
                        },
                        {
                            name: this.data.length > 0 ? this.data[0].compStnNm : "",
                            // name: "compare",
                            type: "line",
                            data: this.selectedItem.value == "pm10" ?
                                this.data.map(obj => obj.compPm10) :
                                this.data.map(obj => obj.compPm25),
                            itemStyle: {
                                normal: {
                                    color: "#ff7e0d"
                                }
                            },
                            showSymbol: true
                        }
                    ]
                });

                this.comparisonChart?.setOption({
                    dataset: [
                        {
                            source: this.correlationData
                        },
                        {
                            transform: {
                                type: 'ecStat:regression'
                                // 'linear' by default.
                                // config: { method: 'linear', formulaOn: 'end'}
                            }
                        }
                    ],
                    title: {
                        text: "상관분석 차트",
                        left: 50,
                        textStyle: {
                            fontFamily: "NanumSquare",
                            fontWeight: "bold"
                        }
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {
                                type: "png"
                            }
                        },
                        right: 40
                    },
                    dataZoom: [
                        {
                            type: "slider"
                        }
                    ],
                    legend: {
                        data: ["scatter", "추세선"],
                        textStyle: {
                            fontFamily: "NanumSquare"
                        }
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'cross'
                        }
                    },
                    xAxis: {
                        splitLine: {
                            show: true
                        },
                        nameTextStyle: {
                            fontFamily: "NanumSquare"
                        },
                        axisLabel: {
                            fontFamily: "NanumSquare"
                        }
                    },
                    yAxis: {
                        splitLine: {
                            show: true
                        },
                        nameTextStyle: {
                            align: "left",
                            fontFamily: "NanumSquare",
                            fontWeight: "bold"
                        },
                        axisLabel: {
                            formatter: `{value}`,
                            fontFamily: "NanumSquare"
                        },
                    },
                    series: [
                        {
                            name: 'scatter',
                            type: 'scatter'
                        },
                        {
                            name: '추세선',
                            type: 'line',
                            datasetIndex: 1,
                            symbolSize: 0.1,
                            symbol: 'circle',
                            label: {
                                show: true,
                                fontSize: 16,
                                fontFamily: "NanumSquare",
                                formatter: data => {
                                    if (data.value[2] !== "") {
                                        const formula = data.value[2].toUpperCase().replace("+ -", "- ");
                                        let tmp = formula.replace("Y = ", "");
                                        this.r = +tmp.substr(0, tmp.indexOf("X"));
                                        this.b = +tmp.substr(tmp.lastIndexOf(tmp.lastIndexOf("+") !== -1 ? "+" : "-")).replace(" ", "");
                                        return formula;
                                    }
                                }
                            },
                            labelLayout: { dx: -20 },
                            encode: { label: 2, tooltip: 1 }
                        }
                    ]
                });

                this.addResizeEvent(() => {
                    this.timeseriesChart.resize();
                    this.comparisonChart.resize();
                });
            }
        },
        mounted() {
            registerTransform(transform.regression);
        },
        destroyed() {
            this.clearResizeEvent();
        }
    }
</script>

<style lang="scss">
    @import "./ContentChart";
</style>
