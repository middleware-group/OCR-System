<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import TopMenu from "./components/top-menu";
export default {
  name: 'App',
    components: {TopMenu}
}
</script>

<style>
#app{
  font-weight: lighter;
  font-family: "Roboto","Helvetica",arial,sans-serif;
}
</style>
