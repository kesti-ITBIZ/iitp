<template>
    <div ref="map"></div>
</template>

<script>
    import { mapState } from "vuex";
    import axios from "axios";

    import KakaoMapUtils from "../../assets/js/map.utils";

    export default {
        name: "Map",
        data: () => ({
            map: null,
            data: []
        }),
        computed: {
            ...mapState({
                selectedData: state => state.selectedData
            })
        },
        watch: {
            async selectedData() {
                this.map.clustering((await axios.get(`/stations/${this.selectedData.toLowerCase()}`).then(response => response.data)).map(obj => ({ latitude: obj.latitude, longitude: obj.longitude })));
                this.map.setOverlay(this.overlayCallback);
            }
        },
        methods: {
            overlayCallback(latitude, longitude) {
                const index = this.data.findIndex(obj => Math.abs(obj.latitude - latitude) <= 1e-7 && Math.abs(obj.longitude - longitude) <= 1e-7);

                const title = this.data && index !== -1 ? this.data[index].address : "주소정보가 존재하지 않습니다.";
                [latitude, longitude] = [Math.round(latitude * 10) / 10, Math.round(longitude * 10) / 10];

                return `
                    <div class="overlay">
                        <h4>${title}</h4>
                        <p>
                            <div>위도: ${latitude}</div>
                            <div>경도: ${longitude}</div>
                        </p>
                    </div>`;
            }
        },
        async mounted() {
            this.map = new KakaoMapUtils(this.$refs["map"]);
            this.map.setMapType("SKYVIEW");

            const data = (await axios.get(`/stations/${this.selectedData.toLowerCase()}`).then(response => response.data));
            this.data = Object.freeze(data);
            this.map.clustering(data.map(obj => ({ latitude: obj.latitude, longitude: obj.longitude })));
            this.map.setOverlay(this.overlayCallback);
        }
    }
</script>

<style>
    @import "./Map.css";
</style>
