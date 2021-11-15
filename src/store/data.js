export default {
    state: {
        data: [
            { label: "전체", value: "all" },
            { label: "환경부", value: "airkorea" },
            { label: "KT", value: "kt" },
            { label: "S-DoT", value: "sDoT" },
            { label: "옵저버", value: "observer" }
        ],
        selectedData: "airkorea",
        stations: {
            all: [],
            airkorea: [],
            kt: [],
            sDoT: [],
            observer: []
        }
    },
    getters: {

    },
    mutations: {
        SET_SELECTED_DATA: (state, data) => state.selectedData = Object.freeze(data)
    },
    actions: {
        SET_SELECTED_DATA: (context, data) => context.commit("SET_SELECTED_DATA", data)
    }
}
