import request from '@/utils/request'
export default {
  getBannerList() {
    return request({
      url: `/educms/front-banner/getAllBanner`,
      method: 'get'
    })
  },
}