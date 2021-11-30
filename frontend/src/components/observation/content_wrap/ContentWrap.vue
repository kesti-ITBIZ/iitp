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

    import { dataApi } from "../../../assets/js/api";

    import SelectDatetime from "./header/select_datetime/SelectDatetime";
    import Category from "./content/category/Category";
    import ContentMap from "./content/map/ContentMap";
    import SearchKeyword from "./content/keyword/SearchKeyword";
    import SelectedStations from "./content/selected_stations/SelectedStations";

    export default {
        name: "ContentWrap",
        components: {
            SelectDatetime,
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
            })
        }
    }
</script>

<style lang="scss">
    @import "./ContentWrap";
</style>
