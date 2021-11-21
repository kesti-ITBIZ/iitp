<template>
    <th id="select-chart">
        <div class="scroll">
            <div :class="['select-chart-btn', selectedChartType == 'table' ? 'on' : '']"
                 @click="setSelectedChartType('table')">
                <div>
                    <font-awesome-icon size="5x" :icon="['fa', 'table']" />
                    <div>Table</div>
                </div>
            </div>
            <div :key="i"
                 v-for="(chartType, i) in ['Line', 'Bar', 'Area', 'Scatter']"
                 :class="['select-chart-btn', selectedChartType == chartType.toLowerCase() ? 'on' : '']"
                 @click="setSelectedChartType(chartType.toLowerCase())">
                <div>
                    <font-awesome-icon size="5x" :icon="['fa', 'chart-' + chartType.toLowerCase()]" />
                    <div>{{ chartType }} Chart</div>
                </div>
            </div>
            <div :class="['select-chart-btn', selectedChartType == 'distribution' ? 'on' : '']"
                 @click="setSelectedChartType('distribution')">
                <div>
                    <font-awesome-icon size="4x" :icon="['fa', 'chart-network']" style="margin: 0 0 0 5px;" />
                    <div>Distribution</div>
                </div>
            </div>
        </div>
    </th>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    export default {
        name: "SelectChart",
        computed: {
            ...mapState({
                selectedChartType: state => state.selectedChartType
            })
        },
        methods: {
            ...mapActions({
                setSelectedChartType: "SET_SELECTED_CHART_TYPE"
            })
        }
    }
</script>

<style lang="scss">
    @import "./SelectChart.scss";
</style>
