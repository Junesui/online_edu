import request from '@/utils/request'
export default {
  //获取阿里云视频凭证
  getPlayAuth(id) {
      return request({
        url: `/eduvod/video/getPlayAuth/${id}`,
        method: 'get'
      })
    }

}