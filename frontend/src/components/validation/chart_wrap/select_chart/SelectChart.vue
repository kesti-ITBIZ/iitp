<template>
    <div id="select-chart">
        <div>
            <div :key="i"
                 v-for="(chartType, i) in ['Line', 'Bar', 'Area', 'Scatter']"
                 :class="['select-chart-btn', selectedChartType == chartType.toLowerCase() ? 'on' : '']"
                 @click="setSelectedChartType(chartType.toLowerCase())">
                <div>
                    <font-awesome-icon :size="`${windowWidth >= reactiveMaxWidth + 1 ? 3 : 2}x`" :icon="['fa', 'chart-' + chartType.toLowerCase()]" />
                    <div>{{ chartType + (windowWidth >= reactiveMaxWidth + 1 ? " Chart" : "") }}</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    // abcabc
    export default {
        name: "SelectChart",
        computed: {
            ...mapState({
                reactiveMaxWidth: state => state.common.reactiveMaxWidth,
                windowWidth: state => state.common.windowWidth,
                selectedChartType: state => state.statistics.selectedChartType,
            })
        },
        methods: {
            ...mapActions({
                setSelectedChartType: "SET_STATISTICS_SELECTED_CHART_TYPE"
            })
        }
    }
</script>

<style lang="scss">
    @import "./SelectChart";
</style>
