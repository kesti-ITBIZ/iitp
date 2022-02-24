import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";

dayjs.extend(customParseFormat);

export default {
    state: {
        startDatetime: dayjs(dayjs().subtract(6, "days").format("YYYY.MM.DD"), "YYYY.MM.DD"),
        endDatetime: dayjs(),
        dateTypes: Object.freeze([
            { label: "시간", type: "hour", stringToDayjsFormat: "YYYY.MM.DD HH:mm", dayjsToStringFormat: "YYYY.MM.DD HH" },
            { label: "일", type: "date", stringToDayjsFormat: "YYYY.MM.DD", dayjsToStringFormat: "YYYY.MM.DD" }
        ]),
        selectedDateType: "hour"
    },
    getters: {

    },
    mutations: {
        SET_VERIFICATION_START_DATETIME: (state, datetime) => state.verification.startDatetime = datetime,
        SET_VERIFICATION_END_DATETIME: (state, datetime) => state.verification.endDatetime = datetime,
        SET_VERIFICATION_SELECTED_DATE_TYPE: (state, dateType) => state.verification.selectedDateType = dateType
    },
    actions: {
        SET_VERIFICATION_START_DATETIME: (context, datetime) => context.commit("SET_VERIFICATION_START_DATETIME", datetime),
        SET_VERIFICATION_END_DATETIME: (context, datetime) => context.commit("SET_VERIFICATION_END_DATETIME", datetime),
        SET_VERIFICATION_SELECTED_DATE_TYPE: (context, dateType) => context.commit("SET_VERIFICATION_SELECTED_DATE_TYPE", dateType)
    }
}
