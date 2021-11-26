<template>
    <div id="layout" class="text-no-drag" @click="clearSelectedItem">
        <content-wrap />
        <side-header />
        <chart-wrap />
        <alert v-show="alert.show"
               :title="alert.title"
               :ok-button-text="alert.okButtonText"
               @ok="alert.onOk" />
|    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    import SideHeader from "./components/side_header/SideHeader";
    import ContentWrap from "./components/content_wrap/ContentWrap";
    import ChartWrap from "./components/chart_wrap/ChartWrap";
    import Alert from "./components/common/alert/Alert";

    export default {
        name: "App",
        components: {
            SideHeader,
            ContentWrap,
            ChartWrap,
            Alert
        },
        computed: {
            ...mapState({
                alert: state => state.common.alert
            })
        },
        methods: {
            ...mapActions({
                clearSelectedItem: "CLEAR_SELECTED_ITEM"
            })
        }
    }
</script>

<style lang="scss">
    @import "./App.scss";
</style>
