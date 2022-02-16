<template>
    <div class="container-wrap options">
        <div class="container">
            <datetime />
            <category />
        </div>
    </div>
</template>

<script>
    import Datetime from "./datetime/Datetime";
    import Category from "./category/Category";

    export default {
        name: "Options",
        components: {
            Datetime,
            Category
        }
    }
</script>

<style lang="scss">
    @import "./Options";
</style>
