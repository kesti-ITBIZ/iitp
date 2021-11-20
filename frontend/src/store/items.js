export default {
    state: {
        airkorea: {
            items: Object.freeze([
                { seq: 0, label: "지점", value: "station", unit: "" },
                { seq: 1, label: "SO2", value: "so2", unit: "ppm" },
                { seq: 2, label: "CO", value: "co", unit: "ppm" },
                { seq: 3, label: "O3", value: "o3", unit: "ppm" },
                { seq: 4, label: "NO2", value: "no2", unit: "ppm" },
                { seq: 5, label: "PM10", value: "pm10", unit: "㎍/㎥" },
                { seq: 6, label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
            ]),
            selectedItems: Object.freeze([]),
            xAxis: Object.freeze([]),
            yAxis: Object.freeze([])
        },
        kt: {
            items: Object.freeze([
                { seq: 0, label: "지점", value: "station", unit: "" },
                { seq: 1, label: "기온", value: "temperature", unit: "℃" },
                { seq: 2, label: "습도", value: "humidity", unit: "%" },
                { seq: 3, label: "PM10", value: "pm10", unit: "㎍/㎥" },
                { seq: 4, label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
            ]),
            selectedItems: Object.freeze([]),
            xAxis: Object.freeze([]),
            yAxis: Object.freeze([])
        },
        sDoT: {
            items: Object.freeze([
                { seq: 0, label: "지점", value: "station", unit: "" },
                { seq: 1, label: "기온", value: "temperature", unit: "℃" },
                { seq: 2, label: "상대습도", value: "relativeHumidity", unit: "%" },
                { seq: 3, label: "풍향", value: "windDirection", unit: "˚" },
                { seq: 4, label: "풍속", value: "windSpeed", unit: "㎧" },
                { seq: 5, label: "PM10", value: "pm10", unit: "㎍/㎥" },
                { seq: 6, label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
            ]),
            selectedItems: Object.freeze([]),
            xAxis: Object.freeze([]),
            yAxis: Object.freeze([])
        },
        observer: {
            items: Object.freeze([
                { seq: 0, label: "지점", value: "station", unit: "" },
                { seq: 1, label: "기온", value: "temperature", unit: "℃" },
                { seq: 2, label: "습도", value: "humidity", unit: "%" },
                { seq: 3, label: "기압", value: "pressure", unit: "㎪" },
                { seq: 4, label: "PM2.5", value: "pm25", unit: "㎍/㎥" }
            ]),
            selectedItems: Object.freeze([]),
            xAxis: Object.freeze([]),
            yAxis: Object.freeze([])
        },
    },
    getters: {

    },
    mutations: {
        SET_SELECTED_ITEM: (state, item) => {
            const category = state.selectedCategory;
            const selectedItems = state[category].selectedItems.concat();
            const index = selectedItems.indexOf(item);
            if (index === -1)
                selectedItems.push(item);
            else selectedItems.splice(index, 1);
            state[category].selectedItems = Object.freeze(selectedItems);
        },
        REMOVE_SELECTED_ITEM: (state, removeItems) => {
            const category = state.selectedCategory;
            const items = state[category].items.concat();
            removeItems.forEach(item => {
                const index = items.findIndex(obj => obj.label === item.label && obj.value === item.value);
                if (index !== -1) items.splice(index, 1);
            });
            state[category].items = Object.freeze(items);
        },
        CLEAR_SELECTED_ITEM: state => state[state.selectedCategory].selectedItems = Object.freeze([]),
        ADD_X_AXIS: (state, items) => {
            const category = state.selectedCategory;
            const xAxis = state[category].xAxis.concat();
            items.forEach(item => {
                if (xAxis.indexOf(item) === -1)
                    xAxis.push(item);
            });
            xAxis.sort();
            state[category].xAxis = Object.freeze(xAxis);
        },
        REMOVE_X_AXIS: (state, item) => {
            const category = state.selectedCategory;
            const xAxis = state[category].xAxis.concat();
            const index = xAxis.findIndex(obj => obj.label === item.label && obj.value === item.value);
            if (index !== -1) {
                xAxis.splice(index, 1);
                const items = state[category].items.concat(item);
                items.sort((a, b) => a.seq < b.seq ? -1 : 1);
                state[category].items = Object.freeze(items);
            }
            state[category].xAxis = Object.freeze(xAxis);
        },
        ADD_Y_AXIS: (state, items) => {
            const category = state.selectedCategory;
            const yAxis = state[category].yAxis.concat();
            items.forEach(item => {
                if (yAxis.indexOf(item) === -1)
                    yAxis.push(item);
            });
            yAxis.sort();
            state[category].yAxis = Object.freeze(yAxis);
        },
        REMOVE_Y_AXIS: (state, item) => {
            const category = state.selectedCategory;
            const yAxis = state[category].yAxis.concat();
            const index = yAxis.findIndex(obj => obj.label === item.label && obj.value === item.value);
            if (index !== -1) {
                yAxis.splice(index, 1);
                const items = state[category].items.concat(item);
                items.sort((a, b) => a.seq < b.seq ? -1 : 1);
                state[category].items = Object.freeze(items);
            }
            state[category].yAxis = Object.freeze(yAxis);
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
