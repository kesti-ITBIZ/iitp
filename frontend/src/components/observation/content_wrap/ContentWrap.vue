<template>
    <div class="content-wrap">
        <div>
            <div>
                <options />
                <content-map v-show="selectedSearchOption == 'map'" />
                <search v-show="selectedSearchOption == 'search'" />
                <station />
            </div>
            <side-header />
            <item-tooltip />
        </div>
        <div><input type="button" class="fetch" value="조회" @click="fetchData" /></div>
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
    import ItemTooltip from "./item_tooltip/ItemTooltip";
    import dayjs from "dayjs";

    export default {
        name: "ContentWrap",
        components: {
            Options,
            ContentMap,
            Search,
            Station,
            SideHeader,
            ItemTooltip
        },
        computed: {
            ...mapState({
                selectedSearchOption: state => state.observation.selectedSearchOption,
                startDatetime: state => state.observation.startDatetime,
                endDatetime: state => state.observation.endDatetime,
                selectedCategory: state => state.observation.selectedCategory,
                xAxis: state => state.observation[state.observation.selectedCategory].xAxis,
                yAxis: state => state.observation[state.observation.selectedCategory].yAxis,
                selectedStation: state => state.observation.selectedStation[state.observation.selectedCategory],
                selectedDateType: state => state.observation.selectedDateType,
            })
        },
        ...dataApi,
        methods: {
            ...mapActions({
                setData: "SET_OBSERVATION_DATA",
                setLoadingVisible: "SET_LOADING_VISIBLE",
                setLoadingInvisible: "SET_LOADING_INVISIBLE"
            }),

            async fetchData() {
                if ((this.selectedDateType == "hour" || this.selectedDateType == "date")
                    && Math.abs(dayjs(this.startDatetime).diff(dayjs(this.endDatetime), "day")))
                    await new Promise(resolve => alert("검색 가능 기간은 30일입니다.", resolve));
                else if (this.selectedStation == null)
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
