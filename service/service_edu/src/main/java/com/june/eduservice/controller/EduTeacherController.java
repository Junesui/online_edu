package com.june.eduservice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.june.eduservice.entity.EduTeacher;
import com.june.eduservice.service.EduTeacherService;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author June
 * @since 2021-01-05
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

	@Autowired
	private EduTeacherService teacherService;
	
	
	@GetMapping("findAll")
	public List<EduTeacher> findAllTeacher() {
		List<EduTeacher> list = teacherService.list(null);
		return list;
	}
	
	
	
}





