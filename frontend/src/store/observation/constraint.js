import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";

dayjs.extend(customParseFormat);

export default {
    state: {
        startDatetime: dayjs(dayjs().subtract(31, "days").format("YYYY.MM.DD 00:00"), "YYYY.MM.DD HH:mm"),
        endDatetime: dayjs(),
        fetchedStartDatetime: null,
        fetchedEndDatetime: null,
        dateTypes: Object.freeze([
            { label: "시간", type: "hour", stringToDayjsFormat: "YYYY.MM.DD HH:mm", dayjsToStringFormat: "YYYY.MM.DD HH" },
            { label: "일", type: "date", stringToDayjsFormat: "YYYY.MM.DD", dayjsToStringFormat: "YYYY.MM.DD" },
            { label: "월", type: "month", stringToDayjsFormat: "YYYY.MM.DD", dayjsToStringFormat: "YYYY.MM" },
            { label: "년", type: "year", stringToDayjsFormat: "YYYY", dayjsToStringFormat: "YYYY" }
        ]),
        selectedDateType: "hour"
    },
    getters: {

    },
    mutations: {
        SET_OBSERVATION_START_DATETIME: (state, datetime) => state.observation.startDatetime = datetime,
        SET_OBSERVATION_END_DATETIME: (state, datetime) => state.observation.endDatetime = datetime,
        SET_OBSERVATION_SELECTED_DATE_TYPE: (state, dateType) => state.observation.selectedDateType = dateType
    },
    actions: {
        SET_OBSERVATION_START_DATETIME: (context, datetime) => context.commit("SET_OBSERVATION_START_DATETIME", datetime),
        SET_OBSERVATION_END_DATETIME: (context, datetime) => context.commit("SET_OBSERVATION_END_DATETIME", datetime),
        SET_OBSERVATION_SELECTED_DATE_TYPE: (context, dateType) => context.commit("SET_OBSERVATION_SELECTED_DATE_TYPE", dateType)
    }
}
