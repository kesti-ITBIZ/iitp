module.exports = {
    outputDir: require("path").resolve(__dirname, "../src/main/resources/static"),
    devServer: {
        port: 8085,
        proxy: {
            "/": { target: "http://127.0.0.1:9200" }
        }
    }
}
