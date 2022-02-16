import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";

dayjs.extend(customParseFormat);

export default {
    state: {
        airkorea: {
            items: Object.freeze([
                { label: "PM10", value: "pm10", unit: "㎍/㎥" },
                { label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
            ]),
            selectedItem: { label: "PM10", value: "pm10", unit: "㎍/㎥" },
            available: []
        },
        kt: {
            items: Object.freeze([
                { label: "PM10", value: "pm10", unit: "㎍/㎥" },
                { label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
            ]),
            selectedItem: { label: "PM10", value: "pm10", unit: "㎍/㎥" },
            available: []
        },
        sDoT: {
            items: Object.freeze([
                { label: "PM10", value: "pm10", unit: "㎍/㎥" },
                { label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
            ]),
            selectedItem: { label: "PM10", value: "pm10", unit: "㎍/㎥" },
            available: []
        },
        observer: {
            items: Object.freeze([
                { label: "PM10", value: "pm10", unit: "㎍/㎥" },
                { label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
            ]),
            selectedItem: { label: "PM10", value: "pm10", unit: "㎍/㎥" },
            available: []
        },
    },
    getters: {

    },
    mutations: {
        SET_VALIDATION_SELECTED_ITEM: (state, item) => state.validation[state.validation.selectedCategory].selectedItem = Object.freeze(item),
        SET_VALIDATION_AVAILABLE: (state, { category, available }) => {
            let availableDatetimes = available.map(dt => dayjs(dt, "YYYY.MM.DD HH:mm")), availableList = [];
            for (let i = 0; i < availableDatetimes.length - 1; ++i) {
                if (i === 0 || (i > 0 && Math.abs(availableDatetimes[i - 1].diff(availableDatetimes[i], "hours")) > 1))
                    availableList.push([availableDatetimes[i]]);
                else availableList[availableList.length - 1].push(availableDatetimes[i]);
            }
            if (availableDatetimes.length > 0) {
                if (Math.abs(availableDatetimes[availableDatetimes.length - 2].diff(availableDatetimes[availableDatetimes.length - 1], "hours")) > 1)
                    availableList.push([availableDatetimes[availableDatetimes.length - 1]]);
                else availableList[availableList.length - 1].push(availableDatetimes[availableDatetimes.length - 1]);
            }
            state.validation[category].available = availableList.map(datetimes => datetimes.length === 1 ? [datetimes[0]] : [datetimes[0], dayjs(datetimes[datetimes.length - 1].format("YYYYMMDDHH5959"))]);
        }
    },
    actions: {
        SET_VALIDATION_SELECTED_ITEM: (context, item) => context.commit("SET_VALIDATION_SELECTED_ITEM", item),
        SET_VALIDATION_AVAILABLE: (context, { category, available }) => context.commit("SET_VALIDATION_AVAILABLE", { category, available })
    }
}
