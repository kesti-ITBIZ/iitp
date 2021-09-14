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
        name: "ScatterChart",
        props: {
            id: String,
            title: String,
            data: Array,
            color: String,
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
                        formatter: "{c}"
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
                        splitLine: {
                            show: false
                        }
                    },
                    yAxis: {
                        splitLine: {
                            show: false
                        },
                        axisLabel: {
                            formatter: `{value}${this.yAxisSuffix}`
                        }
                    },
                    series: [
                        {
                            type: "scatter",
                            color: this.color,
                            symbolSize: 6,
                            data: this.data
                        },
                    ].concat(this.ranges.map(obj => ({
                        type: "scatter",
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
