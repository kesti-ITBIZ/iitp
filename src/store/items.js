export default {
    state: {
        items: [
            { seq: 0, label: "지점", value: "stn", unit: "" },
            { seq: 1, label: "풍향(˚)", value: "wd", unit: "˚" },
            { seq: 2, label: "PM10(㎍/㎥)", value: "pm10", unit: "㎍/㎥" },
            { seq: 3, label: "PM2.5(㎍/㎥)", value: "pm25", unit: "㎍/㎥" },
            { seq: 4, label: "온도(℃)", value: "ta", unit: "℃" },
            { seq: 5, label: "습도(%)", value: "hm", unit: "%" },
            { seq: 6, label: "풍속(㎧)", value: "ws", unit: "㎧" },
            { seq: 7, label: "고농도 일수", value: "highDnstyDays", unit: "일" }
        ],
        selectedItems: [],
        xAxis: [],
        yAxis: []
    },
    getters: {

    },
    mutations: {
        SET_SELECTED_ITEM: (state, item) => {
            const selectedItems = state.selectedItems.concat();
            const index = selectedItems.indexOf(item);
            if (index === -1)
                selectedItems.push(item);
            else selectedItems.splice(index, 1);
            state.selectedItems = Object.freeze(selectedItems);
        },
        REMOVE_SELECTED_ITEM: (state, removeItems) => {
            let items = state.items.concat();
            removeItems.forEach(item => {
                const index = items.findIndex(obj => obj.label === item.label && obj.value === item.value);
                if (index !== -1) items.splice(index, 1);
            });
            state.items = Object.freeze(items);
        },
        CLEAR_SELECTED_ITEM: state => state.selectedItems = [],
        ADD_X_AXIS: (state, items) => {
            const xAxis = state.xAxis.concat();
            items.forEach(item => {
                if (xAxis.indexOf(item) === -1)
                    xAxis.push(item);
            });
            xAxis.sort();
            state.xAxis = Object.freeze(xAxis);
        },
        REMOVE_X_AXIS: (state, item) => {
            const xAxis = state.xAxis.concat();
            const index = xAxis.findIndex(obj => obj.label === item.label && obj.value === item.value);
            if (index !== -1) {
                xAxis.splice(index, 1);
                const items = state.items.concat(item);
                items.sort((a, b) => a.seq < b.seq ? -1 : 1);
                state.items = Object.freeze(items);
            }
            state.xAxis = Object.freeze(xAxis);
        },
        ADD_Y_AXIS: (state, items) => {
            const yAxis = state.yAxis.concat();
            items.forEach(item => {
                if (yAxis.indexOf(item) === -1)
                    yAxis.push(item);
            });
            yAxis.sort();
            state.yAxis = Object.freeze(yAxis);
        },
        REMOVE_Y_AXIS: (state, item) => {
            const yAxis = state.yAxis.concat();
            const index = yAxis.findIndex(obj => obj.label === item.label && obj.value === item.value);
            if (index !== -1) {
                yAxis.splice(index, 1);
                const items = state.items.concat(item);
                items.sort((a, b) => a.seq < b.seq ? -1 : 1);
                state.items = Object.freeze(items);
            }
            state.yAxis = Object.freeze(yAxis);
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
