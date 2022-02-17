import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import common from "./common";
import observation from "./observation";
import statistics from "./statistics";
import validation from "./validation";

export default new Vuex.Store({
    state: {
        ...common.state,
        ...observation.state,
        ...statistics.state,
        ...validation.state
    },
    getters: {
        ...common.getters,
        ...observation.getters,
        ...statistics.getters,
        ...validation.getters
    },
    mutations: {
        ...common.mutations,
        ...observation.mutations,
        ...statistics.mutations,
        ...validation.mutations
    },
    actions: {
        ...common.actions,
        ...observation.actions,
        ...statistics.actions,
        ...validation.actions
    }
});
