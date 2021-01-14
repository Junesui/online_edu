
import request from '@/utils/request'
export default {
  //条件分页课程查询的方法
  getCourseList(page, limit, courseObj) {
    return request({
      url: `/eduservice/coursefront/getFrontCourseList/${page}/${limit}`,
      method: 'post',
      data: courseObj
    })
  },
  
  //查询所有分类的方法
  getAllSubject() {
    return request({
      url: '/eduservice/edu-subject/getAllSubject',
      method: 'get'
    })
  },
  
  //课程详情的方法
  getCourseInfo(courseId) {
    return request({
      url: `/eduservice/coursefront/getFrontCourseInfo/${courseId}`,
      method: 'get'
    })
  }
}
