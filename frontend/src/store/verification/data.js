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
            state.verification.selectedStandardOrg = station == null ? null : state.verification.selectedCategory;
        },
        SET_VERIFICATION_SELECTED_COMPARE_STATION: (state, station) => {
            state.verification.selectedCompareStation = Object.freeze(station);
            let min = 9999999999999, selectedStandardStation = null;
            state.verification.stations.airkorea.forEach(standardStation => {
                const distance = Math.sqrt((station.latitude - standardStation.latitude) ** 2 + (station.longitude - standardStation.longitude) ** 2);
                if (min > distance) {
                    min = distance;
                    selectedStandardStation = standardStation;
                }
            });
            state.verification.selectedStandardStation = Object.freeze(selectedStandardStation);
            state.verification.selectedCompareOrg = station == null ? null : state.verification.selectedCategory;
        },
        SET_VERIFICATION_SEARCHED_STATIONS: (state, stations) => state.verification.searchedStations = Object.freeze(stations),
        APPEND_VERIFICATION_SEARCHED_STATIONS: (state, stations) => state.verification.searchedStations = Object.freeze(state.verification.searchedStations.concat(stations)),
        SET_VERIFICATION_DATA: (state, data) => {
            const _data = [...data];
            _data.sort((a, b) => a.datetime < b.datetime ? -1 : 1);
            state.verification.data = Object.freeze(_data);
        }
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
