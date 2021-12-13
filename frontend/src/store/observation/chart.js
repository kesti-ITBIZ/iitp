export default {
    state: {
        selectedChartType: "line",
    },
    getters: {

    },
    mutations: {
        SET_OBSERVATION_SELECTED_CHART_TYPE: (state, chartType) => state.observation.selectedChartType = chartType
    },
    actions: {
        SET_OBSERVATION_SELECTED_CHART_TYPE: (context, chartType) => context.commit("SET_OBSERVATION_SELECTED_CHART_TYPE", chartType)
    }
}
