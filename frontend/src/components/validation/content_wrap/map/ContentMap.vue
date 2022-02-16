<template>
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
                category: state => state.validation.category,
                reactiveMaxWidth: state => state.common.reactiveMaxWidth,
                windowWidth: state => state.common.windowWidth,
                startDatetime: state => state.validation.startDatetime,
                endDatetime: state => state.validation.endDatetime,
                selectedCategory: state => state.validation.selectedCategory,
                selectedDateType: state => state.validation.selectedDateType,
                stations: state => state.validation.stations,
                selectedSearchOption: state => state.validation.selectedSearchOption
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
                else setTimeout(() => this.$refs[this.selectedCategory + "-map"][0].invalidateSize(), 0);
            },

            selectedSearchOption() {
                if (this.stations[this.selectedCategory] == null) this.fetchStations();
                else setTimeout(() => this.$refs[this.selectedCategory + "-map"][0].invalidateSize(), 0);
            },

            stations() {
                setTimeout(() => this.$refs[this.selectedCategory + "-map"][0].invalidateSize(), 0);
            }
        },
        ...stationApi,
        methods: {
            ...mapActions({
                setStations: "SET_VALIDATION_STATIONS",
                setSelectedStation: "SET_VALIDATION_SELECTED_STATION",
                setData: "SET_VALIDATION_DATA"
            }),

            async fetchStations() {
                const stationQuery = this.$apollo.queries.stations;
                stationQuery.skip = false;
                await this.setStations(await stationQuery.refetch().then(response => !response.data || response.data.stations.length === 0 ? [] : response.data.stations.map(obj => ({
                    address: obj.address,
                    name: obj.name,
                    latitude: obj.latitude,
                    longitude: obj.longitude,
                    pm25: obj.pm25
                }))));
                stationQuery.skip = true;
            },

            tooltip(data) {
                return `
                    <div class="overlay" style="font: 14px 'NanumSquare'; background-color: white; box-shadow: 2px 2px 5px 2px rgba(0, 0, 0, .29); max-width: 350px; border: 1px solid rgb(165, 165, 165); border-radius: 6px; padding: 10px;">
                        <div>
                            <h4 style="font: 16px 'NanumSquare'; font-weight: bold; margin: 0 0 10px 0; transform: rotate(0.03deg);">${data.name}</h4>
                            <div style="margin-top: 5px;">
                                <div style="margin-top: 2px; transform: rotate(0.03deg);">위도: ${Math.round(data.latitude * 100) / 100}</div>
                                <div style="margin-top: 2px; transform: rotate(0.03deg);">경도: ${Math.round(data.longitude * 100) / 100}</div>
                                <div style="margin-top: 2px; transform: rotate(0.03deg);">주소: ${data.address}</div>
                                <div style="margin-top: 2px; transform: rotate(0.03deg);">측정항목: ${this.items[this.selectedCategory].join(", ")}</div>
                                <div style="margin-top: 2px; transform: rotate(0.03deg);">
                                    기간평균 미세먼지 농도: ${this.fineDust(data.pm25)}
                                </div>
                            </div>
                        </div>
                    </div>`;
            },

            fineDust(pm25) {
                if (pm25 == null) return "해당 기간 정보없음";
                else if (pm25 < 16) return "좋음";
                else if (pm25 >= 16 && pm25 < 36) return "보통";
                else if (pm25 >= 36 && pm25 < 76) return "나쁨";
                else if (pm25 >= 76) return "매우나쁨";
            }
        },
        async mounted() {
            Object.keys(this.items).forEach(key => this.items = Object.freeze({ ...this.items, [key]: this.$store.state.validation[key].items.map(obj => obj.label) }));
            await this.fetchStations();
            this.$refs[this.selectedCategory + "-map"][0].invalidateSize();
        }
    }
</script>

<style lang="scss">
    @import "./ContentMap";
</style>
