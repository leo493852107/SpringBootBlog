package com.leo23.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.domain.entity.ArticleTag;

import java.util.Collection;
import java.util.List;

/**
 * 文章标签关联表(ArticleTag)表服务接口
 *
 * @author makejava
 * @since 2023-01-14 10:05:05
 */
public interface ArticleTagService extends IService<ArticleTag> {

    // 更新文章的tags, 先删后增
    void updateArticleTag(Long articleId, List<Long> tagIds);

    // 根据文章id删除所有tag
    void deleteTagsByArticleId(Long articleId);
}

