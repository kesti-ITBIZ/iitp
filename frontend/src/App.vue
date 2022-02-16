<template>
    <div id="layout" class="text-no-drag" :style="{ backgroundColor: 'white' }">
        <router-view />
        <alert v-show="alert.show"
               :title="alert.title"
               :ok-button-text="alert.okButtonText"
               @ok="alert.onOk" />
    </div>
</template>

<script>
    import { mapState } from "vuex";

    import Alert from "./components/common/alert/Alert";

    export default {
        name: "App",
        components: {
            Alert
        },
        computed: {
            ...mapState({
                alert: state => state.common.alert
            })
        }
    }
</script>

<style lang="scss">
    @import "./App.scss";
</style>
