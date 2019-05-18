<template>
  <div class="top-menu">
    <div class="navigator">
      <div class="left-menu">
        <div class="system-icon">
          <router-link to="/">
            <img class="icon" src="../assets/OCR.png" />
          </router-link>
        </div>
      </div>
      <div class="middle-menu" @click="handleClick">
        <router-link to="/ocr">
          <div class="middle-navigator">
            图像识别
          </div>
        </router-link>
        <router-link to="/translate">
          <div class="middle-navigator">
            语言翻译
          </div>
        </router-link>
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
            },
            active: {
                type: Number,
                default: -1
            },
            submenuList: {
                type: Array
            }
        },
        mounted(){
            let index = this.$props.active;
            if(index >= 0){
                document.getElementsByClassName('middle-navigator')[index].classList.add('middle-navigator-active')
            }
        },
        methods:{
            contact:function () {
                this.$router.push('/contact')
            },
            goBack:function () {
                this.$router.back();
            },
            handleClick(event){
                let ele = event.target || event.srcElement;
                ele.classList.add('middle-navigator-active');
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
        z-index: 10;

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
        line-height: 51px;

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
            display: inline-block;

            .icon{
              width: 30px;
              height: 30px;
              margin: 10px 0 0 0;
            }
        }
    }

  .middle-menu{
    position: absolute;
    height: 49px;
    display: inline-block;
    margin-left: 30px;
  }

  .middle-navigator{
    cursor: pointer;
    display: inline-block;
    height: 49px;
    width: 100px;
    text-align: center;
    margin-right: 20px;
    font-size: 18px;
    line-height: 49px;
  }

    .middle-navigator:hover:after,.middle-navigator-active:after{
      content: "";
      display: block;
      width: 100%;
      height: 3px;
      background-color: dodgerblue;
    }
    .middle-navigator-active,.middle-navigator:hover{
      color: dodgerblue;
    }



</style>