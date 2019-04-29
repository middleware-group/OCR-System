<template>
  <div class="top-menu">
    <div class="navigator">
      <div class="left-menu">
        <div class="system-icon">
          <img class="icon" src="../assets/OCR.png" />
        </div>
      </div>
      <div class="right-menu" v-if="!exitAble" @click="contact" style="cursor: pointer">
        <div class="menu-title">
          <span>联系作者</span>
        </div>
      </div>
      <div class="right-menu" v-else @click="goBack" style="cursor: pointer">
        <div class="menu-title">
          <span>返回</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
        name: "top-menu",
        props: {
            exitAble: {
                type: Boolean,
                default: false
            }
        },
        methods:{
            contact:function () {
                this.$router.push('/contact')
            },
            goBack:function () {
                this.$router.back();
            }
        }
    }
</script>

<style lang="less">

    /* Main styles */
    .navigator{
        padding: 0;
        width: 100%;
        height: 50px;
        left: 0;
        top:0;
        box-shadow: 0 0 3px #333333;
        border-bottom: 1px solid whitesmoke;
        position: fixed;
        background-color: white;
        z-index: 1000;

    .right-menu{
        position: fixed;
        padding: 0;
        margin: 0;
        top:0;
        float: right;
        width: 150px;
        /*background-color: #dd5347;*/
        /*background-color: dodgerblue;*/
        background-color: #409EFF;
        height: 51px;
        right: 0;
        line-height: 50px;

        .menu-title{
          text-align: center;
          margin-left: auto;
          margin-right: auto;
          color: white;
          font-weight: normal;
          font-size: 14px;
          /*display: inline-block;*/
        }
    }
        .left-menu{
            width: 60px;
            margin-left: 20px;

            .icon{
              width: 30px;
              height: 30px;
              margin: 10px 0 0 0;
            }
        }
    }

</style>