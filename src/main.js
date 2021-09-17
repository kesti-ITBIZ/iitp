import Vue from "vue";
import App from "./App.vue";
import store from "./store/store";

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

import DatePicker from "vue2-datepicker";
import "vue2-datepicker/index.css";

import VueSlider from "vue-slider-component";
import "vue-slider-component/theme/antd.css";

import "./index.css";

import "animate.css";

// import "echarts-bmap";

Vue.component("date-picker", DatePicker);
Vue.component("vue-slider", VueSlider);
Vue.component("font-awesome-icon", FontAwesomeIcon);
library.add(
    faChartLine,
    faChartBar,
    faChartArea,
    faChartPie,
    faChartScatter,
    faChartNetwork,
    faTable);

Vue.config.productionTip = false;

new Vue({
    store,
    render: h => h(App)
}).$mount("#app");
