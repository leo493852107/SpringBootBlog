package com.leo23.controller;

import com.leo23.domain.ResponseResult;
import com.leo23.domain.dto.LinkDto;
import com.leo23.domain.entity.Link;
import com.leo23.domain.vo.PageVo;
import com.leo23.service.LinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Resource
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult<PageVo> getLinkList(Integer pageNum, Integer pageSize, Link link) {
        return linkService.getLinkList(pageNum, pageSize, link);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody LinkDto linkDto) {
        return linkService.addLink(linkDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable("id") Long id) {
        return linkService.getLinkById(id);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link) {
        return linkService.updateLink(link);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLinkById(@PathVariable("id") Long id) {
        return linkService.deleteLinkById(id);
    }
}
