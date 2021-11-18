export default class Ajax {
    get(url, params = null, headers = null) {
        return fetch(url, {
            method: "get",
            ...(headers == null ? null : { headers }),
            body: params
        }).then(response => response.json());
    }

    post(url, params = null, headers = null) {
        return fetch(url, {
            method: "post",
            headers: headers == null ? { "Content-Type": "application/json" } : headers,
            body: JSON.stringify(params)
        }).then(response => response.json());
    }

    put(url, params = null, headers = null) {
        return fetch(url, {
            method: "put",
            headers: headers == null ? { "Content-Type": "application/json" } : headers,
            body: JSON.stringify(params)
        }).then(response => response.json());
    }

    delete(url, params = null, headers = null) {
        return fetch(url, {
            method: "delete",
            ...(headers == null ? null : { headers }),
            body: params
        }).then(response => response.json());
    }
}
