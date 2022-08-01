<template>
    <div class="datetime-wrap">
        <div>
            <span class="label">데이터 구분 :</span>
            <div>
                <label>
                    <select class="input" :value="selectedDateType" @change="setSelectedDateType($event.target.value)">
                        <option :key="i" v-for="(dateType, i) in dateTypes" :value="dateType.type">{{ dateType.label }}</option>
                    </select>
                </label>
            </div>
        </div>
        <div v-show="windowWidth >= reactiveMaxWidth + 1">
            <span class="label">조회 기간 :</span>
            <div>
                <date-picker
                        valueType="format"
                        :type="selectedDateType == 'hour' ? 'datetime' : selectedDateType"
                        :format="dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].dayjsToStringFormat"
                        :value="startDatetime.format(dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].dayjsToStringFormat)"
                        :disabled-date="date => date.getTime() > new Date().getTime()"
                        @change="onChangeStartDatetime" />
                &nbsp;~&nbsp;
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
                <font-awesome-icon size="1x" :class="isClickedHelpIcon ? 'active' : ''" :icon="['fa', 'question-circle']" @click="isClickedHelpIcon = !isClickedHelpIcon" />
                <div class="help-tooltip scroll" v-show="isClickedHelpIcon && selectedCategory == 'airkorea'">
                    <strong>환경부</strong>의 현재 조회 가능한 기간은 다음과 같습니다.
                    <ul><li :key="i" v-for="(datetime, i) in available" @click="isClickedHelpIcon = false, onClickHelpElement(datetime[0], datetime[datetime.length - 1])">{{ datetime.map(dt => dt.format("YYYY년 MM월 DD일 HH시")).join(" ~ ") }}</li></ul>
                </div>
                <div class="help-tooltip scroll" v-show="isClickedHelpIcon && selectedCategory == 'kt'">
                    <strong>KT</strong>의 현재 조회 가능한 기간은 다음과 같습니다.
                    <ul><li :key="i" v-for="(datetime, i) in available" @click="isClickedHelpIcon = false, onClickHelpElement(datetime[0], datetime[datetime.length - 1])">{{ datetime.map(dt => dt.format("YYYY년 MM월 DD일 HH시")).join(" ~ ") }}</li></ul>
                </div>
                <div class="help-tooltip scroll" v-show="isClickedHelpIcon && selectedCategory == 'sDoT'">
                    <strong>S-DoT</strong>의 현재 조회 가능한 기간은 다음과 같습니다.
                    <ul><li :key="i" v-for="(datetime, i) in available" @click="isClickedHelpIcon = false, onClickHelpElement(datetime[0], datetime[datetime.length - 1])">{{ datetime.map(dt => dt.format("YYYY년 MM월 DD일 HH시")).join(" ~ ") }}</li></ul>
                </div>
                <div class="help-tooltip scroll" v-show="isClickedHelpIcon && selectedCategory == 'observer'">
                    <strong>옵저버</strong>의 현재 조회 가능한 기간은 다음과 같습니다.
                    <ul><li :key="i" v-for="(datetime, i) in available" @click="isClickedHelpIcon = false, onClickHelpElement(datetime[0], datetime[datetime.length - 1])">{{ datetime.map(dt => dt.format("YYYY년 MM월 DD일 HH시")).join(" ~ ") }}</li></ul>
                </div>
            </div>
        </div>
        <div v-show="windowWidth < reactiveMaxWidth + 1">
            <span class="label">조회 기간 (시작) :</span>
            <div>
                <date-picker
                        valueType="format"
                        :type="selectedDateType == 'hour' ? 'datetime' : selectedDateType"
                        :format="dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].dayjsToStringFormat"
                        :value="startDatetime.format(dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].dayjsToStringFormat)"
                        :disabled-date="date => date.getTime() > new Date().getTime()"
                        @change="onChangeStartDatetime" />
            </div>
        </div>
        <div v-show="windowWidth < reactiveMaxWidth + 1">
            <span class="label">조회 기간 (끝) :</span>
            <div>
                <date-picker
                        valueType="format"
                        :type="selectedDateType == 'hour' ? 'datetime' : selectedDateType"
                        :format="dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].dayjsToStringFormat"
                        :value="endDatetime.format(dateTypes[dateTypes.findIndex(obj => obj.type == selectedDateType)].dayjsToStringFormat)"
                        :disabled-date="date => date.getTime() > new Date().getTime()"
                        @change="onChangeEndDatetime" />
            </div>
        </div>
        <div v-show="windowWidth < reactiveMaxWidth + 1">
            <input type="button" class="now-btn" value="NOW" @click="onMoveNowDatetime" />
            <font-awesome-icon size="1x" :class="isClickedHelpIcon ? 'active' : ''" :icon="['fa', 'question-circle']" @click="isClickedHelpIcon = !isClickedHelpIcon" />
            <div class="help-tooltip scroll" v-show="isClickedHelpIcon && selectedCategory == 'airkorea'">
                <strong>환경부</strong>의 현재 조회 가능한 기간은 다음과 같습니다.
                <ul><li :key="i" v-for="(datetime, i) in available" @click="isClickedHelpIcon = false, onClickHelpElement(datetime[0], datetime[datetime.length - 1])">{{ datetime.map(dt => dt.format("YYYY년 MM월 DD일 HH시")).join(" ~ ") }}</li></ul>
            </div>
            <div class="help-tooltip scroll" v-show="isClickedHelpIcon && selectedCategory == 'kt'">
                <strong>KT</strong>의 현재 조회 가능한 기간은 다음과 같습니다.
                <ul><li :key="i" v-for="(datetime, i) in available" @click="isClickedHelpIcon = false, onClickHelpElement(datetime[0], datetime[datetime.length - 1])">{{ datetime.map(dt => dt.format("YYYY년 MM월 DD일 HH시")).join(" ~ ") }}</li></ul>
            </div>
            <div class="help-tooltip scroll" v-show="isClickedHelpIcon && selectedCategory == 'sDoT'">
                <strong>S-DoT</strong>의 현재 조회 가능한 기간은 다음과 같습니다.
                <ul><li :key="i" v-for="(datetime, i) in available" @click="isClickedHelpIcon = false, onClickHelpElement(datetime[0], datetime[datetime.length - 1])">{{ datetime.map(dt => dt.format("YYYY년 MM월 DD일 HH시")).join(" ~ ") }}</li></ul>
            </div>
            <div class="help-tooltip scroll" v-show="isClickedHelpIcon && selectedCategory == 'observer'">
                <strong>옵저버</strong>의 현재 조회 가능한 기간은 다음과 같습니다.
                <ul><li :key="i" v-for="(datetime, i) in available" @click="isClickedHelpIcon = false, onClickHelpElement(datetime[0], datetime[datetime.length - 1])">{{ datetime.map(dt => dt.format("YYYY년 MM월 DD일 HH시")).join(" ~ ") }}</li></ul>
            </div>
        </div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";
    import dayjs from "dayjs";

    import { alert } from "../../../../../assets/js/common.utils";

    export default {
        name: "Datetime",
        data: () => ({
            isClickedHelpIcon: false
        }),
        computed: {
            ...mapState({
                reactiveMaxWidth: state => state.common.reactiveMaxWidth,
                windowWidth: state => state.common.windowWidth,
                selectedCategory: state => state.statistics.selectedCategory,
                startDatetime: state => state.statistics.startDatetime,
                endDatetime: state => state.statistics.endDatetime,
                dateTypes: state => state.statistics.dateTypes,
                selectedDateType: state => state.statistics.selectedDateType,
                available: state => state.statistics[state.statistics.selectedCategory].available
            })
        },
        methods: {
            ...mapActions({
                setStartDatetime: "SET_STATISTICS_START_DATETIME",
                setEndDatetime: "SET_STATISTICS_END_DATETIME",
                setSelectedDateType: "SET_STATISTICS_SELECTED_DATE_TYPE"
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
            },

            onClickHelpElement(startDatetime, endDatetime) {
                if (startDatetime === endDatetime) {
                    this.setStartDatetime(dayjs(startDatetime.format("YYYYMMDDHH0000")));
                    this.setEndDatetime(dayjs(endDatetime.format("YYYYMMDDHH5959")));
                } else {
                    this.setStartDatetime(startDatetime);
                    this.setEndDatetime(endDatetime);
                }
            }
        }
    }
</script>

<style lang="scss">
    @import "./Datetime";
</style>
