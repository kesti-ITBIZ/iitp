<template>
    <div id="layout" class="text-no-drag" :style="{ backgroundColor: 'white' }" @click="clearSelectedItem">
        <router-view />
        <alert v-show="alert.show"
               :title="alert.title"
               :ok-button-text="alert.okButtonText"
               @ok="alert.onOk" />
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    import Alert from "./components/observation/common/alert/Alert";

    export default {
        name: "App",
        components: {
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
