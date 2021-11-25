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
        selectedCategory: "airkorea",
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

            if (len > 0) {
                const datetimeUnit = (state.selectedDateType == "date" ? "day" : state.selectedDateType) + "s";
                const dayjsToStringFormat = state.dateTypes[state.dateTypes.findIndex(obj => obj.type == state.selectedDateType)].dayjsToStringFormat;
                const stringToDayjsFormat = state.dateTypes[state.dateTypes.findIndex(obj => obj.type == state.selectedDateType)].stringToDayjsFormat;

                const padding = (_data, index, start, end) => {
                    const keys = Object.keys(_data[index]);
                    keys.splice(keys.indexOf("datetime"), 1);
                    keys.splice(keys.indexOf("__typename"), 1);
                    keys.splice(keys.indexOf("stnNm"), 1);
                    for (let datetime = start; datetime < end; datetime = datetime.add(1, datetimeUnit)) {
                        const item = {
                            datetime: datetime.format(dayjsToStringFormat),
                            stnNm: _data[index].stnNm,
                            __typename: _data[index].__typename
                        };
                        for (const j in keys)
                            item[keys[j]] = null;
                        _data.push(item);
                    }
                }

                for (let i = 0; i < len - 1; ++i)
                    if (Math.abs(dayjs(data[i].datetime).diff(dayjs(data[i + 1].datetime), datetimeUnit)) > 1)
                        padding(data, i, dayjs(data[i].datetime).add(1, datetimeUnit), dayjs(data[i + 1].datetime));
                data.sort((a, b) => a.datetime < b.datetime ? -1 : 1);

                if (Math.abs(dayjs(data[0].datetime, stringToDayjsFormat).diff(state.startDatetime, datetimeUnit)) > 0)
                    padding(data, 0, state.startDatetime, dayjs(data[0].datetime, stringToDayjsFormat));
                data.sort((a, b) => a.datetime < b.datetime ? -1 : 1);

                if (Math.abs(dayjs(data[data.length - 1].datetime, stringToDayjsFormat).diff(state.endDatetime, datetimeUnit)) > 0)
                    padding(data, data.length - 1, dayjs(data[data.length - 1].datetime, stringToDayjsFormat), state.endDatetime.add(1, datetimeUnit));
                data.sort((a, b) => a.datetime < b.datetime ? -1 : 1);

                state.data = Object.freeze({ ...state.data, [category]: data });
            }
        }
    },
    actions: {
        SET_SELECTED_CATEGORY: (context, category) => context.commit("SET_SELECTED_CATEGORY", category),
        ADD_STATIONS: async (context, stations) => context.commit("ADD_STATIONS", stations),
        SET_DATA: (context, { category, data }) => context.commit("SET_DATA", { category, data })
    }
}
