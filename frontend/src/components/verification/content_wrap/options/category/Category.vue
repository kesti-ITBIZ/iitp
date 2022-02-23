<template>
    <div id="category">
        <ul>
            <li :key="i" v-for="(option, i) in searchOptions">
                <input type="button" :class="selectedSearchOption == option.value ? 'on' : ''" :value="option.label" @click="setSelectedSearchOption(option.value)" />
            </li>
        </ul>
        <div class="org-wrap">
            <div class="label">검증 지점</div>
            <button :class="selectedCategory == 'airkorea' ? 'on' : ''" @click="setSelectedCategory('airkorea')">
                <span class="airkorea"></span>
                <span>환경부</span>
            </button>
            <div class="label">비교 지점</div>
            <ul>
                <li :key="i" v-for="(obj, i) in category.filter(c => c.value !== 'airkorea')">
                    <button :class="selectedCategory == obj.value ? 'on' : ''" @click="setSelectedCategory(obj.value)">
                        <span :class="obj.value"></span>
                        <span>{{ obj.label }}</span>
                    </button>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    export default {
        name: "Category",
        computed: {
            ...mapState({
                reactiveMaxWidth: state => state.common.reactiveMaxWidth,
                windowWidth: state => state.common.windowWidth,
                searchOptions: state => state.verification.searchOptions,
                selectedSearchOption: state => state.verification.selectedSearchOption,
                category: state => state.verification.category,
                selectedCategory: state => state.verification.selectedCategory
            })
        },
        methods: {
            ...mapActions({
                setSelectedSearchOption: "SET_VERIFICATION_SELECTED_SEARCH_OPTION",
                setSelectedCategory: "SET_VERIFICATION_SELECTED_CATEGORY"
            })
        }
    }
</script>

<style lang="scss">
    @import "./Category";
</style>
