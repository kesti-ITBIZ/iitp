import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import common from "@/store/common";
import data from "@/store/data";
import items from "@/store/items";
import constraint from "@/store/constraint";
import chart from "@/store/chart";

export default new Vuex.Store({
    state: {
        ...common.state,
        ...data.state,
        ...items.state,
        ...constraint.state,
        ...chart.state
    },
    getters: {
        ...common.getters,
        ...data.getters,
        ...items.getters,
        ...constraint.getters,
        ...chart.getters
    },
    mutations: {
        ...common.mutations,
        ...data.mutations,
        ...items.mutations,
        ...constraint.mutations,
        ...chart.mutations
    },
    actions: {
        ...common.actions,
        ...data.actions,
        ...items.actions,
        ...constraint.actions,
        ...chart.actions
    }
});
