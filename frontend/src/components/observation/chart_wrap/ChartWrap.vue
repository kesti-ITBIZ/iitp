<template>
    <div id="chart-wrap">
        <div class="chart-wrap-header">
            <select-chart />
        </div>
        <content-chart />
    </div>
</template>

<script>
    import SelectChart from "./select_chart/SelectChart";
    import ContentChart from "./chart/ContentChart";

    export default {
        name: "ChartWrap",
        components: {
            SelectChart,
            ContentChart
        }
    }
</script>

<style lang="scss">
    @import "./ChartWrap";
</style>
