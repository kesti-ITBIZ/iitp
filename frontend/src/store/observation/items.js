import dayjs from "dayjs";

export default {
    state: {
        airkorea: {
            items: (() => {
                let items = [
                    { label: "측정 시간", value: "datetime", unit: "" },
                    { label: "SO2", value: "so2", unit: "ppm" },
                    { label: "CO", value: "co", unit: "ppm" },
                    { label: "O3", value: "o3", unit: "ppm" },
                    { label: "NO2", value: "no2", unit: "ppm" },
                    { label: "PM10", value: "pm10", unit: "㎍/㎥" },
                    { label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
                ];
                for (let i = 0; i < items.length; ++i) items[i]["seq"] = i;
                return Object.freeze(items);
            })(),
            selectedItem: null,
            xAxis: Object.freeze([]),
            yAxis: Object.freeze([]),
            available: []
        },
        kt: {
            items: (() => {
                let items = [
                    { label: "측정 시간", value: "datetime", unit: "" },
                    { label: "기온", value: "temperature", unit: "℃" },
                    { label: "습도", value: "humidity", unit: "%" },
                    { label: "PM10", value: "pm10", unit: "㎍/㎥" },
                    { label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
                ];
                for (let i = 0; i < items.length; ++i) items[i]["seq"] = i;
                return Object.freeze(items);
            })(),
            selectedItem: null,
            xAxis: Object.freeze([]),
            yAxis: Object.freeze([]),
            available: []
        },
        sDoT: {
            items: (() => {
                let items = [
                    { label: "측정 시간", value: "datetime", unit: "" },
                    { label: "풍향", value: "windDirection", unit: "˚" },
                    { label: "기온", value: "temperature", unit: "℃" },
                    { label: "상대습도", value: "relativeHumidity", unit: "%" },
                    { label: "풍속", value: "windSpeed", unit: "㎧" },
                    { label: "PM10", value: "pm10", unit: "㎍/㎥" },
                    { label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
                ];
                for (let i = 0; i < items.length; ++i) items[i]["seq"] = i;
                return Object.freeze(items);
            })(),
            selectedItem: null,
            xAxis: Object.freeze([]),
            yAxis: Object.freeze([]),
            available: []
        },
        observer: {
            items: (() => {
                let items = [
                    { label: "측정 시간", value: "datetime", unit: "" },
                    { label: "기온", value: "temperature", unit: "℃" },
                    { label: "습도", value: "humidity", unit: "%" },
                    { label: "기압", value: "pressure", unit: "㎩" },
                    { label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
                ];
                for (let i = 0; i < items.length; ++i) items[i]["seq"] = i;
                return Object.freeze(items);
            })(),
            selectedItem: null,
            xAxis: Object.freeze([]),
            yAxis: Object.freeze([]),
            available: []
        },
    },
    getters: {

    },
    mutations: {
        SET_SELECTED_ITEM: (state, item) => state.observation[state.observation.selectedCategory].selectedItem = state.observation[state.observation.selectedCategory].selectedItem === item ? null : item,
        ADD_X_AXIS: (state, item) => {
            const category = state.observation.selectedCategory;
            state.observation[category].xAxis = Object.freeze(state.observation[category].xAxis.concat([item]));
            const items = state.observation[category].items.concat();
            items.splice(items.findIndex(obj => obj.value === item.value), 1);
            state.observation[category].items = Object.freeze(items);
        },
        REMOVE_X_AXIS: state => {
            const category = state.observation.selectedCategory;
            const items = state.observation[category].items.concat(state.observation[category].xAxis);
            items.sort((a, b) => a.seq < b.seq ? -1 : 1);
            state.observation[category].items = Object.freeze(items);
            state.observation[category].xAxis = Object.freeze([]);
        },
        ADD_Y_AXIS: (state, item) => {
            const category = state.observation.selectedCategory;
            state.observation[category].yAxis = Object.freeze(state.observation[category].yAxis.concat([item]));
            const items = state.observation[category].items.concat();
            items.splice(items.findIndex(obj => obj.value === item.value), 1);
            state.observation[category].items = Object.freeze(items);
        },
        REMOVE_Y_AXIS: (state, item) => {
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
        SET_AVAILABLE: (state, { category, available }) => {
            const format = "YYYY년 MM월 DD일 HH시";
            let availableDatetimes = available.map(dayjs), availableList = [];
            for (let i = 0; i < availableDatetimes.length - 1; ++i) {
                if (i === 0 || (i > 0 && Math.abs(availableDatetimes[i - 1].diff(availableDatetimes[i], "hours")) > 1))
                    availableList.push([availableDatetimes[i].format(format)]);
                else availableList[availableList.length - 1].push(availableDatetimes[i].format(format));
            }
            if (availableDatetimes.length > 0) {
                if (Math.abs(availableDatetimes[availableDatetimes.length - 2].diff(availableDatetimes[availableDatetimes.length - 1], "hours")) > 1)
                    availableList.push([availableDatetimes[availableDatetimes.length - 1].format(format)]);
                else availableList[availableList.length - 1].push(availableDatetimes[availableDatetimes.length - 1].format(format));
            }
            state.observation[category].available = availableList.map(datetimes => datetimes.length === 1 ? datetimes[0] : `${datetimes[0]} ~ ${datetimes[datetimes.length - 1]}`);
        }
    },
    actions: {
        SET_SELECTED_ITEM: (context, item) => context.commit("SET_SELECTED_ITEM", item),
        ADD_X_AXIS: (context, item) => context.commit("ADD_X_AXIS", item),
        REMOVE_X_AXIS: context => context.commit("REMOVE_X_AXIS"),
        ADD_Y_AXIS: (context, items) => context.commit("ADD_Y_AXIS", items),
        REMOVE_Y_AXIS: (context, item) => context.commit("REMOVE_Y_AXIS", item),
        SET_AVAILABLE: (context, { category, available }) => context.commit("SET_AVAILABLE", { category, available })
    }
}
