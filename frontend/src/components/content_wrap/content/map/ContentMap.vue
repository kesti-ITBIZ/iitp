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
            airkoreaData: null,
            ktData: null,
            observerData: null,
            sDoTData: null
        }),
        computed: {
            ...mapState({
                selectedCategory: state => state.selectedCategory,
                obsItems: state => state[state.selectedCategory].items.filter(obj => obj.value !== "obsTime"),
                stations: state => state.stations[state.selectedCategory]
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
        },
        methods: {
            ...mapActions({
                addStations: "ADD_STATIONS",
                setSelectedStation: "SET_SELECTED_STATION",
                setData: "SET_DATA"
            }),

            async initMap() {
                const map = new KakaoMapUtils(this.$refs[this.selectedCategory + "-map"]);
                map.setMapType("SKYVIEW");

                await this.fetchStations();
                map.clustering(this.stations);
                map.setOverlay(this.overlayCallback);
                map.addMarkerEventListener("click", async (latitude, longitude) => {
                    await this.setSelectedStation({
                        category: this.selectedCategory,
                        station: this.stations.filter(obj =>
                            obj.latitude >= latitude - 2e-6
                            && obj.latitude <= latitude + 2e-6
                            && obj.longitude >= longitude - 2e-6
                            && obj.longitude <= longitude + 2e-6)[0]
                    });
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
                                <div>측정항목: ${this.obsItems.map(obj => obj.label).join(", ")}</div>
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
