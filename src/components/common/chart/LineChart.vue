<template>
    <div :ref="id"></div>
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
            yAxisSuffix: String,
            ranges: Array
        },
        data: () => ({
            chart: null
        }),
        watch: {
            async data() {
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
                    ].concat(this.ranges.map(obj => ({
                        type: "line",
                        color: obj.color,
                        markArea: {
                            data: [[
                                { xAxis: obj.data[0] },
                                { xAxis: obj.data[1] },
                            ]]
                        }
                    })))
                });
                await this.addResizeEvent(() => this.chart.resize());
            }
        },
        methods: mapActions({
            addResizeEvent: "ADD_RESIZE_EVENT",
            clearResizeEvent: "CLEAR_RESIZE_EVENT"
        }),
        async destroyed() {
            await this.clearResizeEvent();
        }
    }
</script>
