import axios from 'axios'
//引入调用js-cookie
import cookie from 'js-cookie'
import {
  MessageBox,
  Message
} from 'element-ui'

// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:8666', // api的base_url
  timeout: 20000 // 请求超时时间
})

//创建拦截器 获取token 传递token到cookie中
service.interceptors.request.use(
  config => {
    //判断cookie里面是否有MindSchool_token数据
    if (cookie.get('MindSchool_token')) {
      //把获取到的token放入cookie
      config.headers['token'] = cookie.get('MindSchool_token');
    }
    return config
  },
  err => {
    return Promise.reject(err);
  })

// http response 拦截器
service.interceptors.response.use(
  response => {
    if (response.data.code == 28004) {
      console.log("response.data.resultCode是28004")
      // 返回 错误代码-1 清除ticket信息并跳转到登录页面
      window.location.href = "/login"
      return
    } else {
      if (response.data.code !== 20000) {
        //25000：订单支付中，不做任何提示
        if (response.data.code != 25000) {
          Message({
            message: response.data.message || 'error',
            type: 'error',
            duration: 5 * 1000
          })
        }
      } else {
        return response;
      }
    }
  },
  error => {
    return Promise.reject(error.response) // 返回接口返回的错误信息
  });

export default service
