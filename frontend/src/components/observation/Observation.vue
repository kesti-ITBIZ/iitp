<template>
    <div id="observation">
        <table>
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
    import { alert } from "../../assets/js/common.utils";

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
                selectedCategory: state => state.selectedCategory,
                startDatetime: state => state.startDatetime,
                endDatetime: state => state.endDatetime,
                stations: state => state.stations[state.selectedCategory],
                selectedStation: state => state.selectedStation[state.selectedCategory],
                selectedDateType: state => state.selectedDateType,
                xAxis: state => state[state.selectedCategory].xAxis,
                yAxis: state => state[state.selectedCategory].yAxis,
                selectedFineParticleRange: state => state.selectedFineParticleRange
            })
        },
        ...dataApi,
        methods: {
            ...mapActions({
                setData: "SET_DATA",
                setLoadingVisible: "SET_LOADING_VISIBLE",
                setLoadingInvisible: "SET_LOADING_INVISIBLE"
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
        }
    }
</script>

<style lang="scss">
    @import "./Observation";
</style>
