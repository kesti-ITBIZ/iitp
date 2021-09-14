import Vue from "vue";
import App from "./App.vue";
import store from "./store/store";

import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import {
    faChartLine,
    faChartBar,
    faChartArea,
    faChartPie
} from "@fortawesome/free-solid-svg-icons";

import "./index.css";

Vue.component("font-awesome-icon", FontAwesomeIcon);
library.add(
    faChartLine,
    faChartBar,
    faChartArea,
    faChartPie);

Vue.config.productionTip = false;

new Vue({
    store,
    render: h => h(App)
}).$mount("#app");
