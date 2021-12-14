<template>
    <div id="y-axis">
        <div>
            <table>
                <thead>
                    <tr>
                        <th class="opt-head">· Y축</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="opt-body scroll no-scrollbar">
                            <ul>
                                <li :key="i" v-for="(data, i) in yAxis">
                                    <div @click="removeYAxis(data)">
                                        {{ data.label + (data.unit !== "" ? ` (${data.unit})` : "") }}
                                        <span>&times;</span>
                                    </div>
                                </li>
                            </ul>
                        </td>
                    </tr>
                </tbody>
            </table>
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
                removeYAxis: "REMOVE_Y_AXIS"
            })
        }
    }
</script>

<style lang="scss">
    @import "./YAxis";
</style>
