<template>
    <div class="container-wrap items">
        <div class="container">
            <div class="head">· 데이터 항목</div>
            <div class="body">
                <ul v-if="selectedCategory != 'observer'">
                    <li :key="i" v-for="(item, i) in items">
                        <input type="button"
                               :class="['item-btn', item.value, selectedItem.value == item.value ? 'on' : '']"
                               :value="item.label + (item.unit !== '' ? ` (${item.unit})` : '')"
                               @click="setSelectedItem(item)" />
                        <font-awesome-icon v-show="selectedItem.value == item.value" size="1x" :icon="['fa', 'check']" />
                    </li>
                </ul>
                <ul v-else>
                    <li>
                        <input type="button" class="item-btn pm25 on" value="PM2.5 (㎍/㎥)" />
                        <font-awesome-icon size="1x" :icon="['fa', 'check']" />
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
    import { mapState, mapActions } from "vuex";

    export default {
        name: "Items",
        data: () => ({
            showItemTooltip: false,
            itemTooltip: {
                top: 0,
                left: 0
            }
        }),
        computed: {
            ...mapState({
                selectedCategory: state => state.verification.selectedCategory,
                items: state => state.verification.items,
                selectedItem: state => state.verification.selectedItem
            })
        },
        watch: {
            selectedCategory() {
                this.showItemTooltip = false;
                if (this.selectedCategory == "observer")
                    this.setSelectedItem({
                        label: "PM2.5",
                        value: "pm25",
                        unit: "㎍/㎥"
                    });
            }
        },
        methods: {
            ...mapActions({
                setSelectedItem: "SET_VERIFICATION_SELECTED_ITEM",
            })
        }
    }
</script>

<style lang="scss">
    @import "./Items";
</style>
