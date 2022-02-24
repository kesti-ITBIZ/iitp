<template>
    <div id="chart-wrap">
        <content-chart />
    </div>
</template>

<script>
    import ContentChart from "./chart/ContentChart";

    export default {
        name: "ChartWrap",
        components: {
            ContentChart
        }
    }
</script>

<style lang="scss">
    @import "./ChartWrap";
</style>
