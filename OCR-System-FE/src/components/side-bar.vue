<template>
  <div class="side-bar">
    <div style="display: block; left: 0;width: 200px; height:100%">
      <el-menu
              :default-active=active
              @select="handleSelect"
              text-color="#000"
              active-text-color="#409EFF"
              class="el-menu-vertical-demo side-menu">
        <el-menu-item index="1">
          <i class="el-icon-document"></i>
          <span slot="title">第一题</span>
        </el-menu-item>
        <el-menu-item index="2">
          <i class="el-icon-document"></i>
          <span slot="title">第三题</span>
        </el-menu-item>
      </el-menu>
    </div>
  </div>
</template>

<script>
    export default {
        name: "side-bar",
        props:{
            active: {
                type: String,
                default: "1"
            }
        },
        methods:{
            handleSelect(key, keyPath) {
                let active = this.$props.active;
                if(key==="1" && active!=="1"){
                    this.$router.push('/first');
                }else if(key==="2" && active!=="2"){
                    this.$router.push('/second');
                }
            },
        }
    }
</script>

<style scoped>
.side-menu{
  height: 100%;
  font-size: 16px;
  font-weight: lighter;
  font-family: "Roboto","Helvetica",arial,sans-serif;
}
</style>