import dayjs from "dayjs";

export default {
    state: {
        category: Object.freeze([
            { label: "환경부", value: "airkorea" },
            { label: "KT AirMap", value: "kt" },
            { label: "서울시", value: "sDoT" },
            { label: "옵저버", value: "observer" }
        ]),
        selectedCategory: "airkorea",
        searchOptions: [
            { label: "지도기반 지점 선택", value: "map" },
            { label: "지점명 조회로 선택", value: "search" }
        ],
        selectedSearchOption: "map",
        stations: Object.freeze({
            airkorea: null,
            kt: null,
            sDoT: null,
            observer: null
        }),
        selectedStation: Object.freeze({
            airkorea: null,
            kt: null,
            sDoT: null,
            observer: null
        }),
        searchedStations: [],
        data: Object.freeze({
            airkorea: [],
            kt: [],
            sDoT: [],
            observer: []
        })
    },
    getters: {

    },
    mutations: {
        SET_SELECTED_SEARCH_OPTION: (state, option) => state.observation.selectedSearchOption = option,
        SET_SELECTED_CATEGORY: (state, category) => state.observation.selectedCategory = category,
        SET_STATIONS: (state, stations) => {
            const _stations = { ...state.observation.stations };
            _stations[state.observation.selectedCategory] = Object.freeze(stations);
            state.observation.stations = Object.freeze(_stations);
        },
        SET_SELECTED_STATION: (state, { category, station }) => {
            const selectedStation = { ...state.observation.selectedStation };
            selectedStation[category] = station;
            state.observation.selectedStation = Object.freeze(selectedStation);
        },
        REMOVE_SELECTED_STATION: state => {
            const selectedStation = { ...state.observation.selectedStation };
            selectedStation[state.observation.selectedCategory] = null;
            state.observation.selectedStation = Object.freeze(selectedStation);
        },
        SET_SEARCHED_STATIONS: (state, stations) => state.observation.searchedStations = Object.freeze(stations),
        APPEND_SEARCHED_STATIONS: (state, stations) => state.observation.searchedStations = Object.freeze(state.observation.searchedStations.concat(stations)),
        SET_DATA: (state, { category, data }) => {
            const len = data.length

            if (len > 0) {
                // const datetimeUnit = (state.selectedDateType == "date" ? "day" : state.selectedDateType) + "s";
                const datetimeUnit = "hours";

                const padding = (_data, index, start, end) => {
                    const keys = Object.keys(_data[index]);
                    keys.splice(keys.indexOf("datetime"), 1);
                    keys.splice(keys.indexOf("__typename"), 1);
                    keys.splice(keys.indexOf("stnNm"), 1);
                    for (let datetime = start; datetime <= end; datetime = datetime.add(1, datetimeUnit)) {
                        let item = {
                            datetime,
                            stnNm: _data[index].stnNm,
                            __typename: _data[index].__typename
                        };
                        for (const j in keys)
                            item[keys[j]] = null;
                        _data.push(item);
                    }
                };

                data = data.map(obj => ({ ...obj, datetime: dayjs(obj.datetime) }));

                for (let i = 0; i < len - 1; ++i)
                    if (Math.abs(data[i].datetime.diff(data[i + 1].datetime, datetimeUnit)) > 1)
                        padding(data, i, data[i].datetime.add(1, datetimeUnit), data[i + 1].datetime);
                data.sort((a, b) => a.datetime < b.datetime ? -1 : 1);

                if (Math.abs(data[0].datetime.diff(state.observation.startDatetime, datetimeUnit)) > 0)
                    padding(data, 0, state.observation.startDatetime, data[0].datetime.subtract(1, datetimeUnit));
                data.sort((a, b) => a.datetime < b.datetime ? -1 : 1);

                if (Math.abs(data[data.length - 1].datetime.diff(state.observation.endDatetime, datetimeUnit)) > 0)
                    padding(data, data.length - 1, data[data.length - 1].datetime.add(1, datetimeUnit), state.observation.endDatetime);
                data.sort((a, b) => a.datetime < b.datetime ? -1 : 1);

                state.observation.data = Object.freeze({ ...state.observation.data, [category]: data });
            } else state.observation.data = Object.freeze([]);
        }
    },
    actions: {
        SET_SELECTED_SEARCH_OPTION: (context, option) => context.commit("SET_SELECTED_SEARCH_OPTION", option),
        SET_SELECTED_CATEGORY: (context, category) => context.commit("SET_SELECTED_CATEGORY", category),
        SET_STATIONS: (context, stations) => context.commit("SET_STATIONS", stations),
        SET_SELECTED_STATION: (context, { category, station }) => context.commit("SET_SELECTED_STATION", { category, station }),
        REMOVE_SELECTED_STATION: context => context.commit("REMOVE_SELECTED_STATION"),
        SET_SEARCHED_STATIONS: (context, stations) => context.commit("SET_SEARCHED_STATIONS", stations),
        APPEND_SEARCHED_STATIONS: (context, stations) => context.commit("APPEND_SEARCHED_STATIONS", stations),
        SET_DATA: (context, { category, data }) => context.commit("SET_DATA", { category, data })
    }
}
