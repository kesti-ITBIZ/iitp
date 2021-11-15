import dayjs from "dayjs";

export default {
    state: {
        startDatetime: dayjs().format("YYYY.MM.DD HH"),
        endDatetime: dayjs().format("YYYY.MM.DD HH"),
        dateTypes: [
            { label: "시간", type: "hour", format: "YYYY.MM.DD HH:00" },
            { label: "일", type: "date", format: "YYYY.MM.DD" },
            { label: "월", type: "month", format: "YYYY.MM" },
            { label: "년", type: "year", format: "YYYY" }
        ],
        selectedDateType: "hour"
    },
    getters: {

    },
    mutations: {
        SET_START_DATETIME: (state, datetime) => state.startDatetime = datetime,
        SET_END_DATETIME: (state, datetime) => state.endDatetime = datetime,
        SET_SELECTED_DATE_TYPE: (state, dateType) => state.selectedDateType = Object.freeze(dateType)
    },
    actions: {
        SET_START_DATETIME: (context, datetime) => context.commit("SET_START_DATETIME", datetime),
        SET_END_DATETIME: (context, datetime) => context.commit("SET_END_DATETIME", datetime),
        SET_SELECTED_DATE_TYPE: (context, dateType) => context.commit("SET_SELECTED_DATE_TYPE", dateType)
    }
}
