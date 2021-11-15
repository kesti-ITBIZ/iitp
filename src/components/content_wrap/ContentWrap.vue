<template>
    <div id="content-wrap">
        <table>
            <thead>
                <tr>
                    <th colspan="2">
                        <table>
                            <colgroup>
                                <col style="min-width: 600px;" />
                                <col style="width: 570px; min-width: 570px;" />
                            </colgroup>
                            <thead>
                                <tr>
                                    <select-datetime />
                                    <select-constraint />
                                </tr>
                                <tr><select-chart /></tr>
                            </thead>
                        </table>
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <content-chart />
                    <td class="map">
                        <div class="map-data">
                            <content-map />
                        </div>
                        <content-data />
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import SelectDatetime from "./header/select_datetime/SelectDatetime";
    import SelectConstraint from "./header/select_constraint/SelectConstraint";
    import SelectChart from "./header/select_chart/SelectChart";
    import ContentChart from "./content/chart/ContentChart";
    import ContentMap from "./content/map/ContentMap";
    import ContentData from "./content/content_data/ContentData";

    export default {
        name: "ContentWrap",
        components: {
            SelectDatetime,
            SelectConstraint,
            SelectChart,
            ContentChart,
            ContentMap,
            ContentData
        }
    }
</script>

<style>
    @import "./ContentWrap.css";
</style>
