<template>
    <div id="content-data">
        <ul>
            <li :key="i" v-for="(obj, i) in data">
                <input type="button" :class="selectedData.value == obj.value ? 'on' : ''" :value="obj.label" @click="setSelectedData(obj)" />
            </li>
        </ul>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    export default {
        name: "ContentData",
        computed: {
            ...mapState({
                data: state => state.data,
                selectedData: state => state.selectedData
            })
        },
        methods: {
            ...mapActions({
                setSelectedData: "SET_SELECTED_DATA"
            })
        }
    }
</script>

<style>
    @import "./ContentData.css";
</style>
