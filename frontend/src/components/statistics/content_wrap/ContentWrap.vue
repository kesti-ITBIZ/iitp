<template>
    <div class="content-wrap">
        <options />
        <content-map v-show="selectedSearchOption == 'map'" />
        <search v-show="selectedSearchOption == 'search'" />
        <div class="inline">
            <station />
            <side-header />
        </div>
        <div><input type="button" class="fetch" value="조회" @click="fetchData" /></div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import { alert } from "../../../assets/js/common.utils";
    import { statisticsApi } from "../../../assets/js/api";

    import Options from "./options/Options";
    import ContentMap from "./map/ContentMap";
    import Search from "./search/Search";
    import Station from "./station/Station";
    import SideHeader from "./side_header/SideHeader";

    export default {
        name: "ContentWrap",
        components: {
            Options,
            ContentMap,
            Search,
            Station,
            SideHeader,
        },
        computed: {
            ...mapState({
                selectedSearchOption: state => state.statistics.selectedSearchOption,
                startDatetime: state => state.statistics.startDatetime,
                endDatetime: state => state.statistics.endDatetime,
                selectedItem: state => state.statistics[this.statistics.selectedCategory].selectedItem,
                selectedCategory: state => state.statistics.selectedCategory,
                selectedDateType: state => state.statistics.selectedDateType,
                selectedStation: state => state.statistics.selectedStation[state.statistics.selectedCategory]
            })
        },
        ...statisticsApi,
        methods: {
            ...mapActions({
                setData: "SET_STATISTICS_DATA",
                setLoadingVisible: "SET_LOADING_VISIBLE",
                setLoadingInvisible: "SET_LOADING_INVISIBLE"
            }),

            async fetchData() {
                if (this.selectedStation == null)
                    await new Promise(resolve => alert("조회할 지점을 선택해주세요.", resolve));
                else {
                    await this.setLoadingVisible();
                    let dataQuery = this.$apollo.queries.statisticsData;
                    dataQuery.skip = false;
                    await this.setData(await dataQuery.refetch().then(response => response.data.statisticsData));
                    await this.setLoadingInvisible();
                    dataQuery.skip = true;
                }
            }
        }
    }
</script>

<style lang="scss">
    @import "./ContentWrap";
</style>
