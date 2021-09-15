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
        name: "LineAreaChart",
        props: {
            id: String,
            title: String,
            colors: Array,
            lower2: Array,
            lower1: Array,
            upper1: Array,
            upper2: Array,
            obsVal: Array,
            inferVal: Array,
            xAxisLabels: Array,
            yAxisSuffix: String,
            ranges: Array
        },
        data: () => ({
            chart: null
        }),
        watch: {
            async lower2() {
                const min = Math.min.apply(null, this.lower2.filter(value => value != null));
                const max = Math.max.apply(null, this.upper2.filter(value => value != null));

                if (this.chart) this.chart.clear();
                this.chart = init(this.$refs[this.id]);
                this.chart.setOption({
                    tooltip: {
                        trigger: "axis",
                        formatter: params => `
                                [${params[0].name}]<br />
                                ±95%: ${Math.round(params[0].value * 100) / 100} ~ ${Math.round((params[0].value + params[1].value + params[2].value + params[3].value) * 100) / 100}<br />
                                ±85%: ${Math.round((params[0].value + params[1].value) * 100) / 100} ~ ${Math.round((params[0].value + params[1].value + params[2].value) * 100) / 100}<br />
                                관측값: ${Math.round(params[4].value * 100) / 100}<br />
                                모델추정값: ${Math.round(params[5].value * 100) / 100}<br />`
                    },
                    toolbox: { feature: { saveAsImage: { type: "png" } } },
                    dataZoom: [{ type: "slider" }],
                    xAxis: {
                        type: "category",
                        boundaryGap: false,
                        splitLine: { show: false },
                        data: this.xAxisLabels
                    },
                    yAxis: {
                        splitLine: { show: false },
                        axisLabel: { formatter: `{value}${this.yAxisSuffix}` },
                        type: "value",
                        min: min - 1,
                        max: max + 1
                    },
                    series: [
                        {
                            z: -1,
                            name: "UPPER2",
                            type: "line",
                            lineStyle: { opacity: 0 },
                            areaStyle: {
                                color: this.colors[1],
                                origin: "start"
                            },
                            data: this.upper2,
                            symbol: "none"
                        }, {
                            z: -1,
                            name: "UPPER1",
                            type: "line",
                            lineStyle: { opacity: 0 },
                            areaStyle: {
                                color: this.colors[0],
                                origin: "start"
                            },
                            data: this.upper1,
                            symbol: "none"
                        }, {
                            z: -1,
                            name: "LOWER1",
                            type: "line",
                            lineStyle: { opacity: 0 },
                            areaStyle: {
                                color: this.colors[1],
                                origin: "start"
                            },
                            data: this.lower1,
                            symbol: "none"
                        }, {
                            z: -1,
                            name: "LOWER2",
                            type: "line",
                            lineStyle: { opacity: 0 },
                            areaStyle: {
                                color: "white",
                                origin: "start",
                                opacity: 1
                            },
                            data: this.lower2,
                            symbol: "none"
                        }, {
                            name: "obsVal",
                            type: "line",
                            data: this.obsVal,
                            itemStyle: { color: this.colors[2] },
                            symbol: "none"
                        }, {
                            name: "inferVal",
                            type: "line",
                            data: this.inferVal,
                            itemStyle: { color: this.colors[3] },
                            symbol: "none"
                        }
                    ].concat(this.ranges.map(obj => ({
                        type: "line",
                        color: obj.color,
                        markArea: {
                            data: [[
                                { xAxis: obj.data[0] },
                                { xAxis: obj.data[1] }
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
