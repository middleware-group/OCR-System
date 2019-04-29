<template>
  <div id="test">
    <beike-button @click="myClick" type="primary" round shake >贝壳</beike-button>
    <beike-button type="warning" round hoverEffect>warning</beike-button>
    <beike-button hoverEffect>贝壳</beike-button>
  </div>
</template>

<script>

export default {
    name: "test",
    components: {
        'beike-button': () => import('../components/beike-button')
    },
    methods:{
        myClick:function () {
            console.log('click');
        }
    }
}
</script>

<style scoped>

</style>