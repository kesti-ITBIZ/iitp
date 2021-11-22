<template>
    <td id="content-chart">
        <div v-show="selectedChartType != 'table'" id="chart" ref="chart" />
        <div v-show="selectedChartType == 'table' && dataValues.length > 0" id="table" class="scroll">
            <table>
                <thead>
                    <tr>
                        <th>측정 시간</th>
                        <th :key="i" v-for="(y, i) in yAxis">{{ y.label }}</th>
                    </tr>
                </thead>
                <tbody>
                    <tr :key="i" v-for="(d, i) in dataValues[0]">
                        <td>{{ xAxisLabels[i] }}</td>
                        <td :key="j" v-for="(y, j) in yAxis">
                            {{ Math.round(+dataValues[j][i] * 100) / 100 }}{{ y.unit }}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </td>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    import { init, registerMap } from "echarts";

    export default {
        name: "ContentChart",
        data: () => ({
            chart: null,
            title: ""
        }),
        computed: {
            ...mapState({
                selectedChartType: state => state.selectedChartType,
                xAxis: state => state[state.selectedCategory].xAxis,
                yAxis: state => state[state.selectedCategory].yAxis,
                startDatetime: state => state.startDatetime,
                endDatetime: state => state.endDatetime,
                dateTypes: state => state.dateTypes,
                selectedDateType: state => state.selectedDateType,
                selectedCategory: state => state.selectedCategory,
                data: state => state.data
            }),

            dataValues() {
                if (this.data == null) {
                    return this.yAxis.map(() => {
                        const _data = [];
                        for (let j = 0, value = Math.random();
                             j < Math.abs(this.startDatetime.diff(this.endDatetime, this.selectedDateType == "date" ? "day" : this.selectedDateType) - 1);
                             ++j, value += 0.1 * Math.random() * (Math.random() < 0.5 ? -1 : 1)) {
                            _data.push(Math.abs(value) * 100);
                        }
                        return _data;
                    });
                } else return this.yAxis.map(obj => this.data[this.selectedCategory].map(_obj => _obj[obj.value]));
            },

            xAxisLabels() {
                if (this.data == null) {
                    const _data = [];
                    const format = this.dateTypes[this.dateTypes.findIndex(obj => obj.type == this.selectedDateType)].dayjsToStringFormat;
                    for (let datetime = this.startDatetime;
                         datetime <= this.endDatetime;
                         datetime = datetime.add(1, (this.selectedDateType == "date" ? "day" : this.selectedDateType) + "s"))
                        _data.push(datetime.format(format));
                    return _data;
                } else return this.data[this.selectedCategory].map(obj => obj.datetime);
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
            },

            startDatetime() {
                this.initChart();
            },

            endDatetime() {
                this.initChart();
            },

            selectedDateType() {
                this.initChart();
            },

            data() {
                console.log(this.data);
                this.initChart();
            }
        },
        methods: {
            ...mapActions({
                addResizeEvent: "ADD_RESIZE_EVENT",
                clearResizeEvent: "CLEAR_RESIZE_EVENT"
            }),

            async initChart() {
                if (this.chart) this.chart.clear();
                else this.chart = init(this.$refs["chart"]);
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
                        title: {
                            text: this.data[this.selectedCategory] && this.data[this.selectedCategory].length > 0 ? this.data[this.selectedCategory][0].stnNm : "",
                            left: 50
                        },
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
                        legend: {
                            data: this.yAxis.map(obj => obj.label)
                        },
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
                                formatter: `{value}${this.yAxis.length === 1 ? this.yAxis[0].unit : ""}`
                            },
                            splitLine: {
                                show: this.selectedChartType != "pie"
                            }
                        },
                        series: this.yAxis.map((obj, i) => {
                            switch (this.selectedChartType) {
                            case "pie":
                                return {
                                    name: obj.label,
                                    data: this.dataValues[i],
                                    type: "pie",
                                    showSymbol: false
                                };
                            case "line":
                                return {
                                    name: obj.label,
                                    data: this.dataValues[i],
                                    type: "line",
                                    showSymbol: false
                                };
                            case "bar":
                                return {
                                    name: obj.label,
                                    data: this.dataValues[i],
                                    type: "bar",
                                    showSymbol: false
                                };
                            case "area":
                                return {
                                    name: obj.label,
                                    data: this.dataValues[i],
                                    type: "line",
                                    showSymbol: false,
                                    areaStyle: {}
                                };
                            case "scatter":
                                return {
                                    name: obj.label,
                                    data: this.dataValues[i],
                                    type: "scatter",
                                    showSymbol: false
                                };
                            }
                        })
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

<style lang="scss">
    @import "./ContentChart.scss";
</style>
