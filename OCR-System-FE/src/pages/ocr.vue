<template>
  <div class="ocr">
    <!--<div id="mask"></div>-->
    <top-menu></top-menu>
    <el-container class="main-container">
      <el-main class="main-container-el">
        <div class="breadcrumb">
          <el-breadcrumb separator-class="el-icon-arrow-right" style="font-size: 18px">
            <el-breadcrumb-item></el-breadcrumb-item>
            <el-breadcrumb-item>目标语言</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="question">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          请选择目标语言：
          <el-radio v-model="radio" label="1" border style="margin-right: 0">英文</el-radio>
          <el-radio v-model="radio" label="2" border>中文</el-radio>
        </div>
        <div class="breadcrumb">
          <el-breadcrumb separator-class="el-icon-arrow-right" style="font-size: 18px">
            <el-breadcrumb-item></el-breadcrumb-item>
            <el-breadcrumb-item>识别结果</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="exec" v-loading="isLoading">
          <div class="play" v-show="!isPlay">
            <el-upload
                    class="upload-demo"
                    drag
                    action="https://jsonplaceholder.typicode.com/posts/"
                    multiple>
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传 JPG/PNG 文件，且不超过 500KB</div>
            </el-upload>
            <!--<img src="../assets/start.png" @click="play"/>-->
          </div>
          <div class="result" v-show="isPlay">
            <div class="result-text">
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              本次准确率：<span style="color: #67C23A">{{active.value}}</span> <br><br>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              平均准确率：<span style="color: #67C23A">{{ave}}</span> <br><br>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              耗时：<span style="color: #67C23A">{{showTime}} ms</span>
            </div>
            <div class="result-image">
              <img :src="active.file"/>
            </div>
          </div>

        </div>

      </el-main>
    </el-container>
  </div>
</template>

<script>
    import TopMenu from "../components/top-menu";
    import SideBar from "../components/side-bar";
    export default {
        name: "ocr",
        components: {
            TopMenu,
            SideBar
        },mounted(){
            function addStyle(element,key,value,time){
                return new Promise(resolve => {
                    setTimeout(()=>{
                        element.style[key] = value;
                        resolve();
                    },time);
                })
            }
            (function(){
                let main = document.getElementsByClassName('main-container-el')[0];
                addStyle(main,'opacity','1.0',0);
            }());

        },
        data(){
            return{
                isPlay: false,
                isLoading: false,
                radio: "1",
                ave: "",
                res: [],
                active: {

                },
                page: 1,
                time: new Date(),
                showTime: 0
            }
        },
        methods:{
            play: function(){
                let _this = this;
                _this.$data.isPlay = true;
                _this.$data.isLoading = true;
                _this.$data.time = new Date();
                this.exec();
            },
            exec: function(){
                let _this=this;
                this.$axios({
                    method:'get',
                    url:'/first',
                }).then(function (response) {
                    let d = new Date();
                    _this.$data.showTime = parseInt((d - _this.$data.time));//两个时间相差的秒数
                    console.log(_this.$data.showTime);
                    let data = response.data;
                    let result = data.result;
                    let filePath = data.filePath;
                    _this.$data.ave = data["average"];
                    _this.$data.active = {
                        value: result[0],
                        file: filePath[0]
                    };
                    for(let i=0; i<result.length; i++){
                        _this.$data.res.push({
                            value: result[i],
                            file: filePath[i]
                        })
                    }
                    _this.$data.isLoading = false;
                }).catch(function (error) {
                    console.log(error.response.data)
                })
            }
        }

    }
</script>

<style scoped>
.main-container{
  left: 0;
  margin: 50px 0 0 0;
  padding: 0;
  width: 100%;
  height: 100vh;
  overflow: auto;
  background-color: white;
}
.main-container-el{
  opacity: 0.1;
  transition: opacity 2s;
}
.question{
  margin-top:20px;
  margin-left: 50px;
  font-size: 18px;
  width: 90%;
  min-width: 400px;
  background-color: white;
  z-index: 100;
  position: relative;
}
.exec{
  margin-top: 30px;
  margin-left: 90px;
  font-size: 18px;
  width: 85%;
  min-width: 400px;
}
.breadcrumb{
  margin-top: 30px;
  margin-left: 30px;
  width: 400px;
}
.play{
  position: relative;
  background: rgba(233, 233, 235, 0.4);
  height: 500px;
}
.play > img{
  position: absolute;
  left: 50%;
  top: 50%;
  width: 200px;
  margin: -100px 0 0 -100px;
  display: inline-block;
  cursor: pointer;
}
.result{
  position: relative;
  height: 750px;
  text-align: center;
}
.result-image > img{
  display: inline-block;
}
.result-text{
  margin: 20px 0 30px 0;
  font-size: 18px;
  text-align: left;
}
#mask {
  position: absolute;   /* 使用绝对定位或固定定位  */
  left: 0;
  top: 0;
  width:100%;
  height:100%;
  text-align: center;
  z-index: 99;
  background-color: rgba(0,0,0,0.4);
}
.upload-demo{
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
  text-align: center;
  font-size: 40px;
}
.el-upload__text{
  margin-top: 5px;
  font-size: 16px;
}
.el-upload__tip{
  font-size: 14px;
}
</style>