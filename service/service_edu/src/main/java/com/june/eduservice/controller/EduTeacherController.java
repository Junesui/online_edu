package com.june.eduservice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.june.commonutils.R;
import com.june.eduservice.entity.EduTeacher;
import com.june.eduservice.service.EduTeacherService;
import com.june.eduservice.vo.TeacherQuery;
import com.june.servicebase.exceptionhandler.CustomizeException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author June
 * @since 2021-01-05
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

	@Autowired
	private EduTeacherService teacherService;
	
	/**
	 * 查找所有讲师
	 * @return
	 */
	@ApiOperation(value = "查询所有讲师")
	@GetMapping("findAll")
	public R findAllTeacher() {
		List<EduTeacher> list = teacherService.list(null);
		return R.ok().data("items", list);
	}
	
	@ApiOperation(value = "根据id逻辑删除讲师")
	@DeleteMapping("{id}")
	public R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true)  @PathVariable String id) {
		boolean flag = teacherService.removeById(id);
		if (flag) {
			return R.ok();
		} else {
			return R.error();
		}
	}
	
	@ApiOperation(value = "分页查询讲师")
	@GetMapping("pageTeacher/{current}/{size}")
	public R pageTeacher(@PathVariable Long current, @PathVariable Long size) {
		Page<EduTeacher> pageTeacher = new Page<>(current, size);
		teacherService.pageMaps(pageTeacher, null);
		Long total = pageTeacher.getTotal();
		List<EduTeacher> rows = pageTeacher.getRecords();
		return R.ok().data("total",total).data("rows",rows);
	}
	
	@ApiOperation(value = "条件分页查询讲师")
	@PostMapping("pageTeacherCondition/{current}/{size}")
	public R pageTeacherCondition(@PathVariable Long current, 
								  @PathVariable Long size,
							      @RequestBody(required = false) TeacherQuery teacherQuery) {
		Page<EduTeacher> pageTeacher = new Page<>(current,size);
		QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
		String name = teacherQuery.getName();
		Integer level = teacherQuery.getLevel();
		String begin = teacherQuery.getBegin();
		String end = teacherQuery.getEnd();
		if (!StringUtils.isEmpty(name)) {
			wrapper.like("name", name);
		}
		if (!StringUtils.isEmpty(level)) {
			wrapper.eq("level", level);
		}
		if (!StringUtils.isEmpty(begin)) {
			wrapper.gt("gmt_create", begin);
		}
		if (!StringUtils.isEmpty(end)) {
			wrapper.lt("gmt_modified", begin);
		}
		
		teacherService.pageMaps(pageTeacher, wrapper);
		Long total = pageTeacher.getTotal();
		List<EduTeacher> rows = pageTeacher.getRecords();
		return R.ok().data("total",total).data("rows",rows);
	}
	
	@ApiOperation(value = "添加讲师")
	@PostMapping("addTeacher")
	public R addTeacher(@RequestBody EduTeacher eduTeacher) {
		boolean save = teacherService.save(eduTeacher);
		if (save) {
			return R.ok();
		} else {
			return R.error();
		}
	}
	
	@ApiOperation(value = "通过ID查询讲师")
	@GetMapping("getTeacher/{id}")
	public R getTeacher(@PathVariable String id) {
		EduTeacher eduTeacher = teacherService.getById(id);
		return R.ok().data("teacher", eduTeacher);
	}
	
	@ApiOperation(value = "通过ID修改讲师")
	@PostMapping("updateTeacher")
	public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
		boolean update = teacherService.updateById(eduTeacher);
		if (update) {
			return R.ok();
		} else {
			return R.error();
		}
	} 
	
}





