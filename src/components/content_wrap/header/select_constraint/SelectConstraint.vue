<template>
    <th id="select-constraint" rowspan="2">
        <div>
            <h5>미세먼지 농도</h5>
            <vue-slider
                    v-model="degree"
                    v-bind="{ ...options, dataValue: 'degree', min: 1, max: 4 }"
                    :data="['좋음', '보통', '나쁨', '매우나쁨']" />
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
    import { mapActions } from "vuex";
    import dayjs from "dayjs";

    export default {
        name: "SelectConstraint",
        data: () => ({
            degree: '좋음',
            month: '1월',
            season: '봄',
            options: {
                dotSize: 20,
                width: 500,
                height: 15,
                contained: false,
                direction: 'ltr',
                data: null,
                dataLabel: 'label',
                dataValue: 'value',
                interval: 1,
                disabled: false,
                clickable: true,
                duration: 0,
                adsorb: true,
                lazy: false,
                tooltip: 'active',
                tooltipPlacement: 'top',
                tooltipFormatter: undefined,
                useKeyboard: false,
                keydownHook: null,
                dragOnClick: false,
                enableCross: true,
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
            }
        }),
        computed: {

        },
        methods: {
            ...mapActions({
                setSelectedDateType: "SET_SELECTED_DATE_TYPE",
                setStartDatetime: "SET_START_DATETIME",
                setEndDatetime: "SET_END_DATETIME"
            }),

            onChangeMonthSlider(month) {
                const year = dayjs().year();
                let monthValue = +month.substring(0, month.length - 1);
                monthValue = (monthValue < 10 ? "0" : "") + monthValue;
                this.setSelectedDateType("month");
                this.setStartDatetime(year + "." + monthValue);
                this.setEndDatetime(year + "." + monthValue);
            },

            onChangeSeasonSlider(season) {
                const year = dayjs().year();
                this.setSelectedDateType("month");
                switch (season) {
                case "봄":
                    this.setStartDatetime(year + ".03");
                    this.setEndDatetime(year + ".05");
                    break;
                case "여름":
                    this.setStartDatetime(year + ".06");
                    this.setEndDatetime(year + ".08");
                    break;
                case "가을":
                    this.setStartDatetime(year + ".09");
                    this.setEndDatetime(year + ".11");
                    break;
                case "겨울":
                    this.setStartDatetime(year + ".12");
                    this.setEndDatetime((year + 1) + ".02");
                    break;
                }
            }
        }
    }
</script>

<style>
    @import "./SelectConstraint.css";
</style>
