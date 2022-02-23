import Vue from "vue";
import App from "./App.vue";
import store from "./store";
import router from "./router";
import "./index.scss";

import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import {
    faChartLine,
    faChartBar,
    faChartArea,
    faChartPie,
    faChartScatter,
    faTable,
    faFileDownload,
    faQuestionCircle,
    faCheck
} from "@fortawesome/free-solid-svg-icons";
import {
    faQuestionCircle as farQuestionCircle
} from "@fortawesome/free-regular-svg-icons";

Vue.component("font-awesome-icon", FontAwesomeIcon);
library.add(
    faChartLine,
    faChartBar,
    faChartArea,
    faChartPie,
    faChartScatter,
    faTable,
    faFileDownload,
    faQuestionCircle,
    farQuestionCircle,
    faCheck);

import DatePicker from "vue2-datepicker";
import "vue2-datepicker/index.css";

Vue.component("date-picker", DatePicker);

import InfiniteLoading from "vue-infinite-loading";

Vue.component("infinite-loading", InfiniteLoading);

import VueSlider from "vue-slider-component";
import "vue-slider-component/theme/antd.css";

Vue.component("vue-slider", VueSlider);

import "animate.css";

Vue.config.productionTip = false;

import { Icon } from "leaflet";

delete Icon.Default.prototype._getIconUrl;

import { LMap, LTileLayer, LMarker, LIcon, LTooltip } from "vue2-leaflet";
import Vue2LeafLetCanvas from "@skinnyjames/vue2-leaflet-canvas";
import "leaflet/dist/leaflet.css";

Vue.component("v-map", LMap);
Vue.component("v-tile-layer", LTileLayer);
Vue.component("v-canvas-layer", Vue2LeafLetCanvas);
Vue.component("v-marker", LMarker);
Vue.component("v-icon", LIcon);
Vue.component("v-tooltip", LTooltip);

import GeoMap from "./components/common/GeoMap";
import GeoMarker from "./components/common/GeoMarker";
import MarkerTooltip from "./components/common/MarkerTooltip";

Vue.component("geo-map", GeoMap);
Vue.component("geo-marker", GeoMarker);
Vue.component("marker-tooltip", MarkerTooltip);

import Ajax from "./assets/js/ajax";

Vue.prototype.$http = new Ajax();

import VueApollo from "vue-apollo";

Vue.use(VueApollo);

import { ApolloClient } from "apollo-client";
import { createHttpLink } from "apollo-link-http";
import { InMemoryCache } from "apollo-cache-inmemory";
import fetch from "cross-fetch";

const apolloProvider = new VueApollo({
    defaultClient: new ApolloClient({
        link: createHttpLink({
            uri: "http://211.55.33.242:9100/graphql",
            fetch
        }),
        cache: new InMemoryCache()
    })
});

new Vue({
    store,
    apolloProvider,
    router,
    render: h => h(App)
}).$mount("#app");
