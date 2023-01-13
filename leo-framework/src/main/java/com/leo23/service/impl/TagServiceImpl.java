package com.leo23.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo23.domain.ResponseResult;
import com.leo23.domain.dto.TagListDto;
import com.leo23.domain.vo.PageVo;
import com.leo23.mapper.TagMapper;
import com.leo23.domain.entity.Tag;
import com.leo23.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-01-12 10:56:05
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        // 分页查询
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        wrapper.like(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());
        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        // 封装数据
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult add(Tag tag) {
        baseMapper.insert(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTagById(Integer id) {
        baseMapper.deleteById(id);
        return ResponseResult.okResult();
    }
}

