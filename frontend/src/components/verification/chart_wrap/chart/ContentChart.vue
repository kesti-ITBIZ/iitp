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
                    <h2 v-if="data && data.length > 0" class="formula">Y = {{ r }}X {{ b >= 0 ? `+ ${b}` : `- ${-b}` }}&emsp;R<sup>2</sup> = {{ (Math.round((this.selectedItem.value == 'pm10' ? this.corrPm10 ** 2 : this.corrPm25 ** 2) * 10000) / 10000).toFixed(4) }}</h2>
                </div>
            </div>
            <div class="timeseries">
                <h2>시계열 차트</h2>
                <div ref="timeseries"></div>
            </div>
            <div class="comparison">
                <h2>상관분석 차트</h2>
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
    import { transform } from "echarts-stat/";
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
                reactiveMaxWidth: state => state.common.reactiveMaxWidth,
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
            },

            corrPm10() {
                const n = this.data.length;
                let avgX = 0, avgY = 0;

                this.data.forEach(obj => {
                    avgX += obj.compPm10;
                    avgY += obj.stdPm10;
                });
                avgX /= n;
                avgY /= n;

                let cov = 0, std = 0;
                let sumOfSubXAvgXSqure = 0, sumOfSubYAvgYSqure = 0;
                this.data.forEach(obj => {
                    const subXAvgX = obj.compPm10 - avgX;
                    const subYAvgY = obj.stdPm10 - avgY;
                    cov += subXAvgX * subYAvgY;
                    sumOfSubXAvgXSqure += subXAvgX ** 2;
                    sumOfSubYAvgYSqure += subYAvgY ** 2;
                });
                std = Math.sqrt(sumOfSubXAvgXSqure * sumOfSubYAvgYSqure);

                return cov / std;
            },

            corrPm25() {
                const n = this.data.length;
                let avgX = 0, avgY = 0;

                this.data.forEach(obj => {
                    avgX += obj.compPm25;
                    avgY += obj.stdPm25;
                });
                avgX /= n;
                avgY /= n;

                let cov = 0, std = 0;
                let sumOfSubXAvgXSqure = 0, sumOfSubYAvgYSqure = 0;
                this.data.forEach(obj => {
                    const subXAvgX = obj.compPm25 - avgX;
                    const subYAvgY = obj.stdPm25 - avgY;
                    cov += subXAvgX * subYAvgY;
                    sumOfSubXAvgXSqure += subXAvgX ** 2;
                    sumOfSubYAvgYSqure += subYAvgY ** 2;
                });
                std = Math.sqrt(sumOfSubXAvgXSqure * sumOfSubYAvgYSqure);

                return cov / std;
            }
        },
        watch: {
            async selectedItem() {
                if (this.data && this.data.length > 0)
                    await this.initChart();
            },

            // async selectedDateType() {
            //     if (this.data && this.data.length > 0)
            //         await this.initChart();
            // }

            async data() {
                if (this.data && this.data.length > 0)
                    await this.initChart();
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
                    tooltip: {
                        formatter: data => `
                            <strong style="font-size: 16px; margin-bottom: 5px; transform: rotate(0.03deg)">${data.componentIndex === 0 ? "관측" : "비교"} 지점</strong><br />
                            지점명: ${data.seriesName}<br />
                            측정 시간: ${data.name}<br />
                            ${this.selectedItem.label}: ${data.value}${this.selectedItem.unit}<br />`,
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

                registerTransform(transform.regression);
                this.comparisonChart?.setOption({
                    dataset: [
                        {
                            source: this.correlationData
                        },
                        {
                            transform: {
                                type: "ecStat:regression"
                            }
                        }
                    ],
                    tooltip: {
                        formatter: data => {
                            if (data.componentIndex === 0) {
                                const label = this.selectedItem.label;
                                const unit = this.selectedItem.unit;
                                return `
                                    기준 지점 ${label}: ${data.value[0]}${unit}<br />
                                    비교 지점 ${label}: ${data.value[1]}${unit}<br />`;
                            }
                        },
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
                        data: ["산점도", "추세선"],
                        textStyle: {
                            fontFamily: "NanumSquare"
                        }
                    },
                    xAxis: {
                        name: this.data && this.data.length > 0 ? this.data[0].compStnNm : "",
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
                        name: this.data && this.data.length > 0 ? this.data[0].stdStnNm : "",
                        splitLine: {
                            show: true
                        },
                        nameTextStyle: {
                            align: "left",
                            fontFamily: "NanumSquare",
                            fontWeight: "bold"
                        },
                        axisLabel: {
                            formatter: "{value}",
                            fontFamily: "NanumSquare"
                        },
                    },
                    series: [
                        {
                            name: "산점도",
                            type: "scatter",
                            symbolSize: 7,
                            itemStyle: {
                                normal: {
                                    color: "#5b9bd6"
                                }
                            }
                        },
                        {
                            name: "추세선",
                            type: "line",
                            datasetIndex: 1,
                            symbolSize: 0.1,
                            symbol: "circle",
                            label: {
                                show: true,
                                fontSize: 16,
                                fontFamily: "NanumSquare",
                                fontWeight: "bold",
                                formatter: data => {
                                    if (data.value[2] !== "") {
                                        const formula = data.value[2].toUpperCase().replace("+ -", "- ");
                                        let tmp = formula.replace("Y = ", "");
                                        this.r = +tmp.substr(0, tmp.indexOf("X"));
                                        this.b = +tmp.substr(tmp.lastIndexOf(tmp.lastIndexOf("+") !== -1 ? "+" : "-")).replace(" ", "");
                                        return formula + `    R² = ${(Math.round((this.selectedItem.value == 'pm10' ? this.corrPm10 ** 2 : this.corrPm25 ** 2) * 10000) / 10000).toFixed(4)}`;
                                    }
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: "#313131"
                                }
                            },
                            labelLayout: {
                                dx: -100,
                                dy: 100
                            },
                            encode: {
                                label: 2,
                                tooltip: 1
                            }
                        }
                    ]
                });

                this.addResizeEvent(() => {
                    this.timeseriesChart.resize();
                    this.comparisonChart.resize();
                });
                await new Promise(resolve => {
                    this.timeseriesChart.resize();
                    this.comparisonChart.resize();
                    resolve();
                });
            }
        },
        destroyed() {
            this.clearResizeEvent();
        }
    }
</script>

<style lang="scss">
    @import "./ContentChart";
</style>
