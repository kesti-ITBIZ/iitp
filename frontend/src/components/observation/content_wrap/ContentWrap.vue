<template>
    <table id="content-wrap">
        <thead>
            <tr>
                <th>
                    <table>
                        <thead>
                            <tr>
                                <th><select-datetime /></th>
<!--                                    <th><select-constraint /></th>-->
                            </tr>
                        </thead>
                    </table>
                </th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><category /></td>
            </tr>
            <tr>
                <td class="map">
                    <content-map v-show="selectedSearchOption == 'map'" />
                    <search-keyword v-show="selectedSearchOption == 'keyword'" />
                </td>
            </tr>
            <tr><td><selected-stations /></td></tr>
        </tbody>
    </table>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    import { alert } from "../../../assets/js/common.utils";
    import { dataApi } from "../../../assets/js/api";

    import SelectDatetime from "./header/select_datetime/SelectDatetime";
    // import SelectConstraint from "./header/select_constraint/SelectConstraint";
    import Category from "./content/category/Category";
    import ContentMap from "./content/map/ContentMap";
    import SearchKeyword from "./content/keyword/SearchKeyword";
    import SelectedStations from "./content/selected_stations/SelectedStations";

    export default {
        name: "ContentWrap",
        components: {
            SelectDatetime,
            // SelectConstraint,
            Category,
            ContentMap,
            SearchKeyword,
            SelectedStations
        },
        computed: {
            ...mapState({
                selectedSearchOption: state => state.selectedSearchOption,
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
                setLoadingVisible: "SET_LOADING_VISIBLE"
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
                    // const constraint = this.xAxis[0].value === "datetime" ? "Datetime" : "Item";
                    const constraint = "Datetime";
                    let dataQuery = this.$apollo.queries[this.selectedCategory + "DataBy" + constraint];
                    dataQuery.skip = false;
                    await this.setData(JSON.parse(JSON.stringify({
                        category: this.selectedCategory,
                        data: await dataQuery.refetch().then(response => response.data[this.selectedCategory + "DataBy" + constraint])
                    })));
                    dataQuery.skip = true;
                }
            }
        }
    }
</script>

<style lang="scss">
    @import "./ContentWrap";
</style>
