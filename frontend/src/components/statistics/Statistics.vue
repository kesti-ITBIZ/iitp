<template>
    <div id="statistics">
        <table>
            <colgroup>
                <col style="width: calc(50% - 200px);" />
                <col style="width: 200px;" />
            </colgroup>
            <tbody>
                <tr>
                    <td><content-wrap /></td>
                    <td><items /></td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="2">
                        <input type="button" value="조회" />
                    </td>
                </tr>
            </tfoot>
        </table>
        <chart-wrap />
    </div>
</template>

<script>
    import ContentWrap from "./content_wrap/ContentWrap";
    import Items from "./items/Items";
    import ChartWrap from "./chart_wrap/ChartWrap";

    export default {
        name: "Statistics",
        components: {
            ContentWrap,
            Items,
            ChartWrap
        }
    }
</script>

<style lang="scss">
    @import "./Statistics";
</style>
