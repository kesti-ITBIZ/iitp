export default {
    state: {
        category: Object.freeze([
            { label: "전체", value: "all" },
            { label: "환경부", value: "airkorea" },
            { label: "KT", value: "kt" },
            { label: "S-DoT", value: "sDoT" },
            { label: "옵저버", value: "observer" }
        ]),
        selectedCategory: "airkorea",
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
        ADD_STATIONS: (state, { category, stations }) => {
            const _stations = { ...state.stations };
            _stations[category] = Object.freeze(_stations[category].concat(stations));
            _stations["all"] = Object.freeze(_stations["all"].concat(stations));
            state.stations = Object.freeze(_stations);
        },
        SET_DATA: (state, data) => state.data = Object.freeze(data)
    },
    actions: {
        SET_SELECTED_CATEGORY: (context, data) => context.commit("SET_SELECTED_CATEGORY", data),
        ADD_STATIONS: async (context, category) => {
            if (category === "all") {
                for (const key of Object.keys({ ...context.state.stations }))
                    if (context.state.stations[key].length === 0)
                        context.commit("ADD_STATIONS", {
                            category: key,
                            stations: await fetch(`/api/${key}/stations`).then(response => response.json())
                        });
            } else context.commit("ADD_STATIONS", {
                category,
                stations: await fetch(`/api/${category}/stations`).then(response => response.json())
            });
        },
        SET_DATA: (context, data) => context.commit("SET_DATA", data)
    }
}
