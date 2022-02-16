export default {
    state: {
        selectedChartType: "bar",
    },
    getters: {

    },
    mutations: {
        SET_VALIDATION_SELECTED_CHART_TYPE: (state, chartType) => state.validation.selectedChartType = chartType
    },
    actions: {
        SET_VALIDATION_SELECTED_CHART_TYPE: (context, chartType) => context.commit("SET_VALIDATION_SELECTED_CHART_TYPE", chartType)
    }
}
