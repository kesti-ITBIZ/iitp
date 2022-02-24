<template>
    <div class="container-wrap search">
        <div class="container">
            <table>
                <colgroup>
                    <col style="width: 55px;" />
                    <col style="width: 80px;" v-show="windowWidth >= reactiveMaxWidth + 1" />
                    <col style="width: 150px;" v-show="windowWidth >= reactiveMaxWidth + 1" />
                    <col />
                </colgroup>
                <thead>
                    <tr>
                        <th :colspan="windowWidth >= reactiveMaxWidth + 1 ? 4 : 2">
                            <label>지점 검색</label>
                            <input type="text" class="input" placeholder="검색할 지점명 또는 주소를 입력하세요." v-model="keyword" @keyup="e => { if (e.key === 'Enter') search(); }" />
                            <input type="button" value="검색" @click="search" />
                        </th>
                    </tr>
                    <tr>
                        <th><span>No.</span></th>
                        <th v-show="windowWidth >= reactiveMaxWidth + 1"><span>생산기관</span></th>
                        <th v-show="windowWidth >= reactiveMaxWidth + 1"><span>지점명</span></th>
                        <th v-show="windowWidth >= reactiveMaxWidth + 1"><span>주소</span></th>
                        <th v-show="windowWidth < reactiveMaxWidth + 1"><span>지점정보</span></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td :colspan="windowWidth >= reactiveMaxWidth + 1 ? 4 : 2"
                            v-show="searchedStations.length === 0"
                            :style="{ height: `${height}px !important` }">
                            <div class="no-data">데이터가 없습니다.</div>
                        </td>
                        <td :colspan="windowWidth >= reactiveMaxWidth + 1 ? 4 : 2" v-show="searchedStations.length > 0">
                            <div class="scroll" :style="{ height: `${height}px !important` }">
                                <table>
                                    <colgroup>
                                        <col style="width: 55px;" />
                                        <col style="width: 80px;" v-show="windowWidth >= reactiveMaxWidth + 1" />
                                        <col style="width: 150px;" v-show="windowWidth >= reactiveMaxWidth + 1" />
                                        <col />
                                    </colgroup>
                                    <tbody>
                                        <tr :key="i" v-for="(station, i) in searchedStations" @click="select({ name: station.name, address: station.address })">
                                            <td><span>{{ i + 1 }}</span></td>
                                            <td v-show="windowWidth >= reactiveMaxWidth + 1"><span>{{ station.category }}</span></td>
                                            <td v-show="windowWidth >= reactiveMaxWidth + 1"><span>{{ station.name }}</span></td>
                                            <td v-show="windowWidth >= reactiveMaxWidth + 1"><span>{{ station.address }}</span></td>
                                            <td v-show="windowWidth < reactiveMaxWidth + 1">
                                                <div>
                                                    <h3>{{ station.name }}</h3>
                                                    {{ station.category }}<br />{{ station.address }}
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <infinite-loading v-if="searchedStations.length >= 100" @infinite="infiniteHandler" spinner="waveDots">
                                    <div slot="no-results" class="no-results">
                                        더 이상 데이터가 없어요. ( ͡° ͜ʖ ͡°)
                                    </div>
                                </infinite-loading>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import gql from "graphql-tag";

    import { alert } from "../../../../assets/js/common.utils";

    export default {
        name: "Search",
        data: () => ({
            keyword: "",
            prevKeyword: null,
            pageIndex: 0,
            infiniteState: null,
            height: 0
        }),
        computed: {
            ...mapState({
                reactiveMaxWidth: state => state.common.reactiveMaxWidth,
                windowWidth: state => state.common.windowWidth,
                windowHeight: state => state.common.windowHeight,
                selectedCategory: state => state.observation.selectedCategory,
                searchedStations: state => state.observation.searchedStations
            })
        },
        watch: {
            windowWidth() {
                this.height = this.windowWidth >= this.reactiveMaxWidth ? this.windowHeight - 580 : 390;
            },

            windowHeight() {
                this.height = this.windowWidth >= this.reactiveMaxWidth ? this.windowHeight - 580 : 390;
            }
        },
        apollo: {
            ...(() => {
                let queries = {};
                ["airkorea", "kt", "observer", "sDoT"].forEach(category => {
                    queries[category + "StationsByKeyword"] = {
                        query: gql`
                            query ${category}StationsByKeyword($keyword: String!, $page: Int!, $size: Int!) {
                                ${category}StationsByKeyword(keyword: $keyword, page: $page, size: $size) {
                                    category
                                    name
                                    stnId
                                    address
                                    latitude
                                    longitude
                                }
                            }
                        `,
                        skip: true,
                        variables() {
                            return {
                                keyword: this.prevKeyword,
                                page: this.pageIndex,
                                size: 100
                            };
                        }
                    };
                });
                return queries;
            })()
        },
        methods: {
            ...mapActions({
                setSearchedStations: "SET_OBSERVATION_SEARCHED_STATIONS",
                appendSearchedStations: "APPEND_OBSERVATION_SEARCHED_STATIONS",
                setSelectedStation: "SET_OBSERVATION_SELECTED_STATION"
            }),

            async search() {
                if (this.keyword === "")
                    await new Promise(resolve => alert("검색할 지점명 또는 주소를 입력하세요.", resolve));
                else if (this.prevKeyword !== this.keyword) {
                    const dataQuery = this.$apollo.queries[this.selectedCategory + "StationsByKeyword"];
                    this.prevKeyword = this.keyword;
                    this.pageIndex = 0;
                    dataQuery.skip = false;
                    this.setSearchedStations((await dataQuery.refetch()
                        .then(response => response.data[this.selectedCategory + "StationsByKeyword"]))
                        .map(obj => ({
                            category: obj.category,
                            name: obj.name,
                            stnId: obj.stnId,
                            address: obj.address,
                            latitude: obj.latitude,
                            longitude: obj.longitude
                        })));
                    dataQuery.skip = true;
                    this.pageIndex = this.pageIndex + 1;

                    if (this.infiniteState) this.infiniteState.reset();
                }
            },

            infiniteHandler($state) {
                this.infiniteState = $state;

                setTimeout(async () => {
                    const dataQuery = this.$apollo.queries[this.selectedCategory + "StationsByKeyword"];
                    dataQuery.skip = false;
                    const stations = (await dataQuery.refetch()
                        .then(response => response.data[this.selectedCategory + "StationsByKeyword"]))
                        .map(obj => ({
                            category: obj.category,
                            name: obj.name,
                            address: obj.address,
                            latitude: obj.latitude,
                            longitude: obj.longitude
                        }));
                    dataQuery.skip = true;

                    if (stations != null) {
                        this.appendSearchedStations(stations);
                        if (stations.length < 100) $state.complete();
                        else $state.loaded();
                    }
                }, 600);
            },

            select({ name, address }) {
                this.setSelectedStation({
                    category: this.selectedCategory,
                    station: this.searchedStations.filter(obj => obj.name === name && obj.address === address)[0]
                });
            }
        },
        mounted() {
            this.height = this.windowWidth >= this.reactiveMaxWidth ? this.windowHeight - 580 : 390;
        }
    }
</script>

<style lang="scss">
    @import "./Search";
</style>
