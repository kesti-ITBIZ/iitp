<template>
    <th id="select-datetime">
        <date-picker
                valueType="format"
                :type="selectedDateType == 'hour' ? 'datetime' : selectedDateType"
                :format="dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].format == 'YYYY.MM.DD HH:00' ? 'YYYY.MM.DD HH' : dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].format"
                :value="startDatetime"
                @change="onChangeStartDatetime" />
        &nbsp;~&nbsp;
        <date-picker
                valueType="format"
                :type="selectedDateType == 'hour' ? 'datetime' : selectedDateType"
                :format="dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].format == 'YYYY.MM.DD HH:00' ? 'YYYY.MM.DD HH' : dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].format"
                :value="endDatetime"
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
                if (datetime > this.endDatetime)
                    await new Promise(resolve => alert("잘못된 시간대 입력입니다.", resolve));
                else this.setStartDatetime(datetime);
            },

            async onChangeEndDatetime(datetime) {
                if (datetime < this.startDatetime)
                    await new Promise(resolve => alert("잘못된 시간대 입력입니다.", resolve));
                else this.setEndDatetime(datetime);
            }
        }
    }
</script>

<style>
    @import "./SelectDatetime.css";
</style>
