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
            }
        }),
        computed: {
            ...mapState({
                selectedCategory: state => state.selectedCategory,
                stations: state => state.stations[state.selectedCategory],
                startDatetime: state => state.startDatetime,
                endDatetime: state => state.endDatetime,
                xAxis: state => state[state.selectedCategory].xAxis,
                yAxis: state => state[state.selectedCategory].yAxis,
                selectedFineParticleRange: state => state.selectedFineParticleRange
            })
        },
        watch: {
            async selectedCategory() {
                if (!this.maps[this.selectedCategory]) {
                    await this.addStations(this.selectedCategory);
                    const map = new KakaoMapUtils(this.$refs[this.selectedCategory + "-map"]);
                    map.setMapType("SKYVIEW");
                    map.clustering(this.stations);
                    map.setOverlay(this.overlayCallback);
                    this.maps = { ...this.maps, [this.selectedCategory]: map }
                }
            }
        },
        methods: {
            ...mapActions({
                addStations: "ADD_STATIONS",
                setData: "SET_DATA"
            }),

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
            }
        },
        async mounted() {
            const map = new KakaoMapUtils(this.$refs[this.selectedCategory + "-map"]);
            map.setMapType("SKYVIEW");

            await this.addStations(this.selectedCategory);
            map.clustering(this.stations);
            map.setOverlay(this.overlayCallback);
            map.addMarkerEventListener("click", async (latitude, longitude) => {
                if (this.xAxis.length === 0)
                    await new Promise(resolve => alert("X축 항목을 추가해주세요.", resolve));
                else if (this.yAxis.length === 0)
                    await new Promise(resolve => alert("Y축 항목을 추가해주세요.", resolve));
                else {
                    // let data = {};
                    // switch (this.selectedCategory) {
                    // case "airkorea":
                    //     data = gql`
                    //         query {
                    //             airkoreaData(param: {
                    //                 startDatetime: "adf",
                    //                 endDatetime: "",
                    //                 stnNm: "",
                    //                 pm10: [],
                    //                 pm25: []
                    //             }) {
                    //                 datetime
                    //
                    //             }
                    //         }
                    //     `
                    //     break;
                    // case "kt":
                    //     break;
                    // case "observer":
                    //     break;
                    // case "sDoT":
                    //     break;
                    // }

                    await this.setData(await this.$http.post(`/api/${this.selectedCategory}/getData`, {
                        startDatetime: this.selectedCategory == "kt" || this.selectedCategory == "observer" ? this.startDatetime : this.startDatetime.format("YYYYMMDDHHmmss"),
                        endDatetime: this.selectedCategory == "kt" || this.selectedCategory == "observer" ? this.endDatetime : this.endDatetime.format("YYYYMMDDHHmmss"),
                        stnNm: this.stations.filter(obj => obj.latitude >= latitude - 2e-6
                            && obj.latitude <= latitude + 2e-6
                            && obj.longitude >= longitude - 2e-6
                            && obj.longitude <= longitude + 2e-6)[0].name,
                        ...this.selectedFineParticleRange
                    }));
                }
            });

            this.maps = { ...this.maps, [this.selectedCategory]: map };
        }
    }
</script>

<style lang="scss">
    @import "./ContentMap.scss";
</style>
