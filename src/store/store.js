import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import common from "@/store/common";
import data from "@/store/data";
import items from "@/store/items";
import datetime from "@/store/datetime";
import chart from "@/store/chart";

export default new Vuex.Store({
    state: {
        ...common.state,
        ...data.state,
        ...items.state,
        ...datetime.state,
        ...chart.state
    },
    getters: {
        ...common.getters,
        ...data.getters,
        ...items.getters,
        ...datetime.getters,
        ...chart.getters
    },
    mutations: {
        ...common.mutations,
        ...data.mutations,
        ...items.mutations,
        ...datetime.mutations,
        ...chart.mutations
    },
    actions: {
        ...common.actions,
        ...data.actions,
        ...items.actions,
        ...datetime.actions,
        ...chart.actions
    }
});
