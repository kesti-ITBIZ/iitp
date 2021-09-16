<template>
    <div id="line-chart" :ref="id"></div>
</template>

<script>
    /**
     * Vuex
     * */
    import { mapActions } from  "vuex";

    /**
     * Modules
     * */
    import { init } from "echarts";

    export default {
        name: "LineChart",
        props: {
            id: String,
            title: String,
            data: Array,
            color: String,
            xAxisLabels: Array,
            yAxisSuffix: String
        },
        data: () => ({
            chart: null
        }),
        watch: {
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
                if (this.chart) this.chart.clear();
                this.chart = init(this.$refs[this.id]);
                this.chart.setOption({
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
                        data: this.xAxisLabels,
                        splitLine: {
                            show: false
                        }
                    },
                    yAxis: {
                        type: "value",
                        splitLine: {
                            show: false
                        },
                        axisLabel: {
                            formatter: `{value}${this.yAxisSuffix}`
                        }
                    },
                    series: [
                        {
                            data: this.data,
                            type: "line",
                            color: this.color,
                            showSymbol: false
                        }
                    ]
                });
                await this.addResizeEvent(() => this.chart.resize());
            }
        },
        mounted() {
            this.initChart();
        },
        async destroyed() {
            await this.clearResizeEvent();
        }
    }
</script>

<style>
    #line-chart {
        width: 100%;
        height: 100%;
        /*display: block;*/
    }
</style>
