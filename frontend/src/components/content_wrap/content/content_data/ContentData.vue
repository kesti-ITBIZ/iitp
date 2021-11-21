<template>
    <div id="content-data">
        <ul>
            <li :key="i" v-for="(obj, i) in category">
                <input type="button" :class="selectedCategory == obj.value ? 'on' : ''" :value="obj.label" @click="setSelectedCategory(obj.value)" />
            </li>
        </ul>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    import { gql } from "graphql-tag";

    export default {
        name: "ContentData",
        computed: {
            ...mapState({
                category: state => state.category,
                selectedCategory: state => state.selectedCategory
            })
        },
        methods: {
            ...mapActions({
                setSelectedCategory: "SET_SELECTED_CATEGORY"
            })
        },
        apollo: {
            airkoreaStations: gql`
                query {
                    airkoreaStations {
                        address
                    }
                }
            `
        },
        async mounted() {
            console.log("a");
            console.log(await this.$apollo.queries.airkoreaStations);
            console.log("b");
        }
    }
</script>

<style lang="scss">
    @import "./ContentData.scss";
</style>
