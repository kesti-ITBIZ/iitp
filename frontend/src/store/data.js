import dayjs from "dayjs";

export default {
    state: {
        category: Object.freeze([
            { label: "전체", value: "all" },
            { label: "환경부", value: "airkorea" },
            { label: "KT", value: "kt" },
            { label: "S-DoT", value: "sDoT" },
            { label: "옵저버", value: "observer" }
        ]),
        selectedCategory: "sDoT",
        stations: Object.freeze({
            all: [],
            airkorea: [],
            kt: [],
            sDoT: [],
            observer: []
        }),
        data: {
            all: Object.freeze([]),
            airkorea: Object.freeze([]),
            kt: Object.freeze([]),
            sDoT: Object.freeze([]),
            observer: Object.freeze([])
        }
    },
    getters: {

    },
    mutations: {
        SET_SELECTED_CATEGORY: (state, category) => state.selectedCategory = category,
        ADD_STATIONS: (state, stations) => {
            const _stations = { ...state.stations };
            _stations[state.selectedCategory] = Object.freeze(_stations[state.selectedCategory].concat(stations));
            _stations["all"] = Object.freeze(_stations["all"].concat(stations));
            state.stations = Object.freeze(_stations);
        },
        SET_DATA: (state, { category, data }) => {
            const len = data.length
            const datetimeUnit = (state.selectedDateType == "date" ? "day" : state.selectedDateType) + "s";
            const dayjsToStringFormat = state.dateTypes[state.dateTypes.findIndex(obj => obj.type == state.selectedDateType)].dayjsToStringFormat;
            for (let i = 0; i < len - 1; ++i) {
                const diff = Math.abs(dayjs(data[i].datetime).diff(dayjs(data[i + 1].datetime), datetimeUnit));
                if (diff > 1) {
                    const keys = Object.keys(data[i]);
                    keys.splice(keys.indexOf("datetime"), 1);
                    keys.splice(keys.indexOf("__typename"), 1);
                    keys.splice(keys.indexOf("stnNm"), 1);
                    for (let datetime = dayjs(data[i].datetime).add(1, datetimeUnit);
                         datetime <= dayjs(data[i + 1].datetime).subtract(1, datetimeUnit);
                         datetime = datetime.add(1, datetimeUnit)) {
                        const item = {
                            datetime: datetime.format(dayjsToStringFormat),
                            stnNm: data[i].stnNm,
                            __typename: data[i].__typename
                        };
                        for (const j in keys)
                            item[keys[j]] = null;
                        data.push(item);
                    }
                }
            }
            data.sort((a, b) => a.datetime < b.datetime ? -1 : 1);
            state.data = Object.freeze({ ...state.data, [category]: data });
        }
    },
    actions: {
        SET_SELECTED_CATEGORY: (context, category) => context.commit("SET_SELECTED_CATEGORY", category),
        ADD_STATIONS: async (context, stations) => context.commit("ADD_STATIONS", stations),
        SET_DATA: (context, { category, data }) => context.commit("SET_DATA", { category, data })
    }
}
