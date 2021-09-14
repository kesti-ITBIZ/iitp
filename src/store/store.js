import Vue from "vue";
import Vuex from "vuex";

import dayjs from "dayjs";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        data: [
            { label: "전체", value: "ALL" },
            { label: "환경부", value: "ME" },
            { label: "에어코리아", value: "AIRKOREA" },
            { label: "S-DoT", value: "AIRMAP" },
            { label: "옵저버", value: "OBSERVER" }
        ],
        selectedData: "AIRKOREA",
        items: [
            { seq: 0, label: "지점", value: "stn" },
            { seq: 1, label: "측정 시간", value: "obsTime" },
            { seq: 2, label: "풍향(˚)", value: "wd" },
            { seq: 3, label: "PM10(㎍/㎥)", value: "pm10" },
            { seq: 4, label: "PM2.5(㎍/㎥)", value: "pm25" },
            { seq: 5, label: "온도(℃)", value: "ta" },
            { seq: 6, label: "습도(%)", value: "hm" },
            { seq: 7, label: "풍속(㎧)", value: "ws" },
            { seq: 8, label: "고농도 일수", value: "highDnstyDays" },
        ],
        selectedItems: [],
        xAxis: [],
        yAxis: [],
        startDatetime: dayjs().format("YYYY.MM.DD HH"),
        endDatetime: dayjs().format("YYYY.MM.DD HH"),
        dateTypes: [
            { label: "시간", type: "datetime", format: "YYYY.MM.DD HH" },
            { label: "일", type: "date", format: "YYYY.MM.DD" },
            { label: "월", type: "month", format: "YYYY.MM" },
            { label: "년", type: "year", format: "YYYY" }
        ],
        selectedDateType: "datetime",
        selectedChartType: "line"
    },
    getters: {

    },
    mutations: {
        SET_SELECTED_DATA: (state, data) => state.selectedData = Object.freeze(data),
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
        },
        SET_START_DATETIME: (state, datetime) => state.startDatetime = datetime,
        SET_END_DATETIME: (state, datetime) => state.endDatetime = datetime,
        SET_SELECTED_DATE_TYPE: (state, dateType) => state.selectedDateType = Object.freeze(dateType),
        SET_SELECTED_CHART_TYPE: (state, chartType) => state.selectedChartType = chartType
    },
    actions: {
        SET_SELECTED_DATA: (context, data) => context.commit("SET_SELECTED_DATA", data),
        SET_SELECTED_ITEM: (context, item) => context.commit("SET_SELECTED_ITEM", item),
        REMOVE_SELECTED_ITEM: (context, item) => context.commit("REMOVE_SELECTED_ITEM", item),
        CLEAR_SELECTED_ITEM: context => context.commit("CLEAR_SELECTED_ITEM"),
        ADD_X_AXIS: (context, items) => context.commit("ADD_X_AXIS", items),
        REMOVE_X_AXIS: (context, item) => context.commit("REMOVE_X_AXIS", item),
        ADD_Y_AXIS: (context, items) => context.commit("ADD_Y_AXIS", items),
        REMOVE_Y_AXIS: (context, item) => context.commit("REMOVE_Y_AXIS", item),
        SET_START_DATETIME: (context, datetime) => context.commit("SET_START_DATETIME", datetime),
        SET_END_DATETIME: (context, datetime) => context.commit("SET_END_DATETIME", datetime),
        SET_SELECTED_DATE_TYPE: (context, dateType) => context.commit("SET_SELECTED_DATE_TYPE", dateType),
        SET_SELECTED_CHART_TYPE: (context, chartType) => context.commit("SET_SELECTED_CHART_TYPE", chartType)
    }
});
