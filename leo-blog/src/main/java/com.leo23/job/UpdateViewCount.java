package com.leo23.job;

import com.leo23.constants.ArticleConstants;
import com.leo23.domain.entity.Article;
import com.leo23.service.ArticleService;
import com.leo23.utils.RedisCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCount {
    @Resource
    private RedisCache redisCache;
    @Resource
    private ArticleService articleService;

    @Scheduled(cron = "0 0 * * * ?")
    public void updateArticleViewCount() {
        // 获取redis的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(ArticleConstants.ARTICLE_VIEW_COUNT);
        List<Article> articleList = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        // 更新到数据库
        articleService.updateBatchById(articleList);
    }

}
