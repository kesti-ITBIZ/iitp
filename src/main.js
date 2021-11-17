import Vue from "vue";
import App from "./App.vue";
import store from "./store/store";

import "./index.css";

import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import {
    faChartLine,
    faChartBar,
    faChartArea,
    faChartPie,
    faChartScatter,
    faChartNetwork,
    faTable
} from "@fortawesome/free-solid-svg-icons";

Vue.component("font-awesome-icon", FontAwesomeIcon);
library.add(
    faChartLine,
    faChartBar,
    faChartArea,
    faChartPie,
    faChartScatter,
    faChartNetwork,
    faTable);

import DatePicker from "vue2-datepicker";
import "vue2-datepicker/index.css";

Vue.component("date-picker", DatePicker);

import VueSlider from "vue-slider-component";
import "vue-slider-component/theme/antd.css";

Vue.component("vue-slider", VueSlider);

import "animate.css";

Vue.config.productionTip = false;

import Ajax from "@/assets/js/ajax";

Vue.prototype.$http = new Ajax();

new Vue({
    store,
    render: h => h(App)
}).$mount("#app");
