import Vue from "vue";
import App from "./App.vue";
import store from "./store/store";

import "./index.scss";

import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import {
    faChartLine,
    faChartBar,
    faChartArea,
    faChartPie,
    faChartScatter,
    faChartNetwork,
    faTable,
    faSpinner
} from "@fortawesome/free-solid-svg-icons";

Vue.component("font-awesome-icon", FontAwesomeIcon);
library.add(
    faChartLine,
    faChartBar,
    faChartArea,
    faChartPie,
    faChartScatter,
    faChartNetwork,
    faTable,
    faSpinner);

import DatePicker from "vue2-datepicker";
import "vue2-datepicker/index.css";

Vue.component("date-picker", DatePicker);

import VueSlider from "vue-slider-component";
import "vue-slider-component/theme/antd.css";

Vue.component("vue-slider", VueSlider);

import "animate.css";

Vue.config.productionTip = false;

import Ajax from "./assets/js/ajax";

Vue.prototype.$http = new Ajax();

import VueApollo from "vue-apollo";

Vue.use(VueApollo);

import { ApolloClient } from "apollo-client";
import { createHttpLink } from "apollo-link-http";
import { InMemoryCache } from "apollo-cache-inmemory";

const apolloProvider = new VueApollo({
    defaultClient: new ApolloClient({
        link: createHttpLink({
            uri: "http://211.55.33.242:9200/graphql"
        }),
        cache: new InMemoryCache()
    })
});

new Vue({
    store,
    apolloProvider,
    render: h => h(App)
}).$mount("#app");
