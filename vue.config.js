module.exports = {
    devServer: {
        port: 8085,
        proxy: {
            "/": { target: "http://localhost:9200" }
        }
    }
}
