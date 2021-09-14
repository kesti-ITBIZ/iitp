import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        data: ["AIRKOREA", "AIRMAP", "OBSERVER"],
        selectedData: "AIRKOREA",
        categories: {
            "AIRKOREA": [
                "CO",
                "NO2",
                "O3",
                "PM10",
                "PM2.5",
                "SO2"
            ],
            "AIRMAP": [
                "HUMDT_MSR_AVG_VAL",
                "PM10_MSR_AVG_VAL",
                "PM25_MSR_AVG_VAL",
                "TEMPR_MSR_AVG_VAL"
            ],
            "OBSERVER": [
                "PM25",
                "PM25_R",
                "PRE",
                "PTY",
                "REH",
                "TMP"
            ]
        },
        selectedCategories: [],
        xAxis: [],
        yAxis: [],
        selectedChartType: "line"
    },
    getters: {

    },
    mutations: {
        SET_SELECTED_DATA: (state, data) => state.selectedData = data,
        SET_SELECTED_CATEGORY: (state, category) => {
            const selectedCategories = state.selectedCategories.concat();
            if (selectedCategories.indexOf(category) === -1)
                selectedCategories.push(category);
            state.selectedCategories = Object.freeze(selectedCategories);
        },
        REMOVE_SELECTED_CATEGORY: (state, removeCategories) => {
            let categories = state.categories[state.selectedData].concat();
            removeCategories.forEach(category => {
                const index = categories.indexOf(category);
                if (index !== -1) categories.splice(index, 1);
            });
            state.categories = Object.freeze({ ...state.categories, [state.selectedData]: Object.freeze(categories) });
        },
        CLEAR_SELECTED_CATEGORY: state => state.selectedCategories = [],
        ADD_X_AXIS: (state, categories) => {
            const xAxis = state.xAxis.concat();
            categories.forEach(category => {
                if (xAxis.indexOf(category) === -1)
                    xAxis.push(category);
            });
            xAxis.sort();
            state.xAxis = Object.freeze(xAxis);
        },
        REMOVE_X_AXIS: (state, category) => {
            const xAxis = state.xAxis.concat();
            const index = xAxis.indexOf(category);
            if (index !== -1) {
                xAxis.splice(index, 1);
                const categories = state.categories[state.selectedData].concat(category);
                categories.sort();
                state.categories = { ...state.categories, [state.selectedData]: Object.freeze(categories) };
            }
            state.xAxis = Object.freeze(xAxis);
        },
        ADD_Y_AXIS: (state, categories) => {
            const yAxis = state.yAxis.concat();
            categories.forEach(category => {
                if (yAxis.indexOf(category) === -1)
                    yAxis.push(category);
            });
            yAxis.sort();
            state.yAxis = Object.freeze(yAxis);
        },
        REMOVE_Y_AXIS: (state, category) => {
            const yAxis = state.yAxis.concat();
            const index = yAxis.indexOf(category);
            if (index !== -1) {
                yAxis.splice(index, 1);
                const categories = state.categories[state.selectedData].concat(category);
                categories.sort();
                state.categories = { ...state.categories, [state.selectedData]: Object.freeze(categories) };
            }
            state.yAxis = Object.freeze(yAxis);
        },
        SET_SELECTED_CHART_TYPE: (state, chartType) => state.selectedChartType = chartType
    },
    actions: {
        SET_SELECTED_DATA: (context, data) => context.commit("SET_SELECTED_DATA", data),
        SET_SELECTED_CATEGORY: (context, category) => context.commit("SET_SELECTED_CATEGORY", category),
        REMOVE_SELECTED_CATEGORY: (context, categories) => context.commit("REMOVE_SELECTED_CATEGORY", categories),
        CLEAR_SELECTED_CATEGORY: context => context.commit("CLEAR_SELECTED_CATEGORY"),
        ADD_X_AXIS: (context, categories) => context.commit("ADD_X_AXIS", categories),
        REMOVE_X_AXIS: (context, category) => context.commit("REMOVE_X_AXIS", category),
        ADD_Y_AXIS: (context, categories) => context.commit("ADD_Y_AXIS", categories),
        REMOVE_Y_AXIS: (context, category) => context.commit("REMOVE_Y_AXIS", category),
        SET_SELECTED_CHART_TYPE: (context, chartType) => context.commit("SET_SELECTED_CHART_TYPE", chartType)
    }
});
