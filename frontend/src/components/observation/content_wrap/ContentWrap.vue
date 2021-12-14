<template>
    <table id="content-wrap">
        <thead>
            <tr>
                <th>
                    <div>
                        <table>
                            <thead>
                                <tr>
                                    <th><select-datetime /></th>
                                </tr>
                                <tr>
                                    <th><category /></th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </th>
            </tr>
        </thead>
        <tbody>
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
    import Category from "./header/category/Category";
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
                selectedSearchOption: state => state.observation.selectedSearchOption,
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
                setLoadingVisible: "SET_LOADING_VISIBLE"
            })
        }
    }
</script>

<style lang="scss">
    @import "./ContentWrap";
</style>
