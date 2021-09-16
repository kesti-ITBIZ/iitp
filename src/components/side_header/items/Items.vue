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
                                <input type="button" :class="selectedItems.indexOf(item) != -1 ? 'on' : ''" :value="item.label" @click.stop="setSelectedItem(item)" />
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
                items: state => state.items,
                selectedItems: state => state.selectedItems,
                xAxis: state => state.xAxis
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
                if (this.selectedItems.length > 1)
                    await new Promise(resolve => alert("X축 항목은 한 개만 선택해주세요.", resolve));
                else {
                    if (this.xAxis.length > 0)
                        this.removeXAxis(this.xAxis[0]);
                    this.addXAxis(this.selectedItems);
                    this.removeSelectedItem(this.selectedItems);
                    this.clearSelectedItem();
                }
            },

            addY() {
                this.addYAxis(this.selectedItems);
                this.removeSelectedItem(this.selectedItems);
                this.clearSelectedItem();
            }
        }
    }
</script>

<style>
    @import "./Items.css";
</style>
