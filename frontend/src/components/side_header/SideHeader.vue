<template>
    <div id="side_header">
        <table>
            <colgroup>
                <col style="width: 50%;" />
                <col style="width: 50%;" />
            </colgroup>
            <tbody>
                <tr>
                    <td><items /></td>
                    <td><x-axis /></td>
                </tr>
                <tr>
                    <td><selected-stations /></td>
                    <td><y-axis /></td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import Items from "./items/Items";
    import SelectedStations from "./selected_stations/SelectedStations";
    import XAxis from "./x_axis/XAxis";
    import YAxis from "./y_axis/YAxis";

    export default {
        name: "SideHeader",
        components: {
            Items,
            SelectedStations,
            XAxis,
            YAxis
        }
    }
</script>

<style lang="scss">
    @import "./SideHeader.scss";
</style>
