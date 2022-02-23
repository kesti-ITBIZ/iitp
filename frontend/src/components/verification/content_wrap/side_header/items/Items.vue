<template>
    <div class="container-wrap items">
        <div class="container">
            <div class="head">· 데이터 항목</div>
            <div class="body">
                <ul>
                    <li :key="i" v-for="(item, i) in items">
                        <input type="button"
                               :class="['item-btn', item.value, selectedItem.value == item.value ? 'on' : '']"
                               :value="item.label + (item.unit !== '' ? ` (${item.unit})` : '')"
                               @click="setSelectedItem(item)" />
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
        data: () => ({
            showItemTooltip: false,
            itemTooltip: {
                top: 0,
                left: 0
            }
        }),
        computed: {
            ...mapState({
                selectedCategory: state => state.verification.selectedCategory,
                items: state => state.verification.items,
                selectedItem: state => state.verification.selectedItem
            })
        },
        watch: {
            selectedCategory() {
                this.showItemTooltip = false;
            }
        },
        methods: {
            ...mapActions({
                setSelectedItem: "SET_VERIFICATION_SELECTED_ITEM",
            })
        }
    }
</script>

<style lang="scss">
    @import "./Items";
</style>
