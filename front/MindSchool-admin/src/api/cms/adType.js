// @ 符号在build/webpack.base.conf.js 中配置 表示 'src' 路径
import request from '@/utils/request'

export default {

  //所有推荐类别列表
  list() {
    return request({
      url: '/educms/admin-cms/ad-type/list',
      method: 'get'
    })
  },
  
  //推荐类别分页列表
  pageList(page, limit) {
    return request({
      url: `/educms/admin-cms/ad-type/list/${page}/${limit}`,
      method: 'get'
    })
  },
  
  //根据ID删除推荐类别
  removeById(id) {
    return request({
      url: `/educms/admin-cms/ad-type/remove/${id}`,
      method: 'delete'
    })
  },
  
  //新增推荐类别
  save(adType) {
    return request({
      url: '/educms/admin-cms/ad-type/save',
      method: 'post',
      data: adType
    })
  },
  
  //根据id获取推荐类别信息
  getById(id) {
    return request({
      url: `/educms/admin-cms/ad-type/get/${id}`,
      method: 'get'
    })
  },
  
  //更新推荐类别
  updateById(adType) {
    return request({
      url: '/educms/admin-cms/ad-type/update',
      method: 'put',
      data: adType
    })
  }
}
