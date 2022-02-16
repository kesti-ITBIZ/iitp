import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";

dayjs.extend(customParseFormat);

export default {
    state: {
        startDatetime: dayjs(dayjs().subtract(6, "days").format("YYYY.MM.DD"), "YYYY.MM.DD"),
        endDatetime: dayjs(),
        dateTypes: Object.freeze([
            { label: "월", type: "month", stringToDayjsFormat: "YYYY.MM.DD", dayjsToStringFormat: "YYYY.MM" },
            { label: "년", type: "year", stringToDayjsFormat: "YYYY", dayjsToStringFormat: "YYYY" }
        ]),
        selectedDateType: "month"
    },
    getters: {

    },
    mutations: {
        SET_VALIDATION_START_DATETIME: (state, datetime) => state.validation.startDatetime = datetime,
        SET_VALIDATION_END_DATETIME: (state, datetime) => state.validation.endDatetime = datetime,
        SET_VALIDATION_SELECTED_DATE_TYPE: (state, dateType) => state.validation.selectedDateType = dateType
    },
    actions: {
        SET_VALIDATION_START_DATETIME: (context, datetime) => context.commit("SET_VALIDATION_START_DATETIME", datetime),
        SET_VALIDATION_END_DATETIME: (context, datetime) => context.commit("SET_VALIDATION_END_DATETIME", datetime),
        SET_VALIDATION_SELECTED_DATE_TYPE: (context, dateType) => context.commit("SET_VALIDATION_SELECTED_DATE_TYPE", dateType)
    }
}
