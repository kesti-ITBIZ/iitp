<template>
    <div id="select-chart">
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
<!--        <div :class="['select-chart-btn', selectedChartType == 'distribution' && !isDisabled('distribution') ? 'on' : '', isDisabled('distribution') ? 'disabled' : '']"-->
<!--             @click="isDisabled('distribution') ? null : setSelectedChartType('distribution')">-->
<!--            <div>-->
<!--                <font-awesome-icon size="3x" :icon="['fa', 'chart-network']" style="margin: 0 0 0 5px;" />-->
<!--                <div>Distribution</div>-->
<!--            </div>-->
<!--        </div>-->
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    export default {
        name: "SelectChart",
        computed: {
            ...mapState({
                selectedChartType: state => state.selectedChartType,
                xAxis: state => state[state.selectedCategory].xAxis
            })
        },
        watch: {
            xAxis() {
                if (this.xAxis.length === 0)
                    this.setSelectedChartType("");
                else if (this.xAxis[0].value !== "datetime" && this.selectedChartType != "table" && this.selectedChartType != "scatter")
                    this.setSelectedChartType("scatter");
                else this.setSelectedChartType("line");
            }
        },
        methods: {
            ...mapActions({
                setSelectedChartType: "SET_SELECTED_CHART_TYPE"
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
