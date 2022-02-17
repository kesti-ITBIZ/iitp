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
        SET_VALIDATION_SELECTED_SEARCH_OPTION: (state, option) => state.validation.selectedSearchOption = option,
        SET_VALIDATION_SELECTED_CATEGORY: (state, category) => state.validation.selectedCategory = category,
        SET_VALIDATION_STATIONS: (state, stations) => {
            const _stations = { ...state.validation.stations };
            _stations[state.validation.selectedCategory] = Object.freeze(stations);
            state.validation.stations = Object.freeze(_stations);
        },
        SET_VALIDATION_SELECTED_STATION: (state, { category, station }) => {
            const selectedStation = { ...state.validation.selectedStation };
            selectedStation[category] = station;
            state.validation.selectedStation = Object.freeze(selectedStation);
        },
        REMOVE_VALIDATION_SELECTED_STATION: state => {
            const selectedStation = { ...state.validation.selectedStation };
            selectedStation[state.validation.selectedCategory] = null;
            state.validation.selectedStation = Object.freeze(selectedStation);
        },
        SET_VALIDATION_SEARCHED_STATIONS: (state, stations) => state.validation.searchedStations = Object.freeze(stations),
        APPEND_VALIDATION_SEARCHED_STATIONS: (state, stations) => state.validation.searchedStations = Object.freeze(state.validation.searchedStations.concat(stations)),
        SET_VALIDATION_DATA: (state, data) => {
            const category = state.validation.selectedCategory;
            const stnNm = state.validation.selectedStation[category].name;
            let list = [];

            const [yearData, monthData] = [
                data.filter(obj => obj.datetime.length === 4),
                data.filter(obj => obj.datetime.length === 6)
            ];

            yearData.sort((a, b) => a.datetime < b.datetime ? -1 : 1);
            monthData.sort((a, b) => a.datetime < b.datetime ? -1 : 1);

            list = list.concat(setData(yearData, "YYYY", "year", state.validation.startDatetime, state.validation.endDatetime, category, stnNm));
            list = list.concat(setData(monthData, "YYYYMM", "month", state.validation.startDatetime, state.validation.endDatetime, category, stnNm));

            state.validation.data = Object.freeze({
                ...state.validation.data,
                [category]: Object.freeze(list)
            });
        }
    },
    actions: {
        SET_VALIDATION_SELECTED_SEARCH_OPTION: (context, option) => context.commit("SET_VALIDATION_SELECTED_SEARCH_OPTION", option),
        SET_VALIDATION_SELECTED_CATEGORY: (context, category) => context.commit("SET_VALIDATION_SELECTED_CATEGORY", category),
        SET_VALIDATION_STATIONS: (context, stations) => context.commit("SET_VALIDATION_STATIONS", stations),
        SET_VALIDATION_SELECTED_STATION: (context, { category, station }) => context.commit("SET_VALIDATION_SELECTED_STATION", { category, station }),
        REMOVE_VALIDATION_SELECTED_STATION: context => context.commit("REMOVE_VALIDATION_SELECTED_STATION"),
        SET_VALIDATION_SEARCHED_STATIONS: (context, stations) => context.commit("SET_VALIDATION_SEARCHED_STATIONS", stations),
        APPEND_VALIDATION_SEARCHED_STATIONS: (context, stations) => context.commit("APPEND_VALIDATION_SEARCHED_STATIONS", stations),
        SET_VALIDATION_DATA: (context, data) => context.commit("SET_VALIDATION_DATA", data)
    }
}
