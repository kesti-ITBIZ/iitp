export default class MapDsl {
    static select(...attrs) {
        this.prototype._select = true;
        this.prototype.selectFunction = data => {
            console.log("select");
            const keys = this.keys.filter(key => attrs.indexOf(key) !== -1);
            return data.map(obj => {
                let _obj = {};
                for (const i in keys)
                    _obj[keys[i]] = obj[keys[i]];
                return _obj;
            });
        };
        return this;
    }

    static from(data) {
        console.log("from");
        this.prototype._from = true;
        this.prototype.fromData = data.concat();
        if (data.length > 0) this.prototype.keys = Object.keys(data[0]);
        return this;
    }

    static where(...constraints) {
        this.prototype.whereFunction = () => {
            console.log("where");
        };
        return this;
    }

    static groupBy(...attrs) {
        this.prototype.groupByAttrs = attrs;
        this.prototype.groupByFunction = () => {
            console.log("group by");
        };
        return this;
    }

    static orderBy(...attrs) {
        this.prototype._orderBy = true;
        this.prototype.orderByFunction = () => {
            console.log("order by");
            const data = this.prototype.data.concat();
            data.sort((a, b) => {
                let compare = -1;
                attrs.forEach(attr => {
                    if (typeof attr === "string")
                        compare = a[attr] < b[attr] ? -1 : 1;
                })
            });
            return data;
        };
        return this;
    }

    static fetch() {
        let data = this.prototype.whereFunction();
        data = this.prototype.groupByFunction();
        data = this.prototype.selectFunction();
        data = this.prototype.orderByFunction();

        this.prototype._select = false;
        this.prototype._from = false;
        this.prototype._where = false;
        this.prototype._groupBy = false;
        this.prototype._orderBy = false;
    }
}
