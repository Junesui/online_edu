import request from '@/utils/request'
export default {
  //1.添加课程信息
  addCourseInfo(courseInfo) {
    return request({
      url: `/eduservice/edu-course/addCourseInfo`,
      method: 'post',
      data: courseInfo
    })
  },
  //2.查询所有的讲师
  getListTeacher() {
    return request({
      url: `/eduservice/edu-teacher/findAll`,
      method: 'get',
    })
  },
  //3.根据课程id查询课程基本信息
  getCourseInfoById(courseId) {
    return request({
      url: `/eduservice/edu-course/getCourseInfo/${courseId}`,
      method: 'get',
    })
  },
  //4.修改课程信息
  updateCourseInfo(courseInfo) {
    return request({
      url: `/eduservice/edu-course/updateCourseInfo`,
      method: 'post',
      data: courseInfo
    })
  },
  //5.发布课程第三步：课程信息显示 进行确认
  getPublishCourseInfo(id) {
    return request({
      url: `/eduservice/edu-course/getPublishCourseInfo/${id}`,
      method: 'get',
    })
  },
  //6.发布课程第三步：课程最终发布
  publishCourse(id) {
    return request({
      url: `/eduservice/edu-course/publishCourse/${id}`,
      method: 'post',
    })
  },

  //7.讲师列表-条件查询分页
  //current 当前页 limit 每页记录数 courseQuery 条件对象
  getCourseListPage(current,limit,courseQuery){
    return request({
      url: `/eduservice/edu-course/pageCourseCondition/${current}/${limit}`,
      method: 'post',
      //teacherQuery条件对象，后端使用RequestBody获取数据
      //data表示吧对象转换为json进行传递到接口里面
      data: courseQuery
    })
  },

  //8.删除课程
  deleteCourseById(courseId){
    return request({
      url: `/eduservice/edu-course/deleteCourseById/${courseId}`,
      method: 'delete'
    })
  },
}
