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
                    <h2>Y = RX + b&emsp;R<sup>2</sup> = 0.81</h2>
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

    import { init } from "echarts";
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
            formula: "$$y = 4x^2 + 1   R^2 = 0.81"
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
                // let xAxis = [];
                // for (let i = 0; i < 100; ++i) xAxis.push((i + 1).toString());
                // return xAxis;
                if (this.selectedDateType == "hour")
                    return this.data.map(obj => dayjs(obj.datetime, "YYYYMMDDHH").format("YYYY년 MM월 DD일 HH시"));
                else return this.data.map(obj => dayjs(obj.datetime, "YYYYMMDD").format("YYYY년 MM월 DD일"));
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
                    toolbox: {
                        feature: {
                            saveAsImage: {
                                type: "png"
                            }
                        }
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
                        // data: ["standard", "compare"],
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
                        nameTextStyle: {
                            align: "left",
                            fontFamily: "NanumSquare",
                            fontWeight: "bold"
                        },
                        axisLabel: {
                            formatter: `{value}`,
                            fontFamily: "NanumSquare"
                        },
                        splitLine: {
                            show: true
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
                            // data: (() => {
                            //     const data = [], len = 100;
                            //     let value = Math.random();
                            //     for (let i = 0, j = 1; i < len; ++i, j = (Math.random() * 100) % 3 === 0 ? 1 : -1) {
                            //         const addValue = Math.random() * 10;
                            //         data.push(value += (value + j * addValue < 0 ? -j : j) * addValue);
                            //     }
                            //     return data;
                            // })(),
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
                            // data: (() => {
                            //     const data = [], len = 100;
                            //     let value = Math.random();
                            //     for (let i = 0, j = 1; i < len; ++i, j = (Math.random() * 100) % 3 === 0 ? 1 : -1) {
                            //         const addValue = Math.random() * 10;
                            //         data.push(value += (value + j * addValue < 0 ? -j : j) * addValue);
                            //     }
                            //     return data;
                            // })(),
                            itemStyle: {
                                normal: {
                                    color: "#ff7e0d"
                                }
                            },
                            showSymbol: true
                        }
                    ]
                });
                // this.comparisonChart.setOption({
                //
                // });

                this.addResizeEvent(() => {
                    this.timeseriesChart.resize();
                    this.comparisonChart.resize();
                });
            }
        },
        mounted() {
            this.initChart();
        },
        destroyed() {
            this.clearResizeEvent();
        }
    }
</script>

<style lang="scss">
    @import "./ContentChart";
</style>
