<template>
    <div id="map">
        <div v-show="selectedData == 'airkorea'" class="map" ref="airkorea-map"></div>
        <div v-show="selectedData == 'kt'" class="map" ref="kt-map"></div>
        <div v-show="selectedData == 'sDoT'" class="map" ref="sDoT-map"></div>
        <div v-show="selectedData == 'observer'" class="map" ref="observer-map"></div>
        <div v-show="selectedData == 'all'" class="map" ref="all-map"></div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import { alert } from "@/assets/js/common.utils";

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
                selectedData: state => state.selectedData,
                stations: state => state.stations[state.selectedData],
                startDatetime: state => state.startDatetime,
                endDatetime: state => state.endDatetime,
                xAxis: state => state[state.selectedData].xAxis,
                yAxis: state => state[state.selectedData].yAxis,
                selectedFindParticleRange: state => state.selectedFineParticleRange
            })
        },
        watch: {
            async selectedData() {
                if (!this.maps[this.selectedData]) {
                    await this.addStations(this.selectedData);
                    const map = new KakaoMapUtils(this.$refs[this.selectedData + "-map"]);
                    map.setMapType("SKYVIEW");
                    map.clustering(this.stations);
                    map.setOverlay(this.overlayCallback);
                    this.maps = { ...this.maps, [this.selectedData]: map }
                }
            }
        },
        methods: {
            ...mapActions({
                addStations: "ADD_STATIONS"
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
            const map = new KakaoMapUtils(this.$refs[this.selectedData + "-map"]);
            map.setMapType("SKYVIEW");

            await this.addStations(this.selectedData);
            map.clustering(this.stations);
            map.setOverlay(this.overlayCallback);
            map.addMarkerEventListener("click", async (latitude, longitude) => {
                if (this.xAxis.length === 0)
                    await new Promise(resolve => alert("X축 항목을 추가해주세요.", resolve));
                else if (this.yAxis.length === 0)
                    await new Promise(resolve => alert("Y축 항목을 추가해주세요.", resolve));
                else {
                    console.log(
                        this.stations.filter(obj => obj.latitude >= latitude - 2e-6
                            && obj.latitude <= latitude + 2e-6
                            && obj.longitude >= longitude - 2e-6
                            && obj.longitude <= longitude + 2e-6)[0].name,
                        this.selectedData,
                        this.startDatetime,
                        this.endDatetime,
                        this.xAxis,
                        this.yAxis,
                        this.selectedFindParticleRange
                    );
                }
            });

            this.maps = { ...this.maps, [this.selectedData]: map };
        }
    }
</script>

<style>
    @import "./ContentMap.css";
</style>
