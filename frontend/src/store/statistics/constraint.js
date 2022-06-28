import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";

dayjs.extend(customParseFormat);

export default {
    state: {
        startDatetime: dayjs(dayjs().subtract(6, "days").format("YYYY.MM.DD"), "YYYY.MM.DD"),
        endDatetime: dayjs(),
        fetchedStartDatetime: null,
        fetchedEndDatetime: null,
        dateTypes: Object.freeze([
            { label: "월", type: "month", stringToDayjsFormat: "YYYY.MM.DD", dayjsToStringFormat: "YYYY.MM" },
            { label: "년", type: "year", stringToDayjsFormat: "YYYY", dayjsToStringFormat: "YYYY" }
        ]),
        selectedDateType: "month"
    },
    getters: {

    },
    mutations: {
        SET_STATISTICS_START_DATETIME: (state, datetime) => state.statistics.startDatetime = datetime,
        SET_STATISTICS_END_DATETIME: (state, datetime) => state.statistics.endDatetime = datetime,
        SET_STATISTICS_SELECTED_DATE_TYPE: (state, dateType) => state.statistics.selectedDateType = dateType
    },
    actions: {
        SET_STATISTICS_START_DATETIME: (context, datetime) => context.commit("SET_STATISTICS_START_DATETIME", datetime),
        SET_STATISTICS_END_DATETIME: (context, datetime) => context.commit("SET_STATISTICS_END_DATETIME", datetime),
        SET_STATISTICS_SELECTED_DATE_TYPE: (context, dateType) => context.commit("SET_STATISTICS_SELECTED_DATE_TYPE", dateType)
    }
}
