export default {
    state: {
        category: Object.freeze([
            { label: "전체", value: "all" },
            { label: "환경부", value: "airkorea" },
            { label: "KT", value: "kt" },
            { label: "S-DoT", value: "sDoT" },
            { label: "옵저버", value: "observer" }
        ]),
        selectedCategory: "observer",
        stations: Object.freeze({
            all: [],
            airkorea: [],
            kt: [],
            sDoT: [],
            observer: []
        }),
        data: Object.freeze([])
    },
    getters: {

    },
    mutations: {
        SET_SELECTED_CATEGORY: (state, data) => state.selectedCategory = Object.freeze(data),
        ADD_STATIONS: (state, stations) => {
            const _stations = { ...state.stations };
            _stations[state.selectedCategory] = Object.freeze(_stations[state.selectedCategory].concat(stations));
            _stations["all"] = Object.freeze(_stations["all"].concat(stations));
            state.stations = Object.freeze(_stations);
        },
        SET_DATA: (state, data) => state.data = Object.freeze(data)
    },
    actions: {
        SET_SELECTED_CATEGORY: (context, data) => context.commit("SET_SELECTED_CATEGORY", data),
        ADD_STATIONS: async (context, stations) => context.commit("ADD_STATIONS", stations),
        SET_DATA: (context, data) => context.commit("SET_DATA", data)
    }
}
