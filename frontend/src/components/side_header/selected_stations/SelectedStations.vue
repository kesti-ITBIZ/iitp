<template>
    <table id="selected-stations">
        <thead>
            <tr>
                <th class="opt-head">선택 지점</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td class="opt-body scroll no-scrollbar">
                    <ul>
                        <li :key="i" v-for="(station, i) in selectedStation" @click="removeSelectedStation({ category: selectedCategory, station })">
                            <span class="close">&times;</span>
                            <h2>{{ station.name }}</h2>
                            <div>위/경도: {{ station.latitude }} / {{ station.longitude }}</div>
                            <div>주소: {{ station.address }}</div>
                            <div>측정항목: {{ obsItems.map(obsItem => obsItem.label).join(", ") }}</div>
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
        name: "SelectedStations",
        computed: {
            ...mapState({
                selectedCategory: state => state.selectedCategory,
                obsItems: state => state[state.selectedCategory].items.filter(obj => obj.value !== "datetime"),
                selectedStation: state => state.selectedStation[state.selectedCategory]
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
