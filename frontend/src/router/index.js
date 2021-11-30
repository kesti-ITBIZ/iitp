import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

export default new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [
        {
            path: "/",
            redirect: "/observation"
        },
        {
            path: "/observation",
            component: () => import("../components/observation/Observation")
        },
        {
            path: "/statistics",
            component: () => import("../components/statistics/Statistics")
        },
    ]
});
