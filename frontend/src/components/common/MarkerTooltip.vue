<template>
    <v-tooltip :options="{ offset: [20, -23], direction: 'right' }"><slot /></v-tooltip>
</template>

<script>
    export default {
        name: "MarkerTooltip"
    }
</script>
