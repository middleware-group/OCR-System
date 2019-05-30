<template>
  <div class="tip-container">
    <img src="../assets/对话框反向.png" style="width: 250px">
    <div class="content">&nbsp;&nbsp;&nbsp;&nbsp;{{content}}</div>
    <div class="button-container">
      <el-button :type="type" plain size="small" @click="next">{{buttonText}}</el-button>
    </div>
  </div>
</template>

<script>
    export default {
        name: "tip",
        props:{
            direction: {
                type: String,
                default: "right"
            },
            content: {
                type: String,
                default: "ri"
            },
            buttonText: {
                type: String,
                default: "下一步"
            },
            type: {
                type: String,
                default: "plain"
            },
        },
        mounted(){

        },
        methods:{
            next: function () {
                this.$emit('next')
            }
        }
    }
</script>

<style scoped>
.tip-container{
  position: relative;
  z-index: 100;
}
.content{
  position: absolute;
  top: 30%;
  left: 25%;
  width: 150px;
}
.button-container{
  position: absolute;
  top: 60%;
  left: 50%;
  transform: translateX(-50%);
}
</style>