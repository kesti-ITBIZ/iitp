<template>
    <div class="container-wrap station">
        <div class="container">
            <div class="head">· {{ title }}</div>
            <div class="body">
                <div v-if="station" @click="$emit('remove')">
                    <span class="close">&times;</span>
                    <h2>{{ station.name }}</h2>
                    <div>위/경도: {{ Math.round(station.latitude * 100) / 100 }} / {{ Math.round(station.longitude * 100) / 100 }}</div>
                    <div>측정항목: {{ obsItems.map(obsItem => obsItem.label).join(", ") }}</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import { mapState } from "vuex";

    export default {
        name: "Station",
        props: {
            title: String,
            station: Object
        },
        computed: {
            ...mapState({
                selectedCategory: state => state.verification.selectedCategory,
                obsItems: state => {
                    const selectedCategory = state.verification.selectedCategory;
                    const obsItems = state.verification[selectedCategory].items.filter(obj => obj.value !== "datetime")
                        .concat(state.verification[selectedCategory].xAxis.filter(obj => obj.value !== "datetime"))
                        .concat(state.verification[selectedCategory].yAxis.filter(obj => obj.value !== "datetime"));
                    obsItems.sort((a, b) => a.label < b.label ? -1 : 1);
                    return obsItems;
                }
            })
        }
    }
</script>

<style lang="scss">
    @import "./Station";
</style>
