export default {
    state: {
        selectedChartType: "bar",
    },
    getters: {

    },
    mutations: {
        SET_VERIFICATION_SELECTED_CHART_TYPE: (state, chartType) => state.verification.selectedChartType = chartType
    },
    actions: {
        SET_VERIFICATION_SELECTED_CHART_TYPE: (context, chartType) => context.commit("SET_VERIFICATION_SELECTED_CHART_TYPE", chartType)
    }
}
