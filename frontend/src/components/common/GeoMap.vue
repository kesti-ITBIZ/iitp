<template>
    <v-map
            ref="map"
            :zoom="zoom"
            :center="[centerLat, centerLon]"
            :options="{ attributionControl: false }"
            style="height: 100%;">
        <v-tile-layer url="http://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png" />
        <v-canvas-layer
                :location="locations"
                @l-drawing="drawing" />
        <v-canvas-layer
                :location="locations"
                @l-mousemove="hover"
                @l-click="click" />
<!--                <slot />-->
    </v-map>
</template>

<script>
    export default {
        name: "GeoMap",
        props: {
            zoom: Number,
            centerLat: Number,
            centerLon: Number,
            data: Array
        },
        data: () => ({
            markerPoints: []
        }),
        computed: {
            locations() {
                return this.data ? this.data.map(station => ({ latlng: [station.latitude, station.longitude] })) : [];
            }
        },
        methods: {
            invalidateSize(invalidate) {
                this.$refs.map.mapObject.invalidateSize(invalidate);
            },

            drawing(info) {
                const markerPoints = [];
                const canvas = info.canvas;
                const context = canvas.getContext("2d");
                const map = this.$refs.map.mapObject;
                const bounds = map.getBounds();

                for (let i = 0; this.data && i < this.data.length; i++) {
                    const latlng = [this.data[i].latitude, this.data[i].longitude];
                    if (bounds.contains(latlng)) {
                        const markerImg = new Image(32, 44),
                            shadowImg = new Image(35, 16);

                        const dot = map.latLngToContainerPoint(latlng);
                        const [x, y] = [dot.x, dot.y];

                        shadowImg.src = require("../../assets/img/markers_shadow.png");
                        shadowImg.onload = () => {
                            context.drawImage(shadowImg, x - 10, y - 11, 35, 16);
                        };

                        if (this.data[i].pm25 == null) markerImg.src = require("../../assets/img/marker_0.svg");
                        else if (this.data[i].pm25 < 16) markerImg.src = require("../../assets/img/marker_1.svg");
                        else if (this.data[i].pm25 >= 16 && this.data[i].pm25 < 36) markerImg.src = require("../../assets/img/marker_2.svg");
                        else if (this.data[i].pm25 >= 36 && this.data[i].pm25 < 76) markerImg.src = require("../../assets/img/marker_3.svg");
                        else markerImg.src = require("../../assets/img/marker_4.svg");
                        markerImg.onload = () => {
                            context.drawImage(markerImg, x -16, y - 42, 32, 44);
                            context.font = "italic 10pt sans-serif";
                            context.fillText(this.data[i].pm25 ? this.data[i].pm25.toString() : "-", x - 1, y - 24)
                            context.fillStyle = "white";
                            context.textBaseline = "middle";
                            context.textAlign = "center";

                            markerPoints.push({ x, y: y - 24, radius: 14, ...this.data[i] });
                        };
                    }
                }
                this.markerPoints = markerPoints;
            },

            hover(info) {
                const canvas = info.canvas;
                const [cursorX, cursorY] = [info.containerPoint.x, info.containerPoint.y];
                let closedX = 99999, closedY = 99999;
                let minDistance = 99999, index = -1;
                this.markerPoints.forEach(({ x, y }, i) => {
                    const distance = ((cursorX - x) ** 2 + (cursorY - y) ** 2) ** .5;
                    if (minDistance > distance) {
                        minDistance = distance;
                        [closedX, closedY] = [x, y];
                        index = i;
                    }
                });
                if (index !== -1 && ((cursorX - closedX) ** 2 + (cursorY - closedY) ** 2) ** .5 <= 16) {
                    canvas.style.cursor = "pointer";
                    const tooltip = new Image();
                    const markerData = this.markerPoints[index];
                    tooltip.src = "data:image/svg+xml," +
                        `<svg xmlns="http://www.w3.org/2000/svg">
                            <foreignObject width="100%" height="100%">
                                <div xmlns="http://www.w3.org/1999/xhtml">
                                    <div class="overlay" style="font: 14px 'NanumSquare'; background-color: white; box-shadow: 2px 2px 5px 2px rgba(0, 0, 0, .29); max-width: 350px; border: 1px solid rgb(165, 165, 165); border-radius: 6px; padding: 10px;">
                                        <div>
                                            <h4 style="font: 16px 'NanumSquare'; font-weight: bold; margin: 0 0 10px 0; transform: rotate(0.03deg);">${markerData.name}</h4>
                                            <div style="margin-top: 5px;">
                                                <div style="margin-top: 2px; transform: rotate(0.03deg);">위도: ${Math.round(markerData.latitude * 100) / 100}</div>
                                                <div style="margin-top: 2px; transform: rotate(0.03deg);">경도: ${Math.round(markerData.longitude * 100) / 100}</div>
                                                <div style="margin-top: 2px; transform: rotate(0.03deg);">주소: ${markerData.address}</div>
                                                <!--<div>측정항목: {{ items[category].join(", ") }}</div>-->
                                                <div style="margin-top: 2px; transform: rotate(0.03deg);">기간평균 미세먼지 농도: ${this.fineDust(markerData.pm25)}</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </foreignObject>
                        </svg>`;
                    tooltip.onload = () => {
                        const context = canvas.getContext("2d");
                        context.clearRect(0, 0, canvas.width, canvas.height);
                        context.drawImage(tooltip, cursorX, cursorY);
                    };
                } else {
                    canvas.style.cursor = "grab";
                    const context = canvas.getContext("2d");
                    context.clearRect(0, 0, canvas.width, canvas.height);
                }
            },

            click(info) {
                const [cursorX, cursorY] = [info.containerPoint.x, info.containerPoint.y];
                let closedX = 99999, closedY = 99999;
                let minDistance = 99999, index = -1;
                this.markerPoints.forEach(({ x, y }, i) => {
                    const distance = ((cursorX - x) ** 2 + (cursorY - y) ** 2) ** .5;
                    if (minDistance > distance) {
                        minDistance = distance;
                        [closedX, closedY] = [x, y];
                        index = i;
                    }
                });
                if (index !== -1 && ((cursorX - closedX) ** 2 + (cursorY - closedY) ** 2) ** .5 <= 16) {
                    const station = { ...this.markerPoints[index] };
                    delete station.x;
                    delete station.y;
                    this.$emit("click", station);
                }
            },

            fineDust(pm25) {
                if (pm25 == null) return "해당 기간 정보없음";
                else if (pm25 < 16) return "좋음";
                else if (pm25 >= 16 && pm25 < 36) return "보통";
                else if (pm25 >= 36 && pm25 < 76) return "나쁨";
                else if (pm25 >= 76) return "매우나쁨";
            }
        },
        mounted() {
            console.log(this.locations);
        },
        updated() {
            console.log(this.locations);
        }
    }
</script>
