<template>
    <div class="container-wrap station">
        <div class="container">
            <div class="head">· 선택 지점</div>
            <div class="body">
                <div v-if="selectedStation" @click="removeSelectedStation">
                    <span class="close">&times;</span>
                    <h2>{{ selectedStation.name }}</h2>
                    <div>위/경도: {{ Math.round(selectedStation.latitude * 100) / 100 }} / {{ Math.round(selectedStation.longitude * 100) / 100 }}</div>
                    <div>주소: {{ selectedStation.address }}</div>
                    <div>측정항목: {{ obsItems.map(obsItem => obsItem.label).join(", ") }}</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    export default {
        name: "Station",
        computed: {
            ...mapState({
                selectedCategory: state => state.statistics.selectedCategory,
                obsItems: state => {
                    const selectedCategory = state.statistics.selectedCategory;
                    const obsItems = state.statistics[selectedCategory].items.filter(obj => obj.value !== "datetime");
                    obsItems.sort((a, b) => a.label < b.label ? -1 : 1);
                    return obsItems;
                },
                selectedStation: state => state.statistics.selectedStation[state.statistics.selectedCategory]
            })
        },
        methods: {
            ...mapActions({
                removeSelectedStation: "REMOVE_STATISTICS_SELECTED_STATION"
            })
        }
    }
</script>

<style lang="scss">
    @import "./Station";
</style>
