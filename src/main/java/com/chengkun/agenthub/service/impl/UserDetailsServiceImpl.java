package com.chengkun.agenthub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chengkun.agenthub.mapper.SysMenuMapper;
import com.chengkun.agenthub.mapper.SysUserMapper;
import com.chengkun.agenthub.pojo.SysUser;
import com.chengkun.agenthub.pojo.dto.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.chengkun.agenthub.constants.Constants.INCORRECT_USERNAME_OR_PASSWORD;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName, userName);//方法引用
        SysUser user = sysUserMapper.selectOne(wrapper);
        //如果查询不到数据就通过抛出异常来给出提示
        if (Objects.isNull(user)) {
            throw new RuntimeException(INCORRECT_USERNAME_OR_PASSWORD);
        }
        //根据用户查询权限信息 添加到LoginUser中

        //封装成UserDetails对象返回
        List<String> list = menuMapper.selectPermsByUserId(user.getUserId());
        return new LoginUser(user, list);
    }
}