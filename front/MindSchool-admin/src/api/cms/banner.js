import request from '@/utils/request'
export default {
  //1 修改banner
  updateBanner(banner){
    return request({
      url: `/educms/admin-banner/update`,
      method: 'post',
      data: banner
    })
  },

  //2 添加banner
  addBanner(banner){
    return request({
      url: `/educms/admin-banner/addBanner`,
      method: 'post',
      data: banner
    })
  },

  //3 删除banner
  deleteBannerById(id){
    return request({
      url: `/educms/admin-banner/remove/${id}`,
      method: 'delete'
    })
  },

  //4 分页查询banner
  //current 当前页    limit 每页记录数
  getBannerList(page,limit){
    return request({
      url: `/educms/admin-banner/pageBanner/${page}/${limit}`,
      method: 'get',
    })
  },

  //5.根据课程id查询banner
  getBannerInfo(id){
    return request({
      url: `/educms/admin-banner/get/${id}`,
      method: 'get'
    })
  },
}
