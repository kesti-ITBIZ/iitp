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
                    :data="['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']" />
        </div>
        <hr />
        <div>
            <h5>계절</h5>
            <vue-slider
                    v-model="season"
                    v-bind="{ ...options, dataValue: 'season', min: 1, max: 4 }"
                    :data="['봄', '여름', '가을', '겨울']" />
        </div>
    </th>
</template>

<script>
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

        }
    }
</script>

<style>
    @import "./SelectConstraint.css";
</style>
