<template>
    <div id="observation" @click="setSelectedItem(null)">
<!--        <table>-->
<!--            <colgroup>-->
<!--                <col style="width: calc(50% - 200px);" />-->
<!--                <col v-if="windowWidth > 1200" style="width: 200px;" />-->
<!--            </colgroup>-->
<!--            <tbody>-->
<!--                <tr>-->
<!--                    <td><content-wrap /></td>-->
<!--                    <td v-if="windowWidth > 1200"><side-header /></td>-->
<!--                </tr>-->
<!--                <tr>-->
<!--                    <td v-if="windowWidth <= 1200"><side-header /></td>-->
<!--                </tr>-->
<!--            </tbody>-->
<!--            <tfoot>-->
<!--                <tr>-->
<!--                    <td colspan="2">-->
<!--                        <input type="button" value="조회" @click="fetchData" />-->
<!--                    </td>-->
<!--                </tr>-->
<!--            </tfoot>-->
<!--        </table>-->
<!--        <chart-wrap />-->
        <div>
            <div>
                <div></div>
                <side-header />
                <item-tooltip />
            </div>
            <div><input type="button" class="fetch" value="조회" @click="fetchData" /></div>
        </div>
        <div><chart-wrap /></div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    import { dataApi } from "../../assets/js/api";
    import { alert, execAsync } from "../../assets/js/common.utils";

    // import ContentWrap from "./content_wrap/ContentWrap";
    import SideHeader from "./side_header/SideHeader";
    import ItemTooltip from "./item_tooltip/ItemTooltip";
    import ChartWrap from "./chart_wrap/ChartWrap";

    export default {
        name: "Observation",
        components: {
            // ContentWrap,
            SideHeader,
            ItemTooltip,
            ChartWrap
        },
        computed: {
            ...mapState({
                windowWidth: state => state.common.windowWidth,
                selectedCategory: state => state.observation.selectedCategory,
                startDatetime: state => state.observation.startDatetime,
                endDatetime: state => state.observation.endDatetime,
                stations: state => state.observation.stations[state.observation.selectedCategory],
                selectedStation: state => state.observation.selectedStation[state.observation.selectedCategory],
                selectedDateType: state => state.observation.selectedDateType,
                selectedItem: state => state.observation[state.observation.selectedCategory].selectedItem,
                xAxis: state => state.observation[state.observation.selectedCategory].xAxis,
                yAxis: state => state.observation[state.observation.selectedCategory].yAxis,
                selectedFineParticleRange: state => state.observation.selectedFineParticleRange
            })
        },
        ...dataApi,
        methods: {
            ...mapActions({
                addResizeEvent: "ADD_RESIZE_EVENT",
                clearResizeEvent: "CLEAR_RESIZE_EVENT",
                setWindowWidth: "SET_WINDOW_WIDTH",
                setData: "SET_DATA",
                setLoadingVisible: "SET_LOADING_VISIBLE",
                setLoadingInvisible: "SET_LOADING_INVISIBLE",
                setAvailable: "SET_AVAILABLE",
                setSelectedItem: "SET_SELECTED_ITEM"
            }),

            async fetchData() {
                if (this.selectedStation.length === 0)
                    await new Promise(resolve => alert("조회할 지점을 선택해주세요.", resolve));
                else if (this.xAxis.length === 0)
                    await new Promise(resolve => alert("X축 항목을 추가해주세요.", resolve));
                else if (this.yAxis.length === 0)
                    await new Promise(resolve => alert("Y축 항목을 추가해주세요.", resolve));
                else {
                    await this.setLoadingVisible();
                    let dataQuery = this.$apollo.queries[this.selectedCategory + "Data"];
                    dataQuery.skip = false;
                    await this.setData(JSON.parse(JSON.stringify({
                        category: this.selectedCategory,
                        data: await dataQuery.refetch().then(response => response.data[this.selectedCategory + "Data"])
                    })));
                    await this.setLoadingInvisible();
                    dataQuery.skip = true;
                }
            }
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
            this.addResizeEvent(() => this.setWindowWidth(window.innerWidth));
        },
        destroyed() {
            this.clearResizeEvent();
        }
    }
</script>

<style lang="scss">
    @import "./Observation";
</style>
