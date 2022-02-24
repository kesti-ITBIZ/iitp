import data from "./data";
import items from "./items";
import constraint from "./constraint";
import chart from "./chart";

export default {
    state: {
        verification: {
            ...data.state,
            ...items.state,
            ...constraint.state,
            ...chart.state
        }
    },
    getters: {
        ...data.getters,
        ...items.getters,
        ...constraint.getters,
        ...chart.getters
    },
    mutations: {
        ...data.mutations,
        ...items.mutations,
        ...constraint.mutations,
        ...chart.mutations
    },
    actions: {
        ...data.actions,
        ...items.actions,
        ...constraint.actions,
        ...chart.actions
    }
}
