<template>
    <div id="loading-wrap" v-show="$store.state.common.loading">
        <div>
            <div id="loading"></div>
        </div>
    </div>
</template>

<script>export default { name: "Loading" }</script>

<style lang="scss">
    @import "./Loading";
</style>
