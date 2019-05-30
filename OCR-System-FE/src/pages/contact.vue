<template>
  <div class="contact">
    <top-menu exit-able="false" style="text-align: left"></top-menu>
    <div class="main">
      <div class="member-container">
        <div class="member-card">
          <div v-for="member in members" :key="member.name" class="card">
            <el-card shadow="always">
              <p class="member-text">
                <span>姓名：{{member.name}}</span>
                <span style="margin-left: 45px">性别：{{member.sex}}</span>
              </p>
              <p class="member-text">学号：{{member.email}}</p>
              <p class="member-text">主要负责：{{member.job}}</p>
            </el-card>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
    import TopMenu from "../components/top-menu";
    export default {
        name: "contact",
        components: {TopMenu},
        mounted(){
            function addStyle(element,key,value,time){
                return new Promise(resolve => {
                    setTimeout(()=>{
                        element.style[key] = value;
                        resolve();
                    },time);
                })
            }
            (async function(){
                let memberCard = document.getElementsByClassName('card');
                let time = 0;
                for(let card of memberCard){
                    await addStyle(card,'opacity','0.9',time);
                    time += 300;
                }
            }());

        },
        data(){
            return{
                members:[
                    {
                        name:"谭源杰",
                        sex:"男",
                        email:"24320162202906",
                        job:"系统前端架构及编码"
                    },
                    {
                        name:"梁佳音",
                        sex:"女",
                        email:"24320162202863",
                        job:"图像预处理及OCR识别"
                    },
                    {
                        name:"杨雨涵",
                        sex:"男",
                        email:"24320162202943",
                        job:"系统后端架构及OCR识别"
                    }
                ],
                organization:[
                    {
                        visible:"hidden"
                    },
                    {
                        visible:"visible"
                    },
                    {
                        visible:"hidden"
                    },
                ]

            }
        }
    }
</script>

<style scoped>
.contact{
  text-align: center;
  background: url("../assets/5.jpg")0 0 no-repeat ;
}
.main{
  left: 0;
  margin: 0;
  padding: 0;
  background: url("../assets/4.jpg")0 0 no-repeat ;
  background-size: cover;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}
.member-container{
  width: 100%;
  margin: 300px auto 0 50px;
}
.member-card{
  text-align: center;
}
.member-text{
  font-size: 17px;
  margin-left: 20px;
  width: fit-content;
  text-align: left;
}
.organization{
  margin-bottom: 50px;
}
.visible{
  visibility: visible;
}
.hidden{
  visibility: hidden;
}
.card{
  display: inline-block;
  width: 400px;
  padding: 0 40px 0 0;
  margin: 0 50px 50px 0;
  opacity: 0.1;
  transition: opacity 2.5s;
}
</style>