<template>
    <div id="layout" class="text-no-drag" @click="clearSelectedCategory">
        <div class="header">
            <table>
                <colgroup>
                    <col style="width: 50%;" />
                    <col style="width: 50%;" />
                </colgroup>
                <tbody>
                    <tr>
                        <td class="data">
                            <table>
                                <thead>
                                    <tr>
                                        <th class="opt-head">데이터 분류</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td class="opt-body scroll no-scrollbar">
                                            <ul>
                                                <li :key="i" v-for="(_data, i) in data">
                                                    <input type="button" :class="selectedData == _data ? 'on' : ''" :value="_data" @click="setSelectedData(_data)" />
                                                </li>
                                            </ul>
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
                                                    <input type="button" :value="data" @click="removeXAxis($event.target.value)" />
                                                </li>
                                            </ul>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="category">
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
                                                <li :key="i" v-for="(item, i) in categories[selectedData]">
                                                    <input type="button" :class="selectedCategories.indexOf(item) != -1 ? 'on' : ''" :value="item" @click.stop="setSelectedCategory($event.target.value)" />
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
                                                    <input type="button" :value="data" @click="removeYAxis($event.target.value)" />
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
        <div class="chart">
            <table>
                <thead>
                    <tr>
                        <th colspan="2">
                            <div :class="['select-chart-btn', selectedChartType == 'line' ? 'on' : '']" @click="setSelectedChartType('line')">
                                <div>
                                    <font-awesome-icon size="5x" :icon="['fa', 'chart-line']" />
                                    <div>Line Chart</div>
                                </div>
                            </div>
                            <div :class="['select-chart-btn', selectedChartType == 'bar' ? 'on' : '']" @click="setSelectedChartType('bar')">
                                <div>
                                    <font-awesome-icon size="5x" :icon="['fa', 'chart-bar']" />
                                    <div>Bar Chart</div>
                                </div>
                            </div>
                            <div :class="['select-chart-btn', selectedChartType == 'area' ? 'on' : '']" @click="setSelectedChartType('area')">
                                <div>
                                    <font-awesome-icon size="5x" :icon="['fa', 'chart-area']" />
                                    <div>Area Chart</div>
                                </div>
                            </div>
                            <div :class="['select-chart-btn', selectedChartType == 'pie' ? 'on' : '']" @click="setSelectedChartType('pie')">
                                <div>
                                    <font-awesome-icon size="5x" :icon="['fa', 'chart-pie']" />
                                    <div>Pie Chart</div>
                                </div>
                            </div>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>

                        </td>
                        <td>
                            <map-component />
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
                categories: state => state.categories,
                selectedCategories: state => state.selectedCategories,
                xAxis: state => state.xAxis,
                yAxis: state => state.yAxis,
                selectedChartType: state => state.selectedChartType
            })
        },
        methods: {
            ...mapActions({
                setSelectedData: "SET_SELECTED_DATA",
                setSelectedCategory: "SET_SELECTED_CATEGORY",
                removeSelectedCategory: "REMOVE_SELECTED_CATEGORY",
                clearSelectedCategory: "CLEAR_SELECTED_CATEGORY",
                addXAxis: "ADD_X_AXIS",
                removeXAxis: "REMOVE_X_AXIS",
                addYAxis: "ADD_Y_AXIS",
                removeYAxis: "REMOVE_Y_AXIS",
                setSelectedChartType: "SET_SELECTED_CHART_TYPE"
            }),

            addX() {
                this.addXAxis(this.selectedCategories);
                this.removeSelectedCategory(this.selectedCategories);
                this.clearSelectedCategory();
            },

            addY() {
                this.addYAxis(this.selectedCategories);
                this.removeSelectedCategory(this.selectedCategories);
                this.clearSelectedCategory();
            },

            moveXAxisToCategories() {

            }
        }
    }
</script>

<style>
    @import "./App.css";
</style>
