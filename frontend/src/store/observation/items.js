import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";

dayjs.extend(customParseFormat);

export default {
    state: {
        airkorea: {
            items: (() => {
                let items = [
                    { label: "SO2", value: "so2", unit: "ppm" },
                    { label: "CO", value: "co", unit: "ppm" },
                    { label: "O3", value: "o3", unit: "ppm" },
                    { label: "NO2", value: "no2", unit: "ppm" },
                    { label: "PM10", value: "pm10", unit: "㎍/㎥" },
                    { label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
                ];
                for (let i = 0; i < items.length; ++i) items[i]["seq"] = i + 1;
                return Object.freeze(items);
            })(),
            selectedItem: null,
            xAxis: Object.freeze([{ label: "측정 시간", seq: 0, value: "datetime", unit: "" }]),
            yAxis: Object.freeze([]),
            available: []
        },
        kt: {
            items: (() => {
                let items = [
                    { label: "기온", value: "temperature", unit: "℃" },
                    { label: "습도", value: "humidity", unit: "%" },
                    { label: "PM10", value: "pm10", unit: "㎍/㎥" },
                    { label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
                ];
                for (let i = 0; i < items.length; ++i) items[i]["seq"] = i + 1;
                return Object.freeze(items);
            })(),
            selectedItem: null,
            xAxis: Object.freeze([{ label: "측정 시간", seq: 0, value: "datetime", unit: "" }]),
            yAxis: Object.freeze([]),
            available: []
        },
        sDoT: {
            items: (() => {
                let items = [
                    { label: "기온", value: "temperature", unit: "℃" },
                    { label: "상대습도", value: "relativeHumidity", unit: "%" },
                    { label: "PM10", value: "pm10", unit: "㎍/㎥" },
                    { label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
                ];
                for (let i = 0; i < items.length; ++i) items[i]["seq"] = i + 1;
                return Object.freeze(items);
            })(),
            selectedItem: null,
            xAxis: Object.freeze([{ label: "측정 시간", seq: 0, value: "datetime", unit: "" }]),
            yAxis: Object.freeze([]),
            available: []
        },
        observer: {
            items: (() => {
                let items = [
                    { label: "기온", value: "temperature", unit: "℃" },
                    { label: "습도", value: "humidity", unit: "%" },
                    { label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
                ];
                for (let i = 0; i < items.length; ++i) items[i]["seq"] = i + 1;
                return Object.freeze(items);
            })(),
            selectedItem: null,
            xAxis: Object.freeze([{ label: "측정 시간", seq: 0, value: "datetime", unit: "" }]),
            yAxis: Object.freeze([]),
            available: []
        },
    },
    getters: {

    },
    mutations: {
        SET_OBSERVATION_SELECTED_ITEM: (state, item) => state.observation[state.observation.selectedCategory].selectedItem = Object.freeze(state.observation[state.observation.selectedCategory].selectedItem === item ? null : item),
        ADD_OBSERVATION_X_AXIS: (state, item) => {
            const category = state.observation.selectedCategory;
            state.observation[category].xAxis = Object.freeze(state.observation[category].xAxis.concat([item]));
            const items = state.observation[category].items.concat();
            items.splice(items.findIndex(obj => obj.value === item.value), 1);
            state.observation[category].items = Object.freeze(items);
        },
        REMOVE_OBSERVATION_X_AXIS: state => {
            const category = state.observation.selectedCategory;
            const items = state.observation[category].items.concat(state.observation[category].xAxis);
            items.sort((a, b) => a.seq < b.seq ? -1 : 1);
            state.observation[category].items = Object.freeze(items);
            state.observation[category].xAxis = Object.freeze([]);
        },
        ADD_OBSERVATION_Y_AXIS: (state, item) => {
            const category = state.observation.selectedCategory;
            state.observation[category].yAxis = Object.freeze(state.observation[category].yAxis.concat([item]));
            const items = state.observation[category].items.concat();
            items.splice(items.findIndex(obj => obj.value === item.value), 1);
            state.observation[category].items = Object.freeze(items);
        },
        REMOVE_OBSERVATION_Y_AXIS: (state, item) => {
            const category = state.observation.selectedCategory;
            const yAxis = state.observation[category].yAxis.concat();
            const index = yAxis.findIndex(obj => obj.value === item.value);
            if (index !== -1) {
                yAxis.splice(index, 1);
                const items = state.observation[category].items.concat(item);
                items.sort((a, b) => a.seq < b.seq ? -1 : 1);
                state.observation[category].items = Object.freeze(items);
            }
            state.observation[category].yAxis = Object.freeze(yAxis);
        },
        SET_OBSERVATION_AVAILABLE: (state, { category, available }) => {
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
            state.observation[category].available = availableList.map(datetimes => datetimes.length === 1 ? [datetimes[0]] : [datetimes[0], dayjs(datetimes[datetimes.length - 1].format("YYYYMMDDHH5959"))]);
        }
    },
    actions: {
        SET_OBSERVATION_SELECTED_ITEM: (context, item) => context.commit("SET_OBSERVATION_SELECTED_ITEM", item),
        ADD_OBSERVATION_X_AXIS: (context, item) => context.commit("ADD_OBSERVATION_X_AXIS", item),
        REMOVE_OBSERVATION_X_AXIS: context => context.commit("REMOVE_OBSERVATION_X_AXIS"),
        ADD_OBSERVATION_Y_AXIS: (context, items) => context.commit("ADD_OBSERVATION_Y_AXIS", items),
        REMOVE_OBSERVATION_Y_AXIS: (context, item) => context.commit("REMOVE_OBSERVATION_Y_AXIS", item),
        SET_OBSERVATION_AVAILABLE: (context, { category, available }) => context.commit("SET_OBSERVATION_AVAILABLE", { category, available })
    }
}
