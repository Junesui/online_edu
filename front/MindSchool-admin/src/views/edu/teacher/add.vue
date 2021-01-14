<template>
  <div class="app-container">
    <div style="font-size: 19PX; font-weight: 600; margin-bottom: 20px; margin-left: 20px;">
      <div v-if="this.$route.params && this.$route.params.id">修改讲师</div>
      <div v-else="">添加讲师</div>
    </div>

    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" min="0" />
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            value使用动态绑定的值，讲师头衔由1 2代替
          -->
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" />
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea" />
      </el-form-item>

      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
          <el-upload
                     :show-file-list="true"
                     :on-success="handleAvatarSuccess"
                     :on-error="handleAvatarError"
                     :before-upload="beforeAvatarUpload"
                     class="avatar-uploader"
                     :action="BASE_API+'/eduoss/fileoss'">
              <img v-if="teacher.avatar" :src="teacher.avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"/>
          </el-upload>
      </el-form-item>
      <span style="margin-left: 7%;font-size: 15px; font-weight: 400;">*点击图片框修改头像*</span>
      <br /><br /><br />
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" plain="true" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  //引入调用teacher.js文件
  import teacherApi from '@/api/edu/teacher'
  //引入上传头像的功能组件
  import ImageCropper from '@/components/ImageCropper'
  import PanThumb from '@/components/PanThumb'

  export default {
    components: {ImageCropper,PanThumb}, //组件的声明
    data() {
      return {
        teacher: {
          name:'',
          sort:0,
          level:1,
          career:'',
          intro:'',
          avatar:'https://edu-zjm.oss-cn-beijing.aliyuncs.com/2020/04/18/%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg',
        }, //v-model双向绑定
        imagecropperShow: false, //上传弹框组件是否显示
        imagecropperKey: 0, //上传组件唯一标识
        BASE_API: process.env.BASE_API, //获取dev.env.js里面地址
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
      // 文件上传成功
      handleAvatarSuccess(response) {
        if (response.success) {
          this.teacher.avatar = response.data.url
          // 强制重新渲染
          this.$forceUpdate()
        } else {
          this.$message.error('上传失败! （非20000）')
        }
      },

      // 文件上传失败（http）
      handleAvatarError() {
        this.$message.error('上传失败! （http失败）')
      },

      // 上传校验
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt3M = file.size / 1024 / 1024 < 3

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt3M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt3M
      },

      init(){
        //判断路径有id值  修改操作
        if (this.$route.params && this.$route.params.id) {
          const id = this.$route.params.id
          this.getInfo(id)
        }else{ //判断路径没有id值  添加操作
          //清空表单即清空teacher
          this.teacher = {
          name:'',
          sort:'',
          level:'',
          career:'',
          intro:'',
          avatar:'https://edu-zjm.oss-cn-beijing.aliyuncs.com/2020/04/18/%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg',
          }//v-model双向绑定
        }
      },
      //根据讲师id查到讲师信息 回显操作
      getInfo(id) {
        teacherApi.getTeacherInfo(id)
          .then(response => {
            this.teacher = response.data.teacher
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
        //判断修改或添加 teacher是否有id
        if(!this.teacher.id){
          //添加
          this.addTeacher()
        }else{
          //修改
          this.updateTeacher()
        }
      },

      //添加讲师的方法
      addTeacher() {
        teacherApi.addTeacher(this.teacher)
          .then(response => { //添加成功
            //提示成功
            this.$message({
              type: 'success',
              message: '添加成功！ 😄'
            });
            //回到讲师列表页面
            //vue路由跳转
            this.$router.push({
              path: '/teacher/table'
            })
          })
      },

      //修改讲师的方法
      updateTeacher() {
        teacherApi.updateTeacher(this.teacher)
          .then(response => { //修改成功
            //提示成功
            this.$message({
              type: 'success',
              message: '修改成功！ 🧙‍♂️'
            });
            //回到讲师列表页面
            //vue路由跳转
            this.$router.push({
              path: '/teacher/table'
            })
          })
      }
    }
  }
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar-uploader img {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
