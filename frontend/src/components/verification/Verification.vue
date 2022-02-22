<template>
    <div id="verification" @click="setSelectedItem(null)">
        <content-wrap />
        <div><chart-wrap /></div>
    </div>
</template>

<script>
    import { mapActions } from "vuex";

    import { dataApi } from "../../assets/js/api";
    import { execAsync } from "../../assets/js/common.utils";

    import ContentWrap from "./content_wrap/ContentWrap";
    import ChartWrap from "./chart_wrap/ChartWrap";

    export default {
        name: "Verification",
        components: {
            ContentWrap,
            ChartWrap
        },
        ...dataApi,
        methods: {
            ...mapActions({
                addResizeEvent: "ADD_RESIZE_EVENT",
                clearResizeEvent: "CLEAR_RESIZE_EVENT",
                setWindowWidth: "SET_WINDOW_WIDTH",
                setWindowHeight: "SET_WINDOW_HEIGHT",
                setAvailable: "SET_VERIFICATION_AVAILABLE",
                setSelectedItem: "SET_VERIFICATION_SELECTED_ITEM"
            })
        },
        mounted() {
            execAsync(
                async () => {
                    const dataQuery = this.$apollo.queries.airkoreaAvailableDatetimes;
                    dataQuery.skip = false;
                    await this.setAvailable({
                        category: "airkorea",
                        available: await dataQuery.refetch().then(response => response.data.airkoreaAvailableDatetimes)
                    });
                    dataQuery.skip = true;
                },
                async () => {
                    const dataQuery = this.$apollo.queries.ktAvailableDatetimes;
                    dataQuery.skip = false;
                    await this.setAvailable({
                        category: "kt",
                        available: await dataQuery.refetch().then(response => response.data.ktAvailableDatetimes)
                    });
                    dataQuery.skip = true;
                },
                async () => {
                    const dataQuery = this.$apollo.queries.observerAvailableDatetimes;
                    dataQuery.skip = false;
                    await this.setAvailable({
                        category: "observer",
                        available: await dataQuery.refetch().then(response => response.data.observerAvailableDatetimes)
                    });
                    dataQuery.skip = true;
                },
                async () => {
                    const dataQuery = this.$apollo.queries.sDoTAvailableDatetimes;
                    dataQuery.skip = false;
                    await this.setAvailable({
                        category: "sDoT",
                        available: await dataQuery.refetch().then(response => response.data.sDoTAvailableDatetimes)
                    });
                    dataQuery.skip = true;
                });
            this.addResizeEvent(() => {
                this.setWindowWidth(window.innerWidth);
                this.setWindowHeight(window.innerHeight);
            });
        },
        destroyed() {
            this.clearResizeEvent();
        }
    }
</script>

<style lang="scss">
    @import "./Verification";
</style>
