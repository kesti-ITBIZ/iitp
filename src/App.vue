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
                        <th>
                            <div>
                                <div class="select-datetime">
                                    <date-picker
                                            valueType="format"
                                            :type="selectedDateType"
                                            :format="dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].format"
                                            :value="startDatetime"
                                            @change="setStartDatetime" />
                                    &nbsp;~&nbsp;
                                    <date-picker
                                            valueType="format"
                                            :type="selectedDateType"
                                            :format="dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].format"
                                            :value="endDatetime"
                                            @change="setEndDatetime" />
                                    <label>
                                        <select :value="selectedDateType" @change="setSelectedDateType($event.target.value)">
                                            <option :key="i" v-for="(dateType, i) in dateTypes" :value="dateType.type">{{ dateType.label }}</option>
                                        </select>
                                    </label>
                                </div>
                                <div class="select-chart">
                                    <div :key="i"
                                         v-for="(chartType, i) in ['Line', 'Bar', 'Area', 'Pie', 'Scatter']"
                                         :class="['select-chart-btn', selectedChartType == chartType.toLowerCase() ? 'on' : '']"
                                         @click="setSelectedChartType(chartType.toLowerCase())">
                                        <div>
                                            <font-awesome-icon size="5x" :icon="['fa', 'chart-' + chartType.toLowerCase()]" />
                                            <div>{{ chartType }} Chart</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </th>
                        <th class="select-constraint">
                            <div>
                                <h5>미세먼지 농도</h5>
                                <vue-slider
                                        v-model="degree"
                                        v-bind="{ ...options, dataValue: 'degree', min: 1, max: 4 }"
                                        :data="['좋음', '보통', '나쁨', '매우나쁨']" />
                            </div>
                            <div>
                                <h5>월</h5>
                                <vue-slider
                                        v-model="month"
                                        v-bind="{ ...options, dataValue: 'month', min: 1, max: 12 }"
                                        :data="['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']" />
                            </div>
                            <div>
                                <h5>계절</h5>
                                <vue-slider
                                        v-model="season"
                                        v-bind="{ ...options, dataValue: 'season', min: 1, max: 4 }"
                                        :data="['봄', '여름', '가을', '겨울']" />
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
        data: () => ({
            degree: '좋음',
            month: '1월',
            season: '봄',
            options: {
                dotSize: 20,
                width: 'calc(100% - 100px)',
                height: 15,
                contained: false,
                direction: 'ltr',
                data: null,
                dataLabel: 'label',
                dataValue: 'value',
                interval: 1,
                disabled: false,
                clickable: true,
                duration: 0,
                adsorb: true,
                lazy: false,
                tooltip: 'active',
                tooltipPlacement: 'top',
                tooltipFormatter: undefined,
                useKeyboard: false,
                keydownHook: null,
                dragOnClick: false,
                enableCross: true,
                fixed: false,
                minRange: undefined,
                maxRange: undefined,
                order: true,
                marks: false,
                dotOptions: undefined,
                dotAttrs: undefined,
                process: true,
                dotStyle: undefined,
                railStyle: undefined,
                processStyle: undefined,
                tooltipStyle: undefined,
                stepStyle: undefined,
                stepActiveStyle: undefined,
                labelStyle: undefined,
                labelActiveStyle: undefined
            }
        }),
        computed: {
            ...mapState({
                data: state => state.data,
                selectedData: state => state.selectedData,
                items: state => state.items,
                selectedItems: state => state.selectedItems,
                xAxis: state => state.xAxis,
                yAxis: state => state.yAxis,
                startDatetime: state => state.startDatetime,
                endDatetime: state => state.endDatetime,
                dateTypes: state => state.dateTypes,
                selectedDateType: state => state.selectedDateType,
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
                setStartDatetime: "SET_START_DATETIME",
                setEndDatetime: "SET_END_DATETIME",
                setSelectedDateType: "SET_SELECTED_DATE_TYPE",
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
            }
        }
    }
</script>

<style>
    @import "./App.css";
</style>
