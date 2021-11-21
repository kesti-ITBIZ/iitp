<template>
    <div>
        <h1>Hello</h1>
        {{ airkoreaStations }}
    </div>
</template>

<script>
    import gql from "graphql-tag";

    export default {
        data: () => ({
            airkoreaStations: null
        }),
        apollo: {
            airkoreaStations: gql`
                query {
                    airkoreaStations {
                        address
                    }
                }
            `
        }
    }
</script>
