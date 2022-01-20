<template>
    <v-map
            ref="map"
            :zoom="zoom"
            :center="[centerLat, centerLon]"
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
            centerLat: Number,
            centerLon: Number
        },
        methods: {
            invalidateSize(invalidate = true) {
                this.$refs.map.mapObject.invalidateSize(invalidate);
            }
        }
    }
</script>
