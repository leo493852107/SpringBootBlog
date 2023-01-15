package com.leo23.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo23.domain.ResponseResult;
import com.leo23.domain.dto.UserDto;
import com.leo23.domain.entity.UserRole;
import com.leo23.domain.vo.PageVo;
import com.leo23.domain.vo.UserInfoVo;
import com.leo23.domain.vo.UserVo;
import com.leo23.enums.AppHttpCodeEnum;
import com.leo23.exception.SystemException;
import com.leo23.mapper.UserMapper;
import com.leo23.domain.entity.User;
import com.leo23.service.UserRoleService;
import com.leo23.service.UserService;
import com.leo23.utils.BeanCopyUtils;
import com.leo23.utils.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-01-10 15:57:15
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRoleService userRoleService;

    @Override
    public ResponseResult userInfo() {
        // 获取用户id
        Long userId = SecurityUtils.getUserId();
        // 根据id查用户信息
        User user = getById(userId);
        // 封装UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        // 对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        // 对数据进行是否存在的判断
        if (userNameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nickNameExist(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        // 密码加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        save(user);
        return ResponseResult.okResult();
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, userName);
        return count(wrapper) > 0;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getNickName, nickName);
        return count(wrapper) > 0;
    }

    @Override
    public ResponseResult<PageVo> getUserList(Integer pageNum, Integer pageSize, User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(user.getUserName()), User::getUserName, user.getUserName());
        wrapper.like(StringUtils.hasText(user.getPhonenumber()), User::getPhonenumber, user.getPhonenumber());
        wrapper.eq(StringUtils.hasText(user.getStatus()), User::getStatus, user.getStatus());
        // 分页查询
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        List<User> users = page.getRecords();
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(users, UserVo.class);
        return ResponseResult.okResult(new PageVo(userVos, page.getTotal()));
    }

    @Override
    public ResponseResult addUser(UserDto userDto) {
        // 保存用户信息到user表
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        // 判断 用户名/手机号/邮箱 不能为空，是否存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (!(StringUtils.hasText(user.getUserName()) && StringUtils.hasText(user.getPhonenumber()) && StringUtils.hasText(user.getEmail()))) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_PHONE_EMAIL_NULL);
        }
        if (count(wrapper.eq(User::getUserName, user.getUserName())) > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (count(wrapper.eq(User::getPhonenumber, user.getPhonenumber())) > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (count(wrapper.eq(User::getEmail, user.getEmail())) > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_EXIST);
        }
        // 密码加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        save(user);
        // 保存用户的角色信息到user_role表
        for (String roleId : userDto.getRoleIds()) {
            userRoleService.save(new UserRole(user.getId(), Long.parseLong(roleId)));
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUserById(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}

