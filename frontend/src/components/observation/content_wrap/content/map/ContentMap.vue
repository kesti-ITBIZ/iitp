<template>
    <div id="map">
        <div class="map" v-show="selectedCategory == category" :key="i" v-for="(category, i) in Object.keys(stations)">
            <geo-map :ref="category + '-map'" :zoom="8" :center-lat="36" :center-lon="127.5">
                <geo-marker
                        :key="j"
                        v-for="(station, j) in stations[category]"
                        :lat="station.latitude"
                        :lon="station.longitude"
                        :pm25="station.pm25"
                        @click="setSelectedStation({ category, station })">
                    <marker-tooltip class="overlay">
                        <div>
                            <h4>{{ station.name }}</h4>
                            <div>
                                <div>위도: {{ Math.round(station.latitude * 100) / 100 }}</div>
                                <div>경도: {{ Math.round(station.longitude * 100) / 100 }}</div>
                                <div>주소: {{ station.address }}</div>
                                <div>측정항목: {{ items[category].join(", ") }}</div>
                                <div>기간평균 미세먼지 농도: {{ fineDust(station.pm25) }}</div>
                            </div>
                        </div>
                    </marker-tooltip>
                </geo-marker>
            </geo-map>
            <div v-show="windowWidth <= 1200">스<br /><br />크<br /><br />롤</div>
        </div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    import { stationApi } from "../../../../../assets/js/api";

    export default {
        name: "ContentMap",
        data: () => ({
            items: {
                airkorea: [],
                kt: [],
                sDoT: [],
                observer: []
            }
        }),
        computed: {
            ...mapState({
                windowWidth: state => state.common.windowWidth,
                startDatetime: state => state.observation.startDatetime,
                endDatetime: state => state.observation.endDatetime,
                selectedCategory: state => state.observation.selectedCategory,
                stations: state => state.observation.stations,
                selectedSearchOption: state => state.observation.selectedSearchOption
            })
        },
        watch: {
            startDatetime() {
                this.fetchStations();
            },

            endDatetime() {
                this.fetchStations();
            },

            selectedCategory() {
                if (this.stations[this.selectedCategory] == null) this.fetchStations();
                setTimeout(() => this.$refs[this.selectedCategory + "-map"][0].invalidateSize(true), 0);
            }
        },
        ...stationApi,
        methods: {
            ...mapActions({
                setStations: "SET_STATIONS",
                setSelectedStation: "SET_SELECTED_STATION",
                setData: "SET_DATA"
            }),

            async fetchStations() {
                const stationQuery = this.$apollo.queries[this.selectedCategory + "Stations"];
                stationQuery.skip = false;
                await this.setStations(await stationQuery.refetch().then(response => response.data === undefined || response.data[this.selectedCategory + "Stations"].length === 0 ? [] : response.data[this.selectedCategory + "Stations"].map(obj => ({
                    address: obj.address,
                    name: obj.name,
                    latitude: obj.latitude,
                    longitude: obj.longitude,
                    pm25: obj.pm25
                }))));
                stationQuery.skip = true;
            },

            fineDust(pm25) {
                if (pm25 < 16) return "좋음";
                else if (pm25 >= 16 && pm25 < 36) return "보통";
                else if (pm25 >= 36 && pm25 < 76) return "나쁨";
                else if (pm25 >= 76) return "매우나쁨";
                else return "해당 기간 정보없음";
            }
        },
        mounted() {
            Object.keys(this.items).forEach(key => this.items = Object.freeze({ ...this.items, [key]: this.$store.state.observation[key].items.map(obj => obj.label) }));
            this.fetchStations();
        }
    }
</script>

<style lang="scss">
    @import "./ContentMap";
</style>
