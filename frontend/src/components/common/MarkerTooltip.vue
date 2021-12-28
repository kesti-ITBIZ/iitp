<template>
    <l-tooltip :options="{ offset: [20, -23], direction: 'right' }"><slot /></l-tooltip>
</template>

<script>
    export default {
        name: "MarkerTooltip"
    }
</script>
