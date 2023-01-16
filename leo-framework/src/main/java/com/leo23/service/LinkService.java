package com.leo23.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.domain.ResponseResult;
import com.leo23.domain.dto.LinkDto;
import com.leo23.domain.entity.Link;
import com.leo23.domain.vo.PageVo;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-01-08 20:10:03
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult<PageVo> getLinkList(Integer pageNum, Integer pageSize, Link link);

    ResponseResult addLink(LinkDto linkDto);

    ResponseResult getLinkById(Long id);

    ResponseResult updateLink(Link link);

    ResponseResult deleteLinkById(Long id);
}

