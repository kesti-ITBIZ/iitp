<template>
    <div id="side-header">
        <items />
        <x-axis v-show="windowWidth >= reactiveMaxWidth" />
        <y-axis v-show="windowWidth >= reactiveMaxWidth" />
        <div>
            <x-axis v-show="windowWidth < reactiveMaxWidth" />
            <y-axis v-show="windowWidth < reactiveMaxWidth" />
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
                reactiveMaxWidth: state => state.common.reactiveMaxWidth,
                windowWidth: state => state.common.windowWidth
            })
        }
    }
</script>

<style lang="scss">
    @import "src/components/observation/content_wrap/side_header/SideHeader";
</style>
