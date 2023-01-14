package com.leo23.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.domain.ResponseResult;
import com.leo23.domain.dto.AddArticleDto;
import com.leo23.domain.dto.ArticleListDto;
import com.leo23.domain.entity.Article;
import com.leo23.domain.vo.PageVo;

/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2023-01-08 09:11:39
 */
public interface ArticleService extends IService<Article> {
    // 查询热门文章
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto articleDto);

    // 根据title，summary分页查询列表
    ResponseResult<PageVo> pageList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);

    // 获取文章详情页
    ResponseResult getArticleById(Long id);

    // 根据文章id更新文章，注意tags字段
    ResponseResult updateArticle(AddArticleDto addArticleDto);
}

