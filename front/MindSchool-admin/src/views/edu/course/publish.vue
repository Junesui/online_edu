<template>

  <div class="app-container">

    <div v-if="this.$route.params && this.$route.params.id"><h2 style="text-align: center;">修改课程信息</h2></div>
    <div v-else><h2 style="text-align: center;">发布新课程</h2></div>

    <el-steps :active="3" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="发布课程"/>
    </el-steps>

    <div class="ccInfo">
      <img :src="publishCourseInfo.cover">
      <div class="main">
        <h2>{{ publishCourseInfo.title }}</h2>
        <p class="gray"><span>共{{ publishCourseInfo.lessonNum }}课时</span></p>
        <p><span>所属分类：{{ publishCourseInfo.subjectLevelOne }} — {{ publishCourseInfo.subjectLevelTwo }}</span></p>
        <p>课程讲师：{{ publishCourseInfo.teacherName }}</p>
        <h3 class="red">￥{{ publishCourseInfo.price }}</h3>
      </div>
    </div>

    <div>
      <el-button @click="previous">返回修改</el-button>
      <el-button :disabled="saveBtnDisabled" plain="true" type="primary" @click="publish">发布课程</el-button>
    </div>
  </div>
</template>

<script>
//引入调用course.js文件
import courseApi from '@/api/edu/course'
export default {
  data() {
    return {
      saveBtnDisabled: false, // 保存按钮是否禁用
      courseId:'',   //课程id  索引
      publishCourseInfo:{},  //封装所有查询出的课程信息
    }
  },

  created() {
    //获取路由里面的课程id值
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id
      //调用回显方法
      this.getCoursePublishInfo()
    //调用接口里的方法
    }
  },

  methods: {
    //根据课程id查询最终发布信息
    getCoursePublishInfo(){
      courseApi.getPublishCourseInfo(this.courseId)
        .then(response => {
          this.publishCourseInfo = response.data.publishCourse
        })
    },
    previous() {
      console.log('previous')
      this.$router.push({ path: '/course/chapter/1' })
    },

    //课程最终发布按钮
    publish() {
        this.$confirm('是否发布此课程?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          courseApi.publishCourse(this.courseId)
            .then(response => { //删除成功
              //提示信息
              this.$message({
                type: 'success',
                message: '课程发布成功！ 🤭'
              });
              //跳转课程列表页面
              this.$router.push({ path: '/course/list' })
            })
        })
          //点取消执行catch方法  用户体验角度
          //此处无需进行提示取消
          //框架在./utils/request.js封装好了提示error的方法


    }
  }
}
</script>

<style scoped>
.ccInfo {
    background: #f5f5f5;
    padding: 20px;
    overflow: hidden;
    border: 1px dashed #DDD;
    margin-bottom: 40px;
    position: relative;
}
.ccInfo img {
    background: #d6d6d6;
    width: 500px;
    height: 278px;
    display: block;
    float: left;
    border: none;
}
.ccInfo .main {
    margin-left: 520px;
}

.ccInfo .main h2 {
    font-size: 28px;
    margin-bottom: 30px;
    line-height: 1;
    font-weight: normal;
}
.ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
}

.ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
}
.ccInfo .main h3 {
    left: 540px;
    bottom: 20px;
    line-height: 1;
    font-size: 28px;
    color: #d32f24;
    font-weight: normal;
    position: absolute;
}
</style>
