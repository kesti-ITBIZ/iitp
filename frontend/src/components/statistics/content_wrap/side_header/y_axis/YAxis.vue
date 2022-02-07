<template>
    <div class="container-wrap y-axis">
        <div class="container">
            <div class="head">· Y축</div>
            <div class="body">
                <ul>
                    <li :key="i" v-for="(data, i) in yAxis">
                        <button class="item-btn" @click="removeYAxis(data)">
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
        name: "YAxis",
        computed: {
            ...mapState({
                yAxis: state => state.observation[state.observation.selectedCategory].yAxis
            })
        },
        methods: {
            ...mapActions({
                removeYAxis: "REMOVE_OBSERVATION_Y_AXIS"
            })
        }
    }
</script>

<style lang="scss">
    @import "src/components/observation/content_wrap/side_header/y_axis/YAxis";
</style>
