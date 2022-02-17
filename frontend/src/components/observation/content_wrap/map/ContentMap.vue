<template>
<<<<<<< HEAD:frontend/src/components/observation/content_wrap/content/map/ContentMap.vue
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
                                <div>위도: {{ (Math.round(station.latitude * 100) / 100).toFixed(2) }}</div>
                                <div>경도: {{ (Math.round(station.longitude * 100) / 100).toFixed(2) }}</div>
                                <div>주소: {{ station.address }}</div>
                                <div>측정항목: {{ items[category].join(", ") }}</div>
                                <div>기간평균 미세먼지 농도: {{ fineDust(station.pm25) }}</div>
                            </div>
                        </div>
                    </marker-tooltip>
                </geo-marker>
            </geo-map>
            <div v-show="windowWidth <= 1200">스<br /><br />크<br /><br />롤</div>
=======
    <div class="container-wrap map">
        <div class="container">
            <div class="map" v-show="selectedCategory == category" :key="i" v-for="(category, i) in Object.keys(stations)">
                <geo-map
                        :ref="category + '-map'"
                        :zoom="windowWidth < reactiveMaxWidth + 1 ? 7 : 8"
                        :center="[36, 127.5]"
                        :data="stations[category]"
                        :marker="marker"
                        :tooltip="tooltip"
                        @click="station => setSelectedStation({ category, station })" />
            </div>
>>>>>>> dev:frontend/src/components/observation/content_wrap/map/ContentMap.vue
        </div>
        <div v-show="windowWidth < reactiveMaxWidth + 1" class="scroll-area">스크롤</div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    import { stationApi } from "../../../../assets/js/api";

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
                category: state => state.observation.category,
                reactiveMaxWidth: state => state.common.reactiveMaxWidth,
                windowWidth: state => state.common.windowWidth,
                startDatetime: state => state.observation.startDatetime,
                endDatetime: state => state.observation.endDatetime,
                selectedCategory: state => state.observation.selectedCategory,
                selectedDateType: state => state.observation.selectedDateType,
                stations: state => state.observation.stations,
                selectedSearchOption: state => state.observation.selectedSearchOption
<<<<<<< HEAD:frontend/src/components/observation/content_wrap/content/map/ContentMap.vue
            })
=======
            }),

            marker() {
                return {
                    enableCompareDot: false,

                    img(data) {
                        if (data.pm25 == null) return require("../../../../assets/img/marker_0.png");
                        else if (data.pm25 < 16) return require("../../../../assets/img/marker_1.png");
                        else if (data.pm25 >= 16 && data.pm25 < 36) return require("../../../../assets/img/marker_2.png");
                        else if (data.pm25 >= 36 && data.pm25 < 76) return require("../../../../assets/img/marker_3.png");
                        else return require("../../../../assets/img/marker_4.png");
                    },
                    imgStyle: {
                        top: -37,
                        left: -13.5
                    },

                    text(data) {
                        return data.pm25 ? data.pm25 : "-";
                    },
                    textStyle: {
                        top: -21,
                        left: -1,
                        fontStyle: "italic",
                        fontSize: "10pt",
                        fontFamily: "sans-serif",
                        color: "white",
                        verticalAlign: "middle",
                        textAlign: "center"
                    },

                    pointCenter: {
                        top: -19,
                        left: 0,
                        radius: 16
                    }
                }
            }
>>>>>>> dev:frontend/src/components/observation/content_wrap/map/ContentMap.vue
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
<<<<<<< HEAD:frontend/src/components/observation/content_wrap/content/map/ContentMap.vue
                setTimeout(() => this.$refs[this.selectedCategory + "-map"][0].invalidateSize(true), 0);
=======
                else setTimeout(() => this.$refs[this.selectedCategory + "-map"][0].invalidateSize(), 0);
            },

            selectedSearchOption() {
                if (this.stations[this.selectedCategory] == null) this.fetchStations();
                else setTimeout(() => this.$refs[this.selectedCategory + "-map"][0].invalidateSize(), 0);
            },

            stations() {
                setTimeout(() => this.$refs[this.selectedCategory + "-map"][0].invalidateSize(), 0);
>>>>>>> dev:frontend/src/components/observation/content_wrap/map/ContentMap.vue
            }
        },
        ...stationApi,
        methods: {
            ...mapActions({
                setStations: "SET_OBSERVATION_STATIONS",
                setSelectedStation: "SET_OBSERVATION_SELECTED_STATION",
                setData: "SET_OBSERVATION_DATA"
            }),

            async fetchStations() {
                const stationQuery = this.$apollo.queries.stations;
                stationQuery.skip = false;
                await this.setStations(await stationQuery.refetch().then(response => response.data === undefined || response.data.stations.length === 0 ? [] : response.data.stations.map(obj => ({
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
<<<<<<< HEAD:frontend/src/components/observation/content_wrap/content/map/ContentMap.vue
        mounted() {
            Object.keys(this.items).forEach(key => this.items = Object.freeze({
                ...this.items,
                [key]: this.$store.state.observation[key].items.map(obj => obj.label)
            }));
            this.fetchStations();
=======
        async mounted() {
            Object.keys(this.items).forEach(key => this.items = Object.freeze({ ...this.items, [key]: this.$store.state.observation[key].items.map(obj => obj.label) }));
            await this.fetchStations();
>>>>>>> dev:frontend/src/components/observation/content_wrap/map/ContentMap.vue
        }
    }
</script>

<style lang="scss">
    @import "./ContentMap";
</style>
