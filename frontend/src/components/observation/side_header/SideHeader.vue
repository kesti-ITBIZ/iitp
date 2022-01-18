<template>
    <table id="side_header">
        <colgroup v-if="windowWidth <= 1200">
            <col style="width: 50%;" />
            <col style="width: 50%;" />
        </colgroup>
        <tbody v-if="windowWidth > 1200">
            <tr><td><items /></td></tr>
            <tr><td><x-axis /></td></tr>
            <tr><td><y-axis /></td></tr>
        </tbody>
        <tbody v-else>
            <tr>
                <td rowspan="2"><items /></td>
                <td><x-axis /></td>
            </tr>
            <tr><td><y-axis /></td></tr>
        </tbody>
    </table>
</template>

<script>
    import { mapState } from "vuex";

    import Items from "./items/Items";
    import XAxis from "./x_axis/XAxis";
    import YAxis from "./y_axis/YAxis";

    export default {
        name: "SideHeader",
        components: {
            Items,
            XAxis,
            YAxis
        },
        computed: {
            ...mapState({
                windowWidth: state => state.common.windowWidth
            })
        }
    }
</script>

<style lang="scss">
    @import "./SideHeader";
</style>
