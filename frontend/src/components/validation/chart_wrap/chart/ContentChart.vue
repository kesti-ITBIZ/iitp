<template>
    <div class="content-chart">
        <!--
        TODO 차트 구현
        -->
        <div id="no-data">
            <div>
                <h1>데이터가 없습니다.</h1>
            </div>
        </div>
        <loading />
    </div>
</template>

<script>
    // import { mapState, mapActions } from "vuex";
    import dayjs from "dayjs";
    // import { init } from "echarts";
    import customParseFormat from "dayjs/plugin/customParseFormat";

    dayjs.extend(customParseFormat);

    import Loading from "../../../common/loading/Loading";

    export default {
        name: "ContentChart",
        components: {
            Loading
        },
        computed: {

        },
        watch: {

        },
        methods: {
            foo() {

            }
        },
        destroyed() {
            this.clearResizeEvent();
        }
    }
</script>

<style lang="scss">
    @import "./ContentChart";
</style>
