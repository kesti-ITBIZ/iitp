<template>
    <div id="selected-stations">
        <table>
            <thead>
                <tr>
                    <th class="opt-head">· 선택 지점</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="opt-body scroll no-scrollbar">
                        <ul>
                            <li :key="i" v-for="(station, i) in selectedStation" @click="removeSelectedStation({ category: selectedCategory, station })">
                                <span class="close">&times;</span>
                                <h2>{{ station.name }}</h2>
                                <div>위/경도: {{ Math.round(station.latitude * 100) / 100 }} / {{ Math.round(station.longitude * 100) / 100 }}</div>
                                <div>주소: {{ station.address }}</div>
                                <div>측정항목: {{ obsItems.map(obsItem => obsItem.label).join(", ") }}</div>
                            </li>
                        </ul>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    export default {
        name: "SelectedStations",
        computed: {
            ...mapState({
                selectedCategory: state => state.observation.selectedCategory,
                obsItems: state => {
                    const selectedCategory = state.observation.selectedCategory;
                    const obsItems = state.observation[selectedCategory].items.filter(obj => obj.value !== "datetime")
                        .concat(state.observation[selectedCategory].xAxis.filter(obj => obj.value !== "datetime"))
                        .concat(state.observation[selectedCategory].yAxis.filter(obj => obj.value !== "datetime"));
                    obsItems.sort((a, b) => a.label < b.label ? -1 : 1);
                    return obsItems;
                },
                selectedStation: state => state.observation.selectedStation[state.observation.selectedCategory]
            })
        },
        methods: {
            ...mapActions({
                removeSelectedStation: "REMOVE_SELECTED_STATION"
            })
        }
    }
</script>

<style lang="scss">
    @import "./SelectedStations";
</style>
