<template>
    <div class="container-wrap chart">
        <div v-show="data && data.length > 0" class="container chart">
            <div class="info">
                <div>
                    <h4>기준 지점</h4>
                    <h3 v-if="data && data.length > 0">{{ data[0].stdStnNm }}</h3>
                </div>
                <div>
                    <h4>비교 지점</h4>
                    <h3 v-if="data && data.length > 0">{{ data[0].compStnNm }}</h3>
                </div>
                <div>
                    <h4>비교 분석 결과</h4>
                    <h3 v-if="data && data.length > 0" class="formula">
                        Y = {{ r }}X {{ b >= 0 ? `+ ${b}` : `- ${-b}` }}<br />
                        R<sup>2</sup> = {{ (Math.round((this.selectedItem.value == 'pm10' ? this.corrPm10 ** 2 : this.corrPm25 ** 2) * 10000) / 10000).toFixed(4) }}
                    </h3>
                </div>
            </div>
            <div class="timeseries">
                <h3>시계열 차트</h3>
                <div ref="timeseries"></div>
            </div>
            <div class="correlation">
                <h3>상관분석 차트 - {{ selectedItem.label }} (단위: {{ selectedItem.unit }}) </h3>
                <div ref="correlation"></div>
            </div>
        </div>
        <div v-show="!data || data.length === 0" id="no-data" class="container">
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
            correlationChart: null,
            r: 0,
            b: 0
        }),
        computed: {
            ...mapState({
                windowWidth: state => state.common.windowWidth,
                reactiveMaxWidth: state => state.common.reactiveMaxWidth,
                selectedStandardStation: state => state.verification.selectedStandardStation,
                selectedCompareStation: state => state.verification.selectedCompareStation,
                selectedDateType: state => state.verification.selectedDateType,
                selectedItem: state => state.verification.selectedItem,
                data: state => state.verification.data
            }),

            xAxisLabels() {
                let datetimes = [];
                const startDatetime = dayjs(this.data[0].datetime, "YYYYMMDDHH");
                const endDatetime = dayjs(this.data[this.data.length - 1].datetime, "YYYYMMDDHH");
                if (this.selectedDateType == "hour")
                    for (let datetime = startDatetime;
                         datetime <= endDatetime;
                         datetime = datetime.add(1, "hours"))
                        datetimes.push(datetime.format("YYYY년 MM월 DD일 HH시"));
                else
                    for (let datetime = startDatetime;
                         datetime <= endDatetime;
                         datetime = datetime.add(1, "days"))
                        datetimes.push(datetime.format("YYYY년 MM월 DD일"));
                return datetimes;
            },

            timeseriesData() {
                let stdPm10 = [];
                let stdPm25 = [];
                let compPm10 = [];
                let compPm25 = [];

                if (this.selectedDateType == "hour") {
                    let datetime = dayjs(this.data[0].datetime, "YYYYMMDDHH");
                    this.data.forEach(obj => {
                        for (; datetime.format("YYYYMMDDHH") < obj.datetime;
                               datetime = datetime.add(1, "hours")) {
                            stdPm10.push(null);
                            stdPm25.push(null);
                            compPm10.push(null);
                            compPm25.push(null);
                        }
                        stdPm10.push(obj.stdPm10);
                        stdPm25.push(obj.stdPm25);
                        compPm10.push(obj.compPm10);
                        compPm25.push(obj.compPm25);
                        datetime = datetime.add(1, "hours");
                    });
                } else {
                    const stdPm10Bucket = {};
                    const stdPm25Bucket = {};
                    const compPm10Bucket = {};
                    const compPm25Bucket = {};
                    const startDatetime = dayjs(this.data[0].datetime, "YYYYMMDD");
                    const endDatetime = dayjs(this.data[this.data.length - 1].datetime, "YYYYMMDD");
                    const datetimes = [];

                    for (let datetime = startDatetime;
                         datetime <= endDatetime;
                         datetime = datetime.add(1, "days")) {
                        const dt = datetime.format("YYYYMMDD");
                        datetimes.push(dt);
                        if (!(dt in stdPm10Bucket)) stdPm10Bucket[dt] = [];
                        if (!(dt in stdPm25Bucket)) stdPm25Bucket[dt] = [];
                        if (!(dt in compPm10Bucket)) compPm10Bucket[dt] = [];
                        if (!(dt in compPm25Bucket)) compPm25Bucket[dt] = [];
                    }

                    this.data.forEach(obj => {
                        const dt = obj.datetime.substr(0, 8);
                        stdPm10Bucket[dt].push(obj.stdPm10);
                        stdPm25Bucket[dt].push(obj.stdPm25);
                        compPm10Bucket[dt].push(obj.compPm10);
                        compPm25Bucket[dt].push(obj.compPm25);
                    })

                    datetimes.forEach(key => {
                        if (stdPm10Bucket[key].length === 0) stdPm10Bucket[key] = [0, 0];
                        if (stdPm25Bucket[key].length === 0) stdPm25Bucket[key] = [0, 0];
                        if (compPm10Bucket[key].length === 0) compPm10Bucket[key] = [0, 0];
                        if (compPm25Bucket[key].length === 0) compPm25Bucket[key] = [0, 0];
                    });

                    let result = [[], [], [], []];
                    Object.keys(stdPm10Bucket).map(date => {
                        result[0].push(stdPm10Bucket[date].reduce((acc, cur) => acc + cur) / 24);
                        result[1].push(stdPm25Bucket[date].reduce((acc, cur) => acc + cur) / 24);
                        result[2].push(compPm10Bucket[date].reduce((acc, cur) => acc + cur) / 24);
                        result[3].push(compPm25Bucket[date].reduce((acc, cur) => acc + cur) / 24);
                    });
                    [stdPm10, stdPm25, compPm10, compPm25] = result;
                }

                return {
                    stdPm10,
                    stdPm25,
                    compPm10,
                    compPm25
                };
            },

            correlationData() {
                if (this.selectedItem.value == "pm10")
                    return this.data.map(obj => [obj.compPm10, obj.stdPm10]);
                else return this.data.map(obj => [obj.compPm25, obj.stdPm25]);
            },

            corrPm10() {
                return this.corr("pm10");
            },

            corrPm25() {
                return this.corr("pm25");
            }
        },
        watch: {
            selectedItem() {
                if (this.data && this.data.length > 0) {
                    this.reInitTimeseriesChart();
                    this.reInitCorrelationChart();
                }
            },

            selectedDateType() {
                if (this.data && this.data.length > 0)
                    this.reInitTimeseriesChart();
            },

            data() {
                if (this.data && this.data.length > 0) {
                    this.reInitTimeseriesChart();
                    this.reInitCorrelationChart();
                }
            }
        },
        methods: {
            ...mapActions({
                addResizeEvent: "ADD_RESIZE_EVENT",
                removeResizeEvent: "REMOVE_RESIZE_EVENT"
            }),

            reInitTimeseriesChart() {
                if (this.data && this.data.length > 0)
                    setTimeout(this.initTimeseriesChart, 0);
            },

            reInitCorrelationChart() {
                if (this.data && this.data.length > 0)
                    setTimeout(this.initCorrelationChart, 0);
            },

            initTimeseriesChart() {
                if (this.timeseriesChart != null) this.timeseriesChart.clear();
                else this.timeseriesChart = init(this.$refs["timeseries"]);

                this.timeseriesChart.setOption({
                    tooltip: {
                        formatter: data => `
                            <strong style="font-size: 16px; margin-bottom: 5px;">${data.componentIndex === 0 ? "기준" : "비교"} 지점</strong><br />
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
                            type: "line",
                            data: this.selectedItem.value == "pm10" ? this.timeseriesData.stdPm10 : this.timeseriesData.stdPm25,
                            itemStyle: {
                                color: "#2279b5"
                            },
                            showSymbol: true
                        },
                        {
                            name: this.data.length > 0 ? this.data[0].compStnNm : "",
                            type: "line",
                            data: this.selectedItem.value == "pm10" ? this.timeseriesData.compPm10 : this.timeseriesData.compPm25,
                            itemStyle: {
                                color: "#ff7e0d"
                            },
                            showSymbol: true
                        }
                    ]
                });

                this.addResizeEvent({
                    name: "resizeVrfyTimeseriesChart",
                    callback: () => this.timeseriesChart.resize()
                });
            },

            initCorrelationChart() {
                if (this.correlationChart != null) this.correlationChart.clear();
                else this.correlationChart = init(this.$refs["correlation"]);

                registerTransform(transform.regression);
                this.correlationChart?.setOption({
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
                                color: "#5b9bd6"
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
                                color: "#5c5c5c"
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

                this.addResizeEvent({
                    name: "resizeVrfyCorrelationChart",
                    callback: () => this.correlationChart.resize()
                });
            },

            /**
             * @param itemCategory: "pm10" | "pm25"
             * */
            corr(itemCategory) {
                itemCategory = itemCategory.split("");
                itemCategory[0] = itemCategory[0].toUpperCase();
                itemCategory = itemCategory.join("");

                const n = this.data.length;
                let avgX = 0, avgY = 0;

                this.data.forEach(obj => {
                    avgX += obj["comp" + itemCategory];
                    avgY += obj["std" + itemCategory];
                });
                avgX /= n;
                avgY /= n;

                let cov = 0, std = 0;
                let sumOfSubXAvgXSqure = 0, sumOfSubYAvgYSqure = 0;
                this.data.forEach(obj => {
                    const subXAvgX = obj["comp" + itemCategory] - avgX;
                    const subYAvgY = obj["std" + itemCategory] - avgY;
                    cov += subXAvgX * subYAvgY;
                    sumOfSubXAvgXSqure += subXAvgX ** 2;
                    sumOfSubYAvgYSqure += subYAvgY ** 2;
                });
                std = Math.sqrt(sumOfSubXAvgXSqure * sumOfSubYAvgYSqure);

                return cov / std;
            }
        },
        destroyed() {
            this.removeResizeEvent("resizeVrfyTimeseriesChart");
            this.removeResizeEvent("resizeVrfyCorrelationChart");
        }
    }
</script>

<style lang="scss">
    @import "./ContentChart";
</style>
