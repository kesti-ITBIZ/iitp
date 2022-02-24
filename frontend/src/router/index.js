import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

export default new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [
        {
            path: "/",
            redirect: "/view"
        },
        {
            path: "/view",
            redirect: "/view/observation"
        },
        {
            path: "/view/observation",
            component: () => import("../components/observation/Observation")
        },
        {
            path: "/view/statistics",
            component: () => import("../components/statistics/Statistics")
        },
        {
            path: "/view/verification",
            component: () => import("../components/verification/Verification")
        }
    ]
});
