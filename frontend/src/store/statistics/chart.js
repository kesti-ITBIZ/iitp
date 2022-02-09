export default {
    state: {
        selectedChartType: "bar",
    },
    getters: {

    },
    mutations: {
        SET_STATISTICS_SELECTED_CHART_TYPE: (state, chartType) => state.statistics.selectedChartType = chartType
    },
    actions: {
        SET_STATISTICS_SELECTED_CHART_TYPE: (context, chartType) => context.commit("SET_STATISTICS_SELECTED_CHART_TYPE", chartType)
    }
}
