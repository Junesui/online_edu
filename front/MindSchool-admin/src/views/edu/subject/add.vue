<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download" />
          <a :href="'/static/course.xlsx'">点击下载模版</a>
        </el-tag>

      </el-form-item>

      <el-form-item label="选择Excel">  <!-- upload唯一标识  -->
        <el-upload ref="upload"     
                   :auto-upload="false"  
                   :on-success="fileUploadSuccess" 
                   :on-error="fileUploadError" 
                   :disabled="importBtnDisabled" 
                   :limit="1" 
                   :action="BASE_API+'/eduservice/edu-subject/addSubject'"
                   name="file" 
                   accept="application/vnd.ms-excel"> <!-- 设置上传格式 微软excel -->
          <el-button plain="true" slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button :loading="loading" plain="true" style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        BASE_API: process.env.BASE_API, // 接口API地址
        //OSS_PATH: process.env.OSS_PATH, // 阿里云OSS地址  提供模板  后面再改 p100
        importBtnDisabled: false, // 按钮是否禁用,
        loading: false
      }
    },
    created() {

    },
    methods: {
      //点击上传按钮  上传excel文件到接口种
      submitUpload() {
        this.importBtnDisabled = true
        this.loading = true
        this.$refs.upload.submit()
      },

      //上传成功
      fileUploadSuccess(response) {
        //提示信息
        this.loading = false
        this.$message({
          type: 'success',
          message: '导入文件成功'
        })
        //跳转到课程分类列表中去
        //路由跳转
        this.$router.push({path:'/subject/list'})
      },

      //上传失败
      fileUploadError() {
        this.loading = false
        this.$message({
          type: 'error',
          message: '导入文件失败'
        })
      },

    }
  }
</script>
