<template>
    <td id="content-chart">
        <div v-show="selectedChartType != 'table'" id="chart" ref="chart" />
        <div v-show="selectedChartType == 'table'" id="table" class="scroll">
            <table>
                <thead>
                    <tr>
                        <th>{{ xAxis.length === 0 ? "" : xAxis[0].label }}</th>
                        <th :key="i" v-for="(y, i) in yAxis">{{ y.label }}</th>
                    </tr>
                </thead>
                <tbody>
                    <tr :key="i" v-for="(d, i) in data">
                        <td>{{ xAxisLabels[i] }}</td>
                        <td :key="j" v-for="(y, j) in yAxis">
                            {{ Math.round(+data[i] * 100) / 100 }}{{ yAxisSuffix }}
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

            title: "",
            data: (() => {
                const _data = [];
                for (let i = 0, value = Math.random(); i < 100; ++i, value += (Math.round(Math.random() * 100) % 3 === 0 ? 1 : -1) * (Math.random() / 10))
                    _data.push(value * 100);
                return _data;
            })(),
            xAxisLabels: (() => {
                const _data = [];
                for (let i = 0; i < 100; ++i)
                    _data.push("abc");
                return _data;
            })(),
            yAxisSuffix: "℃"
        }),
        computed: {
            ...mapState({
                selectedChartType: state => state.selectedChartType,
                xAxis: state => state.xAxis,
                yAxis: state => state.yAxis
            })
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

                    // const data = [
                    //     { name: "강원도", value: Math.round(Math.random() * 10000) },
                    //     { name: "경기도", value: Math.round(Math.random() * 10000) },
                    //     { name: "경상남도", value: Math.round(Math.random() * 10000) },
                    //     { name: "경상북도", value: Math.round(Math.random() * 10000) },
                    //     { name: "광주광역시", value: Math.round(Math.random() * 10000) },
                    //     { name: "대구광역시", value: Math.round(Math.random() * 10000) },
                    //     { name: "대전광역시", value: Math.round(Math.random() * 10000) },
                    //     { name: "부산광역시", value: Math.round(Math.random() * 10000) },
                    //     { name: "서울특별시", value: Math.round(Math.random() * 10000) },
                    //     { name: "세종특별자치시", value: Math.round(Math.random() * 10000) },
                    //     { name: "울산광역시", value: Math.round(Math.random() * 10000) },
                    //     { name: "인천광역시", value: Math.round(Math.random() * 10000) },
                    //     { name: "전라남도", value: Math.round(Math.random() * 10000) },
                    //     { name: "전라북도", value: Math.round(Math.random() * 10000) },
                    //     { name: "제주특별자치도", value: Math.round(Math.random() * 10000) },
                    //     { name: "충청남도", value: Math.round(Math.random() * 10000) },
                    //     { name: "충청북도", value: Math.round(Math.random() * 10000) },
                    // ];

                    let data = require("../../../../assets/json/data.json").map(obj => obj.name.split("|").map(val => +val).concat([1]));
                    console.log(data);

                    let mapOption = {
                        animation: false,
                        bmap: {
                            center: [30.240018034923, 120.13066322374],
                            zoom: 14,
                            roam: true
                        },
                        visualMap: {
                            left: "right",
                            min: 0,
                            max: 5,
                            seriesIndex: 1,
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
                                id: "distribution",
                                type: "map",
                                roam: true,
                                map: "KOREA",
                                animationDurationUpdate: 100,
                                universalTransition: true,
                                data
                            },
                            {
                                id: "dist-heatmap",
                                type: "heatmap",
                                coordinateSystem: 'bmap',
                                data,
                                pointSize: 20,
                                blurSize: 6
                            }
                        ]
                    };

                    // var barOption = {
                    //     xAxis: {
                    //         type: "value"
                    //     },
                    //     yAxis: {
                    //         type: "category",
                    //         axisLabel: {
                    //             rotate: 30
                    //         },
                    //         data: data.map(item => item.name)
                    //     },
                    //     animationDurationUpdate: 1000,
                    //     series: {
                    //         type: "bar",
                    //         id: "population",
                    //         data: data.map(item => item.value),
                    //         universalTransition: true
                    //     }
                    // };

                    this.chart.setOption(mapOption);
                    // var bmap = this.chart.getModel().getComponent('bmap').getBMap();
                    // eslint-disable-next-line no-undef
                    // bmap.addControl(new BMap.MapTypeControl());
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
                                formatter: `{value}${this.yAxisSuffix}`
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
                                        data: this.data,
                                        type: "pie",
                                        showSymbol: false
                                    };
                                case "line":
                                    return {
                                        data: this.data,
                                        type: "line",
                                        showSymbol: false
                                    };
                                case "bar":
                                    return {
                                        data: this.data,
                                        type: "bar",
                                        showSymbol: false
                                    };
                                case "area":
                                    return {
                                        data: this.data,
                                        type: "line",
                                        showSymbol: false,
                                        areaStyle: {}
                                    };
                                case "scatter":
                                    return {
                                        data: this.data,
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
