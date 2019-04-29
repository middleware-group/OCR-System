<template>
  <div class="contact">
    <top-menu exit-able="false"></top-menu>
    <div class="main">
      <div class="member-container">
        <div class="member-card">
          <!--<el-row :gutter="100" class="organization">-->
            <!--<el-col :span="8" v-for="o in organization" :key="o.name">-->
              <!--<el-card shadow="always" v-bind:class="o.visible">-->
                <!--<p class="member-text">组织名称：middleware-group</p>-->
                <!--<p class="member-text">GitHub：</p>-->
                <!--<p class="member-text">邮箱：{{o.email}}</p>-->
              <!--</el-card>-->
            <!--</el-col>-->
          <!--</el-row>-->
          <el-row :gutter="100">
            <el-col :span="8" v-for="member in members" :key="member.name">
              <el-card shadow="always">
                <p class="member-text">姓名：{{member.name}}</p>
                <p class="member-text">性别：{{member.sex}}</p>
                <p class="member-text">GitHub：{{member.github}}</p>
                <p class="member-text">邮箱：{{member.email}}</p>
              </el-card>
            </el-col>
          </el-row>
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
        data(){
            return{
                members:[
                    {
                        name:"谭源杰",
                        sex:"男",
                        email:"413145873@qq.com",
                        github:"TanYJie"
                    },
                    {
                        name:"梁佳音",
                        sex:"女",
                        email:"2807513230@qq.com",
                        github:"greengreenlemon"
                    },
                    {
                        name:"杨雨涵",
                        sex:"男",
                        email:"631971262@qq.com",
                        github:"YYhyang"
                    },
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
  background: url("../assets/5.jpg")0 0 no-repeat ;
  background-size: cover;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}
.member-container{
  width: 90%;
  margin: 200px auto 0 auto;
  font-weight: lighter;
  font-family: "Roboto","Helvetica",arial,sans-serif;
}
.member-card{
  text-align: left;
  opacity: 0.9;
}
.member-text{
  margin-left: 35px;
  width: fit-content;
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
</style>