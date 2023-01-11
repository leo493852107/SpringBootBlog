package com.leo23.runner;

import com.leo23.constants.ArticleConstants;
import com.leo23.domain.entity.Article;
import com.leo23.mapper.ArticleMapper;
import com.leo23.utils.RedisCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 查询博客信息 id viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> articleViewCount = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        // 存储到redis
        redisCache.setCacheMap(ArticleConstants.ARTICLE_VIEW_COUNT, articleViewCount);

    }
}
