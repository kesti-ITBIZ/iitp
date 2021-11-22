<template>
    <div id="map">
        <div v-show="selectedCategory == 'airkorea'" class="map" ref="airkorea-map"></div>
        <div v-show="selectedCategory == 'kt'" class="map" ref="kt-map"></div>
        <div v-show="selectedCategory == 'sDoT'" class="map" ref="sDoT-map"></div>
        <div v-show="selectedCategory == 'observer'" class="map" ref="observer-map"></div>
        <div v-show="selectedCategory == 'all'" class="map" ref="all-map"></div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import gql from "graphql-tag";

    import { alert } from "../../../../assets/js/common.utils";

    import KakaoMapUtils from "../../../../assets/js/map.utils";

    export default {
        name: "ContentMap",
        data: () => ({
            maps: {
                all: null,
                airkorea: null,
                kt: null,
                sDoT: null,
                observer: null
            },
            airkoreaStations: null,
            ktStations: null,
            observerStations: null,
            sDoTStations: null,
            selectedLatitude: null,
            selectedLongitude: null,

            airkoreaData: null,
            ktData: null,
            observerData: null,
            sDoTData: null
        }),
        computed: {
            ...mapState({
                selectedCategory: state => state.selectedCategory,
                stations: state => state.stations[state.selectedCategory],
                selectedDateType: state => state.selectedDateType,
                startDatetime: state => state.startDatetime,
                endDatetime: state => state.endDatetime,
                xAxis: state => state[state.selectedCategory].xAxis,
                yAxis: state => state[state.selectedCategory].yAxis,
                selectedFineParticleRange: state => state.selectedFineParticleRange
            })
        },
        watch: {
            selectedCategory() {
                if (!this.maps[this.selectedCategory]) this.initMap();
            }
        },
        apollo: {
            ...(() => {
                let obj = {};
                ["airkorea", "kt", "observer", "sDoT"].forEach(category => {
                    if (category !== "all")
                        obj[category + "Stations"] = {
                            query: gql`
                                query {
                                    ${category}Stations {
                                        address
                                        name
                                        latitude
                                        longitude
                                    }
                                }
                            `,
                            skip: true
                        };
                });
                return obj;
            })(),
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
                            stnNm: this.stations.filter(obj => obj.latitude >= this.selectedLatitude - 2e-6
                                && obj.latitude <= this.selectedLatitude + 2e-6
                                && obj.longitude >= this.selectedLongitude - 2e-6
                                && obj.longitude <= this.selectedLongitude + 2e-6)[0].name,
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
                            stnNm: this.stations.filter(obj => obj.latitude >= this.selectedLatitude - 2e-6
                                && obj.latitude <= this.selectedLatitude + 2e-6
                                && obj.longitude >= this.selectedLongitude - 2e-6
                                && obj.longitude <= this.selectedLongitude + 2e-6)[0].name,
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
                            stnNm: this.stations.filter(obj => obj.latitude >= this.selectedLatitude - 2e-6
                                && obj.latitude <= this.selectedLatitude + 2e-6
                                && obj.longitude >= this.selectedLongitude - 2e-6
                                && obj.longitude <= this.selectedLongitude + 2e-6)[0].name,
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
                            stnNm: this.stations.filter(obj => obj.latitude >= this.selectedLatitude - 2e-6
                                && obj.latitude <= this.selectedLatitude + 2e-6
                                && obj.longitude >= this.selectedLongitude - 2e-6
                                && obj.longitude <= this.selectedLongitude + 2e-6)[0].name,
                            ...this.selectedFineParticleRange
                        }
                    };
                },
                skip: true
            }
        },
        methods: {
            ...mapActions({
                addStations: "ADD_STATIONS",
                setData: "SET_DATA"
            }),

            async initMap() {
                const map = new KakaoMapUtils(this.$refs[this.selectedCategory + "-map"]);
                map.setMapType("SKYVIEW");

                await this.fetchStations();
                map.clustering(this.stations);
                map.setOverlay(this.overlayCallback);
                map.addMarkerEventListener("click", async (latitude, longitude) => {
                    if (this.xAxis.length === 0)
                        await new Promise(resolve => alert("X축 항목을 추가해주세요.", resolve));
                    else if (this.yAxis.length === 0)
                        await new Promise(resolve => alert("Y축 항목을 추가해주세요.", resolve));
                    else {
                        [this.selectedLatitude, this.selectedLongitude] = [latitude, longitude];
                        let dataQuery = this.$apollo.queries[this.selectedCategory + "Data"];
                        dataQuery.skip = false;
                        await this.setData(await dataQuery.refetch().then(response => response.data[this.selectedCategory + "Data"]));
                        dataQuery.skip = true;
                    }
                });

                this.maps = Object.freeze({ ...this.maps, [this.selectedCategory]: map });
            },

            overlayCallback(latitude, longitude) {
                const data = this.stations.concat();
                if (data.length > 0) {
                    const index = data.findIndex(obj => Math.abs(obj.latitude - latitude) <= 1e-6 && Math.abs(obj.longitude - longitude) <= 1e-6);
                    const title = index !== -1 ? data[index].name : "";
                    const address = index !== -1 ? data[index].address : "주소 정보가 존재하지 않습니다.";
                    [latitude, longitude] = [Math.round(latitude * 10000) / 10000, Math.round(longitude * 10000) / 10000];

                    return `
                        <div class="overlay">
                            <h4>${title}</h4>
                            <p>
                                <div>위도: ${latitude}</div>
                                <div>경도: ${longitude}</div>
                                <div>주소: ${address}</div>
                            </p>
                        </div>`;
                }
            },

            async fetchStations() {
                const _fetchStations = async category => {
                    const stationQuery = this.$apollo.queries[category + "Stations"];
                    stationQuery.skip = false;
                    this.addStations(await stationQuery.refetch().then(response => response.data[category + "Stations"].map(obj => ({
                        address: obj.address,
                        name: obj.name,
                        latitude: obj.latitude,
                        longitude: obj.longitude
                    }))));
                    stationQuery.skip = true;
                }

                if (this.selectedCategory == "all") {
                    for (const key of Object.keys({ ...this.stations }))
                        if (this.stations[key].length === 0) await _fetchStations(key);
                } else await _fetchStations(this.selectedCategory);
            }
        },
        mounted() {
            this.initMap();
        }
    }
</script>

<style lang="scss">
    @import "./ContentMap.scss";
</style>
