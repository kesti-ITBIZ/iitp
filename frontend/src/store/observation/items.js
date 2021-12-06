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
            selectedItems: Object.freeze([]),
            xAxis: Object.freeze([]),
            yAxis: Object.freeze([])
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
            selectedItems: Object.freeze([]),
            xAxis: Object.freeze([]),
            yAxis: Object.freeze([])
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
            selectedItems: Object.freeze([]),
            xAxis: Object.freeze([]),
            yAxis: Object.freeze([])
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
            selectedItems: Object.freeze([]),
            xAxis: Object.freeze([]),
            yAxis: Object.freeze([])
        },
    },
    getters: {

    },
    mutations: {
        SET_SELECTED_ITEM: (state, item) => {
            const category = state.observation.selectedCategory;
            const selectedItems = state.observation[category].selectedItems.concat();
            const index = selectedItems.indexOf(item);
            if (index === -1)
                selectedItems.push(item);
            else selectedItems.splice(index, 1);
            state.observation[category].selectedItems = Object.freeze(selectedItems);
        },
        REMOVE_SELECTED_ITEM: (state, removeItems) => {
            const category = state.observation.selectedCategory;
            const items = state.observation[category].items.concat();
            removeItems.forEach(item => {
                const index = items.findIndex(obj => obj.label === item.label && obj.value === item.value);
                if (index !== -1) items.splice(index, 1);
            });
            state.observation[category].items = Object.freeze(items);
        },
        CLEAR_SELECTED_ITEM: state => state.observation[state.observation.selectedCategory].selectedItems = Object.freeze([]),
        ADD_X_AXIS: (state, items) => {
            const category = state.observation.selectedCategory;
            const xAxis = state.observation[category].xAxis.concat();
            items.forEach(item => {
                if (xAxis.indexOf(item) === -1)
                    xAxis.push(item);
            });
            xAxis.sort();
            state.observation[category].xAxis = Object.freeze(xAxis);
        },
        REMOVE_X_AXIS: (state, item) => {
            const category = state.observation.selectedCategory;
            const xAxis = state.observation[category].xAxis.concat();
            const index = xAxis.findIndex(obj => obj.label === item.label && obj.value === item.value);
            if (index !== -1) {
                xAxis.splice(index, 1);
                const items = state.observation[category].items.concat(item);
                items.sort((a, b) => a.seq < b.seq ? -1 : 1);
                state.observation[category].items = Object.freeze(items);
            }
            state.observation[category].xAxis = Object.freeze(xAxis);
        },
        ADD_Y_AXIS: (state, items) => {
            const category = state.observation.selectedCategory;
            const yAxis = state.observation[category].yAxis.concat();
            items.forEach(item => {
                if (yAxis.indexOf(item) === -1)
                    yAxis.push(item);
            });
            yAxis.sort();
            state.observation[category].yAxis = Object.freeze(yAxis);
        },
        REMOVE_Y_AXIS: (state, item) => {
            const category = state.observation.selectedCategory;
            const yAxis = state.observation[category].yAxis.concat();
            const index = yAxis.findIndex(obj => obj.label === item.label && obj.value === item.value);
            if (index !== -1) {
                yAxis.splice(index, 1);
                const items = state.observation[category].items.concat(item);
                items.sort((a, b) => a.seq < b.seq ? -1 : 1);
                state.observation[category].items = Object.freeze(items);
            }
            state.observation[category].yAxis = Object.freeze(yAxis);
        }
    },
    actions: {
        SET_SELECTED_ITEM: (context, item) => context.commit("SET_SELECTED_ITEM", item),
        REMOVE_SELECTED_ITEM: (context, item) => context.commit("REMOVE_SELECTED_ITEM", item),
        CLEAR_SELECTED_ITEM: context => context.commit("CLEAR_SELECTED_ITEM"),
        ADD_X_AXIS: (context, items) => context.commit("ADD_X_AXIS", items),
        REMOVE_X_AXIS: (context, item) => context.commit("REMOVE_X_AXIS", item),
        ADD_Y_AXIS: (context, items) => context.commit("ADD_Y_AXIS", items),
        REMOVE_Y_AXIS: (context, item) => context.commit("REMOVE_Y_AXIS", item)
    }
}
