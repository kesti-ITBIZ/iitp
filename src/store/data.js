export default {
    state: {
        data: Object.freeze([
            { label: "전체", value: "all" },
            { label: "환경부", value: "airkorea" },
            { label: "KT", value: "kt" },
            { label: "S-DoT", value: "sDoT" },
            { label: "옵저버", value: "observer" }
        ]),
        selectedData: "airkorea",
        stations: Object.freeze({
            all: [],
            airkorea: [],
            kt: [],
            sDoT: [],
            observer: []
        })
    },
    getters: {

    },
    mutations: {
        SET_SELECTED_DATA: (state, data) => state.selectedData = Object.freeze(data),
        ADD_STATIONS: (state, { category, stations }) => {
            const _stations = { ...state.stations };
            _stations[category] = Object.freeze(_stations[category].concat(stations));
            _stations["all"] = Object.freeze(_stations["all"].concat(stations));
            state.stations = Object.freeze(_stations);
        }
    },
    actions: {
        SET_SELECTED_DATA: (context, data) => context.commit("SET_SELECTED_DATA", data),
        ADD_STATIONS: async (context, category) => {
            if (category === "all") {
                for (const key of Object.keys({ ...context.state.stations })) {
                    console.log(key, context.state.stations[key].length);
                    if (context.state.stations[key].length === 0)
                        context.commit("ADD_STATIONS", {
                            category: key,
                            stations: await fetch(`/api/${key}/stations`).then(response => response.json())
                        });
                }
            } else context.commit("ADD_STATIONS", {
                category,
                stations: await fetch(`/api/${category}/stations`).then(response => response.json())
            });
        }
    }
}
