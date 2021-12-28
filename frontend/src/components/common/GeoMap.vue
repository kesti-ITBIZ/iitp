<template>
    <l-map
            ref="map"
            :zoom="zoom"
            :center="[centerLat, centerLon]"
            :options="{ attributionControl: false }"
            style="height: 100%;">
        <l-tile-layer url="http://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png" />
        <slot />
    </l-map>
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
            invalidateSize(invalidate) {
                this.$refs.map.mapObject.invalidateSize(invalidate);
            }
        }
    }
</script>
