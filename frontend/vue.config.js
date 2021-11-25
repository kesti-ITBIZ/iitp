module.exports = {
    outputDir: require("path").resolve(__dirname, "../src/main/resources/static"),
    devServer: {
        port: 8085,
        proxy: {
            "/": { target: "http://localhost:9200" }
        }
    },
    chainWebpack: config => {
        config.plugin("html").tap(args => {
            args[0].title = "IITP 미세먼지 분석도구 서비스";
            return args;
        });
    }
}
