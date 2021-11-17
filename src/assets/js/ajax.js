export default class Ajax {
    async get(url, params = null, headers = null) {
        return await fetch(url, {
            method: "get",
            ...(headers == null ? null : { headers }),
            body: params
        }).then(response => response.json());
    }

    async post(url, params = null, headers = null) {
        return await fetch(url, {
            method: "post",
            headers: headers == null ? { "Content-Type": "application/json" } : headers,
            body: JSON.stringify(params)
        }).then(response => response.json());
    }

    async put(url, params = null, headers = null) {
        return await fetch(url, {
            method: "put",
            headers: headers == null ? { "Content-Type": "application/json" } : headers,
            body: JSON.stringify(params)
        }).then(response => response.json());
    }

    async delete(url, params = null, headers = null) {
        return await fetch(url, {
            method: "delete",
            ...(headers == null ? null : { headers }),
            body: params
        }).then(response => response.json());
    }
}
