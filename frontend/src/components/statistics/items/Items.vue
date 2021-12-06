<template>
    <table id="items">
        <thead>
            <tr>
                <th class="opt-head">데이터 항목</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td class="opt-body scroll no-scrollbar">
                    <div class="pm10">PM10 (ppm)</div>
                    <div class="pm25">PM2.5 (ppm)</div>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<script>
    export default {
        name: "Items"
    }
</script>

<style lang="scss">
    @import "src/components/statistics/items/Items";
</style>
