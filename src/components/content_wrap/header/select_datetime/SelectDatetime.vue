<template>
    <th id="select-datetime">
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
    </th>
</template>

<script>
    import { mapState, mapActions } from "vuex";

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
            })
        }
    }
</script>

<style>
    @import "./SelectDatetime.css";
</style>
