<template>
  <div class="app-container">
    <!-- 添加banner -->
    <div align="center" style="line-height: 60px; font-weight: 700; font-size: 22px; color: #a85a11;">添加Banner</div>
    <br />
    <el-form label-width="120px">
      <el-form-item label="标题">
        <el-input v-model="banner.title" />
      </el-form-item>
      <el-form-item label="排序">
        <el-input-number v-model="banner.sort" controls-position="right" min="0" />
      </el-form-item>
      <el-form-item label="图片地址">
        <el-input v-model="banner.imageUrl" />
      </el-form-item>
      <el-form-item label="链接地址">
        <el-input v-model="banner.linkUrl" />
      </el-form-item>


      <el-form-item>
        <el-button id="banneradd" :disabled="saveBtnDisabled" type="primary" plain="true" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  //引入调用banner.js文件
  import bannerApi from '@/api/cms/banner.js'

  export default {
    data() {
      return {
        banner: {
          title:'',
          sort:0,
          imageUrl:'',
          linkUrl:'',
        }, //v-model双向绑定
        saveBtnDisabled: false //保存按钮是否禁用
      }
    },
    created() { //页面渲染前执行
      this.init()
    },
    watch: {  //vue的监听
        $route(to, from) {  //路由变化方式，路由一发生变化 就执行方法
          this.init()
        }
      },
    methods: {
      init(){
        //判断路径有id值  修改操作
        if (this.$route.params && this.$route.params.id) {
          const id = this.$route.params.id
          this.getInfo(id)
        }else{ //判断路径没有id值  添加操作
          //清空表单即清空banner
          this.banner = {}
        }
      },
      //根据id查到banner信息 回显操作
      getInfo(id) {
        bannerApi.getBannerInfo(id)
          .then(response => {
            this.banner = response.data.item
          })
          .catch((response) => {
            this.$message({
              type: 'error',
              message: '获取数据失败'
            })
          })
      },

      //保存按钮调用的方法
      saveOrUpdate() {
        //判断修改或添加 banner是否有id
        if(!this.banner.id){
          //添加
          this.addBanner()
        }else{
          //修改
          this.updateBanner()
        }
      },

      //添加Banner的方法
      addBanner() {
        bannerApi.addBanner(this.banner)
          .then(response => { //添加成功
            //提示成功
            this.$message({
              type: 'success',
              message: '添加成功！🙋‍♂️'
            });
            //回到讲师列表页面
            //vue路由跳转
            this.$router.push({
              path: '/banner/list'
            })
          })
      },

      //修改Banner的方法
      updateBanner() {
        bannerApi.updateBanner(this.banner)
          .then(response => { //修改成功
            //提示成功
            this.$message({
              type: 'success',
              message: '修改成功！🌎️'
            });
            //回到讲师列表页面
            //vue路由跳转
            this.$router.push({
              path: '/banner/list'
            })
          })
      }
    }
  }
</script>

<!-- <style>
  .el-button {
    background-color: #75cca4;
    border-color: #75cca4;
    color: #c9fae3;

  }

</style>
 -->
