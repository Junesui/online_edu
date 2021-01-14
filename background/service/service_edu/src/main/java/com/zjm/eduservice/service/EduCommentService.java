package com.zjm.eduservice.service;

import com.zjm.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 评论 服务类
 * @author ZengJinming
 * @since 2020-05-30
 */
public interface EduCommentService extends IService<EduComment> {

    //删除评论
    boolean deleteById(String commentId, String memberId);

    //判断是否是该用户的评论
    boolean isComment(String commentId, String memberId);
}
