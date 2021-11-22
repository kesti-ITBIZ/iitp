<template>
    <td id="items" rowspan="2">
        <table>
            <thead>
                <tr>
                    <th class="opt-head">데이터 항목</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="opt-body">
                        <ul class="scroll no-scrollbar">
                            <li :key="i" v-for="(item, i) in items">
                                <input type="button" :class="[item.value, selectedItems.indexOf(item) != -1 ? 'on' : '']" :value="item.label + (item.unit !== '' ? ` (${item.unit})` : '')" @click.stop="setSelectedItem(item)" />
                            </li>
                        </ul>
                        <div>
                            <div><div @click.stop="addX">X</div></div>
                            <div><div @click.stop="addY">Y</div></div>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </td>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    import { alert } from "@/assets/js/common.utils";

    export default {
        name: "Items",
        computed: {
            ...mapState({
                items: state => state[state.selectedCategory].items,
                selectedItems: state => state[state.selectedCategory].selectedItems,
                xAxis: state => state[state.selectedCategory].xAxis,
                yAxis: state => state[state.selectedCategory].yAxis
            })
        },
        methods: {
            ...mapActions({
                setSelectedItem: "SET_SELECTED_ITEM",
                removeSelectedItem: "REMOVE_SELECTED_ITEM",
                clearSelectedItem: "CLEAR_SELECTED_ITEM",
                addXAxis: "ADD_X_AXIS",
                removeXAxis: "REMOVE_X_AXIS",
                addYAxis: "ADD_Y_AXIS"
            }),

            async addX() {
                if (this.selectedItems.length === 0)
                    await new Promise(resolve => alert("X축에 추가할 항목을 선택해주세요.", resolve));
                else if (this.selectedItems.length > 1)
                    await new Promise(resolve => alert("X축 항목은 한 개만 선택해주세요.", resolve));
                else {
                    if (this.xAxis.length > 0)
                        this.removeXAxis(this.xAxis[0]);
                    this.addXAxis(this.selectedItems);
                    this.removeSelectedItem(this.selectedItems);
                    this.clearSelectedItem();
                }
            },

            async addY() {
                if (this.selectedItems.length === 0)
                    await new Promise(resolve => alert("Y축에 추가할 항목을 선택해주세요.", resolve));
                else if (this.selectedItems[0].value == "station" || this.selectedItems[0].value == "windDirection")
                    await new Promise(resolve => alert("지점, 풍향은 X축에만 추가할 수 있습니다.", resolve));
                else if (this.selectedItems.length > 2 || this.yAxis.length === 2)
                    await new Promise(resolve => alert("Y축 항목은 최대 두 개까지 추가할 수 있습니다.", resolve));
                else {
                    await this.addYAxis(this.selectedItems);
                    await this.removeSelectedItem(this.selectedItems);
                    await this.clearSelectedItem();
                }
            }
        }
    }
</script>

<style lang="scss">
    @import "./Items.scss";
</style>
