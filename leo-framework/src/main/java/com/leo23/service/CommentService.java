package com.leo23.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.domain.ResponseResult;
import com.leo23.domain.entity.Comment;

/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-01-10 09:26:27
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentService(Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

