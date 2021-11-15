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
        name: "BarChart",
        props: {
            id: String,
            title: String,
            data: Array,
            colors: Array,
            xAxisLabels: Array,
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
                    tooltip: {
                        formatter: "{b} - {a}: {c}"
                    },
                    color: this.colors,
                    xAxis: [
                        {
                            type: "category",
                            data: this.xAxisLabels
                        }
                    ],
                    yAxis: [
                        {
                            type: "value"
                        }
                    ],
                    series: this.data.map(obj => ({
                        type: "bar",
                        barGap: 0,
                        name: obj.name,
                        data: obj.value
                    }))
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
