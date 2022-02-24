<template>
    <div class="content-chart">
        <div v-show="data && data.length > 0">
            <div class="avg" ref="avg"></div>
            <div class="good" ref="good"></div>
            <div class="bad" ref="bad"></div>
            <div class="high" ref="high"></div>
        </div>
        <div v-show="(!data || data.length === 0) && !loading" id="no-data">
            <div>
                <h1>데이터가 없습니다.</h1>
            </div>
        </div>
        <loading />
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import dayjs from "dayjs";
    import { init } from "echarts";
    import customParseFormat from "dayjs/plugin/customParseFormat";

    dayjs.extend(customParseFormat);

    import Loading from "../../../common/loading/Loading";

    export default {
        name: "ContentChart",
        components: {
            Loading
        },
        data: () => ({
            chart: {
                avg: null,
                good: null,
                bad: null,
                high: null
            },
            title: {
                avg: "평균농도",
                good: "좋음일수",
                bad: "나쁨일수",
                high: "고농도일수"
            },
            color: {
                avg: "#5e5e5e",
                good: "#1b9fe5",
                bad: "#ffba25",
                high: "#dc3e20"
            }
        }),
        computed: {
            ...mapState({
                loading: state => state.common.loading,
                selectedChartType: state => state.statistics.selectedChartType,
                startDatetime: state => state.statistics.startDatetime,
                endDatetime: state => state.statistics.endDatetime,
                dateTypes: state => state.statistics.dateTypes,
                selectedDateType: state => state.statistics.selectedDateType,
                selectedItem: state => state.statistics[state.statistics.selectedCategory].selectedItem,
                selectedCategory: state => state.statistics.selectedCategory,
                data: state => state.statistics.data[state.statistics.selectedCategory].filter(obj => obj.type === state.statistics.selectedDateType)
            }),

            xAxisLabels() {
                return this.data && this.data.length > 0 ?
                    this.data.map(obj => obj.datetime.format("YYYY" + (this.selectedDateType == "month" ? ".MM" : "")))
                    : [];
            }
        },
        watch: {
            selectedChartType() {
                this.reInitChart();
            },

            xAxis() {
                this.reInitChart();
            },

            yAxis() {
                this.reInitChart();
            },

            selectedItem() {
                this.reInitChart();
            },

            data() {
                this.reInitChart();
            }
        },
        methods: {
            ...mapActions({
                addResizeEvent: "ADD_RESIZE_EVENT",
                removeResizeEvent: "REMOVE_RESIZE_EVENT",
                setLoadingInvisible: "SET_LOADING_INVISIBLE"
            }),

            reInitChart() {
                if (this.data && this.data.length > 0)
                    setTimeout(this.initChart, 0);
            },

            initChart() {
                Object.keys(this.chart).forEach(key => {
                    if (this.chart[key] != null) this.chart[key].clear();
                    else this.chart = Object.freeze({ ...this.chart, [key]: init(this.$refs[key]) });
                    this.chart[key].setOption({
                        title: {
                            text: this.title[key],
                            left: 10,
                            textStyle: {
                                fontFamily: "NanumSquare",
                                fontWeight: "bold"
                            }
                        },
                        tooltip: {
                            formatter: data => {
                                let xLabel = data.name;
                                if (this.selectedDateType == "month") xLabel = dayjs(xLabel + ".01", "YYYY.MM").format("YYYY년 MM월");
                                else if (this.selectedDateType == "year") xLabel = dayjs(xLabel, "YYYY").format("YYYY년");

                                return `
                                    <h3 style="margin-bottom: 6px;">${data.seriesName}</h3>
                                    항목: ${this.selectedItem.label}<br />
                                    X: ${xLabel}<br />
                                    Y: ${Math.round((typeof data.value === "object" ? data.value[1] : data.value) * 10) / 10}`;
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
                            }
                        },
                        dataZoom: [
                            {
                                type: "slider"
                            }
                        ],
                        legend: {
                            data: [this.title[key]],
                            align: "right",
                            right: 50,
                            textStyle: {
                                fontFamily: "NanumSquare",
                                fontSize: 14
                            }
                        },
                        xAxis: {
                            type: "category",
                            name: "기간",
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
                            name: this.selectedItem.label,
                            min: 0,
                            max: (() => {
                                const factor = key === "avg" ? 100 : 15;
                                const maxValue = this.data.map(obj => {
                                    const item = obj[this.selectedItem.value];
                                    return item[Object.keys(item).filter(_key => _key.toLowerCase().indexOf(key.toLowerCase()) !== -1)[0]];
                                }).reduce((acc, cur) => Math.max(acc, cur));
                                return (Math.floor(maxValue / factor) + 1) * factor;
                            })(),
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
                        series: (() => {
                            switch (this.selectedChartType) {
                            case "line":
                                return {
                                    name: this.title[key],
                                    data: this.chartData(key),
                                    type: "line",
                                    showSymbol: true,
                                    itemStyle: {
                                        color: this.color[key]
                                    }
                                };
                            case "bar":
                                return {
                                    name: this.title[key],
                                    data: this.chartData(key),
                                    type: "bar",
                                    showSymbol: true,
                                    itemStyle: {
                                        color: this.color[key]
                                    }
                                };
                            case "area":
                                return {
                                    name: this.title[key],
                                    data: this.chartData(key),
                                    type: "line",
                                    showSymbol: true,
                                    itemStyle: {
                                        color: this.color[key]
                                    },
                                    areaStyle: {
                                        color: this.color[key]
                                    }
                                };
                            case "scatter":
                                return {
                                    name: this.title[key],
                                    data: this.chartData(key),
                                    type: "scatter",
                                    showSymbol: true,
                                    itemStyle: {
                                        color: this.color[key]
                                    }
                                };
                            }
                        })()
                    });
                    this.addResizeEvent({
                        name: "resizeStatisticsChart",
                        callback: () => this.chart[key].resize()
                    });
                });
            },

            chartData(key) {
                return this.data && this.data.length > 0 ?
                    this.data.map(obj => {
                        const item = obj[this.selectedItem.value];
                        return item[Object.keys(item).filter(_key => _key.toLowerCase().indexOf(key.toLowerCase()) !== -1)[0]];
                    }) : [];
            },

            round(value) {
                if (value == null) return null;
                else if (value === 0) return 0;
                let v = value, cnt = 0;
                while (Math.round(v) === 0) {
                    v *= 10;
                    ++cnt;
                }
                return Math.round(value * (10 ** (cnt + 2))) / (10 ** (cnt + 2));
            }
        },
        destroyed() {
            this.removeResizeEvent("resizeStatisticsChart");
        }
    }
</script>

<style lang="scss">
    @import "./ContentChart";
</style>
