<template>
    <div id="content-wrap">
        <table>
            <thead>
                <tr>
                    <th colspan="2">
                        <table>
                            <colgroup>
                                <col style="width: 50%;" />
                                <col style="width: 50%;" />
                            </colgroup>
                            <thead>
                                <tr>
                                    <select-datetime />
                                    <select-constraint />
                                </tr>
                            </thead>
                        </table>
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="map">
                        <div class="map-data">
                            <content-map />
                        </div>
                        <content-data />
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="button" value="조회" @click="fetchData" />
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import gql from "graphql-tag";

    import { alert } from "../../assets/js/common.utils";

    import SelectDatetime from "./header/select_datetime/SelectDatetime";
    import SelectConstraint from "./header/select_constraint/SelectConstraint";
    import ContentMap from "./content/map/ContentMap";
    import ContentData from "./content/content_data/ContentData";

    export default {
        name: "ContentWrap",
        components: {
            SelectDatetime,
            SelectConstraint,
            ContentMap,
            ContentData
        },
        computed: {
            ...mapState({
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
        apollo: {
            // ...(() => {
            //     let obj = {};
            //     ["airkorea", "kt", "observer", "sDoT"].forEach(category => {
            //         if (category !== "all")
            //             obj[category + "Stations"] = {
            //                 query: gql`
            //                     query {
            //                         ${category}Stations {
            //                             address
            //                             name
            //                             latitude
            //                             longitude
            //                         }
            //                     }
            //                 `,
            //                 skip: true
            //             };
            //     });
            //     return obj;
            // })(),
            airkoreaData: {
                query: gql`
                    query airkoreaData($param: DataParam) {
                        airkoreaData(param: $param) {
                            datetime
                            stnNm
                            so2
                            no2
                            o3
                            co
                            pm10
                            pm25
                        }
                    }
                `,
                variables() {
                    return {
                        param: {
                            startDatetime: this.startDatetime.format("YYYYMMDDHHmmss"),
                            endDatetime: this.endDatetime.format("YYYYMMDDHHmmss"),
                            dateType: this.selectedDateType,
                            stnNm: this.selectedStation[0].name,
                            ...this.selectedFineParticleRange
                        }
                    };
                },
                skip: true
            },
            ktData: {
                query: gql`
                    query ktData($param: DataParam) {
                        ktData(param: $param) {
                            datetime
                            stnNm
                            temperature
                            humidity
                            pm10
                            pm25
                        }
                    }
                `,
                variables() {
                    return {
                        param: {
                            startDatetime: this.startDatetime.format("YYYYMMDDHHmmss"),
                            endDatetime: this.endDatetime.format("YYYYMMDDHHmmss"),
                            dateType: this.selectedDateType,
                            stnNm: this.selectedStation[0].name,
                            ...this.selectedFineParticleRange
                        }
                    };
                },
                skip: true
            },
            observerData: {
                query: gql`
                    query observerData($param: DataParam) {
                        observerData(param: $param) {
                            datetime
                            stnNm
                            temperature
                            humidity
                            pressure
                            pm25
                        }
                    }
                `,
                variables() {
                    return {
                        param: {
                            startDatetime: this.startDatetime.format("YYYYMMDDHHmmss"),
                            endDatetime: this.endDatetime.format("YYYYMMDDHHmmss"),
                            dateType: this.selectedDateType,
                            stnNm: this.selectedStation[0].name,
                            ...this.selectedFineParticleRange
                        }
                    };
                },
                skip: true
            },
            sDoTData: {
                query: gql`
                    query sDoTData($param: DataParam) {
                        sDoTData(param: $param) {
                            datetime
                            stnNm
                            temperature
                            relativeHumidity
                            windDirection
                            windSpeed
                            pm10
                            pm25
                        }
                    }
                `,
                variables() {
                    return {
                        param: {
                            startDatetime: this.startDatetime.format("YYYYMMDDHHmmss"),
                            endDatetime: this.endDatetime.format("YYYYMMDDHHmmss"),
                            dateType: this.selectedDateType,
                            stnNm: this.selectedStation[0].name,
                            ...this.selectedFineParticleRange
                        }
                    };
                },
                skip: true
            }
        },
        methods: {
            ...mapActions({
                setData: "SET_DATA"
            }),

            variables() {
                return {
                    param: {
                        startDatetime: this.startDatetime.format("YYYYMMDDHHmmss"),
                        endDatetime: this.endDatetime.format("YYYYMMDDHHmmss"),
                        dateType: this.selectedDateType,
                        stnNm: this.selectedStation[0].name,
                        ...this.selectedFineParticleRange
                    }
                };
            },

            async fetchData() {
                if (this.selectedStation.length === 0)
                    await new Promise(resolve => alert("조회할 지점을 선택해주세요.", resolve));
                else if (this.xAxis.length === 0)
                    await new Promise(resolve => alert("X축 항목을 추가해주세요.", resolve));
                else if (this.yAxis.length === 0)
                    await new Promise(resolve => alert("Y축 항목을 추가해주세요.", resolve));
                else {
                    let dataQuery = this.$apollo.queries[this.selectedCategory + "Data"];
                    dataQuery.skip = false;
                    await this.setData(JSON.parse(JSON.stringify({
                        category: this.selectedCategory,
                        data: await dataQuery.refetch().then(response => response.data[this.selectedCategory + "Data"])
                    })));
                    dataQuery.skip = true;
                }
            }
        }
    }
</script>

<style lang="scss">
    @import "./ContentWrap.scss";
</style>
