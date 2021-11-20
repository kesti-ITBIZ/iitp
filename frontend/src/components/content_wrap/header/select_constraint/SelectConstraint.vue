<template>
    <th id="select-constraint" rowspan="2">
        <div>
            <h5>미세먼지 농도</h5>
            <vue-slider
                    v-model="degree"
                    v-bind="{ ...options, dataValue: 'degree', min: 1, max: 4 }"
                    :data="['좋음', '보통', '나쁨', '매우나쁨']"
                    @change="onChangeFineParticleSlider" />
        </div>
        <hr />
        <div>
            <h5>월</h5>
            <vue-slider
                    v-model="month"
                    v-bind="{ ...options, dataValue: 'month', min: 1, max: 12 }"
                    :data="['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']"
                    @change="onChangeMonthSlider" />
        </div>
        <hr />
        <div>
            <h5>계절</h5>
            <vue-slider
                    v-model="season"
                    v-bind="{ ...options, dataValue: 'season', min: 1, max: 4 }"
                    :data="['봄', '여름', '가을', '겨울']"
                    @change="onChangeSeasonSlider" />
        </div>
    </th>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import dayjs from "dayjs";

    export default {
        name: "SelectConstraint",
        data: () => ({
            degree: ["좋음", "매우나쁨"],
            month: ["1월", "12월"],
            season: ["봄", "겨울"],
            options: {
                dotSize: 20,
                width: 500,
                height: 15,
                contained: false,
                direction: "ltr",
                data: null,
                dataLabel: "label",
                dataValue: "value",
                interval: 1,
                disabled: false,
                clickable: true,
                duration: 0,
                adsorb: true,
                lazy: false,
                tooltip: "active",
                tooltipPlacement: "top",
                tooltipFormatter: undefined,
                useKeyboard: false,
                keydownHook: null,
                dragOnClick: false,
                enableCross: false,
                fixed: false,
                minRange: undefined,
                maxRange: undefined,
                order: true,
                marks: true,
                dotOptions: undefined,
                dotAttrs: undefined,
                process: true,
                dotStyle: undefined,
                railStyle: undefined,
                processStyle: undefined,
                tooltipStyle: undefined,
                stepStyle: undefined,
                stepActiveStyle: undefined,
                labelStyle: undefined,
                labelActiveStyle: undefined
            },
            preMonthRange: ["1월", "12월"],
            preSeasonRange: ["봄", "겨울"]
        }),
        computed: {
            ...mapState({
                selectedDateType: state => state.selectedDateType,
                fineParticleRanges: state => state.fineParticleRanges
            })
        },
        methods: {
            ...mapActions({
                setSelectedDateType: "SET_SELECTED_DATE_TYPE",
                setStartDatetime: "SET_START_DATETIME",
                setEndDatetime: "SET_END_DATETIME",
                setSelectedFineParticleRange: "SET_SELECTED_FINE_PARTICLE_RANGE"
            }),

            onChangeFineParticleSlider(fineParticle) {
                const ranges = ["좋음", "보통", "나쁨", "매우나쁨"];
                const begin = this.fineParticleRanges[ranges.indexOf(fineParticle[0])];
                const end = this.fineParticleRanges[ranges.indexOf(fineParticle[1])];
                this.setSelectedFineParticleRange({
                    pm10: [begin.pm10[0], end.pm10[1]],
                    pm25: [begin.pm25[0], end.pm25[1]]
                });
            },

            onChangeMonthSlider(month) {
                if (this.selectedDateType != "month")
                    this.setSelectedDateType("month");
                const year = dayjs().year();
                if (this.preMonthRange[0] !== month[0])
                    this.setStartDatetime(dayjs(year + "." + dayjs(month[0].substring(0, month[0].length - 1), "M").format("MM") + ".01", "YYYY.MM.DD"));
                if (this.preMonthRange[1] !== month[1])
                    this.setEndDatetime(dayjs(year + "." + dayjs(month[1].substring(0, month[1].length - 1), "M").format("MM") + ".01", "YYYY.MM.DD"));
            },

            onChangeSeasonSlider(season) {
                const year = dayjs().year();
                if (this.selectedDateType != "month")
                    this.setSelectedDateType("month");
                let seasonRange;
                if (this.preSeasonRange[0] !== season[0] || this.preSeasonRange[1] !== season[1])
                    seasonRange = this.preSeasonRange.concat();
                if (this.preSeasonRange[0] !== season[0]) {
                    switch (season[0]) {
                    case "봄": this.setStartDatetime(dayjs(year + ".03.01", "YYYY.MM.DD")); break;
                    case "여름": this.setStartDatetime(dayjs(year + ".06.01", "YYYY.MM.DD")); break;
                    case "가을": this.setStartDatetime(dayjs(year + ".09.01", "YYYY.MM.DD")); break;
                    case "겨울": this.setStartDatetime(dayjs(year + ".12.01", "YYYY.MM.DD")); break;
                    }
                    if (seasonRange != null) seasonRange[0] = season[0];
                }
                if (this.preSeasonRange[1] !== season[1]) {
                    switch (season[1]) {
                    case "봄": this.setEndDatetime(dayjs(year + ".05.01", "YYYY.MM.DD")); break;
                    case "여름": this.setEndDatetime(dayjs(year + ".08.01", "YYYY.MM.DD")); break;
                    case "가을": this.setEndDatetime(dayjs(year + ".11.01", "YYYY.MM.DD")); break;
                    case "겨울": this.setEndDatetime(dayjs((year + 1) + ".02.01", "YYYY.MM.DD")); break;
                    }
                    if (seasonRange != null) seasonRange[1] = season[1];
                }
                if (this.preSeasonRange[0] !== season[0] || this.preSeasonRange[1] !== season[1])
                    this.preSeasonRange = Object.freeze(seasonRange);
            }
        }
    }
</script>

<style>
    @import "./SelectConstraint.css";
</style>
