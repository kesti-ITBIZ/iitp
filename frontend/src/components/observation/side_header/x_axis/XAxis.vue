<template>
    <table id="x-axis">
        <thead>
            <tr>
                <th class="opt-head">X축</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td class="opt-body scroll no-scrollbar">
                    <ul>
                        <li :key="i" v-for="(data, i) in xAxis">
                            <div @click="removeXAxis(data)">
                                {{ data.label + (data.unit !== "" ? ` (${data.unit})` : "") }}
                                <span>&times;</span>
                            </div>
                        </li>
                    </ul>
                </td>
            </tr>
        </tbody>
    </table>
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
                removeXAxis: "REMOVE_X_AXIS"
            })
        }
    }
</script>

<style lang="scss">
    @import "./XAxis";
</style>
