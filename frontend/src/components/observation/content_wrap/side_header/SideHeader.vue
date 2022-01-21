<template>
    <div id="side-header">
        <items />
        <x-axis v-show="windowWidth >= 1160" />
        <y-axis v-show="windowWidth >= 1160" />
        <div>
            <x-axis v-show="windowWidth < 1160" />
            <y-axis v-show="windowWidth < 1160" />
        </div>
    </div>
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
    @import "src/components/observation/content_wrap/side_header/SideHeader";
</style>
