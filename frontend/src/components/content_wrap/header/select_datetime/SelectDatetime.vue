<template>
    <th id="select-datetime">
        <date-picker
                valueType="format"
                :type="selectedDateType == 'hour' ? 'datetime' : selectedDateType"
                :format="dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].dayjsToStringFormat"
                :value="startDatetime.format(dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].dayjsToStringFormat)"
                @change="onChangeStartDatetime" />
        &nbsp;~&nbsp;
        <date-picker
                valueType="format"
                :type="selectedDateType == 'hour' ? 'datetime' : selectedDateType"
                :format="dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].dayjsToStringFormat"
                :value="endDatetime.format(dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].dayjsToStringFormat)"
                @change="onChangeEndDatetime" />
        <label>
            <select :value="selectedDateType" @change="setSelectedDateType($event.target.value)">
                <option :key="i" v-for="(dateType, i) in dateTypes" :value="dateType.type">{{ dateType.label }}</option>
            </select>
        </label>
    </th>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import dayjs from "dayjs";

    import { alert } from "@/assets/js/common.utils";

    export default {
        name: "SelectDatetime",
        computed: {
            ...mapState({
                startDatetime: state => state.startDatetime,
                endDatetime: state => state.endDatetime,
                dateTypes: state => state.dateTypes,
                selectedDateType: state => state.selectedDateType
            })
        },
        methods: {
            ...mapActions({
                setStartDatetime: "SET_START_DATETIME",
                setEndDatetime: "SET_END_DATETIME",
                setSelectedDateType: "SET_SELECTED_DATE_TYPE"
            }),

            async onChangeStartDatetime(datetime) {
                const dateType = this.dateTypes[this.dateTypes.findIndex(obj => obj.type == this.selectedDateType)];
                const [stringToDayjsFormat, dayjsToStringFormat] = [dateType.stringToDayjsFormat, dateType.dayjsToStringFormat];
                if (this.selectedDateType == "hour") datetime += ":00";
                else if (this.selectedDateType == "month") datetime += ".01";
                if (datetime > this.endDatetime.format(dayjsToStringFormat))
                    await new Promise(resolve => alert("잘못된 시간대 입력입니다.", resolve));
                else await this.setStartDatetime(dayjs(datetime, stringToDayjsFormat));
            },

            async onChangeEndDatetime(datetime) {
                let [stringToDayjsFormat, dayjsToStringFormat] = ["", "YYYY.MM.DD HH:mm:ss"];
                if (this.selectedDateType == "hour") {
                    stringToDayjsFormat = "YYYY.MM.DD HH:59:59";
                    datetime += ":59";
                } else if (this.selectedDateType == "date") {
                    stringToDayjsFormat = "YYYY.MM.DD 23:59:59";
                    datetime += " 23:59:59";
                } else if (this.selectedDateType == "month") {
                    const daysInMonth = dayjs(datetime + ".01", stringToDayjsFormat).daysInMonth();
                    stringToDayjsFormat = `YYYY.MM.${daysInMonth} 23:59:59`;
                    datetime += `.${daysInMonth} 23:59:59`;
                } else if (this.selectedDateType == "year") {
                    stringToDayjsFormat = "YYYY.12.31 23:59:59";
                    datetime += ".12.31 23:59:59";
                }

                if (datetime < this.startDatetime.format(dayjsToStringFormat))
                    await new Promise(resolve => alert("잘못된 시간대 입력입니다.", resolve));
                else await this.setEndDatetime(dayjs(datetime, stringToDayjsFormat));
            }
        }
    }
</script>

<style lang="scss">
    @import "./SelectDatetime.scss";
</style>
