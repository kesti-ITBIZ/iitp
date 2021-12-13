<template>
    <div id="category">
        <ul>
            <li :key="i" v-for="(option, i) in searchOptions">
                <input type="button" :class="selectedSearchOption == option.value ? 'on' : ''" :value="option.label" @click="setSelectedSearchOption(option.value)" />
            </li>
        </ul>
        <ul>
            <li :key="i" v-for="(obj, i) in category">
                <button :class="[selectedCategory == obj.value ? 'on' : '']" @click="setSelectedCategory(obj.value)">
                    <span :class="obj.value"></span>
                    <span>{{ obj.label }}</span>
                </button>
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
                searchOptions: state => state.observation.searchOptions,
                selectedSearchOption: state => state.observation.selectedSearchOption,
                category: state => state.observation.category,
                selectedCategory: state => state.observation.selectedCategory
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
