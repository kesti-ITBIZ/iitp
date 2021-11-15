<template>
    <div id="map" ref="map"></div>
</template>

<script>
    import { mapState } from "vuex";
    import axios from "axios";
    import { execAsync } from "@/assets/js/common.utils";

    import KakaoMapUtils from "../../../../assets/js/map.utils";

    export default {
        name: "ContentMap",
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
                let data;
                if (this.selectedData == "all") {
                    data = [];
                    const results = await execAsync(
                        () => fetch("/api/airkorea/stations").then(response => response.json()),
                        () => fetch("/api/kt/stations").then(response => response.json()),
                        () => fetch("/api/sDoT/stations").then(response => response.json()),
                        () => fetch("/api/observer/stations").then(response => response.json()));
                    Object.keys(results).forEach(i => data = data.concat(results[i].map(obj => ({ latitude: obj.latitude, longitude: obj.longitude }))));
                } else data = (await fetch(`/api/${this.selectedData}/stations`).then(response => response.json())).map(obj => ({ latitude: obj.latitude, longitude: obj.longitude }));
                this.map.clustering(data);
                this.map.setOverlay(this.overlayCallback);
            }
        },
        methods: {
            overlayCallback(latitude, longitude) {
                const index = this.data.findIndex(obj => Math.abs(obj.latitude - latitude) <= 1e-6 && Math.abs(obj.longitude - longitude) <= 1e-6);

                const title = this.data && index !== -1 ? this.data[index].address : "주소정보가 존재하지 않습니다.";
                [latitude, longitude] = [Math.round(latitude * 10000) / 10000, Math.round(longitude * 10000) / 10000];

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

            const data = (await axios.get(`/api/${this.selectedData}/stations`).then(response => response.data));
            this.data = Object.freeze(data);
            this.map.clustering(data.map(obj => ({ latitude: obj.latitude, longitude: obj.longitude })));
            this.map.setOverlay(this.overlayCallback);
        }
    }
</script>

<style>
    @import "./ContentMap.css";
</style>
