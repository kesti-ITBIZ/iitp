module.exports = {
    outputDir: require("path").resolve(__dirname, "../src/main/resources/static"),
    devServer: {
        port: 8085,
        proxy: {
            "/api": { target: "http://localhost:9200" },
            "/graphql": { target: "http://localhost:9200" },
        }
    },
    chainWebpack: config => {
        config.plugin("html").tap(args => {
            args[0].title = "IITP 미세먼지 분석도구 서비스";
            return args;
        });
    },
    transpileDependencies: [
        "animate.css",
        "apollo-boost",
        "apollo-cache-inmemory",
        "apollo-client",
        "apollo-link",
        "apollo-link-http",
        "core-js",
        "cross-fetch",
        "dayjs",
        "echarts",
        "graphql",
        "graphql-tag",
        "leaflet",
        "vue-apollo",
        "vue-infinite-loading",
        "vue-slider-component",
        "vue2-datepicker",
        "vue2-leaflet"
    ]
}
