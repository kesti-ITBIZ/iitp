import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import common from "./common";
import observation from "./observation";
import statistics from "./statistics";

export default new Vuex.Store({
    state: {
        ...common.state,
        ...observation.state,
        ...statistics.state
    },
    getters: {
        ...common.getters,
        ...observation.getters,
        ...statistics.getters
    },
    mutations: {
        ...common.mutations,
        ...observation.mutations,
        ...statistics.mutations
    },
    actions: {
        ...common.actions,
        ...observation.actions,
        ...statistics.actions
    }
});
