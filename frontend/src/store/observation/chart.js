export default {
    state: {
        selectedChartType: ""
    },
    getters: {

    },
    mutations: {
        SET_OBSERVATION_SELECTED_CHART_TYPE: (state, chartType) => state.selectedChartType = chartType
    },
    actions: {
        SET_OBSERVATION_SELECTED_CHART_TYPE: (context, chartType) => context.commit("SET_OBSERVATION_SELECTED_CHART_TYPE", chartType)
    }
}
