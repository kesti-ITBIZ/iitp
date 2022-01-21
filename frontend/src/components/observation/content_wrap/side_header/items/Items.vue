<template>
    <div class="container-wrap items">
        <div class="container">
            <div class="head">· 데이터 항목</div>
            <div class="body">
                <ul>
                    <li :key="i" v-for="(item, i) in items">
                        <input type="button" :class="['item-btn', item.value]" :value="item.label + (item.unit !== '' ? ` (${item.unit})` : '')" @click.stop="e => onClickItem(e, item)" />
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
                selectedCategory: state => state.observation.selectedCategory,
                items: state => state.observation[state.observation.selectedCategory].items,
                selectedItem: state => state.observation[state.observation.selectedCategory].selectedItem,
                xAxis: state => state.observation[state.observation.selectedCategory].xAxis,
                yAxis: state => state.observation[state.observation.selectedCategory].yAxis
            })
        },
        watch: {
            selectedCategory() {
                this.showItemTooltip = false;
            }
        },
        methods: {
            ...mapActions({
                setSelectedItem: "SET_SELECTED_ITEM",
                addXAxis: "ADD_X_AXIS",
                removeXAxis: "REMOVE_X_AXIS",
                addYAxis: "ADD_Y_AXIS"
            }),

            onClickItem(e, item) {
                this.setSelectedItem(this.selectedItem && this.selectedItem.item.value === item.value ? null : {
                    item,
                    tooltipPosition: {
                        top: `${e.pageY - 8}px`,
                        left: `${e.pageX - 10}px`
                    }
                });
            }
        }
    }
</script>

<style lang="scss">
    @import "src/components/observation/content_wrap/side_header/items/Items";
</style>
