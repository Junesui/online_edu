<template>
  <article class="col-7 fl">
    <div class="u-r-cont">
      <section>
        <div>
          <section class="c-infor-tabTitle c-tab-title">

            <a href="javascript: void(0)" title="基本资料" class="current">
              基本资料
            </a>
          </section>
        </div>

        <!-- 表格 -->
        <el-form label-width="120px">
          <el-form-item label="昵称">
            <el-input v-model="memberInfo.nickname" />
          </el-form-item>
          <el-form-item label="性别">
            <el-select v-model="memberInfo.sex" clearable placeholder="请选择">
              <!--
                数据类型一定要和取出的json中的一致，否则没法回填
                value使用动态绑定的值，性别由1 2代替
              -->
              <el-option :value="1" label="女" />
              <el-option :value="2" label="男" />
            </el-select>
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="memberInfo.mobile" />
          </el-form-item>
          <el-form-item label="年龄">
            <el-input-number v-model="memberInfo.age" :min="0" controls-position="right" />
          </el-form-item>
          <!-- 头像 -->
          <el-form-item label="头像">
              <el-upload
                         :show-file-list="true"
                         :on-success="handleAvatarSuccess"
                         :on-error="handleAvatarError"
                         :before-upload="beforeAvatarUpload"
                         class="avatar-uploader"
                         action="http://localhost:8666/eduoss/fileoss">
                  <img v-if="memberInfo.avatar" :src="memberInfo.avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"/>
              </el-upload>
          </el-form-item>
          <el-form-item label="个性签名">
            <el-input v-model="memberInfo.sign" :rows="8" type="textarea" />
            <!-- <el-input
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4}"
              placeholder="请输入内容"
              v-model="memberInfo.sign">
            </el-input> -->
          </el-form-item>

          <el-form-item>
            <el-button :disabled="saveBtnDisabled" plain="true" type="primary" @click="save">保存</el-button>
          </el-form-item>
        </el-form>

      </section>
    </div>
  </article>
</template>
<script>
  //引入调用login.js文件
  import loginApi from '@/api/login'
  //引入调用js-cookie
  import cookie from 'js-cookie'
  export default {
    data() {
      return {
        token: '',
        loginInfo: {
          id: '',
          age: '',
          avatar: '',
          mobile: '',
          nickname: '',
          sex: ''
        },
         memberInfo:{
           id: '',
           age: '',
           avatar: '',
           mobile: '',
           nickname: '',
           sex: '',
           sign:'',
         },
         saveBtnDisabled: false, //保存按钮是否禁用

      }
    },
    created() {
      //获取路径里面token值
      this.token = this.$route.query.token
      if(this.token) {//判断路径是否有token值
         this.wxLogin()
      }

      this.showInfoFromCookie()
    },

    methods: {
      save(){
        loginApi.updataMemberInfo(this.memberInfo)
          .then(response => { //修改成功
            //提示成功
            this.$message({
              type: 'success',
              message: '修改成功！ 🧙‍♂️'
            });
            this.showInfoFromCookie()
          })
      },

      //头像
      // 文件上传成功
      handleAvatarSuccess(response) {
        if (response.success) {
          this.memberInfo.avatar = response.data.url
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

      //从cookie中获取用户信息
      showInfoFromCookie() {
        //从cookie中获取用户信息
        var userStr = cookie.get("MindSchool_ucenter")
        //userStr是字符串   需要转换为json对象
        if (userStr) {
          this.loginInfo = JSON.parse(userStr)
        }
        loginApi.getMemberInfoSelf(this.loginInfo.id).then(response => {
          this.memberInfo = response.data.data.memberInfo
        })
      },

      //微信登录显示的方法
      wxLogin() {
        //把token值放到cookie里面
        cookie.set('MindSchool_token',this.token,{domain: 'localhost'})
        cookie.set('MindSchool_ucenter','',{domain: 'localhost'})
        //调用接口，根据token值获取用户信息
        loginApi.getLoginMemberInfo()
          .then(response => {
             this.loginInfo = response.data.data.userInfo
             cookie.set('MindSchool_ucenter',this.loginInfo,{domain: 'localhost'})
          })
        loginApi.getMemberInfoSelf(this.loginInfo.id).then(response => {
          this.memberInfo = response.data.data.memberInfo
          cookie.set('MindSchool_ucenter',this.memberInfo,{domain: 'localhost'})
        })
      },

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
