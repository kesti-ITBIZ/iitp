<template>
    <div id="side_header">
        <table>
            <tbody>
                <tr><td><items /></td></tr>
                <tr><td><x-axis /></td></tr>
                <tr><td><y-axis /></td></tr>
            </tbody>
        </table>
    </div>
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
    @import "./SideHeader.scss";
</style>
