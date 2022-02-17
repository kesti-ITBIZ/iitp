import gql from "graphql-tag";

export const stationApi = {
    apollo: {
        stations: {
            query: gql`
                query stations($category: String!, $startDatetime: String!, $endDatetime: String!) {
                    stations(category: $category, startDatetime: $startDatetime, endDatetime: $endDatetime) {
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
                    category: this.selectedCategory,
                    startDatetime: this.startDatetime.format("YYYY" + (this.selectedDateType == "year" ? "" : "MM")),
                    endDatetime: this.endDatetime.format("YYYY" + (this.selectedDateType == "year" ? "" : "MM"))
                }
            },
            skip: true
        }
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
};

export const statisticsApi = {
    apollo: {
        statisticsData: {
            query: gql`
                query statisticsData($category: String!, $startDatetime: String!, $endDatetime: String!, $stnNm: String!) {
                    statisticsData(category: $category, startDatetime: $startDatetime, endDatetime: $endDatetime, stnNm: $stnNm) {
                        category
                        datetime
                        stnNm
                        pm10 {
                            avg
                            goodDays
                            badDays
                            highDnstyDays
                        }
                        pm25 {
                            avg
                            goodDays
                            badDays
                            highDnstyDays
                        }
                    }
                }
            `,
            variables() {
                return {
                    category: this.selectedCategory,
                    startDatetime: this.startDatetime.format("YYYY" + (this.selectedDateType == "month" ? "MM" : "")),
                    endDatetime: this.endDatetime.format("YYYY" + (this.selectedDateType == "month" ? "MM" : "")),
                    stnNm: this.selectedStation.name
                }
            },
            skip: true
        }
    }
};
