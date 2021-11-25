import gql from "graphql-tag";

export const stationApi = {
    apollo: {
        ...(() => {
            let obj = {};
            ["airkorea", "kt", "observer", "sDoT"].forEach(category => {
                if (category !== "all")
                    obj[category + "Stations"] = {
                        query: gql`
                            query {
                                ${category}Stations {
                                    address
                                    name
                                    latitude
                                    longitude
                                }
                            }
                        `,
                        skip: true
                    };
            });
            return obj;
        })(),
    }
};

export const dataApi = {
    apollo: {
        airkoreaDataByDatetime: {
            query: gql`
                query airkoreaDataByDatetime($param: DataParam) {
                    airkoreaDataByDatetime(param: $param) {
                        datetime
                        stnNm
                        so2
                        no2
                        o3
                        co
                        pm10
                        pm25
                    }
                }
            `,
            variables() {
                return {
                    param: {
                        startDatetime: this.startDatetime.format("YYYYMMDDHHmmss"),
                        endDatetime: this.endDatetime.format("YYYYMMDDHHmmss"),
                        dateType: this.selectedDateType,
                        stnNm: this.selectedStation[0].name,
                        ...this.selectedFineParticleRange
                    }
                };
            },
            skip: true
        },
        airkoreaDataByItem: {
            query: gql`
                query airkoreaDataByItem($param: DataParam) {
                    airkoreaDataByItem(param: $param) {
                        stnNm
                        so2
                        no2
                        o3
                        co
                        pm10
                        pm25
                    }
                }
            `,
            variables() {
                return {
                    param: {
                        startDatetime: this.startDatetime.format("YYYYMMDDHHmmss"),
                        endDatetime: this.endDatetime.format("YYYYMMDDHHmmss"),
                        stnNm: this.selectedStation[0].name,
                        ...this.selectedFineParticleRange
                    }
                };
            },
            skip: true
        },
        ktDataByDatetime: {
            query: gql`
                query ktDataByDatetime($param: DataParam) {
                    ktDataByDatetime(param: $param) {
                        datetime
                        stnNm
                        temperature
                        humidity
                        pm10
                        pm25
                    }
                }
            `,
            variables() {
                return {
                    param: {
                        startDatetime: this.startDatetime.format("YYYYMMDDHHmmss"),
                        endDatetime: this.endDatetime.format("YYYYMMDDHHmmss"),
                        dateType: this.selectedDateType,
                        stnNm: this.selectedStation[0].name,
                        ...this.selectedFineParticleRange
                    }
                };
            },
            skip: true
        },
        ktDataByItem: {
            query: gql`
                query ktDataByItem($param: DataParam) {
                    ktDataByItem(param: $param) {
                        stnNm
                        temperature
                        humidity
                        pm10
                        pm25
                    }
                }
            `,
            variables() {
                return {
                    param: {
                        startDatetime: this.startDatetime.format("YYYYMMDDHHmmss"),
                        endDatetime: this.endDatetime.format("YYYYMMDDHHmmss"),
                        stnNm: this.selectedStation[0].name,
                        ...this.selectedFineParticleRange
                    }
                };
            },
            skip: true
        },
        observerDataByDatetime: {
            query: gql`
                query observerDataByDatetime($param: DataParam) {
                    observerDataByDatetime(param: $param) {
                        datetime
                        stnNm
                        temperature
                        humidity
                        pressure
                        pm25
                    }
                }
            `,
            variables() {
                return {
                    param: {
                        startDatetime: this.startDatetime.format("YYYYMMDDHHmmss"),
                        endDatetime: this.endDatetime.format("YYYYMMDDHHmmss"),
                        dateType: this.selectedDateType,
                        stnNm: this.selectedStation[0].name,
                        ...this.selectedFineParticleRange
                    }
                };
            },
            skip: true
        },
        observerDataByItem: {
            query: gql`
                query observerDataByItem($param: DataParam) {
                    observerDataByItem(param: $param) {
                        stnNm
                        temperature
                        humidity
                        pressure
                        pm25
                    }
                }
            `,
            variables() {
                return {
                    param: {
                        startDatetime: this.startDatetime.format("YYYYMMDDHHmmss"),
                        endDatetime: this.endDatetime.format("YYYYMMDDHHmmss"),
                        stnNm: this.selectedStation[0].name,
                        ...this.selectedFineParticleRange
                    }
                };
            },
            skip: true
        },
        sDoTDataByDatetime: {
            query: gql`
                query sDoTDataByDatetime($param: DataParam) {
                    sDoTDataByDatetime(param: $param) {
                        datetime
                        stnNm
                        temperature
                        relativeHumidity
                        windDirection
                        windSpeed
                        pm10
                        pm25
                    }
                }
            `,
            variables() {
                return {
                    param: {
                        startDatetime: this.startDatetime.format("YYYYMMDDHHmmss"),
                        endDatetime: this.endDatetime.format("YYYYMMDDHHmmss"),
                        dateType: this.selectedDateType,
                        stnNm: this.selectedStation[0].name,
                        ...this.selectedFineParticleRange
                    }
                };
            },
            skip: true
        },
        sDoTDataByItem: {
            query: gql`
                query sDoTDataByItem($param: DataParam) {
                    sDoTDataByItem(param: $param) {
                        stnNm
                        temperature
                        relativeHumidity
                        windDirection
                        windSpeed
                        pm10
                        pm25
                    }
                }
            `,
            variables() {
                return {
                    param: {
                        startDatetime: this.startDatetime.format("YYYYMMDDHHmmss"),
                        endDatetime: this.endDatetime.format("YYYYMMDDHHmmss"),
                        stnNm: this.selectedStation[0].name,
                        ...this.selectedFineParticleRange
                    }
                };
            },
            skip: true
        }
    }
}
