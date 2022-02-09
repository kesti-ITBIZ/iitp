const setData = (data, format, type, startDatetime, endDatetime, category, stnNm) => {
    let list = [];
    for (let dt = startDatetime, i = 0;
         dt.format(format) <= endDatetime.format(format);
         dt = dt.add(1, type)) {
        const empty = {
            avg: 0,
            goodDays: 0,
            badDays: 0,
            highDnstyDays: 0
        };
        let item = {
            type,
            category,
            datetime: dt,
            stnNm,
            pm10: { ...empty },
            pm25: { ...empty }
        };

        if (data.length > 0 && data[i] && dt.format(format) === data[i].datetime) {
            item = {
                ...item,
                pm10: { ...data[i].pm10 },
                pm25: { ...data[i].pm25 }
            };
            ++i;
        }

        list.push(item);
    }

    return list;
};

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
        SET_STATISTICS_SELECTED_SEARCH_OPTION: (state, option) => state.statistics.selectedSearchOption = option,
        SET_STATISTICS_SELECTED_CATEGORY: (state, category) => state.statistics.selectedCategory = category,
        SET_STATISTICS_STATIONS: (state, stations) => {
            const _stations = { ...state.statistics.stations };
            _stations[state.statistics.selectedCategory] = Object.freeze(stations);
            state.statistics.stations = Object.freeze(_stations);
        },
        SET_STATISTICS_SELECTED_STATION: (state, { category, station }) => {
            const selectedStation = { ...state.statistics.selectedStation };
            selectedStation[category] = station;
            state.statistics.selectedStation = Object.freeze(selectedStation);
        },
        REMOVE_STATISTICS_SELECTED_STATION: state => {
            const selectedStation = { ...state.statistics.selectedStation };
            selectedStation[state.statistics.selectedCategory] = null;
            state.statistics.selectedStation = Object.freeze(selectedStation);
        },
        SET_STATISTICS_SEARCHED_STATIONS: (state, stations) => state.statistics.searchedStations = Object.freeze(stations),
        APPEND_STATISTICS_SEARCHED_STATIONS: (state, stations) => state.statistics.searchedStations = Object.freeze(state.statistics.searchedStations.concat(stations)),
        SET_STATISTICS_DATA: (state, data) => {
            const category = state.statistics.selectedCategory;
            const stnNm = state.statistics.selectedStation[category].name;
            let list = [];

            const [yearData, monthData] = [
                data.filter(obj => obj.datetime.length === 4),
                data.filter(obj => obj.datetime.length === 6)
            ];

            yearData.sort((a, b) => a.datetime < b.datetime ? -1 : 1);
            monthData.sort((a, b) => a.datetime < b.datetime ? -1 : 1);

            list = list.concat(setData(yearData, "YYYY", "year", state.statistics.startDatetime, state.statistics.endDatetime, category, stnNm));
            list = list.concat(setData(monthData, "YYYYMM", "month", state.statistics.startDatetime, state.statistics.endDatetime, category, stnNm));

            state.statistics.data = Object.freeze({
                ...state.statistics.data,
                [category]: Object.freeze(list)
            });
        }
    },
    actions: {
        SET_STATISTICS_SELECTED_SEARCH_OPTION: (context, option) => context.commit("SET_STATISTICS_SELECTED_SEARCH_OPTION", option),
        SET_STATISTICS_SELECTED_CATEGORY: (context, category) => context.commit("SET_STATISTICS_SELECTED_CATEGORY", category),
        SET_STATISTICS_STATIONS: (context, stations) => context.commit("SET_STATISTICS_STATIONS", stations),
        SET_STATISTICS_SELECTED_STATION: (context, { category, station }) => context.commit("SET_STATISTICS_SELECTED_STATION", { category, station }),
        REMOVE_STATISTICS_SELECTED_STATION: context => context.commit("REMOVE_STATISTICS_SELECTED_STATION"),
        SET_STATISTICS_SEARCHED_STATIONS: (context, stations) => context.commit("SET_STATISTICS_SEARCHED_STATIONS", stations),
        APPEND_STATISTICS_SEARCHED_STATIONS: (context, stations) => context.commit("APPEND_STATISTICS_SEARCHED_STATIONS", stations),
        SET_STATISTICS_DATA: (context, data) => context.commit("SET_STATISTICS_DATA", data)
    }
}
