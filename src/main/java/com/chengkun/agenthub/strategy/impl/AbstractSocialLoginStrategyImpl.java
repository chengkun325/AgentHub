package com.chengkun.agenthub.strategy.impl;

import com.chengkun.agenthub.model.dto.SocialTokenDTO;
import com.chengkun.agenthub.model.dto.SocialUserInfoDTO;
import com.chengkun.agenthub.model.dto.UserDetailsDTO;
import com.chengkun.agenthub.model.dto.UserInfoDTO;
import com.chengkun.agenthub.entity.UserAuth;
import com.chengkun.agenthub.entity.UserInfo;
import com.chengkun.agenthub.entity.UserRole;
import com.chengkun.agenthub.enums.RoleEnum;
import com.chengkun.agenthub.exception.BizException;
import com.chengkun.agenthub.mapper.UserAuthMapper;
import com.chengkun.agenthub.mapper.UserInfoMapper;
import com.chengkun.agenthub.mapper.UserRoleMapper;
import com.chengkun.agenthub.service.TokenService;
import com.chengkun.agenthub.service.impl.UserDetailServiceImpl;
import com.chengkun.agenthub.strategy.SocialLoginStrategy;
import com.chengkun.agenthub.util.BeanCopyUtil;
import com.chengkun.agenthub.util.IpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.chengkun.agenthub.constant.CommonConstant.TRUE;

@Service
public abstract class AbstractSocialLoginStrategyImpl implements SocialLoginStrategy {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private TokenService tokenService;

    @Resource
    private HttpServletRequest request;

    @Override
    public UserInfoDTO login(String data) {
        UserDetailsDTO userDetailsDTO;
        // 获取社交登录的令牌信息 socialToken。
        SocialTokenDTO socialToken = getSocialToken(data);
        String ipAddress = IpUtil.getIpAddress(request);
        String ipSource = IpUtil.getIpSource(ipAddress);
        // 方法根据令牌信息查找用户认证信息。
        UserAuth user = getUserAuth(socialToken);
        // 找到用户
        if (Objects.nonNull(user)) {
            userDetailsDTO = getUserDetail(user, ipAddress, ipSource);
        } else {// 未找到
            userDetailsDTO = saveUserDetail(socialToken, ipAddress, ipSource);
        }
        // 检查用户是否被禁用，若被禁用则抛出异常。
        if (userDetailsDTO.getIsDisable().equals(TRUE)) {
            throw new BizException("用户帐号已被锁定");
        }
        // 创建身份验证令牌 UsernamePasswordAuthenticationToken 并设置到安全上下文 SecurityContextHolder 中。
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetailsDTO, null, userDetailsDTO.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        // 复制用户详细信息到 UserInfoDTO 中，并生成用户的访问令牌。
        UserInfoDTO userInfoDTO = BeanCopyUtil.copyObject(userDetailsDTO, UserInfoDTO.class);
        String token = tokenService.createToken(userDetailsDTO);
        userInfoDTO.setToken(token);
        return userInfoDTO;
    }

    public abstract SocialTokenDTO getSocialToken(String data);

    public abstract SocialUserInfoDTO getSocialUserInfo(SocialTokenDTO socialTokenDTO);

    private UserAuth getUserAuth(SocialTokenDTO socialTokenDTO) {
        return userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getUsername, socialTokenDTO.getOpenId())
                .eq(UserAuth::getLoginType, socialTokenDTO.getLoginType()));
    }

    private UserDetailsDTO getUserDetail(UserAuth user, String ipAddress, String ipSource) {
        userAuthMapper.update(new UserAuth(), new LambdaUpdateWrapper<UserAuth>()
                .set(UserAuth::getLastLoginTime, LocalDateTime.now())
                .set(UserAuth::getIpAddress, ipAddress)
                .set(UserAuth::getIpSource, ipSource)
                .eq(UserAuth::getId, user.getId()));
        return userDetailService.convertUserDetail(user, request);
    }

    private UserDetailsDTO saveUserDetail(SocialTokenDTO socialToken, String ipAddress, String ipSource) {
        SocialUserInfoDTO socialUserInfo = getSocialUserInfo(socialToken);
        UserInfo userInfo = UserInfo.builder()
                .nickname(socialUserInfo.getNickname())
                .avatar(socialUserInfo.getAvatar())
                .build();
        userInfoMapper.insert(userInfo);
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username(socialToken.getOpenId())
                .password(socialToken.getAccessToken())
                .loginType(socialToken.getLoginType())
                .lastLoginTime(LocalDateTime.now())
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .build();
        userAuthMapper.insert(userAuth);
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleMapper.insert(userRole);
        return userDetailService.convertUserDetail(userAuth, request);
    }

}
