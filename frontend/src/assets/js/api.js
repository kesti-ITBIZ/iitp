import gql from "graphql-tag";

export const stationApi = {
    apollo: {
        ...(() => {
            let obj = {};
            ["airkorea", "kt", "observer", "sDoT"].forEach(category => {
                if (category !== "all")
                    obj[category + "Stations"] = {
                        query: gql`
                            query ${category}Stations($startDatetime: String!, $endDatetime: String!) {
                                ${category}Stations(startDatetime: $startDatetime, endDatetime: $endDatetime) {
                                    address
                                    name
                                    latitude
                                    longitude
                                    pm25
                                }
                            }
                        `,
                        variables() {
                            return {
                                startDatetime: this.startDatetime.format("YYYYMMDDHHmmss"),
                                endDatetime: this.endDatetime.format("YYYYMMDDHHmmss")
                            }
                        },
                        skip: true
                    };
            });
            return obj;
        })()
    }
};

export const dataApi = {
    apollo: {
        airkoreaData: {
            query: gql`
                query airkoreaData($param: DataParam) {
                    airkoreaData(param: $param) {
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
                        stnNm: this.selectedStation.name
                    }
                };
            },
            skip: true
        },
        ktData: {
            query: gql`
                query ktData($param: DataParam) {
                    ktData(param: $param) {
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
                        stnNm: this.selectedStation.name
                    }
                };
            },
            skip: true
        },
        observerData: {
            query: gql`
                query observerData($param: DataParam) {
                    observerData(param: $param) {
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
                        stnNm: this.selectedStation.name
                    }
                };
            },
            skip: true
        },
        sDoTData: {
            query: gql`
                query sDoTData($param: DataParam) {
                    sDoTData(param: $param) {
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
                        stnNm: this.selectedStation.name
                    }
                };
            },
            skip: true
        },

        airkoreaAvailableDatetimes: {
            query: gql`
                query airkoreaAvailableDatetimes {
                    airkoreaAvailableDatetimes
                }
            `,
            skip: true
        },
        ktAvailableDatetimes: {
            query: gql`
                query ktAvailableDatetimes {
                    ktAvailableDatetimes
                }
            `,
            skip: true
        },
        observerAvailableDatetimes: {
            query: gql`
                query observerAvailableDatetimes {
                    observerAvailableDatetimes
                }
            `,
            skip: true
        },
        sDoTAvailableDatetimes: {
            query: gql`
                query sDoTAvailableDatetimes {
                    sDoTAvailableDatetimes
                }
            `,
            skip: true
        },
    }
}
