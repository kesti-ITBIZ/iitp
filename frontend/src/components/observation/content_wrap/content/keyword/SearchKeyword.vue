<template>
    <div id="search-keyword">
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
        <table>
            <colgroup>
                <col />
                <col />
                <col />
                <col />
            </colgroup>
            <thead>
                <tr>
                    <th>No.</th>
                    <th>생산기관</th>
                    <th>지점명</th>
                    <th>주소</th>
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
                                    <col />
                                    <col />
                                </colgroup>
                                <tbody>
                                    <tr :key="i" v-for="(station, i) in searchedStations" @click="select({ name: station.name, address: station.address })">
                                        <td>{{ i + 1 }}</td>
                                        <td>{{ station.category }}</td>
                                        <td>{{ station.name }}</td>
                                        <td>{{ station.address }}</td>
                                    </tr>
                                </tbody>
                            </table>
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
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import gql from "graphql-tag";

    import { alert } from "../../../../../assets/js/common.utils";

    export default {
        name: "SearchKeyword",
        data: () => ({
            keyword: "",
            prevKeyword: null
        }),
        computed: {
            ...mapState({
                selectedCategory: state => state.selectedCategory,
                searchedStations: state => state.searchedStations
            })
        },
        apollo: {
            ...(() => {
                let queries = {};
                ["airkorea", "kt", "observer", "sDoT"].forEach(category => {
                    queries[category + "StationsByKeyword"] = {
                        query: gql`
                            query ${category}StationsByKeyword($param: String) {
                                ${category}StationsByKeyword(param: $param) {
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
                            return { param: this.keyword };
                        }
                    };
                });
                return queries;
            })()
        },
        methods: {
            ...mapActions({
                setSearchedStations: "SET_SEARCHED_STATIONS",
                setSelectedStation: "SET_SELECTED_STATION"
            }),

            async search() {
                if (this.keyword === "")
                    await new Promise(resolve => alert("검색할 지점명 또는 주소를 입력하세요.", resolve));
                else if (this.prevKeyword !== this.keyword) {
                    const dataQuery = this.$apollo.queries[this.selectedCategory + "StationsByKeyword"];
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

                    this.prevKeyword = this.keyword;
                }
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
