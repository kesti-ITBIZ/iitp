// const setData = (data, format, type, startDatetime, endDatetime, category, stnNm) => {
//     let list = [];
//     for (let dt = startDatetime, i = 0;
//          dt.format(format) <= endDatetime.format(format);
//          dt = dt.add(1, type)) {
//         const empty = {
//             avg: 0,
//             goodDays: 0,
//             badDays: 0,
//             highDnstyDays: 0
//         };
//         let item = {
//             type,
//             category,
//             datetime: dt,
//             stnNm,
//             pm10: { ...empty },
//             pm25: { ...empty }
//         };
//
//         if (data.length > 0 && data[i] && dt.format(format) === data[i].datetime) {
//             item = {
//                 ...item,
//                 pm10: { ...data[i].pm10 },
//                 pm25: { ...data[i].pm25 }
//             };
//             ++i;
//         }
//
//         list.push(item);
//     }
//
//     return list;
// };

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
        selectedStandardStation: null,
        selectedCompareStation: null,
        selectedStandardOrg: "airkorea",
        selectedCompareOrg: null,
        searchedStations: [],
        data: null
    },
    getters: {

    },
    mutations: {
        SET_VERIFICATION_SELECTED_SEARCH_OPTION: (state, option) => state.verification.selectedSearchOption = option,
        SET_VERIFICATION_SELECTED_CATEGORY: (state, category) => state.verification.selectedCategory = category,
        SET_VERIFICATION_STATIONS: (state, stations) => {
            const _stations = { ...state.verification.stations };
            _stations[state.verification.selectedCategory] = Object.freeze(stations);
            state.verification.stations = Object.freeze(_stations);
        },
        SET_VERIFICATION_SELECTED_STANDARD_STATION: (state, station) => {
            state.verification.selectedStandardStation = Object.freeze(station);
        },
        SET_VERIFICATION_SELECTED_COMPARE_STATION: (state, station) => {
            state.verification.selectedCompareStation = Object.freeze(station);
            state.verification.selectedCompareOrg = station == null ? null : state.verification.selectedCategory;
        },
        SET_VERIFICATION_SEARCHED_STATIONS: (state, stations) => state.verification.searchedStations = Object.freeze(stations),
        APPEND_VERIFICATION_SEARCHED_STATIONS: (state, stations) => state.verification.searchedStations = Object.freeze(state.verification.searchedStations.concat(stations)),
        SET_VERIFICATION_DATA: (state, data) => state.verification.data = Object.freeze(data)
    },
    actions: {
        SET_VERIFICATION_SELECTED_SEARCH_OPTION: (context, option) => context.commit("SET_VERIFICATION_SELECTED_SEARCH_OPTION", option),
        SET_VERIFICATION_SELECTED_CATEGORY: (context, category) => context.commit("SET_VERIFICATION_SELECTED_CATEGORY", category),
        SET_VERIFICATION_STATIONS: (context, stations) => context.commit("SET_VERIFICATION_STATIONS", stations),
        SET_VERIFICATION_SELECTED_STANDARD_STATION: (context, station) => context.commit("SET_VERIFICATION_SELECTED_STANDARD_STATION", station),
        SET_VERIFICATION_SELECTED_COMPARE_STATION: (context, station) => context.commit("SET_VERIFICATION_SELECTED_COMPARE_STATION", station),
        SET_VERIFICATION_SEARCHED_STATIONS: (context, stations) => context.commit("SET_VERIFICATION_SEARCHED_STATIONS", stations),
        APPEND_VERIFICATION_SEARCHED_STATIONS: (context, stations) => context.commit("APPEND_VERIFICATION_SEARCHED_STATIONS", stations),
        SET_VERIFICATION_DATA: (context, data) => context.commit("SET_VERIFICATION_DATA", data)
    }
}
