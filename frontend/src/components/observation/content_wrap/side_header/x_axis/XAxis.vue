<template>
    <div class="container-wrap x-axis">
        <div class="container">
            <div class="head">· X축</div>
            <div class="body">
                <ul>
                    <li :key="i" v-for="(data, i) in xAxis">
                        <button class="item-btn" @click="removeXAxis(data)">
                            {{ data.label + (data.unit !== "" ? ` (${data.unit})` : "") }}
                            <span class="times">&times;</span>
                        </button>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    export default {
        name: "XAxis",
        computed: {
            ...mapState({
                xAxis: state => state.observation[state.observation.selectedCategory].xAxis
            })
        },
        methods: {
            ...mapActions({
                removeXAxis: "REMOVE_OBSERVATION_X_AXIS"
            })
        }
    }
</script>

<style lang="scss">
    @import "./XAxis";
</style>
