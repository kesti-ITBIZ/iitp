<template>
    <div id="search-keyword">
        <div>
            <table>
                <colgroup>
                    <col style="width: 100px;" />
                    <col style="width: calc(100% - 100px);" />
                </colgroup>
                <tbody>
                    <tr>
                        <td>지점 검색</td>
                        <td>
                            <input type="text" placeholder="검색할 지점명 또는 주소를 입력하세요." v-model="keyword" @keyup="e => { if (e.key === 'Enter') search(); }" />
                            <input type="button" value="검색" @click="search" />
                        </td>
                    </tr>
                </tbody>
            </table>
            <div>
                <table>
                    <colgroup>
                        <col />
                        <col />
                        <col v-show="windowWidth >= 1200" />
                        <col v-show="windowWidth >= 1200" />
                    </colgroup>
                    <thead>
                        <tr v-show="windowWidth >= 1200">
                            <th>No.</th>
                            <th>생산기관</th>
                            <th>지점명</th>
                            <th>주소</th>
                        </tr>
                        <tr v-show="windowWidth < 1200">
                            <th>No.</th>
                            <th>지점정보</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td colspan="4">
                                <div class="scroll" v-show="searchedStations.length > 0">
                                    <table>
                                        <colgroup>
                                            <col />
                                            <col />
                                            <col v-show="windowWidth >= 1200" />
                                            <col v-show="windowWidth >= 1200" />
                                        </colgroup>
                                        <tbody>
                                            <tr :key="i" v-for="(station, i) in searchedStations" @click="select({ name: station.name, address: station.address })">
                                                <td>{{ i + 1 }}</td>
                                                <td v-show="windowWidth >= 1200">{{ station.category }}</td>
                                                <td v-show="windowWidth >= 1200">{{ station.name }}</td>
                                                <td v-show="windowWidth >= 1200">{{ station.address }}</td>
                                                <td v-show="windowWidth < 1200">
                                                    <h3>{{ station.name }}</h3>
                                                    {{ station.category }}<br />{{ station.address }}
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
                                <div class="no-data" v-show="searchedStations.length === 0">
                                    <table>
                                        <tbody>
                                            <tr>
                                                <td>데이터가 없습니다.</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import gql from "graphql-tag";

    import { alert } from "../../../../../assets/js/common.utils";

    export default {
        name: "SearchKeyword",
        data: () => ({
            keyword: "",
            prevKeyword: null,
            pageIndex: 0,
            infiniteState: null
        }),
        computed: {
            ...mapState({
                windowWidth: state => state.common.windowWidth,
                selectedCategory: state => state.observation.selectedCategory,
                searchedStations: state => state.observation.searchedStations
            })
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
                setSearchedStations: "SET_SEARCHED_STATIONS",
                appendSearchedStations: "APPEND_SEARCHED_STATIONS",
                setSelectedStation: "SET_SELECTED_STATION"
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
        }
    }
</script>

<style lang="scss">
    @import "./SearchKeyword";
</style>
