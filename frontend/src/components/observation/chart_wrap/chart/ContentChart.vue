<template>
    <div class="content-chart">
        <div>
            <div v-show="data[selectedCategory] && data[selectedCategory].length > 0 && selectedChartType != 'table'" id="chart" ref="chart" />
            <div v-show="data[selectedCategory] && data[selectedCategory].length > 0 && selectedChartType == 'table' && Object.keys(tableData).length > 0" id="table" class="scroll">
                <table v-if="xAxis.length > 0 && yAxis.length > 0">
                    <thead>
                        <tr>
                            <th>
                                <span>
                                    {{ xAxis[0].label }}
                                </span>
                            </th>
                            <th :key="i" v-for="(y, i) in yAxis">
                                <span>
                                    {{ y.label }}
                                </span>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <template v-for="(label, i) in xAxis[0].value === 'datetime' ? Object.keys(tableData) : sorted(Object.keys(tableData).map(key => +key))">
                            <template v-if="label != null">
                                <tr :key="`${i}-${j}`" v-for="(yData, j) in tableData[label]">
                                    <td v-if="j === 0" :rowspan="tableData[label].length">
                                        <span>
                                            {{ xAxis[0].value === 'datetime' ? label : round(label) }}{{ xAxis[0].unit }}
                                        </span>
                                    </td>
                                    <td :key="k" v-for="(y, k) in yData">
                                        <span>
                                            {{ y != null && !isNaN(y) ? round(y) + yAxis[k].unit : "-" }}
                                        </span>
                                    </td>
                                </tr>
                            </template>
                        </template>
                    </tbody>
                </table>
            </div>
            <div v-show="data[selectedCategory] && data[selectedCategory].length > 0 && selectedChartType == 'table' && Object.keys(tableData).length > 0" class="csv-download" @click="csvDownload">
                <font-awesome-icon size="lg" :icon="['fa', 'file-download']" />
            </div>
            <div v-show="data[selectedCategory] == null || data[selectedCategory].length === 0" id="no-data">
                <div>
                    <h1>데이터가 없습니다.</h1>
                </div>
            </div>
            <loading />
        </div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import xlsx from "xlsx";
    import dayjs from "dayjs";
    import { init } from "echarts";
    import customParseFormat from "dayjs/plugin/customParseFormat";

    dayjs.extend(customParseFormat);

    import Loading from "../../../common/loading/Loading";

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
                selectedChartType: state => state.observation.selectedChartType,
                xAxis: state => state.observation[state.observation.selectedCategory].xAxis,
                yAxis: state => state.observation[state.observation.selectedCategory].yAxis,
                startDatetime: state => state.observation.startDatetime,
                endDatetime: state => state.observation.endDatetime,
                dateTypes: state => state.observation.dateTypes,
                selectedDateType: state => state.observation.selectedDateType,
                selectedCategory: state => state.observation.selectedCategory,
                data: state => state.observation.data
            }),

            tableData() {
                if (this.data[this.selectedCategory]) {
                    const data = this.data[this.selectedCategory];
                    const xLabel = this.xAxis[0].value;
                    let datasets = {};

                    if (xLabel === "datetime") {
                        const format = this.dateTypes[this.dateTypes.findIndex(dateType => dateType.type == this.selectedDateType)].dayjsToStringFormat;
                        data.forEach(obj => {
                            const key = obj.datetime.format(format), valueArr = [];
                            this.yAxis.forEach(y => {
                                valueArr.push(obj[y.value] == null ? NaN : obj[y.value]);
                            });
                            if (!(key in datasets)) datasets[key] = [];
                            datasets[key].push(valueArr);
                        });

                        const transpose = arr => {
                            let newArr = [];
                            arr[0].forEach(() => newArr.push([]));
                            for (let i = 0; i < arr.length; ++i)
                                for (let j = 0; j < arr[i].length; ++j)
                                    newArr[j].push(arr[i][j]);
                            return newArr;
                        };

                        Object.keys(datasets).forEach(key => {
                            const valueArr = datasets[key];
                            datasets[key] = [transpose(valueArr).map(arr => arr.filter(value => !isNaN(value)).length === 0 ? NaN : arr.reduce((acc, cur) => (isNaN(acc) ? 0 : acc) + (isNaN(cur) ? 0 : cur)) / valueArr.length)];
                        });
                    } else {
                        data.forEach(obj => {
                            const key = obj[xLabel], valueArr = [];
                            this.yAxis.forEach(y => valueArr.push(obj[y.value]));
                            if (!(key in datasets)) datasets[key] = [];
                            datasets[key].push(valueArr);
                        });

                        const compare = (a, b) => {
                            if (this.yAxis.length === 1)
                                return a[0] < b[0] ? -1 : 1;
                            else if (this.yAxis.length === 2)
                                return a[0] < b[0] ? -1 : a[0] === b[0] && a[1] < b[1] ? -1 : 1;
                        };

                        Object.keys(datasets).forEach(key => {
                            const valueArr = datasets[key].filter(arr => arr[0] != null || arr[1] != null);
                            valueArr.sort(compare);
                            for (let i = valueArr.length - 1; i > 0; --i)
                                if (valueArr[i][0] === valueArr[i - 1][0] && valueArr[i][1] === valueArr[i - 1][1])
                                    valueArr.splice(i, 1);
                            datasets[key] = valueArr;
                        });
                    }

                    return datasets;
                } else return {};
            },

            chartData() {
                if (this.data[this.selectedCategory]) {
                    const data = this.data[this.selectedCategory];
                    if (this.xAxis[0].value === "datetime")
                        return this.yAxis.map(obj => {
                            const format = this.dateTypes[this.dateTypes.findIndex(dateType => dateType.type == this.selectedDateType)].dayjsToStringFormat;
                            const _data = {};
                            data.forEach(_obj => {
                                const key = _obj["datetime"].format(format);
                                if (!(key in _data)) _data[key] = [];
                                _data[key].push(_obj[obj.value]);
                            });
                            return Object.keys(_data).map(datetime => {
                                const values = _data[datetime].filter(value => value != null);
                                return values.length === 0 ? null : values.reduce((acc, cur) => acc + cur) / _data[datetime].length;
                            });
                        });
                    else
                        return  this.yAxis.map(obj => {
                            const _data = data.map(_obj => [_obj[this.xAxis[0].value], _obj[obj.value]]);
                            _data.sort((a, b) => a[0] < b[0] ? -1 : a[0] === b[0] && a[1] < b[1] ? -1 : 1);
                            for (let i = _data.length - 1; i > 0; --i)
                                if (_data[i][0] === _data[i - 1][0] && _data[i][1] === _data[i - 1][1]) _data.splice(i, 1);
                            return _data.filter(arr => arr[0] != null && arr[1] != null).map(arr => [arr[0].toString(), arr[1]]);
                        });
                } else return [];
            },

            xAxisLabels() {
                if (this.xAxis.length === 0)
                    return [];
                else if (this.xAxis[0].value === "datetime") {
                    const xLabels = this.data[this.selectedCategory].map(obj => obj.datetime.format(this.dateTypes[this.dateTypes.findIndex(_obj => _obj.type == this.selectedDateType)].dayjsToStringFormat));
                    xLabels.sort();
                    for (let i = xLabels.length - 1; i > 0; --i)
                        if (xLabels[i] === xLabels[i - 1]) xLabels.splice(i, 1);
                    return xLabels;
                } else {
                    const xLabels = this.data[this.selectedCategory].map(obj => obj[this.xAxis[0].value]).filter(value => value != null);
                    xLabels.sort((a, b) => +a < +b ? -1 : 1);
                    return [...new Set(xLabels)].filter(value => value != null && !isNaN(value)).map(value => this.round(value).toString());
                }
            }
        },
        watch: {
            selectedChartType() {
                this.reInitChart();
            },

            xAxis() {
                this.reInitChart();
            },

            yAxis() {
                this.reInitChart();
            },

            selectedDateType() {
                this.reInitChart();
            },

            data() {
                this.reInitChart();
            }
        },
        methods: {
            ...mapActions({
                addResizeEvent: "ADD_RESIZE_EVENT",
                removeResizeEvent: "REMOVE_RESIZE_EVENT",
                setLoadingInvisible: "SET_LOADING_INVISIBLE"
            }),

            reInitChart() {
                if (this.data[this.selectedCategory]
                    && this.data[this.selectedCategory].length > 0
                    && this.xAxis.length > 0
                    && this.yAxis.length > 0
                    && this.selectedChartType != "table")
                    setTimeout(this.initChart, 0);
            },

            initChart() {
                if (this.chart != null) this.chart.clear();
                else this.chart = init(this.$refs["chart"]);
                this.chart.setOption({
                    title: {
                        text: this.data[this.selectedCategory] && this.data[this.selectedCategory].length > 0 ? this.data[this.selectedCategory][0].stnNm : "",
                        left: 50,
                        textStyle: {
                            fontFamily: "NanumSquare",
                            fontWeight: "bold"
                        }
                    },
                    tooltip: {
                        formatter: data => {
                            let xLabel = data.name;
                            if (this.xAxis[0].value === "datetime") {
                                if (this.selectedDateType == "hour") xLabel = dayjs(xLabel + ":00", "YYYY.MM.DD HH:mm").format("YYYY년 MM월 DD일 H시");
                                else if (this.selectedDateType == "date") xLabel = dayjs(xLabel, "YYYY.MM.DD").format("YYYY년 MM월 DD일");
                                else if (this.selectedDateType == "month") xLabel = dayjs(xLabel + ".01", "YYYY.MM").format("YYYY년 MM월");
                                else if (this.selectedDateType == "year") xLabel = dayjs(xLabel, "YYYY").format("YYYY년");
                            }

                            return `
                                항목: ${data.seriesName}<br />
                                X: ${xLabel}<br />
                                Y: ${Math.round((typeof data.value === "object" ? data.value[1] : data.value) * 100) / 100}`;
                        },
                        textStyle: {
                            fontFamily: "NanumSquare"
                        }
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
                        data: this.yAxis.map(obj => obj.label),
                        textStyle: {
                            fontFamily: "NanumSquare"
                        }
                    },
                    xAxis: {
                        type: "category",
                        name: this.xAxis.length === 0 ? "" : this.xAxis[0].label,
                        data: this.xAxisLabels,
                        splitLine: {
                            show: true
                        },
                        nameTextStyle: {
                            fontFamily: "NanumSquare"
                        },
                        axisLabel: {
                            fontFamily: "NanumSquare"
                        }
                    },
                    yAxis: this.yAxis.findIndex(y => y.value === "pm10") !== -1 && this.yAxis.findIndex(y => y.value === "pm25") !== -1 ?
                        {
                            type: "value",
                            name: this.yAxis.map(y => `${y.label} (${y.unit})`).join(", "),
                            min: 0,
                            nameTextStyle: {
                                align: "left",
                                fontFamily: "NanumSquare",
                                fontWeight: "bold"
                            },
                            axisLabel: {
                                formatter: `{value}`,
                                fontFamily: "NanumSquare"
                            },
                            splitLine: {
                                show: true
                            }
                        } :
                        this.yAxis.map(y => ({
                            type: "value",
                            name: `${y.label} (${y.unit})`,
                            min: item => y.value === "temperature" ? (item.min < 0 ? item.min - 1 : 0) : 0,
                            nameTextStyle: {
                                align: "left",
                                fontFamily: "NanumSquare",
                                fontWeight: "bold"
                            },
                            axisLabel: {
                                formatter: `{value}`,
                                fontFamily: "NanumSquare"
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
                                ...(this.yAxis.findIndex(y => y.value === "pm10") !== -1 && this.yAxis.findIndex(y => y.value === "pm25") !== -1 ? null : { yAxisIndex: i }),
                                showSymbol: true
                            };
                        case "bar":
                            return {
                                name: obj.label,
                                data: this.chartData[i],
                                type: "bar",
                                ...(this.yAxis.findIndex(y => y.value === "pm10") !== -1 && this.yAxis.findIndex(y => y.value === "pm25") !== -1 ? null : { yAxisIndex: i }),
                                showSymbol: false
                            };
                        case "area":
                            return {
                                name: obj.label,
                                data: this.chartData[i],
                                type: "line",
                                ...(this.yAxis.findIndex(y => y.value === "pm10") !== -1 && this.yAxis.findIndex(y => y.value === "pm25") !== -1 ? null : { yAxisIndex: i }),
                                showSymbol: false,
                                areaStyle: {}
                            };
                        case "scatter":
                            return {
                                name: obj.label,
                                data: this.chartData[i],
                                type: "scatter",
                                ...(this.yAxis.findIndex(y => y.value === "pm10") !== -1 && this.yAxis.findIndex(y => y.value === "pm25") !== -1 ? null : { yAxisIndex: i }),
                                showSymbol: false
                            };
                        }
                    })
                });
                this.addResizeEvent({
                    name: "resizeObservationChart",
                    callback: () => this.chart.resize()
                });
            },

            csvDownload() {
                const book = xlsx.utils.book_new();
                const csvTable = [], merges = [];
                let i = 1, start, end;

                if (this.xAxis[0].value === "datetime") {
                    this.sorted(Object.keys(this.tableData)).forEach(key =>
                        this.tableData[key].forEach((obj, j) => {
                            const csvData = { [this.xAxis[0].label]: key };
                            this.yAxis.forEach((y, k) => csvData[y.label] = isNaN(obj[k]) ? "-" : obj[k] + y.unit);
                            csvTable.push(csvData);
                            if (j === 0) start = i;
                            else if (j === this.tableData[key].length - 1) {
                                end = i;
                                merges.push({ s: { r: start, c: 0 }, e: { r: end, c: 0 } });
                                start = end = null;
                            }
                            ++i;
                        }));
                } else {
                    this.sorted(Object.keys(this.tableData).filter(key => !isNaN(+key)).map(key => +key)).forEach(key =>
                        this.tableData[key].forEach((obj, j) => {
                            const csvData = { [this.xAxis[0].label.split(" ").join("_")]: key + this.xAxis[0].unit };
                            this.yAxis.forEach((y, k) => csvData[y.label.split(" ").join("_")] = isNaN(obj[k]) ? "-" : obj[k] + y.unit);
                            csvTable.push(csvData);
                            if (j === 0) start = i;
                            else if (j === this.tableData[key].length - 1) {
                                end = i;
                                merges.push({ s: { r: start, c: 0 }, e: { r: end, c: 0 } });
                                start = end = null;
                            }
                            ++i;
                        }));
                }

                let sheet = xlsx.utils.json_to_sheet(csvTable);
                sheet["!merges"] = merges;
                xlsx.utils.book_append_sheet(
                    book,
                    sheet,
                    "데이터");
                xlsx.writeFile(book, `${this.xAxis[0].label}_${this.yAxis.map(y => y.label).join("_")}_${dayjs().format("YYYYMMDDHHmmss")}.xlsx`);
            },

            sorted(arr, compare = undefined) {
                const newArr = arr.concat();
                newArr.sort(compare);
                return newArr;
            },

            round(value) {
                if (value == null) return null;
                else if (value === 0) return 0;
                let v = value, cnt = 0;
                while (Math.round(v) === 0) {
                    v *= 10;
                    ++cnt;
                }
                return Math.round(value * (10 ** (cnt + 2))) / (10 ** (cnt + 2));
            }
        },
        destroyed() {
            this.removeResizeEvent("resizeObservationChart");
        }
    }
</script>

<style lang="scss">
    @import "./ContentChart";
</style>
