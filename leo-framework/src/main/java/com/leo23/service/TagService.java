package com.leo23.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.domain.ResponseResult;
import com.leo23.domain.dto.TagListDto;
import com.leo23.domain.entity.Tag;
import com.leo23.domain.vo.PageVo;

/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-01-12 10:56:05
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult add(Tag tag);
}

