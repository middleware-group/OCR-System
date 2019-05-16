<template>
  <div class="first">
    <top-menu></top-menu>

    <el-container class="main-container">
      <el-aside style="width: 220px;text-align:left;">
        <side-bar active="1" style="height: 100%"></side-bar>
      </el-aside>

      <el-main class="main-container-el">
        <div class="breadcrumb">
          <el-breadcrumb separator-class="el-icon-arrow-right" style="font-size: 18px">
            <el-breadcrumb-item></el-breadcrumb-item>
            <el-breadcrumb-item>题目要求</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="question">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          以波士顿房价数据或其他开放数据集（可选单维或多维）为例，
          选择传统的机器学习算法，例如随机森林、AR 模型等或者深度学习模型 CNN、LSTM、GRU 等模型进行拟合，
          准确率要求达到 80% 以上。
        </div>
        <div class="breadcrumb">
          <el-breadcrumb separator-class="el-icon-arrow-right" style="font-size: 18px">
            <el-breadcrumb-item></el-breadcrumb-item>
            <el-breadcrumb-item>实验结果</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="exec" v-loading="isLoading">
          <div class="play" v-show="!isPlay">
            <img src="../assets/start.png" @click="play"/>
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
            <div class="result-pagination">
              <el-pagination
                      :current-page="page"
                      @current-change="changeClick"
                      background
                      layout="prev, pager, next"
                      :total="50">
              </el-pagination>
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
        name: "first",
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
            changeClick: function(index){
                this.$data.active = {
                    value: this.$data.res[index-1].value,
                    file: this.$data.res[index-1].file
                };
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
  background-color: rgb(240, 249, 235)
}
.main-container-el{
  opacity: 0.1;
  transition: opacity 2s;
}
.question{
  margin-top:20px;
  margin-left: 30px;
  font-size: 18px;
  width: 90%;
  min-width: 400px;
}
.exec{
  margin-top:20px;
  margin-left: 30px;
  font-size: 18px;
  width: 90%;
  min-width: 400px;
}
.breadcrumb{
  margin-top: 30px;
  margin-left: 10px;
  width: 400px;
}
.play{
  position: relative;
  background: rgb(233, 233, 235);
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
.result-pagination{
  margin-top: 30px;
  display: inline-block;
}
.result-text{
  margin: 20px 0 30px 0;
  font-size: 18px;
  text-align: left;
}
</style>