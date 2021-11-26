<template>
    <div id="loading-wrap" v-show="$store.state.common.loading">
        <div>
            <div id="loading"></div>
        </div>
    </div>
</template>

<script>export default { name: "Loading" }</script>

<style lang="scss">
    $size: 70px !important;
    $thickness: 6px !default;
    $base-color: #d2d2d2 !default;
    $wheel-color: #8d8d8d !default;
    $speed: 800ms !default;

    #loading-wrap {
        position: absolute;
        top: 0;
        left: 0;
        display: table;
        background-color: rgba(255, 255, 255, 0.69);
        width: 100%;
        height: 100%;
        z-index: 999999;

        > div {
            display: table-cell;
            vertical-align: middle;

            #loading {
                height: $size;
                width: $size;
                border: $thickness solid $base-color;
                border: {
                    right-color: $wheel-color;
                    top-color: $wheel-color;
                    radius: 100%;
                }
                margin: auto;
                animation: spin $speed infinite linear;
            }
        }
    }

    @keyframes spin {
        from { transform: rotate(0deg); }
        to { transform: rotate(359deg); }
    }
</style>
