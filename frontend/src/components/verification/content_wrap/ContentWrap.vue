<template>
    <div class="content-wrap">
        <options />
        <div v-show="windowWidth >= reactiveMaxWidth + 1">
            <div>
                <content-map v-show="selectedSearchOption == 'map'" />
                <search v-show="selectedSearchOption == 'search'" />
                <div class="station-wrap">
                    <station title="비교 지점" :station="selectedCompareStation" @remove="setSelectedCompareStation(null)" />
                    <station title="기준 지점" :station="selectedStandardStation" @remove="setSelectedStandardStation(null)" />
                </div>
            </div>
            <side-header />
        </div>
        <div v-show="windowWidth < reactiveMaxWidth + 1">
            <content-map v-show="selectedSearchOption == 'map'" />
            <search v-show="selectedSearchOption == 'search'" />
            <div class="station-wrap">
                <station title="비교 지점" :station="selectedCompareStation" @remove="setSelectedCompareStation(null)" />
                <station title="기준 지점" :station="selectedStandardStation" @remove="setSelectedStandardStation(null)" />
            </div>
            <side-header />
        </div>
        <div class="fetch-wrap"><input type="button" class="fetch" value="조회" @click="fetchData" /></div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import { alert } from "../../../assets/js/common.utils";
    import { verificationApi } from "../../../assets/js/api";

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
                windowWidth: state => state.common.windowWidth,
                reactiveMaxWidth: state => state.common.reactiveMaxWidth,
                selectedSearchOption: state => state.verification.selectedSearchOption,
                startDatetime: state => state.verification.startDatetime,
                endDatetime: state => state.verification.endDatetime,
                selectedCategory: state => state.verification.selectedCategory,
                selectedStandardStation: state => state.verification.selectedStandardStation,
                selectedCompareStation: state => state.verification.selectedCompareStation,
                selectedStandardOrg: state => state.verification.selectedStandardOrg,
                selectedCompareOrg: state => state.verification.selectedCompareOrg
            })
        },
        ...verificationApi,
        methods: {
            ...mapActions({
                setData: "SET_VERIFICATION_DATA",
                setLoadingVisible: "SET_LOADING_VISIBLE",
                setLoadingInvisible: "SET_LOADING_INVISIBLE",
                setSelectedStandardStation: "SET_VERIFICATION_SELECTED_STANDARD_STATION",
                setSelectedCompareStation: "SET_VERIFICATION_SELECTED_COMPARE_STATION"
            }),

            async fetchData() {
                if (this.selectedCompareStation == null)
                    await new Promise(resolve => alert("비교 지점을 선택해주세요.", resolve));
                else if (this.selectedStandardStation == null)
                    await new Promise(resolve => alert("기준 지점을 선택해주세요.", resolve));
                else {
                    await this.setLoadingVisible();
                    let org = this.selectedCompareOrg.split("");
                    org[0] = org[0].toUpperCase();
                    org = org.join("");
                    let dataQuery = this.$apollo.queries.verificationData;
                    dataQuery.skip = false;
                    await this.setData(await dataQuery.refetch().then(response => response.data.verificationData));
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
