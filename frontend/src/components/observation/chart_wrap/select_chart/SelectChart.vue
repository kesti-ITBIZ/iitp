<template>
    <div id="select-chart">
        <div>
            <div :class="['select-chart-btn', selectedChartType == 'table' && !isDisabled('table') ? 'on' : '', isDisabled('table') ? 'disabled' : '']"
                 @click="isDisabled('table') ? null : setSelectedChartType('table')">
                <div>
                    <font-awesome-icon size="4x" :icon="['fa', 'table']" />
                    <div>Table</div>
                </div>
            </div>
            <div :key="i"
                 v-for="(chartType, i) in ['Line', 'Bar', 'Area', 'Scatter']"
                 :class="['select-chart-btn', selectedChartType == chartType.toLowerCase() && !isDisabled(chartType.toLowerCase()) ? 'on' : '', isDisabled(chartType.toLowerCase()) ? 'disabled' : '']"
                 @click="isDisabled(chartType.toLowerCase()) ? null : setSelectedChartType(chartType.toLowerCase())">
                <div>
                    <font-awesome-icon size="4x" :icon="['fa', 'chart-' + chartType.toLowerCase()]" />
                    <div>{{ chartType }} Chart</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    export default {
        name: "SelectChart",
        computed: {
            ...mapState({
                selectedChartType: state => state.observation.selectedChartType,
                xAxis: state => state.observation[state.observation.selectedCategory].xAxis
            })
        },
        watch: {
            xAxis() {
                if (this.xAxis.length === 0)
                    this.setSelectedChartType("");
                else if (this.xAxis[0].value !== "datetime" && this.selectedChartType != "table" && this.selectedChartType != "scatter")
                    this.setSelectedChartType("scatter");
                else if (this.xAxis[0].value === "datetime") this.setSelectedChartType("line");
            }
        },
        methods: {
            ...mapActions({
                setSelectedChartType: "SET_OBSERVATION_SELECTED_CHART_TYPE"
            }),

            isDisabled(chartType) {
                if (this.xAxis.length === 0) return true;
                else if (this.xAxis[0].value === "datetime") return false;
                else return chartType !== "scatter" && chartType !== "table";
            }
        }
    }
</script>

<style lang="scss">
    @import "./SelectChart";
</style>
