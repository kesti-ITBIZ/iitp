<template>
    <div id="layout" class="text-no-drag" @click="clearSelectedItem">
        <side-header />
        <content-wrap />
    </div>
</template>

<script>
    import { mapActions } from "vuex";

    import SideHeader from "./components/side_header/SideHeader";
    import ContentWrap from "./components/content_wrap/ContentWrap";

    export default {
        name: "App",
        components: {
            SideHeader,
            ContentWrap
        },
        methods: {
            ...mapActions({
                clearSelectedItem: "CLEAR_SELECTED_ITEM"
            })
        }
    }
</script>

<style>
    @import "./App.css";
</style>
