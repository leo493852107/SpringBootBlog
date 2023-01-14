package com.leo23.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo23.domain.entity.Article;
import com.leo23.mapper.ArticleTagMapper;
import com.leo23.domain.entity.ArticleTag;
import com.leo23.service.ArticleService;
import com.leo23.service.ArticleTagService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-01-14 10:05:05
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Override
    public void updateArticleTag(Long articleId, List<Long> tagIds) {
        // 先删除所有article对应的tag
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId, articleId);
        remove(wrapper);
        // 增加article的tag
        for (Long tagId : tagIds) {
            baseMapper.insert(new ArticleTag(articleId, tagId));
        }
    }
}

