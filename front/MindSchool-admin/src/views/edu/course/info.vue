<template>

  <div class="app-container">


    <div v-if="this.$route.params && this.$route.params.id"><h2 style="text-align: center;">修改课程信息</h2></div>
    <div v-else><h2 style="text-align: center;">发布新课程</h2></div>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>

    <el-form label-width="120px">

      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title"
                  placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写" />
      </el-form-item>

      <!-- 课程分类 -->
      <el-form-item label="课程分类">
        <el-select v-model="courseInfo.subjectParentId" placeholder="一级分类" @change="subjectLevelOneChanged">
          <el-option v-for="subject in subjectOneList" :key="subject.id" :label="subject.title" :value="subject.id" />
        </el-select>

        <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
          <el-option v-for="subject in subjectTwoList" :key="subject.id" :label="subject.title" :value="subject.id" />
        </el-select>
      </el-form-item>

      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select v-model="courseInfo.teacherId" placeholder="请选择讲师">
          <el-option v-for="teacher in teacherList" :key="teacher.id" :label="teacher.name" :value="teacher.id" />
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数" />
      </el-form-item>

      <!-- 课程简介-->
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description" />
      </el-form-item>

      <!-- 课程封面-->
      <el-form-item label="课程封面">
        <el-upload :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload"
          :action="BASE_API+'/eduoss/fileoss'" class="avatar-uploader">
          <img :src="courseInfo.cover">
        </el-upload>

      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元" /> 元
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" plain="true" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
      </el-form-item>

    </el-form>


  </div>
</template>
<script>
  //引入调用course.js文件
  import courseApi from '@/api/edu/course'
  //引入调用subject.js文件
  import subjectApi from '@/api/edu/subject'
  //引入Tinymce富文本编辑器
  import Tinymce from '@/components/Tinymce'
  export default {
    components: {
      Tinymce
    }, //声明组件

    data() {
      return {
        courseInfo: {
          title: '',
          subjectId: '', //二级分类id
          subjectParentId: '', //一级分类id
          teacherId: '',
          lessonNum: 0,
          description: '',
          cover: '/static/fengmian.png',
          price: 0
        },
        courseId:'',   //回显需要的id
        saveBtnDisabled: false, // 保存按钮是否禁用
        teacherList: [], //数组封装所有讲师信息
        subjectOneList: [], //数组封装所有一级分类信息
        subjectTwoList: [], //数组封装所有二级分类信息
        BASE_API: process.env.BASE_API, // 接口API地址

      }
    },

    created() {
      //回显：获取路由中的id值
      if (this.$route.params && this.$route.params.id) {
        this.courseId = this.$route.params.id
        //调用回显方法
        this.getInfo()
      }else{
        //初始化 讲师方法 分类方法
        this.getListTeacher()
        this.getOneSubject()
        this.init()
      }
    },

    watch: {  //vue的监听
        $route(to, from) {  //路由变化方式，路由一发生变化 就执行方法
          this.init()
        }
      },

    methods: {
      //回显： 根据课程id查询信息进行回显
      getInfo(){
        courseApi.getCourseInfoById(this.courseId)
          .then(response => {
            this.courseInfo = response.data.courseInfoVo
            //1查询所有分类  包含一级二级
            subjectApi.getSubjectList()
              .then(response => {
                //2获取所有的一级分类
                this.subjectOneList = response.data.list
                //3遍历一级分类数组，比较当前一级courseInfo里的id是否相同
                for(var i = 0;i<this.subjectOneList.length;i++){
                  //获取每个一级分类
                  var oneSubject = this.subjectOneList[i]
                  //比较当前一级分类id和courseInfo里的id是否相同
                  if(this.courseInfo.subjectParentId == oneSubject.id){
                    //获取一级分类下的二级分类
                    this.subjectTwoList = oneSubject.children
                  }
                }
              })
              //初始化 讲师
              this.getListTeacher()
          })
      },

      //bug：点击发布课程不刷新  初始化方法
      init(){
        //判断路径有id值  修改操作
        if (this.$route.params && this.$route.params.id) {
          const id = this.$route.params.id
          this.getInfo(id)
        }else{ //判断路径没有id值  添加操作
          //清空表单即清空teacher
          this.courseInfo = {title: '',
          subjectId: '', //二级分类id
          subjectParentId: '', //一级分类id
          teacherId: '',
          lessonNum: 0,
          description: '',
          cover: '/static/fengmian.png',
          price: 0}
        }
      },

      //查询所有一级分类 form-select
      getOneSubject() {
        subjectApi.getSubjectList()
          .then(response => {
            this.subjectOneList = response.data.list
          })
      },

      //点击一级分类触发显示对应二级分类 两级联动
      subjectLevelOneChanged(value) {
        //value：一级分类id值
        for (var i = 0; i < this.subjectOneList.length; i++) {
          //得到每个一级分类
          var oneSubject = this.subjectOneList[i]
          //判断所有一级分类id和点击一级分类是否一样
          if (value === oneSubject.id) {
            //从一级分类里获取其下的二级分类
            this.subjectTwoList = oneSubject.children
            //选择其他一级分类时  清空当前所选二级分类
            this.courseInfo.subjectId = ''
          }
        }
      },

      //上传封面成功调用方法
      handleAvatarSuccess(response, file) {
        this.courseInfo.cover = response.data.url
      },

      //上传之前调用方法 规定文件类型大小等条件
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2

        if (!isJPG) {
          this.$message.error('上传图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
      },

      //查询所有讲师 form-select
      getListTeacher() {
        courseApi.getListTeacher()
          .then(response => {
            this.teacherList = response.data.items
          })
      },

      //添加课程
      addCourse(){
        courseApi.addCourseInfo(this.courseInfo)
          .then(response => {
            //提示
            this.$message({
              type: 'success',
              message: '填写课程信息成功 ✌'
            })
            //跳转到下一步
            this.$router.push({
              path: '/course/chapter/' + response.data.courseId
            })
          })
      },

      //修改课程
      updateCourse(){
        courseApi.updateCourseInfo(this.courseInfo)
        .then(response => {
          //提示
          this.$message({
            type: 'success',
            message: '修改课程信息成功 ✌'
          })
          //跳转到下一步 修改的id路由中已经传过了
          this.$router.push({
            path: '/course/chapter/' + this.courseId
          })
        })
      },

      //下一步按钮方法
      saveOrUpdate() {
        //判断添加课程 or 修改课程
        if(!this.courseInfo.id){
          //无id --> 添加
          this.addCourse()
        }else{
          this.updateCourse()
        }
      },

    }
  }
</script>

<style scoped>
  .tinymce-container {
    line-height: 29px;
  }
</style>
