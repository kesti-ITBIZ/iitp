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
    </th>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    export default {
        name: "SelectConstraint",
        data: () => ({
            degree: ["좋음", "매우나쁨"],
            options: {
                dotSize: 20,
                width: "calc(100% - 50px)",
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
            }
        }),
        computed: {
            ...mapState({
                fineParticleRanges: state => state.fineParticleRanges
            })
        },
        methods: {
            ...mapActions({
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
            }
        }
    }
</script>

<style lang="scss">
    @import "./SelectConstraint.scss";
</style>
