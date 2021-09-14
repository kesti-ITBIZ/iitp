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
        name: "PieChart",
        props: {
            id: String,
            title: String,
            data: Array,
            colors: Array
        },
        data: () => ({
            chart: null
        }),
        watch: {
            async data() {
                if (this.chart) this.chart.clear();
                this.chart = init(this.$refs[this.id]);
                this.chart.setOption({
                    title: {
                        text: this.title,
                        left: "left"
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {
                                type: "png"
                            }
                        }
                    },
                    tooltip: {
                        formatter: "{b}: {c}"
                    },
                    color: this.colors,
                    series: [
                        {
                            type: "pie",
                            radius: "50%",
                            data: this.data,
                            label: {
                                formatter: "{c}",
                                position: "outside"
                            },
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: "rgba(0, 0, 0, 0.5)"
                                }
                            }
                        }
                    ]
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
