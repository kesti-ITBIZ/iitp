<template>
    <div class="content-chart">
        <div v-show="data[selectedCategory] && data[selectedCategory].length > 0 && selectedChartType != 'table'" id="chart" ref="chart" />
        <div v-show="data[selectedCategory] && data[selectedCategory].length > 0 && selectedChartType == 'table' && tableCount > 0" id="table" class="scroll">
            <table v-if="xAxis.length > 0 && yAxis.length > 0">
                <thead>
                    <tr>
                        <th>{{ xAxis[0].label }}</th>
                        <th :key="i" v-for="(y, i) in yAxis">{{ y.label }}</th>
                    </tr>
                </thead>
                <tbody>
                    <template v-for="(label, i) in xAxis[0].value === 'datetime' ? Object.keys(tableData) : sorted(Object.keys(tableData).map(key => +key))">
                        <tr :key="`${i}-${j}`" v-for="(yData, j) in tableData[label]">
                            <td v-if="j === 0" :rowspan="tableData[label].length">{{ xAxis[0].value === 'datetime' ? label : round(label) }}{{ xAxis[0].unit }}</td>
                            <td :key="k" v-for="(y, k) in yData">{{ y ? round(y) + yAxis[k].unit : "-" }}</td>
                        </tr>
                    </template>
                </tbody>
            </table>
        </div>
        <div v-show="(data[selectedCategory] == null || data[selectedCategory].length === 0) && !loading" id="no-data">
            <div>
                <h1>데이터가 없습니다.</h1>
            </div>
        </div>
        <loading />
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import { init, registerMap } from "echarts";

    import Loading from "../../../components/common/loading/Loading";

    export default {
        name: "ContentChart",
        components: {
            Loading
        },
        data: () => ({
            chart: null,
            title: ""
        }),
        computed: {
            ...mapState({
                loading: state => state.common.loading,
                selectedChartType: state => state.selectedChartType,
                xAxis: state => state[state.selectedCategory].xAxis,
                yAxis: state => state[state.selectedCategory].yAxis,
                startDatetime: state => state.startDatetime,
                endDatetime: state => state.endDatetime,
                dateTypes: state => state.dateTypes,
                selectedDateType: state => state.selectedDateType,
                selectedCategory: state => state.selectedCategory,
                data: state => state.data
            }),

            tableData() {
                if (this.data[this.selectedCategory]) {
                    const data = this.data[this.selectedCategory];

                    const datasets = {};
                    for (let i = 0; i < data.length; ++i) {
                        const xLabel = data[i][this.xAxis[0].value];
                        if (xLabel == null) continue;
                        if (!(xLabel in datasets))
                            datasets[xLabel] = [];
                        let yData = [];
                        for (let j = 0; j < this.yAxis.length; ++j)
                            yData.push(data[i][this.yAxis[j].value]);
                        datasets[xLabel].push(yData);
                    }

                    const compare = (a, b) => {
                        let acc = [false];
                        for (let i = 0; i < this.yAxis.length; ++i) {
                            let cur = true;
                            for (let j = 0; j <= i; ++j)
                                cur &&= i === 0 || (i > 0 && j > 0) ? a[j] < b[j] : a[j] === b[j];
                            if (cur) return -1;
                            else acc.push(cur);
                        }
                        return acc.reduce((a, b) => a || b) ? -1 : 1;
                    }

                    Object.keys(datasets).forEach(x => {
                        datasets[x].sort(compare);
                        for (let i = datasets[x].length - 1; i > 0; --i) {
                            let compareValues = [];
                            for (let j = 0; j < this.yAxis.length; ++j)
                                if (datasets[x][i][j] === datasets[x][i - 1][j])
                                    compareValues.push(true);
                            if (compareValues.length > 0 && compareValues.reduce((acc, cur) => acc && cur))
                                datasets[x].splice(i, 1);
                        }
                    });

                    return datasets;
                } else return {};
            },

            tableCount() {
                return Object.keys(this.tableData).length === 0 ? 0 : Object.keys(this.tableData).reduce((acc, cur) => typeof acc !== "object" ? this.tableData[acc] : acc.concat(this.tableData[cur])).length;
            },

            chartData() {
                if (this.data[this.selectedCategory]) {
                    return this.xAxis[0].value !== "datetime" ?
                        this.yAxis.map(y => {
                            let data = this.data[this.selectedCategory].map(obj => [obj[this.xAxis[0].value], obj[y.value]]);
                            data.sort((a, b) => a[0] < b[0] ? -1 : a[0] === b[0] && a[1] < b[1] ? -1 : 1);
                            for (let i = data.length - 1; i > 0; --i)
                                if (data[i][0] === data[i - 1][0] && data[i][1] === data[i - 1][1]) data.splice(i, 1);
                            return data.filter(d => d[0] != null && d[1] != null).map(d => [this.round(d[0]).toString(), d[1]]);
                        }) : this.yAxis.map(obj => this.data[this.selectedCategory].map(_obj => _obj[obj.value]));
                } else return [];
            },

            xAxisLabels() {
                if (this.data == null) {
                    const _data = [];
                    const format = this.dateTypes[this.dateTypes.findIndex(obj => obj.type == this.selectedDateType)].dayjsToStringFormat;
                    for (let datetime = this.startDatetime;
                         datetime <= this.endDatetime;
                         datetime = datetime.add(1, (this.selectedDateType == "date" ? "day" : this.selectedDateType) + "s"))
                        _data.push(datetime.format(format));
                    return _data;
                } else if (this.xAxis.length === 0)
                    return [];
                else if (this.xAxis[0].value === "datetime")
                    return this.data[this.selectedCategory].map(obj => obj.datetime);
                else {
                    const xLabels = this.data[this.selectedCategory].map(obj => obj[this.xAxis[0].value]).filter(value => value != null);
                    xLabels.sort((a, b) => +a < +b ? -1 : 1);
                    return [...new Set(xLabels)].map(value => this.round(value).toString());
                }
            }
        },
        watch: {
            selectedChartType() {
                if (this.data[this.selectedCategory] && this.data[this.selectedCategory].length > 0)
                    setTimeout(this.initChart, 0);
            },

            xAxis() {
                if (this.data[this.selectedCategory] && this.data[this.selectedCategory].length > 0)
                    setTimeout(this.initChart, 0);
            },

            yAxis() {
                if (this.data[this.selectedCategory] && this.data[this.selectedCategory].length > 0)
                    setTimeout(this.initChart, 0);
            },

            startDatetime() {
                if (this.data[this.selectedCategory] && this.data[this.selectedCategory].length > 0)
                    setTimeout(this.initChart, 0);
            },

            endDatetime() {
                if (this.data[this.selectedCategory] && this.data[this.selectedCategory].length > 0)
                    setTimeout(this.initChart, 0);
            },

            selectedDateType() {
                if (this.data[this.selectedCategory] && this.data[this.selectedCategory].length > 0)
                    setTimeout(this.initChart, 0);
            },

            data() {
                if (this.data[this.selectedCategory] && this.data[this.selectedCategory].length > 0)
                    setTimeout(async () => {
                        await this.initChart();
                        await this.setLoadingInvisible();
                    }, 0);
            }
        },
        methods: {
            ...mapActions({
                addResizeEvent: "ADD_RESIZE_EVENT",
                clearResizeEvent: "CLEAR_RESIZE_EVENT",
                setLoadingInvisible: "SET_LOADING_INVISIBLE"
            }),

            initChart() {
                if (this.chart != null) this.chart.clear();
                else this.chart = init(this.$refs["chart"]);
                if (this.selectedChartType == "distribution") {
                    registerMap("KOREA", require("../../../assets/json/korea.json"));

                    let data = require("../../../assets/json/data.json").map(obj => obj.name.split("|").map(val => +val).concat([1]));

                    this.chart.setOption({
                        geo: {
                            map: "KOREA",
                            roam: true
                        },
                        visualMap: {
                            left: "right",
                            min: 0,
                            max: 10,
                            seriesIndex: 0,
                            calculable: true,
                            inRange: {
                                color: ["purple", "blue", "green", "yellow", "orange", "red"]
                            }
                        },
                        xAxis: {
                            show: false,
                            type: "category"
                        },
                        yAxis: {
                            show: false,
                            type: "category"
                        },
                        series: [
                            {
                                id: "dist-heatmap",
                                type: "heatmap",
                                geoIndex: 0,
                                coordinateSystem: "geo",
                                data,
                                pointSize: 5,
                                blurSize: 6
                            }
                        ]
                    });
                } else
                    this.chart.setOption({
                        title: {
                            text: this.data[this.selectedCategory] && this.data[this.selectedCategory].length > 0 ? this.data[this.selectedCategory][0].stnNm : "",
                            left: 50
                        },
                        grid: {
                            // height: 500
                        },
                        tooltip: {
                            formatter: "{a} {b} {c}"
                        },
                        toolbox: {
                            feature: {
                                saveAsImage: {
                                    type: "png"
                                }
                            }
                        },
                        dataZoom: [
                            {
                                type: "slider"
                            }
                        ],
                        legend: {
                            data: this.yAxis.map(obj => obj.label)
                        },
                        xAxis: {
                            type: "category",
                            name: this.xAxis.length === 0 ? "" : this.xAxis[0].label,
                            data: this.xAxisLabels,
                            splitLine: {
                                show: true
                            }
                        },
                        yAxis: this.yAxis.map(y => ({
                            type: "value",
                            name: `${y.label} (${y.unit})`,
                            min(item) {
                                // const avg = (item.min + item.max) / 2;
                                // return avg - 1.2 * Math.abs(avg - item.min);
                                return item.min - 1;
                            },
                            max(item) {
                                // const avg = (item.min + item.max) / 2;
                                // return avg + 1.2 * Math.abs(avg - item.max);
                                return item.max + 1;
                            },
                            nameTextStyle: {
                                align: "left"
                            },
                            axisLabel: {
                                formatter: `{value}`
                            },
                            splitLine: {
                                show: true
                            }
                        })),
                        series: this.yAxis.map((obj, i) => {
                            switch (this.selectedChartType) {
                            case "line":
                                return {
                                    name: obj.label,
                                    data: this.chartData[i],
                                    type: "line",
                                    yAxisIndex: i,
                                    showSymbol: false
                                };
                            case "bar":
                                return {
                                    name: obj.label,
                                    data: this.chartData[i],
                                    type: "bar",
                                    yAxisIndex: i,
                                    showSymbol: false
                                };
                            case "area":
                                return {
                                    name: obj.label,
                                    data: this.chartData[i],
                                    type: "line",
                                    yAxisIndex: i,
                                    showSymbol: false,
                                    areaStyle: {}
                                };
                            case "scatter":
                                return {
                                    name: obj.label,
                                    data: this.chartData[i],
                                    type: "scatter",
                                    yAxisIndex: i,
                                    showSymbol: false
                                };
                            }
                        })
                    });
                this.addResizeEvent(() => this.chart.resize());
            },

            sorted(arr, compare = undefined) {
                const newArr = arr.concat();
                newArr.sort(compare);
                return newArr;
            },

            round(value) {
                let v = value, cnt = 0;
                while (Math.round(v) === 0) {
                    v *= 10;
                    ++cnt;
                }
                return Math.round(value * (10 ** (cnt + 2))) / (10 ** (cnt + 2));
            }
        },
        destroyed() {
            this.clearResizeEvent();
        }
    }
</script>

<style lang="scss">
    @import "./ContentChart";
</style>
