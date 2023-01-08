package com.leo23.service;

import com.leo23.domain.ResponseResult;
import com.leo23.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);
}
