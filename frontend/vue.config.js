module.exports = {
    outputDir: require("path").resolve(__dirname, "../src/main/resources/static"),
    devServer: {
        port: 8085,
        proxy: {
            "/": { target: "http://localhost:9200" }
        }
    }
}
