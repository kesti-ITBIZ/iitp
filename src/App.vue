<template>
    <div id="layout" class="text-no-drag" @click="clearSelectedItem">
        <div class="header">
            <table>
                <colgroup>
                    <col style="width: 50%;" />
                    <col style="width: 50%;" />
                </colgroup>
                <tbody>
                    <tr>
                        <td rowspan="2" class="category">
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
                                                <div @click.stop="addX">X</div>
                                                <div @click.stop="addY">Y</div>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                        <td class="x-axis">
                            <table>
                                <thead>
                                    <tr>
                                        <th class="opt-head">X축</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td class="opt-body scroll no-scrollbar">
                                            <ul>
                                                <li :key="i" v-for="(data, i) in xAxis">
                                                    <div @click="removeXAxis(data)">
                                                        {{ data.label }}
                                                        <span>&times;</span>
                                                    </div>
                                                </li>
                                            </ul>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="y-axis">
                            <table>
                                <thead>
                                    <tr>
                                        <th class="opt-head">Y축(값)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td class="opt-body scroll no-scrollbar">
                                            <ul>
                                                <li :key="i" v-for="(data, i) in yAxis">
                                                    <div @click="removeYAxis(data)">
                                                        {{ data.label }}
                                                        <span>&times;</span>
                                                    </div>
                                                </li>
                                            </ul>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="content">
            <table>
                <thead>
                    <tr>
                        <th colspan="2">
                            <div :key="i"
                                 v-for="(chartType, i) in ['Line', 'Bar', 'Area', 'Pie']"
                                 :class="['select-chart-btn', selectedChartType == chartType.toLowerCase() ? 'on' : '']"
                                 @click="setSelectedChartType(chartType.toLowerCase())">
                                <div>
                                    <font-awesome-icon size="5x" :icon="['fa', 'chart-' + chartType.toLowerCase()]" />
                                    <div>{{ chartType }} Chart</div>
                                </div>
                            </div>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="chart">

                        </td>
                        <td class="map">
                            <div class="map-data">
                                <map-component />
                            </div>
                            <div class="data">
                                <ul>
                                    <li :key="i" v-for="(obj, i) in data">
                                        <input type="button" :class="selectedData.value == obj.value ? 'on' : ''" :value="obj.label" @click="setSelectedData(obj)" />
                                    </li>
                                </ul>
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

    import MapComponent from "./components/map/Map";

    export default {
        name: "App",
        components: {
            MapComponent
        },
        computed: {
            ...mapState({
                data: state => state.data,
                selectedData: state => state.selectedData,
                items: state => state.items,
                selectedItems: state => state.selectedItems,
                xAxis: state => state.xAxis,
                yAxis: state => state.yAxis,
                selectedChartType: state => state.selectedChartType
            })
        },
        methods: {
            ...mapActions({
                setSelectedData: "SET_SELECTED_DATA",
                setSelectedItem: "SET_SELECTED_ITEM",
                removeSelectedItem: "REMOVE_SELECTED_ITEM",
                clearSelectedItem: "CLEAR_SELECTED_ITEM",
                addXAxis: "ADD_X_AXIS",
                removeXAxis: "REMOVE_X_AXIS",
                addYAxis: "ADD_Y_AXIS",
                removeYAxis: "REMOVE_Y_AXIS",
                setSelectedChartType: "SET_SELECTED_CHART_TYPE"
            }),

            addX() {
                this.addXAxis(this.selectedItems);
                this.removeSelectedItem(this.selectedItems);
                this.clearSelectedItem();
            },

            addY() {
                this.addYAxis(this.selectedItems);
                this.removeSelectedItem(this.selectedItems);
                this.clearSelectedItem();
            },

            moveXAxisToCategories() {

            }
        }
    }
</script>

<style>
    @import "./App.css";
</style>
