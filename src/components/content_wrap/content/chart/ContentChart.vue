<template>
    <td id="content-chart">
        <div v-show="selectedChartType != 'table'" id="chart" ref="chart" />
        <div v-show="selectedChartType == 'table'" id="table" class="scroll">
            <table>
                <thead>
                    <tr>
                        <th>측정 시간</th>
                        <th :key="i" v-for="(y, i) in yAxis">{{ y.label }}</th>
                    </tr>
                </thead>
                <tbody>
                    <tr :key="i" v-for="(d, i) in dataValues">
                        <td>{{ xAxisLabels[i] }}</td>
                        <td :key="j" v-for="(y, j) in yAxis">
                            {{ Math.round(+dataValues[i] * 100) / 100 }}{{ y.unit }}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </td>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import dayjs from "dayjs";

    import { init, registerMap } from "echarts";

    export default {
        name: "ContentChart",
        data: () => ({
            chart: null,

            title: "",

        }),
        computed: {
            ...mapState({
                selectedChartType: state => state.selectedChartType,
                xAxis: state => state.xAxis,
                yAxis: state => state.yAxis,
                startDatetime: state => state.startDatetime,
                endDatetime: state => state.endDatetime,
                selectedDateType: state => state.selectedDateType
            }),

            format() {
                switch (this.selectedDateType) {
                case "hour":
                    return "YYYY.MM.DD HH:00";
                case "date":
                    return "YYYY.MM.DD";
                case "month":
                    return "YYYY.MM";
                case "year":
                    return "YYYY";
                }
                return null;
            },

            dataValues() {
                let format = this.format;
                const _data = [];
                for (let i = 0, value = Math.random();
                     i < Math.abs(dayjs(this.startDatetime + ":00", format).diff(dayjs(this.endDatetime + ":00", format), "hour")) + 1;
                     ++i, value += 0.1 * Math.random() * (Math.random() < 0.5 ? -1 : 1))
                    _data.push(Math.abs(value) * 100);
                return _data;
            },

            xAxisLabels() {
                const _data = [];
                let format = this.format;

                for (let date = dayjs(this.startDatetime + ":00", format);
                     date <= dayjs(this.endDatetime + ":00", format);
                     date = date.add(1, this.selectedDateType)) {
                    _data.push(date.format(format))
                }
                return _data;
            }
        },
        watch: {
            selectedChartType() {
                this.initChart();
            },

            xAxis() {
                this.initChart();
            },

            yAxis() {
                this.initChart();
            }
        },
        methods: {
            ...mapActions({
                addResizeEvent: "ADD_RESIZE_EVENT",
                clearResizeEvent: "CLEAR_RESIZE_EVENT"
            }),

            async initChart() {
                if (this.chart) {
                    this.chart.clear();
                    this.chart = null;
                }
                this.chart = init(this.$refs["chart"]);
                if (this.selectedChartType == "distribution") {
                    registerMap("KOREA", require("../../../../assets/json/korea.json"));

                    let data = require("../../../../assets/json/data.json").map(obj => obj.name.split("|").map(val => +val).concat([1]));

                    this.chart.setOption({
                        geo: {
                            map: "KOREA",
                            roam: true
                        },
                        visualMap: {
                            left: "right",
                            min: 0,
                            max: 10,
                            seriesIndex: 0,
                            calculable: true,
                            inRange: {
                                color: ["purple", "blue", "green", "yellow", "orange", "red"]
                            }
                        },
                        xAxis: {
                            show: false,
                            type: "category"
                        },
                        yAxis: {
                            show: false,
                            type: "category"
                        },
                        series: [
                            {
                                id: "dist-heatmap",
                                type: "heatmap",
                                geoIndex: 0,
                                coordinateSystem: "geo",
                                data,
                                pointSize: 5,
                                blurSize: 6
                            }
                        ]
                    });
                } else
                    this.chart.setOption({
                        grid: {
                            // height: 500
                        },
                        tooltip: {
                            formatter: "{a} {b} {c}"
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
                        xAxis: {
                            type: "category",
                            name: this.xAxis.length === 0 ? "" : this.xAxis[0].label,
                            data: this.xAxisLabels,
                            splitLine: {
                                show: this.selectedChartType != "pie"
                            }
                        },
                        yAxis: {
                            type: "value",
                            name: this.yAxis.length === 0 ? "" : this.yAxis.map(y => y.label).join(", "),
                            nameTextStyle: {
                                align: "left"
                            },
                            axisLabel: {
                                formatter: `{value}`
                            },
                            splitLine: {
                                show: this.selectedChartType != "pie"
                            }
                        },
                        series: [
                            (() => {
                                switch (this.selectedChartType) {
                                case "pie":
                                    return {
                                        data: this.dataValues,
                                        type: "pie",
                                        showSymbol: false
                                    };
                                case "line":
                                    return {
                                        data: this.dataValues,
                                        type: "line",
                                        showSymbol: false
                                    };
                                case "bar":
                                    return {
                                        data: this.dataValues,
                                        type: "bar",
                                        showSymbol: false
                                    };
                                case "area":
                                    return {
                                        data: this.dataValues,
                                        type: "line",
                                        showSymbol: false,
                                        areaStyle: {}
                                    };
                                case "scatter":
                                    return {
                                        data: this.dataValues,
                                        type: "scatter",
                                        showSymbol: false
                                    };
                                }
                            })()
                        ]
                    });
                await this.addResizeEvent(() => this.chart.resize());
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

<style>
    @import "./ContentChart.css";
</style>
