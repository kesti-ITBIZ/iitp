import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import common from "./common";
import observation from "./observation";
import statistics from "./statistics";
import verification from "./verification";

export default new Vuex.Store({
    state: {
        ...common.state,
        ...observation.state,
        ...statistics.state,
        ...verification.state
    },
    getters: {
        ...common.getters,
        ...observation.getters,
        ...statistics.getters,
        ...verification.getters
    },
    mutations: {
        ...common.mutations,
        ...observation.mutations,
        ...statistics.mutations,
        ...verification.mutations
    },
    actions: {
        ...common.actions,
        ...observation.actions,
        ...statistics.actions,
        ...verification.actions
    }
});
