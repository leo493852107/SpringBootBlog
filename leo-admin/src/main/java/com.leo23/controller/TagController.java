package com.leo23.controller;

import com.leo23.domain.ResponseResult;
import com.leo23.domain.dto.TagListDto;
import com.leo23.domain.entity.Tag;
import com.leo23.domain.vo.PageVo;
import com.leo23.domain.vo.TagVo;
import com.leo23.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Resource
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag() {
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }


    @PostMapping
    public ResponseResult add(@RequestBody Tag tag) {
        return tagService.add(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTagById(@PathVariable(value = "id") Integer id) {
        return tagService.deleteTagById(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getTagById(@PathVariable(value = "id") Integer id) {
        return tagService.getTagById(id);
    }

    @PutMapping
    public ResponseResult updateTagById(@RequestBody Tag tag) {
        return tagService.updateTagById(tag);
    }
}
