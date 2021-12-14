<template>
    <div id="items">
        <div>
            <table>
                <thead>
                    <tr>
                        <th class="opt-head">· 데이터 항목</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="opt-body">
                            <ul class="scroll no-scrollbar">
                                <li :key="i" v-for="(item, i) in items">
                                    <input type="button" :class="item.value" :value="item.label + (item.unit !== '' ? ` (${item.unit})` : '')" @click.stop="e => onClickItem(e, item)" />
                                </li>
                            </ul>
                            <div class="item-tooltip" v-show="showItemTooltip" :style="{ top: itemTooltip.top, left: itemTooltip.left }">
                                <div @click="addX">X축 추가</div><hr />
                                <div :class="selectedItem && (selectedItem.value === 'datetime' || selectedItem.value === 'windDirection') ? 'disabled' : ''" @click="addY">Y축 추가</div>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    import { alert } from "@/assets/js/common.utils";

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

            async onClickItem(e, item) {
                this.itemTooltip = Object.freeze({
                    top: `${e.layerY + 2}px !important`,
                    left: `${e.layerX + 2}px !important`
                });
                await this.setSelectedItem(item);
                this.showItemTooltip = this.selectedItem != null;
            },

            async addX() {
                if (this.xAxis.length > 0)
                    this.removeXAxis(this.xAxis[0]);
                this.addXAxis(this.selectedItem);
                await this.setSelectedItem(null);
                this.showItemTooltip = false;
            },

            async addY() {
                if (this.selectedItem.value === "datetime" || this.selectedItem.value === "windDirection") return;
                if (this.yAxis.length === 2)
                    await new Promise(resolve => alert("Y축 항목은 최대 두 개까지 추가할 수 있습니다.", resolve));
                else await this.addYAxis(this.selectedItem);
                await this.setSelectedItem(null);
                this.showItemTooltip = false;
            }
        }
    }
</script>

<style lang="scss">
    @import "./Items";
</style>
