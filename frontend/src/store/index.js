import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import common from "./common";
import observation from "./observation/observation";

export default new Vuex.Store({
    state: {
        ...common.state,
        ...observation.state
    },
    getters: {
        ...common.getters,
        ...observation.getters
    },
    mutations: {
        ...common.mutations,
        ...observation.mutations
    },
    actions: {
        ...common.actions,
        ...observation.actions
    }
});
