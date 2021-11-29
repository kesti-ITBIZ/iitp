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
                    <th>선택</th>
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
                                    <tr :key="i" v-for="(station, i) in searchedStations">
                                        <td><input type="radio" name="station" /></td>
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
                setSearchedStations: "SET_SEARCHED_STATIONS"
            }),

            async search() {
                if (this.prevKeyword !== this.keyword) {
                    const dataQuery = this.$apollo.queries[this.selectedCategory + "StationsByKeyword"];
                    dataQuery.skip = false;
                    this.setSearchedStations((await dataQuery.refetch()
                        .then(response => response.data[this.selectedCategory + "StationsByKeyword"]))
                        .map(obj => ({
                            category: obj.category,
                            name: obj.name,
                            address: obj.address
                        })));
                    dataQuery.skip = true;

                    this.prevKeyword = this.keyword;
                }
            }
        }
    }
</script>

<style lang="scss">
    @import "./SearchKeyword";
</style>
