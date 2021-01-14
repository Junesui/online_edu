<template>
  <div class="in-wrap">
    <!-- 公共头引入 -->
    <header id="header">
      <section class="container">
        <h1 id="logo">
          <a href="#" title="MindSchool">
            <img src="~/assets/img/logo.png" width="100%" alt="MindSchool">
          </a>
        </h1>
        <div class="h-r-nsl" style="font-weight: 700;">
          <ul class="nav">
            <router-link to="/" tag="li" active-class="current" exact>
              <a>首页</a>
            </router-link>
            <router-link to="/course" tag="li" active-class="current">
              <a>课程</a>
            </router-link>
            <router-link to="/teacher" tag="li" active-class="current">
              <a>名师</a>
            </router-link>
            <router-link to="/practice" tag="li" active-class="current">
              <a>练习</a>
            </router-link>
            <router-link to="/qa" tag="li" active-class="current">
              <a>问答</a>
            </router-link>
          </ul>
          <!-- / nav -->
          <ul class="h-r-login">
            <li v-if="!loginInfo.id" id="no-login">
              <a href="/login" title="登录">
                <em class="icon18 login-icon">&nbsp;</em>
                <span class="vam ml5">登录</span>
              </a>
              |
              <a href="/register" title="注册">
                <span class="vam ml5">注册</span>
              </a>
            </li>
            <li v-if="loginInfo.id" id="is-login-one" class="mr10">
              <a id="headerMsgCountId" href="#" title="消息">
                <em class="icon18 news-icon">&nbsp;</em>
              </a>
              <q class="red-point" style="display: none">&nbsp;</q>
            </li>
            <li v-if="loginInfo.id" id="is-login-two" class="h-r-user">
              <a href="/ucenter" title>
                <img :src="loginInfo.avatar" width="30" height="30" class="vam picImg" alt>
                <span id="userName" class="vam disIb">{{ loginInfo.nickname }}</span>
              </a>
              <a href="javascript:void(0);" title="退出" @click="logout()" class="ml5">退出</a>
            </li>
            <!-- /未登录显示第1 li；登录后显示第2，3 li -->
          </ul>
          <aside class="h-r-search">
            <form action="#" method="post">
              <label class="h-r-s-box">
                <input type="text" placeholder="输入你想学的课程" name="queryCourse.courseName" value>
                <button type="submit" class="s-btn" @click="search()">
                  <em class="icon18">&nbsp;</em>
                </button>
              </label>
            </form>
          </aside>
        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
    </header>
    <!-- /公共头引入 -->

    <nuxt />

    <!-- 公共底引入 -->
    <footer id="footer">
      <section class="container">
        <div class>
          <h4 class="hLh30">
            <span class="fsize18 f-fM c-fff">友情链接</span>
          </h4>
          <ul class="of flink-list">
            <li>
              <a href="http://www.jxnu.edu.cn/" title="江西师范大学" target="_blank">江西师范大学</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
        <div class="b-foot">

          <section class="fl col-7">
            <section class="mr20">
              <section class="b-f-link">
                <a href="#" title="关于我们" target="_blank">关于我们</a>|
                <a href="https://jwc.jxnu.edu.cn/Portal/Index.aspx" title="联系我们" target="_blank">联系我们</a>|
                <a href="#" title="帮助中心" target="_blank">帮助中心</a>|
                <a href="http://jxnu.fanya.chaoxing.com/portal" title="资源下载" target="_blank">资源下载</a>|
                <span>服务热线：13065115690(江西) 18650341991(福建)</span>
                <span>Email：2285532987@qq.com</span>
              </section>
              <section class="b-f-link mt10">
                <span>©2020课程版权均归MindSchool所有 京ICP备xxxxxxxx号</span>
              </section>
            </section>
          </section>

          <aside class="fl col-3 tac mt15">
            <section class="gf-tx">
              <span>
                <a href="https://edu-zjm.oss-cn-beijing.aliyuncs.com/Resume/QR%20Code/wx.jpg"><img src="~/assets/img/wx-icon.png" alt></a>
              </span>
            </section>
            <section class="gf-tx">
              <span>
                <a href="https://weibo.com/u/5483300641"><img src="~/assets/img/wb-icon.png" alt></a>
              </span>
            </section>
          </aside>
          <div class="clear"></div>
        </div>
      </section>
    </footer>
    <!-- /公共底引入 -->
  </div>
</template>
<script>
  import '~/assets/css/reset.css'
  import '~/assets/css/theme.css'
  import '~/assets/css/global.css'
  import '~/assets/css/web.css'
  import '~/assets/css/base.css'
  import '~/assets/css/activity_tab.css'
  import '~/assets/css/bottom_rec.css'
  import '~/assets/css/nice_select.css'
  import '~/assets/css/order.css'
  import '~/assets/css/swiper-3.3.1.min.css'
  import "~/assets/css/pages-weixinpay.css"

  //引入调用js-cookie
  import cookie from 'js-cookie'
  //引入调用login.js文件
  import loginApi from '@/api/login'
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
      //从cookie中获取用户信息
      showInfoFromCookie() {
        //从cookie中获取用户信息
        var userStr = cookie.get("MindSchool_ucenter")
        //userStr是字符串   需要转换为json对象
        if (userStr) {
          this.loginInfo = JSON.parse(userStr)
        }
      },

      //搜索
      search(){
        this.$router.push({path:'/course/'})
      },

      //退出  cookie清空
      logout(){
        cookie.set('MindSchool_ucenter', "", {domain: 'localhost'})
        cookie.set('MindSchool_token', "", {domain: 'localhost'})
        //回首页
        window.location.href = "/"
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
      },
    }
  };
</script>

<style>
  #footer{
    background-image: url(../assets/img/v-play-bg.jpg);
  }
  #font1{
    color: #fff;
  }
</style>
