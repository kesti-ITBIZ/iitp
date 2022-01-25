export default {
    state: {
        common: {
            reactiveMaxWidth: 1160 + 1,
            windowWidth: window.innerWidth,
            windowHeight: window.innerHeight,
            resizeEventListeners: Object.freeze([]),
            alert: Object.freeze({
                show: false,
                title: "",
                okButtonText: "",
                onOk: () => null
            }),
            loading: false
        }
    },
    getters: {

    },
    mutations: {
        ADD_RESIZE_EVENT: (state, callback) => {
            state.common.resizeEventListeners = state.common.resizeEventListeners.concat(callback);
            window.addEventListener("resize", callback);
        },
        CLEAR_RESIZE_EVENT: state => {
            state.common.resizeEventListeners.forEach(func => window.removeEventListener("resize", func));
            state.common.resizeEventListeners = [];
        },
        SET_WINDOW_WIDTH: (state, width) => state.common.windowWidth = width,
        SET_WINDOW_HEIGHT: (state, height) => state.common.windowHeight = height,
        SET_ALERT_INVISIBLE: state => state.common.alert = Object.freeze({ show: false, title: "", okButtonText: "", onOk: () => null }),
        SET_ALERT_VISIBLE: (state, alert) => state.common.alert = Object.freeze({ ...alert }),
        SET_LOADING_VISIBLE: state => state.common.loading = true,
        SET_LOADING_INVISIBLE: state => state.common.loading = false
    },
    actions: {
        ADD_RESIZE_EVENT: (context, callback) => context.commit("ADD_RESIZE_EVENT", callback),
        CLEAR_RESIZE_EVENT: context => context.commit("CLEAR_RESIZE_EVENT"),
        SET_WINDOW_WIDTH: (context, width) => context.commit("SET_WINDOW_WIDTH", width),
        SET_WINDOW_HEIGHT: (context, height) => context.commit("SET_WINDOW_HEIGHT", height),
        SET_ALERT_INVISIBLE: context => context.commit("SET_ALERT_INVISIBLE"),
        SET_ALERT_VISIBLE: (context, alert) => context.commit("SET_ALERT_VISIBLE", alert),
        SET_LOADING_VISIBLE: context => context.commit("SET_LOADING_VISIBLE"),
        SET_LOADING_INVISIBLE: context => context.commit("SET_LOADING_INVISIBLE")
    }
}
