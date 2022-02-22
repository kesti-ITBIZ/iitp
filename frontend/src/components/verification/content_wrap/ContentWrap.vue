<template>
    <div class="content-wrap">
        <options />
        <div>
            <div>
                <content-map v-show="selectedSearchOption == 'map'" />
                <search v-show="selectedSearchOption == 'search'" />
                <div class="station-wrap">
                    <station title="기준 지점" :station="selectedStandardStation" @remove="setSelectedStandardStation(null)" />
                    <station title="비교 지점" :station="selectedCompareStation" @remove="setSelectedCompareStation(null)" />
                </div>
            </div>
            <side-header />
        </div>
        <div class="fetch-wrap"><input type="button" class="fetch" value="조회" @click="fetchData" /></div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import { alert } from "../../../assets/js/common.utils";
    import { dataApi } from "../../../assets/js/api";

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
                selectedSearchOption: state => state.verification.selectedSearchOption,
                startDatetime: state => state.verification.startDatetime,
                endDatetime: state => state.verification.endDatetime,
                selectedCategory: state => state.verification.selectedCategory,
                xAxis: state => state.verification[state.verification.selectedCategory].xAxis,
                yAxis: state => state.verification[state.verification.selectedCategory].yAxis,
                selectedStation: state => state.verification.selectedStation[state.verification.selectedCategory],
                selectedStandardStation: state => state.verification.selectedStandardStation,
                selectedCompareStation: state => state.verification.selectedCompareStation
            })
        },
        ...dataApi,
        methods: {
            ...mapActions({
                setData: "SET_VERIFICATION_DATA",
                setLoadingVisible: "SET_LOADING_VISIBLE",
                setLoadingInvisible: "SET_LOADING_INVISIBLE",
                setSelectedStandardStation: "SET_VERIFICATION_SELECTED_STANDARD_STATION",
                setSelectedCompareStation: "SET_VERIFICATION_SELECTED_COMPARE_STATION"
            }),

            async fetchData() {
                if (this.selectedStation == null)
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
    @import "./ContentWrap";
</style>
