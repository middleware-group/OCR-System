<template>
  <div class="ocr">
    <div id="mask"></div>
    <top-menu :active='active'></top-menu>
    <el-container class="main-container">
      <el-main class="main-container-el">
        <div class="choose">
          <div class="breadcrumb">
            <el-breadcrumb separator-class="el-icon-arrow-right" style="font-size: 18px">
              <el-breadcrumb-item></el-breadcrumb-item>
              <el-breadcrumb-item>所用 API</el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <div class="question">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            请选择 API：
            <el-radio v-model="radio" label="1" border style="margin-right: 0" @click="changeAPI(1)">百度 API</el-radio>
            <el-radio v-model="radio" label="2" border @click="changeAPI(2)">腾讯 API</el-radio>
          </div>
        </div>

        <tip style="display: inline-block"
             content="这里可以选择识别所使用的 API"
             @next="nextStep"
             v-show="guide==0"></tip>

        <div class="choose">
          <div class="breadcrumb">
            <el-breadcrumb separator-class="el-icon-arrow-right" style="font-size: 18px">
              <el-breadcrumb-item></el-breadcrumb-item>
              <el-breadcrumb-item>所用精度</el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <div class="question">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            请选择精度：
            <el-radio v-model="precision" label="1" border style="margin-right: 0">一般精度</el-radio>
            <el-radio v-model="precision" label="2" border>高精度</el-radio>
          </div>
        </div>

        <tip style="display: inline-block"
             content="这里可以选择识别的精度"
             @next="nextStep"
             v-show="guide==2"></tip>

        <div class="breadcrumb">
          <el-breadcrumb separator-class="el-icon-arrow-right" style="font-size: 18px">
            <el-breadcrumb-item></el-breadcrumb-item>
            <el-breadcrumb-item>开始识别</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div id="optContainer">
          <div class="exec" v-loading="isLoading">
            <div class="play" v-show="!isPlay">
              <el-upload
                      v-show="radio==1&&precision==1"
                      class="upload-demo"
                      :limit="fileNum"
                      drag
                      :file-list="fileList"
                      :action="uploadAction"
                      :on-error="uploadError"
                      :on-success="uploadSucceed"
                      :before-upload="beforeUpload"
                      multiple>
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">只能上传 JPG/PNG 文件，且不超过 500KB</div>
              </el-upload>
              <el-upload
                      v-show="radio==2&&precision==1"
                      class="upload-demo"
                      :limit="fileNum"
                      drag
                      :file-list="fileList"
                      :action="uploadActionTx"
                      :on-error="uploadError"
                      :on-success="uploadSucceed"
                      :before-upload="beforeUpload"
                      multiple>
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">只能上传 JPG/PNG 文件，且不超过 500KB</div>
              </el-upload>
              <el-upload
                      v-show="precision==2"
                      class="upload-demo"
                      :limit="fileNum"
                      drag
                      :file-list="fileList"
                      :action="uploadActionHighPrecision"
                      :on-error="uploadError"
                      :on-success="uploadSucceed"
                      :before-upload="beforeUpload"
                      multiple>
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">只能上传 JPG/PNG 文件，且不超过 500KB</div>
              </el-upload>
            </div>
            <tip style="position: absolute;top:0;left: 100%"
                 content="在这里上传需要识别的图片"
                 v-show="guide==4"
                 @next="nextStep"></tip>

            <div class="result" v-show="isPlay">
              <el-card class="box-card">
                <div slot="header" style="text-align: left">
                  图片<span style="color: #67C23A"></span>
                  <el-button
                          style="float: right;"
                          type="primary"
                          @click="resetPicture"
                          size="small">重置</el-button>
                </div>
                <div class="image-container">
                  <img :src="uploadImg" class="result-img"/>
                </div>
              </el-card>
            </div>
          </div>
          <div class="middle-img">
            <img src="../assets/箭头.png"/>
          </div>

          <div class="res" v-loading="isLoading">
            <tip style="position: absolute; top:20%;left: 50%;"
                 content="这里会显示识别的结果"
                 button-text="开始识别"
                 type="primary"
                 v-show="guide==6"
                 @next="nextStep"></tip>
            <div class="play" v-show="!isPlay">

            </div>
            <div class="result" v-show="isPlay">
              <el-card class="box-card">
                <div slot="header" style="text-align: left">
                  OCR 耗时：<span style="color: #67C23A">{{showTime}} ms</span>
                  <el-button
                          style="float: right;"
                          type="primary"
                          @click="reset"
                          size="small">重置</el-button>
                </div>
                <div class="textarea-container">
                  <textarea class="result-textarea" v-model="resultText"></textarea>
                </div>
              </el-card>
            </div>
          </div>

        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
    import TopMenu from "../components/top-menu";
    import tip from "../components/tip";
    import cookieOpt from "../util/cookieOperation";

    export default {
        name: "ocr",
        components: {
            TopMenu,
            tip
        },
        mounted(){
            if(!cookieOpt.getCookie("user")){
                this.$data.guide = 0;
                let mask = document.getElementById('mask');
                mask.style.visibility = "visible";
                cookieOpt.addCookie("user", "anonymous", 1)
            }
            if(this.$data.guide==0) {
                let choose = document.getElementsByClassName('choose')[0];
                choose.classList.add('highlight');
            }
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
                active: 0,
                uploadAction: this.$axios.defaults.baseURL + "basicOcr",
                uploadActionTx: this.$axios.defaults.baseURL + "ocrtx",
                uploadActionHighPrecision: this.$axios.defaults.baseURL + "accurateOcr",
                uploadImg: "",
                uploadImgBaidu: "",
                uploadImgTx: "",
                isPlay: false,
                isLoading: false,
                radio: "1",
                precision: "1",
                fileNum: 1,
                fileList: [],
                resultText: "",
                resultBackup: "",
                time: new Date(),
                showTime: 0,
                guide: -1
            }
        },
        methods:{
            changeAPI: function(index){

            },
            beforeUpload: function(file){
                const isLt2M = file.size / 1024 / 1024 < 1.5;
                if(!isLt2M){
                    this.showMsg("Sorry , 文件大小不可超过 1.5MB","error");
                    return false;
                }
                this.$data.isLoading = true;
                this.$data.time = new Date();
            },

            //上传成功
            uploadSucceed: function(response){
                let _this = this;
                if(_this.$data.radio == "1"||_this.$data.precision=="2"){
                    if(!_this.uploadBaidu(response)){
                        return false;
                    }
                }else if(_this.$data.radio == "2"){
                    if(!_this.uploadTencent(response)){
                        return false;
                    }
                }
                _this.$data.isLoading = false;
                _this.$data.isPlay = true;
                let d = new Date();
                _this.$data.showTime = parseInt((d - _this.$data.time));//两个时间相差的秒数
            },

            uploadBaidu: function(response){
                let _this = this;
                if(response.error_msg){
                    this.$data.isLoading = false;
                    if(response.error_msg=="service timeout"){
                        this.showMsg("Sorry , 服务器正忙 , 请稍后再试","error");
                    }else if(response.error_msg=="image format error"){
                        this.showMsg("Sorry , 文件格式错误 , 请上传 JPG/PNG 格式图片","error");
                    }
                    return false;
                }
                _this.showMsg("识别成功 !","success");
                let result = "";
                response.words_result.forEach((val)=>{
                    result = result + val.words + "\n";
                });
                _this.$data.uploadImg = response.fileName;
                _this.$data.resultText = result;
                _this.$data.resultTextBackup = _this.$data.resultText;
                return true;
            },
            uploadTencent: function(response){
                let _this = this;
                let result = JSON.parse(response.result);
                console.log(result);
                if(result.message==="OK"){
                    _this.showMsg("识别成功 !","success");
                }else{
                    _this.showMsg("Sorry , 识别失败","error");
                    return;
                }
                let resultString = "";
                result.data.items.forEach((value)=>{
                    resultString = resultString + value.itemstring + "\n";
                });
                _this.$data.uploadImg = response.fileName;
                _this.$data.resultText = resultString;
                _this.$data.resultTextBackup = _this.$data.resultText;
                return true;
            },

            //重置文本
            reset: function(){
                this.$data.resultText = this.$data.resultTextBackup;
            },
            //重置图片
            resetPicture: function(){
                this.$data.isPlay = false;
                this.$data.fileList = [];
            },

            //上传错误
            uploadError: function () {
                this.$data.isLoading = false;
                this.showMsg("Sorry, 文件上传失败, 请稍后再试","error");
            },

            //显示消息
            showMsg: function (message,type) {
                if(!message || !type){
                    return;
                }
                this.$message({
                    message: message,
                    type: type,
                    duration: 4000
                });
            },

            nextStep: function () {
                let cur = this.$data.guide;
                if(cur == 0){
                    let choose = document.getElementsByClassName('choose')[0];
                    choose.classList.remove('highlight');
                    this.$data.guide = 1;
                    this.nextStep();
                }else if(cur == 1){
                    let choose = document.getElementsByClassName('choose')[1];
                    choose.classList.add('highlight');
                    this.$data.guide = 2;
                }else if(cur == 2){
                    let choose = document.getElementsByClassName('choose')[1];
                    choose.classList.remove('highlight');
                    this.$data.guide = 3;
                    this.nextStep();
                }else if(cur == 3){
                    let play = document.getElementsByClassName('play')[0];
                    play.classList.add('highlight');
                    this.$data.guide = 4;
                }else if(cur == 4){
                    let play = document.getElementsByClassName('play')[0];
                    play.classList.remove('highlight');
                    this.$data.guide = 5;
                    this.nextStep();
                }else if(cur == 5){
                    let res = document.getElementsByClassName('res')[0];
                    res.classList.add('highlight');
                    this.$data.guide = 6;
                }else if(cur == 6){
                    let res = document.getElementsByClassName('res')[0];
                    res.classList.remove('highlight');
                    let mask = document.getElementById('mask');
                    mask.style.visibility = "hidden";
                    this.$data.guide = 7;
                }
            }
        }

    }
</script>

<style scoped>
@import '../css/ocr.css';

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
  transition: opacity 0.5s;
}
.question{
  margin-top: 20px;
  margin-left: 50px;
  font-size: 18px;
  min-width: 400px;
  background-color: white;
  position: relative;
}
.exec,.res{
  position: relative;
  margin-top: 30px;
  font-size: 18px;
  min-width: 400px;
  display: inline-block;
  vertical-align: top;
  height: 500px;
}
.exec{
  margin-left: 90px;
  width: 38%;
}
.middle-img{
   position: relative;
   display: inline-block;
   margin-left: 40px;
   margin-top: 30px;
   height: 500px;
}
.middle-img > img{
  position: absolute;
  top: 50%;
  transform: translate(-50%,-50%);
  width: 35px;
  height: 35px;
}
.res{
  margin-left: 40px;
  width: 45%;
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
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.25);
  border: 1px solid #ebeef5;
}
.result{
  position: relative;
  height: 500px;
  text-align: center;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
  border: 1px solid #EBEEF5
}
.result-img{
  vertical-align: center;
  max-height: 100%;
  width: 100%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
}
.result-textarea,.box-card{
  position: relative;
  width: 100%;
  height: 100%;
  font-size: 18px;
  font-weight: lighter;
  font-family: "Roboto","Helvetica",arial,sans-serif;
}
.result-textarea{
  box-sizing: border-box;
  /*border: 1.3px solid black;*/
  border-radius: 6px;
  padding: 8px 12px;
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
#optContainer{
  height: 600px;
}
.image-container{
  position: absolute;
  height: calc(95% - 80px);
  width: calc(95% - 10px);
}
.textarea-container{
  position: absolute;
  height: calc(95% - 80px);
  width: calc(95% - 10px);
}
</style>