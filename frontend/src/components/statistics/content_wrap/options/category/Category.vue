<template>
    <div id="category">
        <ul>
            <li :key="i" v-for="(option, i) in searchOptions">
                <input type="button" :class="selectedSearchOption == option.value ? 'on' : ''" :value="option.label" @click="setSelectedSearchOption(option.value)" />
            </li>
        </ul>
        <ul v-show="windowWidth >= reactiveMaxWidth + 1">
            <li :key="i" v-for="(obj, i) in category">
                <button :class="selectedCategory == obj.value ? 'on' : ''" @click="setSelectedCategory(obj.value)">
                    <span :class="obj.value"></span>
                    <span>{{ obj.label }}</span>
                </button>
            </li>
        </ul>
        <table v-show="windowWidth < reactiveMaxWidth + 1">
            <colgroup>
                <col style="width: 50%;" />
                <col style="width: 50%;" />
            </colgroup>
            <thead>
                <tr>
                    <th>
                        <button :class="selectedCategory == 'airkorea' ? 'on' : ''" @click="setSelectedCategory('airkorea')">
                            <span class="airkorea"></span>
                            <span>환경부</span>
                        </button>
                    </th>
                    <th>
                        <button :class="selectedCategory == 'kt' ? 'on' : ''" @click="setSelectedCategory('kt')">
                            <span class="kt"></span>
                            <span>KT AirMap</span>
                        </button>
                    </th>
                </tr>
                <tr>
                    <th>
                        <button :class="selectedCategory == 'sDoT' ? 'on' : ''" @click="setSelectedCategory('sDoT')">
                            <span class="sDoT"></span>
                            <span>서울시</span>
                        </button>
                    </th>
                    <th>
                        <button :class="selectedCategory == 'observer' ? 'on' : ''" @click="setSelectedCategory('observer')">
                            <span class="observer"></span>
                            <span>옵저버</span>
                        </button>
                    </th>
                </tr>
            </thead>
        </table>
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
                searchOptions: state => state.statistics.searchOptions,
                selectedSearchOption: state => state.statistics.selectedSearchOption,
                category: state => state.statistics.category,
                selectedCategory: state => state.statistics.selectedCategory
            })
        },
        methods: {
            ...mapActions({
                setSelectedSearchOption: "SET_STATISTICS_SELECTED_SEARCH_OPTION",
                setSelectedCategory: "SET_STATISTICS_SELECTED_CATEGORY"
            })
        }
    }
</script>

<style lang="scss">
    @import "./Category";
</style>
