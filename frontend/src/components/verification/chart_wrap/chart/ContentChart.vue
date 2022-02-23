<template>
    <div class="container-wrap chart">
        <div class="container chart">
            <div class="info">
                <div>
                    <h3>기준 지점</h3>
                    <h2 v-if="selectedStandardStation">{{ selectedStandardStation.name }}</h2>
                </div>
                <div>
                    <h3>비교 지점</h3>
                    <h2 v-if="selectedCompareStation">{{ selectedCompareStation.name }}</h2>
                </div>
                <div>
                    <h3>비교 분석 결과</h3>
                    <h2>Y = RX<sup>2</sup> + b&emsp;R<sup>2</sup> = 0.81</h2>
                </div>
            </div>
            <div class="timeseries"></div>
            <div class="comparison"></div>
        </div>
<!--        <div v-show="!loading" id="no-data" class="container">-->
<!--            <div>-->
<!--                <h1>데이터가 없습니다.</h1>-->
<!--            </div>-->
<!--        </div>-->
        <loading />
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    import dayjs from "dayjs";
    import customParseFormat from "dayjs/plugin/customParseFormat";

    dayjs.extend(customParseFormat);

    import Loading from "../../../common/loading/Loading";

    export default {
        name: "ContentChart",
        components: {
            Loading
        },
        data: () => ({
            chart: null,
            formula: "$$y = 4x^2 + 1   R^2 = 0.81"
        }),
        computed: {
            ...mapState({
                loading: state => state.common.loading,
                selectedStandardStation: state => state.verification.selectedStandardStation,
                selectedCompareStation: state => state.verification.selectedCompareStation,
                data: state => state.verification.data
            })
        },
        watch: {
            data() {
                console.log(this.data);
            }
        },
        methods: {
            ...mapActions({
                clearResizeEvent: "CLEAR_RESIZE_EVENT"
            })
        },
        destroyed() {
            this.clearResizeEvent();
        }
    }
</script>

<style lang="scss">
    @import "./ContentChart";
</style>