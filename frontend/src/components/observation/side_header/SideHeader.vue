<template>
    <table id="side_header">
        <tbody>
            <tr><td><items /></td></tr>
            <tr><td><x-axis /></td></tr>
            <tr><td><y-axis /></td></tr>
        </tbody>
    </table>
</template>

<script>
    import Items from "./items/Items";
    import XAxis from "./x_axis/XAxis";
    import YAxis from "./y_axis/YAxis";

    export default {
        name: "SideHeader",
        components: {
            Items,
            XAxis,
            YAxis
        }
    }
</script>

<style lang="scss">
    @import "./SideHeader";
</style>
