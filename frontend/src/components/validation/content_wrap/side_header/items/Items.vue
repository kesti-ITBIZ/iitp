<template>
    <div class="container-wrap items">
        <div class="container">
            <div class="head">· 측정 항목</div>
            <div class="body">
                <ul>
                    <li :key="i" v-for="(item, i) in items">
                        <input type="button" :class="['item-btn', item.value, selectedItem && selectedItem.value == item.value ? 'on' : '']" :value="item.label + (item.unit !== '' ? ` (${item.unit})` : '')" @click.stop="setSelectedItem(item)" />
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    export default {
        name: "Items",
        computed: {
            ...mapState({
                items: state => state.validation[state.validation.selectedCategory].items,
                selectedItem: state => state.validation[state.validation.selectedCategory].selectedItem
            })
        },
        methods: {
            ...mapActions({
                setSelectedItem: "SET_VALIDATION_SELECTED_ITEM"
            })
        }
    }
</script>

<style lang="scss">
    @import "./Items";
</style>
