<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import TopMenu from "./components/top-menu";
import cookieOpt from "./util/cookieOperation";
export default {
    name: 'App',
    components: {TopMenu},
    created(){
        //cookieOpt.addCookie("user", "anonymous", 1)
    },
    destroyed(){
        //cookieOpt.delCookie("user");
    }
}
</script>

<style>
#app{
  font-weight: lighter;
  font-family: "Roboto","Helvetica",arial,sans-serif;
}
</style>
