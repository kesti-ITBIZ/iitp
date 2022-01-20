<template>
    <div class="item-tooltip" v-show="selectedItem" :style="selectedItem ? { top: selectedItem.tooltipPosition.top, left: selectedItem.tooltipPosition.left } : null">
        <div @click="addX">X축 추가</div><hr />
        <div :class="selectedItem && (selectedItem.value === 'datetime' || selectedItem.value === 'windDirection') ? 'disabled' : ''" @click="addY">Y축 추가</div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import { alert } from "../../../../assets/js/common.utils";

    export default {
        name: "ItemTooltip",
        computed: {
            ...mapState({
                selectedItem: state => state.observation[state.observation.selectedCategory].selectedItem,
                xAxis: state => state.observation[state.observation.selectedCategory].xAxis,
                yAxis: state => state.observation[state.observation.selectedCategory].yAxis
            })
        },
        methods: {
            ...mapActions({
                setSelectedItem: "SET_SELECTED_ITEM",
                addXAxis: "ADD_X_AXIS",
                removeXAxis: "REMOVE_X_AXIS",
                addYAxis: "ADD_Y_AXIS"
            }),

            async addX() {
                if (this.xAxis.length > 0)
                    this.removeXAxis(this.xAxis[0]);
                this.addXAxis(this.selectedItem.item);
                await this.setSelectedItem(null);
            },

            async addY() {
                if (this.selectedItem.value === "datetime" || this.selectedItem.value === "windDirection") return;
                if (this.yAxis.length === 2)
                    await new Promise(resolve => alert("Y축 항목은 최대 두 개까지 추가할 수 있습니다.", resolve));
                else await this.addYAxis(this.selectedItem.item);
                await this.setSelectedItem(null);
            }
        }
    }
</script>

<style lang="scss">
    @import "./ItemTooltip";
</style>
