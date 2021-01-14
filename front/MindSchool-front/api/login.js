import request from '@/utils/request'
export default {
  //根据token获取用户信息
  getLoginMemberInfo() {
    return request({
      url: `/eduucenter/ucenter-member/getMemberInfo`,
      method: 'get'
    })
  },

  //登录
  loginMember(memberInfo) {
    return request({
      url: `/eduucenter/ucenter-member/login`,
      method: 'post',
      data: memberInfo
    })
  },

  //根据id获取用户信息
  getMemberInfo(id) {
    return request({
      url: `/eduucenter/ucenter-member/getUserInfoOrder/${id}`,
      method: 'post'
    })
  },

  //根据id获取用户信息  （个人中心用）
  getMemberInfoSelf(id) {
    return request({
      url: `/eduucenter/ucenter-member/getUserInfo/${id}`,
      method: 'post'
    })
  },

  //用户信息修改功能
  updataMemberInfo(ucenterMember) {
    return request({
      url: `/eduucenter/ucenter-member/updateMember`,
      method: 'post',
      data: ucenterMember
    })
  },

  //修改密码
  ChangePassword(formItem) {
    return request({
      url: `/eduucenter/ucenter-member/change`,
      method: 'post',
      data: formItem
    })
  },
}
