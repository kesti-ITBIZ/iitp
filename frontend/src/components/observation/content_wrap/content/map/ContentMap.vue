<template>
    <div id="map">
        <div class="map" v-show="selectedCategory == category" :key="i" v-for="(category, i) in Object.keys(stations)">
            <geo-map
                    :ref="category + '-map'"
                    :zoom="windowWidth <= 1200 ? 7 : 8"
                    :center="[36, 127.5]"
                    :data="stations[category]"
                    @click="station => setSelectedStation({ category, station })" />
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
            },

            stations() {
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
