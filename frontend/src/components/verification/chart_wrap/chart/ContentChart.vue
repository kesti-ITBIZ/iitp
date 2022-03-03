<template>
    <div class="container-wrap chart">
        <div v-show="data && data.length > 0 && windowWidth >= reactiveMaxWidth + 1" class="container chart">
            <div class="correlation">
                <h3>비교분석 차트 - {{ selectedItem.label }} (단위: {{ selectedItem.unit }}) </h3>
                <div>
                    <div class="info">
                        <div>
                            <h4>비교 지점</h4>
                            <h3 v-if="data && data.length > 0">{{ data[0].compStnNm }}</h3>
                        </div>
                        <div>
                            <h4>기준 지점</h4>
                            <h3 v-if="data && data.length > 0">{{ data[0].stdStnNm }}</h3>
                        </div>
                        <div>
                            <h4>비교분석 결과</h4>
                            <h3 v-if="data && data.length > 0" class="formula">
                                Y = {{ Math.round(gradient * 10000) / 10000 }}X {{ intercept >= 0 ? `+ ${Math.round(intercept * 10000) / 10000}` : `- ${-Math.round(intercept * 10000) / 10000}` }}<br />
                                R² = {{ (Math.round(corr ** 2 * 10000) / 10000).toFixed(4) }}
                            </h3>
                            <div>
                                <input type="checkbox" id="show-formula" @click="e => showFomula = e.target.checked" :checked="showFomula" />
                                <label for="show-formula">차트 수식 표시</label>
                            </div>
                        </div>
                    </div>
                    <div ref="correlation" class="chart-content"></div>
                </div>
            </div>
            <div class="timeseries">
                <h3>시계열 차트</h3>
                <div ref="timeseries"></div>
            </div>
        </div>
        <div v-show="data && data.length > 0 && windowWidth < reactiveMaxWidth + 1" class="container chart">
            <div class="info">
                <div>
                    <h4>비교 지점</h4>
                    <h3 v-if="data && data.length > 0">{{ data[0].compStnNm }}</h3>
                </div>
                <div>
                    <h4>기준 지점</h4>
                    <h3 v-if="data && data.length > 0">{{ data[0].stdStnNm }}</h3>
                </div>
                <div>
                    <h4>비교분석 결과</h4>
                    <h3 v-if="data && data.length > 0" class="formula">
                        Y = {{ Math.round(gradient * 10000) / 10000 }}X {{ intercept >= 0 ? `+ ${Math.round(intercept * 10000) / 10000}` : `- ${-Math.round(intercept * 10000) / 10000}` }}<br />
                        R² = {{ (Math.round(corr ** 2 * 10000) / 10000).toFixed(4) }}
                    </h3>
                    <div>
                        <input type="checkbox" id="show-formula-mobile" @click="e => showFomula = e.target.checked" :checked="showFomula" />
                        <label for="show-formula-mobile">차트 수식 표시</label>
                    </div>
                </div>
            </div>
            <div class="correlation">
                <h3>비교분석 차트 - {{ selectedItem.label }} (단위: {{ selectedItem.unit }}) </h3>
                <div ref="correlation-mobile" class="chart-content"></div>
            </div>
            <div class="timeseries">
                <h3>시계열 차트</h3>
                <div ref="timeseries-mobile"></div>
            </div>
        </div>
        <div v-show="!data || data.length === 0" id="no-data" class="container">
            <div>
                <h1>분석할 지점을 선택하세요.</h1>
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
            showFomula: true,
            timeseriesChart: null,
            correlationChart: null,
            timeseriesChartMobile: null,
            correlationChartMobile: null
        }),
        computed: {
            ...mapState({
                windowWidth: state => state.common.windowWidth,
                reactiveMaxWidth: state => state.common.reactiveMaxWidth,
                selectedStandardStation: state => state.verification.selectedStandardStation,
                selectedCompareStation: state => state.verification.selectedCompareStation,
                selectedDateType: state => state.verification.selectedDateType,
                selectedItem: state => state.verification.selectedItem,
                fetchedStartDatetime: state => state.verification.fetchedStartDatetime,
                fetchedEndDatetime: state => state.verification.fetchedEndDatetime,
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
                if (this.selectedDateType == "hour") {
                    return this.selectedItem.value == "pm10" ?
                        this.data.filter(obj => obj.compPm10 != null && obj.stdPm10 != null).map(obj => [obj.compPm10, obj.stdPm10]) :
                        this.data.filter(obj => obj.compPm25 != null && obj.stdPm25 != null).map(obj => [obj.compPm25, obj.stdPm25]);
                } else {
                    const valueType = this.selectedItem.value == "pm10" ? "Pm10" : "Pm25";

                    const bucket = [];
                    this.data.forEach(obj => {
                        const date = obj.datetime.substr(0, 8);
                        if (!(date in bucket)) bucket[date] = [];
                        bucket[date].push([
                            obj["comp" + valueType] == null ? 0 : obj["comp" + valueType],
                            obj["std" + valueType] == null ? 0 : obj["std" + valueType]
                        ]);
                    });

                    const data = [];
                    Object.keys(bucket).forEach(date => {
                        let arr = [0, 0];
                        bucket[date].forEach(values => {
                            arr[0] += values[0];
                            arr[1] += values[1];
                        });
                        arr[0] = Math.round(arr[0] / 24);
                        arr[1] = Math.round(arr[1] / 24);
                        if (arr[0] > 0 && arr[1] > 0) data.push(arr);
                    });

                    return data;
                }
            },

            // 비교지점 데이터 평균
            avgX() {
                return this.correlationData.reduce((acc, cur) => (typeof acc == "object" ? acc[1] : acc) + cur[1]) / this.correlationData.length;
            },

            // 기준지점 데이터 평균
            avgY() {
                return this.correlationData.reduce((acc, cur) => (typeof acc == "object" ? acc[0] : acc) + cur[0]) / this.correlationData.length;
            },

            // 상관계수 R값
            corr() {
                const n = this.correlationData.length;
                const avgX = this.avgX;
                const avgY = this.avgY;

                let a = 0, b = 0, c = 0;
                for (let i = 0; i < n; ++i) {
                    const subXAvgX = this.correlationData[i][1] - avgX;
                    const subYAvgY = this.correlationData[i][0] - avgY;
                    a += subXAvgX * subYAvgY;
                    b += subXAvgX ** 2;
                    c += subYAvgY ** 2;
                }

                return a / Math.sqrt(b * c);
            },

            // 기울기
            gradient() {
                const n = this.correlationData.length;
                const avgX = this.avgX;
                const avgY = this.avgY;

                let a = 0, b = 0;
                for (let i = 0; i < n; ++i) {
                    const subXAvgX = this.correlationData[i][1] - avgX;
                    const subYAvgY = this.correlationData[i][0] - avgY;
                    a += subXAvgX * subYAvgY;
                    b += subYAvgY ** 2;
                }

                return a / b;
            },

            // Y절편
            intercept() {
                return this.avgX - this.gradient * this.avgY;
            }
        },
        watch: {
            selectedItem() {
                if (this.data && this.data.length > 0) {
                    this.reInitTimeseriesChart();
                    this.reInitCorrelationChart();
                }
            },

            data() {
                if (this.data && this.data.length > 0) {
                    this.reInitTimeseriesChart();
                    this.reInitCorrelationChart();
                }
            },

            showFomula() {
                if (this.data && this.data.length > 0)
                    this.reInitCorrelationChart();
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

            initCorrelationChart() {
                if (this.correlationChart != null && this.correlationChartMobile != null) {
                    this.correlationChart.clear();
                    this.correlationChartMobile.clear();
                }
                else {
                    this.correlationChart = init(this.$refs["correlation"]);
                    this.correlationChartMobile = init(this.$refs["correlation-mobile"]);
                }

                registerTransform(transform.regression);

                const getOption = isMobile => {
                    return {
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
                                    type: "png",
                                    name: (() => {
                                        const format = "YYYYMMDD" + (this.selectedDateType == "hour" ? "HH" : "");
                                        return [
                                            "상관분석차트",
                                            this.data[0].stdStnNm,
                                            this.data[0].compStnNm,
                                            this.selectedItem.label,
                                            this.fetchedStartDatetime.format(format),
                                            this.fetchedEndDatetime.format(format)
                                        ].join("_");
                                    })()
                                }
                            },
                            right: 40
                        },
                        grid: {
                            top: 70,
                            right: 120,
                            bottom: 20
                        },
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
                                    show: this.showFomula,
                                    fontSize: 16,
                                    fontFamily: "NanumSquare",
                                    fontWeight: "bold",
                                    formatter: data => data.value[2] !== "" ? `Y = ${Math.round(this.gradient * 10000) / 10000}X ${this.intercept < 0 ? "-" : "+"} ${Math.abs(Math.round(this.intercept * 10000) / 10000)}    R² = ${Math.round(this.corr ** 2 * 10000) / 10000}` : ""
                                },
                                itemStyle: {
                                    color: "#5c5c5c"
                                },
                                labelLayout: {
                                    x: isMobile ? "5%" : "35%",
                                    y: "85%",
                                },
                                encode: {
                                    label: 2,
                                    tooltip: 1
                                }
                            }
                        ]
                    };
                }

                this.correlationChart?.setOption(getOption(false));
                this.correlationChartMobile?.setOption(getOption(true));

                this.addResizeEvent({
                    name: "resizeVrfyCorrelationChart",
                    callback: () => {
                        this.correlationChart.resize();
                        this.correlationChartMobile.resize();
                    }
                });
            },

            initTimeseriesChart() {
                if (this.timeseriesChart != null && this.timeseriesChartMobile != null) {
                    this.timeseriesChart.clear();
                    this.timeseriesChartMobile.clear();
                }
                else {
                    this.timeseriesChart = init(this.$refs["timeseries"]);
                    this.timeseriesChartMobile = init(this.$refs["timeseries-mobile"]);
                }

                const option = {
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
                                type: "png",
                                name: `시계열차트_${this.data[0].stdStnNm}_${this.data[0].compStnNm}_${this.selectedItem.label}_${this.fetchedStartDatetime.format("YYYYMMDD" + (this.selectedDateType == "hour" ? "HH" : ""))}_${this.fetchedEndDatetime.format("YYYYMMDD" + (this.selectedDateType == "hour" ? "HH" : ""))}`
                            }
                        },
                        right: 40
                    },
                    dataZoom: [
                        {
                            type: "slider"
                        }
                    ],
                    grid: {
                        right: 80
                    },
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
                };

                this.timeseriesChart.setOption(option);
                this.timeseriesChartMobile.setOption(option);

                this.addResizeEvent({
                    name: "resizeVrfyTimeseriesChart",
                    callback: () => {
                        this.timeseriesChart.resize();
                        this.timeseriesChartMobile.resize();
                    }
                });
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
