import dayjs from "dayjs";

export default {
    state: {
        category: Object.freeze([
            { label: "환경부", value: "airkorea" },
            { label: "KT", value: "kt" },
            { label: "S-DoT", value: "sDoT" },
            { label: "옵저버", value: "observer" }
        ]),
        selectedCategory: "airkorea",
        searchOptions: [
            { label: "지도기반 지점 선택", value: "map" },
            { label: "지점명 조회로 선택", value: "keyword" }
        ],
        selectedSearchOption: "map",
        stations: Object.freeze({
            airkorea: [],
            kt: [],
            sDoT: [],
            observer: []
        }),
        selectedStation: Object.freeze({
            airkorea: [],
            kt: [],
            sDoT: [],
            observer: []
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
        SET_SELECTED_SEARCH_OPTION: (state, option) => state.selectedSearchOption = option,
        SET_SELECTED_CATEGORY: (state, category) => state.selectedCategory = category,
        ADD_STATIONS: (state, stations) => {
            const _stations = { ...state.stations };
            _stations[state.selectedCategory] = Object.freeze(_stations[state.selectedCategory].concat(stations));
            state.stations = Object.freeze(_stations);
        },
        SET_SELECTED_STATION: (state, { category, station }) => {
            const selectedStation = { ...state.selectedStation };
            selectedStation[category] = [station];
            state.selectedStation = Object.freeze(selectedStation);
        },
        REMOVE_SELECTED_STATION: (state, { category, station }) => {
            const selectedStation = { ...state.selectedStation };
            selectedStation[category].splice(selectedStation[category].findIndex(obj =>
                obj.name === station.name
                && obj.address === station.address
                && obj.latitude === station.latitude
                && obj.longitude === station.longitude), 1);
            state.selectedStation = Object.freeze(selectedStation);
        },
        SET_SEARCHED_STATIONS: (state, stations) => {
            console.log("stations:", stations);
            state.searchedStations = Object.freeze(stations);
        },
        APPEND_SEARCHED_STATIONS: (state, stations) => {
            console.log("stations:", stations);
            state.searchedStations = Object.freeze(state.searchedStations.concat(stations));
        },
        SET_DATA: (state, { category, data }) => {
            const len = data.length

            if (len > 0) {
                const datetimeUnit = (state.selectedDateType == "date" ? "day" : state.selectedDateType) + "s";

                const padding = (_data, index, start, end) => {
                    const keys = Object.keys(_data[index]);
                    keys.splice(keys.indexOf("datetime"), 1);
                    keys.splice(keys.indexOf("__typename"), 1);
                    keys.splice(keys.indexOf("stnNm"), 1);
                    for (let datetime = start; datetime < end; datetime = datetime.add(1, datetimeUnit)) {
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

                if (Math.abs(data[0].datetime.diff(state.startDatetime, datetimeUnit)) > 0)
                    padding(data, 0, state.startDatetime, data[0].datetime);
                data.sort((a, b) => a.datetime < b.datetime ? -1 : 1);

                if (Math.abs(data[data.length - 1].datetime.diff(state.endDatetime, datetimeUnit)) > 0)
                    padding(data, data.length - 1, data[data.length - 1].datetime, state.endDatetime.add(1, datetimeUnit));
                data.sort((a, b) => a.datetime < b.datetime ? -1 : 1);

                state.data = Object.freeze({ ...state.data, [category]: data });
            }
        }
    },
    actions: {
        SET_SELECTED_SEARCH_OPTION: (context, option) => context.commit("SET_SELECTED_SEARCH_OPTION", option),
        SET_SELECTED_CATEGORY: (context, category) => context.commit("SET_SELECTED_CATEGORY", category),
        ADD_STATIONS: (context, stations) => context.commit("ADD_STATIONS", stations),
        SET_SELECTED_STATION: (context, { category, station }) => context.commit("SET_SELECTED_STATION", { category, station }),
        REMOVE_SELECTED_STATION: (context, { category, station }) => context.commit("REMOVE_SELECTED_STATION", { category, station }),
        SET_SEARCHED_STATIONS: (context, stations) => context.commit("SET_SEARCHED_STATIONS", stations),
        APPEND_SEARCHED_STATIONS: (context, stations) => context.commit("APPEND_SEARCHED_STATIONS", stations),
        SET_DATA: (context, { category, data }) => context.commit("SET_DATA", { category, data })
    }
}
