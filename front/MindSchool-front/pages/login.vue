<template>
  <div class="main">
    <div class="title">
      <a class="active" href="/login">登录</a>
      <span>·</span>
      <a href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <el-form ref="userForm" :model="user">

        <el-form-item class="input-prepend restyle" prop="mobile" :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' },{validator: checkPhone, trigger: 'blur'}]">
          <div>
            <el-input type="text" placeholder="手机号" v-model="user.mobile" />
            <i class="iconfont icon-phone" />
          </div>
        </el-form-item>

        <el-form-item class="input-prepend" prop="password" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <div>
            <el-input type="password" placeholder="密码" v-model="user.password" />
            <i class="iconfont icon-password" />
          </div>
        </el-form-item>

        <div class="btn">
          <input type="button" class="sign-in-button" value="登录" @click="submitLogin()">
        </div>
      </el-form>
      <!-- 更多登录方式 -->
      <div class="more-sign">
        <h6>社交帐号登录</h6>
        <ul>
          <li><a id="weixin" class="weixin" target="_blank" href="https://open.weixin.qq.com/connect/qrconnect?appid=wxed9954c01bb89b47&redirect_uri=http%3A%2F%2Fguli.shop%2Fapi%2Fucenter%2Fwx%2Fcallback&response_type=code&scope=snsapi_login&state=zjm#wechat_redirect"><i
                class="iconfont icon-weixin" /></a></li>
          <li><a id="qq" class="qq" target="_blank" href="#"><i class="iconfont icon-qq" /></a></li>
        </ul>
      </div>
    </div>

  </div>
</template>

<script>
  import '~/assets/css/sign.css'
  import '~/assets/css/iconfont.css'

  //引入调用js-cookie
  import cookie from 'js-cookie'
  //引入调用login.js文件
  import loginApi from '@/api/login'
  export default {
    layout: 'sign',

    data() {
      return {
        //封装登录的手机号和密码对象
        user: {
          mobile: '',
          password: ''
        },
        //获取到用户信息  用于显示头部
        loginInfo: {}
      }
    },

    methods: {
      //登录的方法
      submitLogin() {
        //调用登录接口 返回token字符串
        loginApi.loginMember(this.user)
          .then(response => {
            //获取到的token字符串放入cookie
            //1.cookie名称，2.token参数值，3.作用范围-在什么样的请求中
            cookie.set("MindSchool_token",response.data.data.token,{domain:'localhost'})
            //调用接口 根据token解析出用户信息 给首页用
            loginApi.getLoginMemberInfo()
              .then(response => {
                this.loginInfo = response.data.data.userInfo
                //获取返回的用户信息  放入cookie
                cookie.set("MindSchool_ucenter",this.loginInfo,{domain:'localhost'})
                // 路由跳转 跳转守页面
                this.$router.push({path: '/'})
              })
          })
      },

      checkPhone(rule, value, callback) {
        //debugger
        if (!(/^1[34578]\d{9}$/.test(value))) {
          return callback(new Error('手机号码格式不正确'))
        }
        return callback()
      }
    }
  }
</script>
<style>
  .el-form-item__error {
    z-index: 9999999;
  }
</style>
