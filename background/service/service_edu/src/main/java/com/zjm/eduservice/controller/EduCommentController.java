package com.zjm.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjm.commonutils.ordervo.UcenterMemberOrder;
import com.zjm.commonutils.result.R;
import com.zjm.commonutils.util.JwtInfo;
import com.zjm.commonutils.util.JwtUtils;
import com.zjm.eduservice.client.UcenterClient;
import com.zjm.eduservice.entity.EduComment;
import com.zjm.eduservice.service.EduCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论 前端控制器
 * @author ZengJinming
 * @since 2020-05-30
 */
@Api(description="课程评论相关")
@RestController
@RequestMapping("/eduservice/edu-comment")
@Slf4j
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;

    @Autowired
    private UcenterClient ucenterClient;

    //根据课程id查询评论列表
    @ApiOperation(value = "评论分页列表")
    @GetMapping("commentList/{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    String courseId) {
        Page<EduComment> pageParam = new Page<>(page, limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(courseId)){
            //构建条件
            wrapper.eq("course_id",courseId);
        }

        commentService.page(pageParam,wrapper);
        List<EduComment> commentList = pageParam.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return R.success().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("saveComment")
    public R save(@RequestBody EduComment comment, HttpServletRequest request) {
        JwtInfo jwtToken = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(jwtToken)) {
            return R.error().code(28004).message("请登录");
        }
        comment.setMemberId(jwtToken.getId());

        UcenterMemberOrder ucenterInfo = ucenterClient.getUserInfoOrder(jwtToken.getId());

        comment.setNickname(ucenterInfo.getNickname());
        comment.setAvatar(ucenterInfo.getAvatar());

        commentService.save(comment);
        return R.success();
    }

    @ApiOperation(value = "删除评论")
    @DeleteMapping("deleteComment/{commentId}")
    public R delete(@PathVariable String commentId, HttpServletRequest request) {
        JwtInfo jwtToken = JwtUtils.getMemberIdByJwtToken(request);
        boolean result = commentService.deleteById(commentId, jwtToken.getId());
        if(result){
            return R.success().message("删除评论成功");
        }else{
            return R.error().message("数据不存在");
        }
    }

    //判断是否是该用户的评论
    @ApiOperation(value = "判断是否是该用户的评论")
    @GetMapping("isComment/{commentId}")
    public R isComment(
            @ApiParam(name = "commentId", value = "评论id", required = true)
            @PathVariable String commentId,
            HttpServletRequest request) {

        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        boolean isComment = commentService.isComment(commentId, jwtInfo.getId());
        return R.success().data("isComment", isComment);
    }

}

