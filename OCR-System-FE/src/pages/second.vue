<template>
  <div class="second">
    <top-menu></top-menu>

    <el-container class="main-container">
      <el-aside style="width: 220px;text-align:left;">
        <side-bar active="2" style="height: 100%"></side-bar>
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
          物流配送计划算法，相同的单一起点和终点的 VRP 问题，即组织适当的行车路线，
          使得由配送中心派出若干辆物流配送车，向若干个客户提供货物的路程最短。
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
              配送车数量：<span style="color: #67C23A">{{active.carNumber}}</span> <br><br>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              配送总距离：<span style="color: #67C23A">{{active.distance}}</span>
            </div>
            <div class="result-canvas" v-for="show in showList" v-show="show">
              <canvas id="tutorial" class="can" width="600" height="600"></canvas>
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

    <img src="../assets/大厦.png" id="building" style="display: none"/>
    <img src="../assets/car.png" id="car" style="display: none"/>
  </div>
</template>

<script>
    import TopMenu from "../components/top-menu";
    import SideBar from "../components/side-bar";
    export default {
        name: "second",
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
                page:1,
                showList: [],
                currentIndex: 0,
                color:[
                    '#1c86d1',
                    '#67C23A',
                    '#F56C6C',
                    '#FF9933',
                    '#FF3399'
                ]
            }
        },
        methods:{
            drawLine:function(ctx, startX, startY, endX, endY, index){
                let car = document.getElementById('car');
                let _this = this;
                return new Promise((resolve)=>{
                    let rotate = (endY - startY)/Math.abs(endX - startX);  //计算角度
                    let up = (endY - startY) > 0;
                    let right = (endX - startX) > 0;
                    let distance = Math.floor(Math.sqrt(Math.pow((endX-startX),2) + Math.pow((endY-endY),2))); //勾股定理计算距离
                    let time = distance / 20;
                    function rowLine(){
                        ctx.clearRect(startX-12.5,startY-12.5,30,30); // 清除车
                        right ? startX++ : startX--;
                        startY += rotate;
                        ctx.drawImage(car,startX-12.5,startY-12.5);  // 绘制车

                        _this.initDraw(); //防止覆盖，重绘
                        if(index === _this.$data.currentIndex){
                            if(up && right){
                                if(startX < endX && startY < endY){
                                    setTimeout(rowLine,time);
                                }else{
                                    resolve();
                                }
                            }else if(up && !right){
                                if(startX > endX && startY < endY){
                                    setTimeout(rowLine,time);
                                }else{
                                    resolve();
                                }
                            }else if(!up && right){
                                if(startX < endX && startY > endY){
                                    setTimeout(rowLine,time);
                                }else{
                                    resolve();
                                }
                            }else if(!up && !right){
                                if(startX > endX && startY > endY){
                                    setTimeout(rowLine,time);
                                }else{
                                    resolve();
                                }
                            }
                        }
                    }
                    setTimeout(rowLine,16);
                });
            },

            initDraw: function(){
                let _this = this;
                //var canvas = document.getElementById('tutorial');
                var canvas = document.getElementsByClassName('can')[_this.$data.currentIndex];
                var ctx = canvas.getContext('2d');
                let data = _this.$data.active.data;
                let color = _this.$data.color;
                let x_array = data.x;
                let y_array = data.y;
                let x_start = data.x[0];
                let y_start = data.y[0];

                // 画起点
                let startImg = document.getElementById('building');
                ctx.drawImage(startImg,x_start,y_start);

                // 画点
                data.path.forEach((route, routeIndex)=>{
                    route.forEach((point, pointIndex)=>{
                        let x = x_array[point];
                        let y = y_array[point];
                        ctx.beginPath();
                        ctx.strokeStyle = color[routeIndex];
                        ctx.arc(x,y,4,0,2*Math.PI,true);
                        ctx.fillStyle = color[routeIndex];
                        ctx.fill();
                        ctx.stroke();
                        ctx.closePath();
                    });
                });

                // 画线
                data.path.forEach((route, routeIndex)=>{
                    ctx.beginPath();
                    ctx.moveTo(x_start,y_start);
                    route.forEach((point, pointIndex)=>{
                        let x = x_array[point];
                        let y = y_array[point];
                        ctx.lineTo(x,y);
                        ctx.strokeStyle = color[routeIndex];
                        ctx.stroke();
                    });
                    ctx.closePath();
                });
            },

            draw: function(){
                let _this = this;
                _this.$nextTick(function () {
                    let canvasList = document.getElementsByClassName('can');
                    let canvas = canvasList.item(_this.$data.currentIndex);
                    var ctx = canvas.getContext('2d');
                    let data = _this.$data.active.data;
                    let color = _this.$data.color;
                    let x_array = data.x;
                    let y_array = data.y;
                    let x_start = data.x[0];
                    let y_start = data.y[0];

                    // 画起点
                    let startImg = document.getElementById('building');
                    ctx.drawImage(startImg,x_start,y_start);

                    // 画点
                    data.path.forEach((route, routeIndex)=>{
                        route.forEach((point, pointIndex)=>{
                            let x = x_array[point];
                            let y = y_array[point];
                            ctx.beginPath();
                            ctx.strokeStyle = color[routeIndex];
                            ctx.arc(x,y,4,0,2*Math.PI,true);
                            ctx.fillStyle = color[routeIndex];
                            ctx.fill();
                            ctx.stroke();
                            ctx.closePath();
                        });
                    });

                    // 画线
                    data.path.forEach((route, routeIndex)=>{
                        ctx.beginPath();
                        ctx.moveTo(x_start,y_start);
                        route.forEach((point, pointIndex)=>{
                            let x = x_array[point];
                            let y = y_array[point];
                            ctx.lineTo(x,y);
                            ctx.strokeStyle = color[routeIndex];
                            ctx.stroke();
                        });
                        ctx.closePath();
                    });

                    // 流光
                    data.path.forEach((route, routeIndex)=>{
                        (async function(){
                            var iindex = _this.$data.currentIndex;
                            for(let i=0; i<route.length-1; i++){
                                let start = route[i];
                                let end = route[i+1];
                                await _this.drawLine(ctx, x_array[start], y_array[start], x_array[end], y_array[end], iindex);
                            }
                        })();
                    });
                });

            },

            play: function(){
                let _this = this;
                _this.$data.isPlay = true;
                _this.$data.isLoading = true;
                this.exec();
            },

            clear: function(){
                let _this = this;
                let canvasList = document.getElementsByClassName('can');
                let canvas = canvasList.item(_this.$data.currentIndex);
                let ctx = canvas.getContext('2d');
                ctx.clearRect(0,0,canvas.width,canvas.height);
            },

            changeClick: function(index){
                let _this = this;
                this.$data.active = {
                    carNumber: this.$data.res[index-1].num,
                    distance: this.$data.res[index-1].distance,
                    data: this.$data.res[index-1]
                };
                _this.clear();
                _this.$data.currentIndex = index - 1;
                _this.$data.showList.forEach((val,showIndex)=>{
                    if(showIndex !== index-1){
                        _this.$data.showList[showIndex] = false;
                    }
                    else{
                        _this.$data.showList[showIndex] = true;
                    }
                });
                _this.clear();
                this.draw();
            },

            exec: function(){
                let _this=this;
                this.$axios({
                    method:'get',
                    url:'/second',
                }).then(function (response) {
                    let data = response.data;
                    _this.$data.showList.push(true);
                    for(let i=1; i<data.length; i++){
                        _this.$data.showList.push(false);
                    }
                    data.forEach((res)=>{
                        res.x.forEach((val, index)=>{
                            res.x[index] = val * 30;
                        });
                        res.y.forEach((val, index)=>{
                            res.y[index] = val * 30;
                        });
                    });
                    _this.$data.res = data;
                    _this.$data.active = {
                        carNumber: data[0].num,
                        distance: data[0].distance,
                        data: data[0]
                    };
                    _this.draw();
                    _this.$data.isLoading = false;
                }).catch(function (error) {
                    console.log(error)
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
  height: 830px;
  text-align: center;
}
.result-canvas{
  display: block;
  width: 900px;
  margin: auto;
  border: 1px solid black;
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