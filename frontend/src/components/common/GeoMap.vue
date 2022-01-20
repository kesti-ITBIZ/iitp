<template>
    <v-map
            ref="map"
            :zoom="zoom"
            :center="center"
            :options="{ attributionControl: false }"
            style="height: 100%;">
        <v-tile-layer url="http://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png" />
        <v-canvas-layer
                v-if="data"
                :location="locations"
                @l-drawing="drawing" />
        <v-canvas-layer
                v-if="data"
                :location="locations"
                @l-mousemove="hover"
                @l-click="click" />
    </v-map>
</template>

<script>
    export default {
        name: "GeoMap",
        props: {
            zoom: Number,
            center: Array,
            data: Array,
            marker: Object,
            tooltip: Function
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
            invalidateSize(invalidate = true) {
                this.$refs.map.mapObject.invalidateSize(invalidate);
            },

            closedMarker(info) {
                const [cursorX, cursorY] = [info.containerPoint.x, info.containerPoint.y];
                let closedX = 99999, closedY = 99999, avgRadius = 0;
                let minDistance = 99999, index = -1;
                this.markerPoints.forEach(({ x, y, radius }, i) => {
                    const distance = ((cursorX - x) ** 2 + (cursorY - y) ** 2) ** .5;
                    if (minDistance > distance) {
                        minDistance = distance;
                        [closedX, closedY] = [x, y];
                        index = i;
                    }
                    avgRadius += radius;
                });
                avgRadius /= this.markerPoints.length;
                if (index !== -1 && ((cursorX - closedX) ** 2 + (cursorY - closedY) ** 2) ** .5 <= avgRadius) {
                    const markerData = { ...this.markerPoints[index] };
                    delete markerData.x;
                    delete markerData.y;
                    return markerData;
                } else return null;
            },

            drawing(info) {
                const markerPoints = [];
                const canvas = info.canvas;
                const context = canvas.getContext("2d");
                const map = this.$refs.map.mapObject;
                const bounds = map.getBounds();

                if (this.data) {
                    const data = this.data.concat();
                    data.sort((a, b) => a.latitude < b.latitude ? 1 : a.latitude === b.latitude && a.longitude < b.longitude ? 1 : -1);
                    for (let i = 0; i < data.length; i++) {
                        const latlng = [data[i].latitude, data[i].longitude];
                        if (bounds.contains(latlng)) {
                            const dot = map.latLngToContainerPoint(latlng);
                            const [x, y] = [dot.x, dot.y];

                            const marker = new Image();
                            marker.src = typeof this.marker.img === "string" ? this.marker.img : this.marker.img(data[i]);
                            marker.onload = () => {
                                if (this.marker.enableCompareDot) {
                                    context.beginPath();
                                    context.fillStyle = "red";
                                    context.arc(x, y, 10, 0, 360);
                                    context.fill();
                                    context.closePath();
                                }

                                let markerTop = 0, markerLeft = 0;
                                if (this.marker.imgStyle) {
                                    if (this.marker.imgStyle.top) markerTop = this.marker.imgStyle.top;
                                    if (this.marker.imgStyle.left) markerLeft = this.marker.imgStyle.left;
                                }
                                context.drawImage(marker, x + markerLeft, y + markerTop, marker.width, marker.height);

                                if (this.marker.text) {
                                    let text = typeof this.marker.text === "string" ? this.marker.text : this.marker.text(data[i]);
                                    if (this.marker.textStyle) {
                                        let textTop = 0, textLeft = 0;
                                        if (this.marker.textStyle.top) textTop = this.marker.textStyle.top;
                                        if (this.marker.textStyle.left) textLeft = this.marker.textStyle.left;
                                        context.fillText(text, x + textLeft, y + textTop);

                                        let font = [];
                                        if (this.marker.textStyle.fontStyle) font.push(this.marker.textStyle.fontStyle);
                                        if (this.marker.textStyle.fontSize) font.push(this.marker.textStyle.fontSize);
                                        if (this.marker.textStyle.fontFamily) font.push(this.marker.textStyle.fontFamily);
                                        context.font = font.join(" ");
                                        if (this.marker.textStyle.color) context.fillStyle = this.marker.textStyle.color;
                                        if (this.marker.textStyle.verticalAlign) context.textBaseline = this.marker.textStyle.verticalAlign;
                                        if (this.marker.textStyle.textAlign) context.textAlign = this.marker.textStyle.textAlign;
                                    } else context.fillText(text, x, y);
                                }

                                let pointTop = 0, pointLeft = 0, pointRadius = 10;
                                if (this.marker.pointCenter) {
                                    if (this.marker.pointCenter.top) pointTop = this.marker.pointCenter.top;
                                    if (this.marker.pointCenter.left) pointLeft = this.marker.pointCenter.left;
                                    if (this.marker.pointCenter.radius) pointRadius = this.marker.pointCenter.radius;
                                }
                                const markerPoint = {
                                    x: x + pointLeft,
                                    y: y + pointTop,
                                    radius: pointRadius,
                                    ...data[i]
                                };
                                markerPoints.push(markerPoint);
                            };
                        }
                    }
                    this.markerPoints = markerPoints;
                }
            },

            hover(info) {
                const canvas = info.canvas;
                const context = canvas.getContext("2d");
                const [cursorX, cursorY] = [info.containerPoint.x, info.containerPoint.y];
                const markerData = this.closedMarker(info);
                if (markerData) {
                    canvas.style.cursor = "pointer";
                    const tooltip = new Image();
                    tooltip.src = "data:image/svg+xml," +
                        `<svg xmlns="http://www.w3.org/2000/svg">
                            <foreignObject width="100%" height="100%">
                                <div xmlns="http://www.w3.org/1999/xhtml">${this.tooltip(markerData)}</div>
                            </foreignObject>
                        </svg>`;
                    tooltip.onload = () => {
                        context.clearRect(0, 0, canvas.width, canvas.height);
                        context.drawImage(tooltip, cursorX, cursorY);
                    };
                } else {
                    canvas.style.cursor = "grab";
                    context.clearRect(0, 0, canvas.width, canvas.height);
                }
            },

            click(info) {
                const markerData = this.closedMarker(info);
                if (markerData) this.$emit("click", markerData);
            }
        }
    }
</script>
