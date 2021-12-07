<template>
    <div id="observation">
        <table>
            <colgroup>
                <col style="width: calc(50% - 200px);" />
                <col style="width: 200px;" />
            </colgroup>
            <tbody>
                <tr>
                    <td><content-wrap /></td>
                    <td><side-header /></td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="2">
                        <input type="button" value="조회" @click="fetchData" />
                    </td>
                </tr>
            </tfoot>
        </table>
        <chart-wrap />
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    import { dataApi } from "../../assets/js/api";
    import { alert, execAsync } from "../../assets/js/common.utils";

    import ContentWrap from "./content_wrap/ContentWrap";
    import SideHeader from "./side_header/SideHeader";
    import ChartWrap from "./chart_wrap/ChartWrap";

    export default {
        name: "Observation",
        components: {
            ContentWrap,
            SideHeader,
            ChartWrap
        },
        computed: {
            ...mapState({
                selectedCategory: state => state.observation.selectedCategory,
                startDatetime: state => state.observation.startDatetime,
                endDatetime: state => state.observation.endDatetime,
                stations: state => state.observation.stations[state.observation.selectedCategory],
                selectedStation: state => state.observation.selectedStation[state.observation.selectedCategory],
                selectedDateType: state => state.observation.selectedDateType,
                xAxis: state => state.observation[state.observation.selectedCategory].xAxis,
                yAxis: state => state.observation[state.observation.selectedCategory].yAxis,
                selectedFineParticleRange: state => state.observation.selectedFineParticleRange
            })
        },
        ...dataApi,
        methods: {
            ...mapActions({
                setData: "SET_DATA",
                setLoadingVisible: "SET_LOADING_VISIBLE",
                setLoadingInvisible: "SET_LOADING_INVISIBLE",
                setAvailable: "SET_AVAILABLE"
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
                }
            )
        }
    }
</script>

<style lang="scss">
    @import "./Observation";
</style>
