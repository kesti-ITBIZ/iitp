export default {
    state: {
        selectedChartType: "line"
    },
    getters: {

    },
    mutations: {
        SET_SELECTED_CHART_TYPE: (state, chartType) => state.selectedChartType = chartType
    },
    actions: {
        SET_SELECTED_CHART_TYPE: (context, chartType) => context.commit("SET_SELECTED_CHART_TYPE", chartType)
    }
}
