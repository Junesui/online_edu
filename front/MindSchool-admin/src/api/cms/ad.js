// @ 符号在build/webpack.base.conf.js 中配置 表示 'src' 路径
import request from '@/utils/request'

export default {
  //推荐分页列表
  pageList(page, limit) {
    return request({
      url: `/educms/admin-cms/ad/list/${page}/${limit}`,
      method: 'get'
    })
  },
  
  //根据ID删除推荐
  removeById(id) {
    return request({
      url: `/educms/admin-cms/ad/remove/${id}`,
      method: 'delete'
    })
  },
  
  //新增推荐
  save(ad) {
    return request({
      url: '/educms/admin-cms/ad/save',
      method: 'post',
      data: ad
    })
  },
  
  //根据id获取推荐信息
  getById(id) {
    return request({
      url: `/educms/admin-cms/ad/get/${id}`,
      method: 'get'
    })
  },
  
  //更新推荐
  updateById(ad) {
    return request({
      url: '/educms/admin-cms/ad/update',
      method: 'put',
      data: ad
    })
  }
}
