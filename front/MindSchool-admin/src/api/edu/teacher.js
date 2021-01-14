import request from '@/utils/request'
export default{
  //1.讲师列表-条件查询分页
  //current 当前页    limit 每页记录数    teacherQuery 条件对象
  getTeacherListPage(current,limit,teacherQuery){
    return request({
      url: `/eduservice/edu-teacher/pageTeacherCondition/${current}/${limit}`,
      method: 'post',
      //teacherQuery条件对象，后端使用RequestBody获取数据
      //data表示吧对象转换为json进行传递到接口里面
      data: teacherQuery
    })
  },
  //删除讲师
  deleteTeacherById(id){
    return request({
      url: `/eduservice/edu-teacher/${id}`,
      method: 'delete'
    })
  },
  //添加讲师
  addTeacher(teacher){
    return request({
      url: `/eduservice/edu-teacher/addTeacher`,
      method: 'post',
      data: teacher
    })
  },
  //根据id查询讲师  数据回显
  getTeacherInfo(id){
    return request({
      url: `/eduservice/edu-teacher/getTeacher/${id}`,
      method: 'get'
    })
  },
  //修改讲师
  updateTeacher(teacher){
    return request({
      url: `/eduservice/edu-teacher/updateTeacher`,
      method: 'post',
      data: teacher
    })
  },
  //查询所有讲师
  listTeacher(){
    return request({
      url: `/eduservice/edu-teacher/findAll`,
      method: 'get',
    })
  },
}
