<template>
    <div class="container-wrap chart">
        <div class="container chart">
            <div class="info">
                <div>
                    <h3>기준 지점</h3>
                    <h2 v-if="selectedStandardStation">{{ selectedStandardStation.name }}</h2>
                </div>
                <div>
                    <h3>비교 지점</h3>
                    <h2 v-if="selectedCompareStation">{{ selectedCompareStation.name }}</h2>
                </div>
                <div>
                    <h3>비교 분석 결과</h3>
                    <h2>Y = RX<sup>2</sup> + b&emsp;R<sup>2</sup> = 0.81</h2>
                </div>
            </div>
            <div ref="timeseries" class="timeseries"></div>
            <div ref="comparison" class="comparison"></div>
        </div>
<!--        <div v-show="!loading" id="no-data" class="container">-->
<!--            <div>-->
<!--                <h1>데이터가 없습니다.</h1>-->
<!--            </div>-->
<!--        </div>-->
        <loading />
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

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
                loading: state => state.common.loading,
                selectedStandardStation: state => state.verification.selectedStandardStation,
                selectedCompareStation: state => state.verification.selectedCompareStation,
                selectedDateType: state => state.verification.selectedDateType,
                data: state => state.verification.data
            }),

            xAxisLabels() {
                if (this.selectedDateType == "hour")
                    return this.data.map(obj => dayjs(obj.datetime, "YYYYMMDDHH").format("YYYY년 MM월 DD일 HH시"));
                else return this.data.map(obj => dayjs(obj.datetime, "YYYYMMDD").format("YYYY년 MM월 DD일"));
            }
        },
        watch: {
            data() {
                console.log(this.data);
                this.initChart();
            }
        },
        methods: {
            ...mapActions({
                clearResizeEvent: "CLEAR_RESIZE_EVENT"
            }),

            async initChart() {
                if (this.timeseriesChart != null) this.timeseriesChart.clear();
                else this.timeseriesChart = init(this.$refs["timeseries"]);
                if (this.comparisonChart != null) this.comparisonChart.clear();
                else this.comparisonChart = init(this.$refs["comparison"]);

                this.timeseriesChart.setOption({
                    title: {
                        text: "",
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
                        data: ["standard", "compare"],
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
                        // name: this.yAxis.map(y => `${y.label} (${y.unit})`).join(", "),
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
                            name: "standard",
                            type: "line",
                            showSymbol: true
                        },
                        {
                            name: "compare",
                            type: "line",
                            showSymbol: true
                        }
                    ]
                });
                this.comparisonChart.setOption({

                });

                this.addResizeEvent(() => {
                    this.timeseriesChart.resize();
                    this.comparisonChart.resize();
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
