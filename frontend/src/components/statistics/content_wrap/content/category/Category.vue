<template>
    <div id="category">
        <ul>
            <li :key="i" v-for="(option, i) in searchOptions">
                <input type="button" :class="selectedSearchOption == option.value ? 'on' : ''" :value="option.label" @click="setSelectedSearchOption(option.value)" />
            </li>
        </ul>
        <ul>
            <li :key="i" v-for="(obj, i) in category">
                <input type="button" :class="selectedCategory == obj.value ? 'on' : ''" :value="obj.label" @click="setSelectedCategory(obj.value)" />
            </li>
        </ul>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    export default {
        name: "Category",
        computed: {
            ...mapState({
                searchOptions: state => state.searchOptions,
                selectedSearchOption: state => state.selectedSearchOption,
                category: state => state.category,
                selectedCategory: state => state.selectedCategory
            })
        },
        methods: {
            ...mapActions({
                setSelectedSearchOption: "SET_SELECTED_SEARCH_OPTION",
                setSelectedCategory: "SET_SELECTED_CATEGORY"
            })
        }
    }
</script>

<style lang="scss">
    @import "./Category";
</style>
