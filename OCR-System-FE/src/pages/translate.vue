<template>
  <div class="translate">
    <top-menu :active='topMenuActive'></top-menu>
    <el-container class="main-container">
      <el-main class="main-container-el">
        <div id="optContainer">
          <div class="aims">
            <div class="option-header">
              <span style="margin-left: 30px">选择语言：</span>
              <el-radio-group v-model="aimsLanguage">
                <el-radio-button :label="language.value" :key="language.key" v-for="language in languages"></el-radio-button>
              </el-radio-group>
              <div class="textarea-container">
                <textarea class="result-textarea" v-model="aimsText"></textarea>
              </div>
            </div>
          </div>
          <div class="middle-img">
            <img src="../assets/箭头.png"/>
          </div>
          <div class="result">
            <div class="option-header">
              <span style="margin-left: 30px">选择语言：</span>
              <el-radio-group v-model="resultLanguage">
                <el-radio-button :label="language.value" :key="language.key" v-for="language in languages"></el-radio-button>
              </el-radio-group>
              <div class="textarea-container" v-loading="resultLoading">
                <textarea class="result-textarea" disabled v-model="resultText"></textarea>
              </div>
            </div>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
    import TopMenu from "../components/top-menu";
    import {MD5} from "../util/md5"
    export default {
        name: "ocr",
        components: {
            TopMenu
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
        watch: {
            aimsText(value) {
                let _this = this;
                clearTimeout(_this.$data.timeId);
                _this.$data.timeId = setTimeout(()=>{
                    let aimsLanguage = _this.$data.languageList[_this.$data.aimsLanguage];
                    let resultLanguage = _this.$data.languageList[_this.$data.resultLanguage];
                    let appid = '20190519000299262',
                        key = '45vN5caCtkXTTvHpiWv2',
                        salt = (new Date).getTime(),
                        query = value,
                        from = aimsLanguage,
                        to = resultLanguage;
                    let str1 = appid + query + salt +key;
                    let sign = MD5(str1);
                    $.ajax({
                        url: 'http://api.fanyi.baidu.com/api/trans/vip/translate',
                        type: 'get',
                        dataType: 'jsonp',
                        data: {
                            q: query,
                            appid: appid,
                            salt: salt,
                            from: from,
                            to: to,
                            sign: sign
                        },
                        success: function (data) {
                            if(data.error_msg){
                                _this.$data.resultText = "";
                                _this.$data.resultLoading = false;
                                return;
                            }
                            let res = "";
                            data.trans_result.forEach((val)=>{
                                res = res + val.dst + "\n";
                            });
                            _this.$data.resultText = res;
                        }
                    });
                },1000);
            },
            aimsLanguage(){
                this.translate(this.$data.aimsText);
            },
            resultLanguage(){
                this.translate(this.$data.aimsText);
            }
        },
        data(){
            return{
                topMenuActive: 1,
                languages: [{
                    key: 0,
                    value: "中文",
                },{
                    key: 1,
                    value: "英语",
                },{
                    key: 2,
                    value: "日语",
                },{
                    key: 3,
                    value: "德语",
                },{
                    key: 4,
                    value: "法语",
                }],
                aimsLanguage: "中文",
                resultLanguage: "中文",

                languageList: {
                    "中文": "zh",
                    "英语": "en",
                    "日语": "jp",
                    "德语": "de",
                    "法语": "fra",
                },
                aimsText: "",
                resultText: "",
                timeId: null,
                resultLoading: false,
            }
        },
        methods:{
            translate: function (value) {
                let _this = this;
                _this.$data.resultLoading = true;
                let aimsLanguage = _this.$data.languageList[_this.$data.aimsLanguage];
                let resultLanguage = _this.$data.languageList[_this.$data.resultLanguage];
                let appid = '20190519000299262',
                    key = '45vN5caCtkXTTvHpiWv2',
                    salt = (new Date).getTime(),
                    query = value,
                    from = aimsLanguage,
                    to = resultLanguage;
                let str1 = appid + query + salt +key;
                let sign = MD5(str1);
                $.ajax({
                    url: 'http://api.fanyi.baidu.com/api/trans/vip/translate',
                    type: 'get',
                    dataType: 'jsonp',
                    data: {
                        q: query,
                        appid: appid,
                        salt: salt,
                        from: from,
                        to: to,
                        sign: sign
                    },
                    success: function (data) {
                        if(data.error_msg){
                            _this.$data.resultText = "";
                            _this.$data.resultLoading = false;
                            return;
                        }
                        let res = "";
                        data.trans_result.forEach((val)=>{
                            res = res + val.dst + "\n";
                        });
                        _this.$data.resultText = res;
                        _this.$data.resultLoading = false;
                    },
                    error: function () {
                        _this.showMsg("Sorry , 翻译出错啦","error")
                    }
                });
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
}
.main-container-el{
  opacity: 0.1;
  transition: opacity 2s;
}
.aims,.result{
  position: relative;
  height: 500px;
  display: inline-block;
  width: 43%;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.25);
  border: 1px solid #ebeef5;
  vertical-align: top;
  margin-left: 40px;
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
  width: 40px;
  height: 40px;
  top: 50%;
  transform: translate(-50%,-50%);
}
#optContainer{
  width: 100%;
  height: 600px;
  margin-top: 50px;
  margin-left: -10px;
  text-align: center;
  z-index: 1;
  background-color: white;
}
.option-header{
  margin-top: 30px;
  text-align: left;
}
.textarea-container{
  position: absolute;
  width: 100%;
  height: calc(90% - 80px);
  margin: 30px 0 0 30px;
}
.result-textarea{
  position: relative;
  width: 90%;
  height: 100%;
  font-size: 18px;
  font-weight: lighter;
  font-family: "Roboto","Helvetica",arial,sans-serif;
  box-sizing: border-box;
  /*border: 1.3px solid black;*/
  border-radius: 6px;
  padding: 8px 12px;
}
textarea:disabled{
  background-color: white;
}
</style>