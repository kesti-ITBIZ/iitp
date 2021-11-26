import dayjs from "dayjs";

export default {
    state: {
        startDatetime: dayjs(dayjs().subtract(6, "days").format("YYYY.MM.DD 00:00"), "YYYY.MM.DD HH:mm"),
        endDatetime: dayjs(),
        dateTypes: Object.freeze([
            { label: "시간", type: "hour", stringToDayjsFormat: "YYYY.MM.DD HH:mm", dayjsToStringFormat: "YYYY.MM.DD HH" },
            { label: "일", type: "date", stringToDayjsFormat: "YYYY.MM.DD", dayjsToStringFormat: "YYYY.MM.DD" },
            { label: "월", type: "month", stringToDayjsFormat: "YYYY.MM.DD", dayjsToStringFormat: "YYYY.MM" },
            { label: "년", type: "year", stringToDayjsFormat: "YYYY", dayjsToStringFormat: "YYYY" }
        ]),
        selectedDateType: "hour",

        fineParticleRanges: Object.freeze([
            {
                pm10: [0, 30],
                pm25: [0, 15]
            },
            {
                pm10: [31, 80],
                pm25: [16, 35]
            },
            {
                pm10: [81, 150],
                pm25: [36, 75]
            },
            {
                pm10: [151, null],
                pm25: [76, null]
            }
        ]),
        selectedFineParticleRange: Object.freeze({
            pm10: [0, null],
            pm25: [0, null]
        })
    },
    getters: {

    },
    mutations: {
        SET_START_DATETIME: (state, datetime) => state.startDatetime = datetime,
        SET_END_DATETIME: (state, datetime) => state.endDatetime = datetime,
        SET_SELECTED_DATE_TYPE: (state, dateType) => state.selectedDateType = dateType,
        SET_SELECTED_FINE_PARTICLE_RANGE: (state, range) => state.selectedFineParticleRange = Object.freeze(range)
    },
    actions: {
        SET_START_DATETIME: (context, datetime) => context.commit("SET_START_DATETIME", datetime),
        SET_END_DATETIME: (context, datetime) => context.commit("SET_END_DATETIME", datetime),
        SET_SELECTED_DATE_TYPE: (context, dateType) => context.commit("SET_SELECTED_DATE_TYPE", dateType),
        SET_SELECTED_FINE_PARTICLE_RANGE: (context, range) => context.commit("SET_SELECTED_FINE_PARTICLE_RANGE", range)
    }
}
