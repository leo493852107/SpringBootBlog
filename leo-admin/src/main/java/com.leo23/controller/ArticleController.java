package com.leo23.controller;

import com.leo23.domain.ResponseResult;
import com.leo23.domain.dto.AddArticleDto;
import com.leo23.domain.dto.ArticleListDto;
import com.leo23.domain.vo.PageVo;
import com.leo23.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto articleDto) {
        return articleService.add(articleDto);
    }

    @GetMapping("/list")
    public ResponseResult<PageVo> pageList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        return articleService.pageList(pageNum, pageSize, articleListDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleById(@PathVariable(value = "id") Long id) {
        return articleService.getArticleById(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody AddArticleDto addArticleDto) {
        return articleService.updateArticle(addArticleDto);
    }
}
