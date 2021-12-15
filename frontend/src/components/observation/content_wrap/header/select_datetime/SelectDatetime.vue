<template>
    <table id="select-datetime">
        <tbody>
            <tr>
                <td>데이터 구분 :</td>
                <td>
                    <label>
                        <select :value="selectedDateType" @change="setSelectedDateType($event.target.value)">
                            <option :key="i" v-for="(dateType, i) in dateTypes" :value="dateType.type">{{ dateType.label }}</option>
                        </select>
                    </label>
                </td>
            </tr>
            <tr>
                <td>조회 기간 :</td>
                <td>
                    <date-picker
                            valueType="format"
                            :type="selectedDateType == 'hour' ? 'datetime' : selectedDateType"
                            :format="dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].dayjsToStringFormat"
                            :value="startDatetime.format(dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].dayjsToStringFormat)"
                            :disabled-date="date => date.getTime() > new Date().getTime()"
                            @change="onChangeStartDatetime" />
                    &nbsp;~&nbsp;<br v-if="windowWidth <= 1200" />
                    <date-picker
                            valueType="format"
                            :type="selectedDateType == 'hour' ? 'datetime' : selectedDateType"
                            :format="dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].dayjsToStringFormat"
                            :value="endDatetime.format(dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].dayjsToStringFormat)"
                            :disabled-date="date => date.getTime() > new Date().getTime()"
                            @change="onChangeEndDatetime" />
                    &nbsp;
                    <input type="button" class="now-btn" value="NOW" @click="onMoveNowDatetime" />
                    &nbsp;&nbsp;
                    <font-awesome-icon size="1x" :icon="['fa' + (isClickedHelpIcon ? 'r' : ''), 'question-circle']" @click="isClickedHelpIcon = !isClickedHelpIcon" />
                    <div class="help-tooltip" v-show="isClickedHelpIcon && selectedCategory == 'airkorea'">
                        <strong>환경부</strong>의 현재 조회 가능한 기간은 다음과 같습니다.
                        <ul><li :key="i" v-for="(datetime, i) in available">{{ datetime }}</li></ul>
                    </div>
                    <div class="help-tooltip" v-show="isClickedHelpIcon && selectedCategory == 'kt'">
                        <strong>KT</strong>의 현재 조회 가능한 기간은 다음과 같습니다.
                        <ul><li :key="i" v-for="(datetime, i) in available">{{ datetime }}</li></ul>
                    </div>
                    <div class="help-tooltip" v-show="isClickedHelpIcon && selectedCategory == 'sDoT'">
                        <strong>S-DoT</strong>의 현재 조회 가능한 기간은 다음과 같습니다.
                        <ul><li :key="i" v-for="(datetime, i) in available">{{ datetime }}</li></ul>
                    </div>
                    <div class="help-tooltip" v-show="isClickedHelpIcon && selectedCategory == 'observer'">
                        <strong>옵저버</strong>의 현재 조회 가능한 기간은 다음과 같습니다.
                        <ul><li :key="i" v-for="(datetime, i) in available">{{ datetime }}</li></ul>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import dayjs from "dayjs";

    import { alert } from "@/assets/js/common.utils";

    export default {
        name: "SelectDatetime",
        data: () => ({
            isClickedHelpIcon: false
        }),
        computed: {
            ...mapState({
                windowWidth: state => state.common.windowWidth,
                selectedCategory: state => state.observation.selectedCategory,
                startDatetime: state => state.observation.startDatetime,
                endDatetime: state => state.observation.endDatetime,
                dateTypes: state => state.observation.dateTypes,
                selectedDateType: state => state.observation.selectedDateType,
                available: state => state.observation[state.observation.selectedCategory].available
            })
        },
        methods: {
            ...mapActions({
                setStartDatetime: "SET_OBSERVATION_START_DATETIME",
                setEndDatetime: "SET_OBSERVATION_END_DATETIME",
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
            },

            onMoveNowDatetime() {
                this.setStartDatetime(dayjs());
                this.setEndDatetime(dayjs());
            }
        }
    }
</script>

<style lang="scss">
    @import "./SelectDatetime";
</style>
