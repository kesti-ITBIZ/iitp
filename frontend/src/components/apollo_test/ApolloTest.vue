<template>
    <div>
        <h1>Hello</h1>
        <input type="button" value="generate" @click="getAirkoreaStations" />
        <div v-if="airkoreaStations">
            [<br />
            <ul>
                <li :key="i" v-for="(obj, i) in airkoreaStations">&emsp;{ latitude: {{ obj.latitude }}, longitude: {{ obj.longitude }} }</li>
            </ul>
            ]
        </div>
    </div>
</template>

<script>
    import gql from "graphql-tag";

    export default {
        data: () => ({
            airkoreaStations: null
        }),
        apollo: {
            airkoreaStations: {
                query: gql`
                    query {
                        airkoreaStations {
                            latitude
                            longitude
                        }
                    }
                `,
                skip: () => true
            }
        },
        methods: {
            getAirkoreaStations() {
                const airkoreaStations = this.$apollo.queries.airkoreaStations;
                airkoreaStations.skip = false;
                airkoreaStations.refresh();
            }
        },
        mounted() {
            console.log(this.airkoreaStations);
        }
    }
</script>
