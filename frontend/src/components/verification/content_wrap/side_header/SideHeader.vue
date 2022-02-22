<template>
    <div id="side-header">
        <items />
    </div>
</template>

<script>
    import { mapState } from "vuex";

    import Items from "./items/Items";

    export default {
        name: "SideHeader",
        components: {
            Items,
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
    @import "./SideHeader";
</style>
