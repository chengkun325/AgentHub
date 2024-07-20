package com.chengkun.agenthub.strategy.impl;

import com.alibaba.fastjson.JSON;
import com.chengkun.agenthub.config.properties.QQConfigProperties;
import com.chengkun.agenthub.constant.SocialLoginConstant;
import com.chengkun.agenthub.model.dto.QQTokenDTO;
import com.chengkun.agenthub.model.dto.QQUserInfoDTO;
import com.chengkun.agenthub.model.dto.SocialTokenDTO;
import com.chengkun.agenthub.model.dto.SocialUserInfoDTO;
import com.chengkun.agenthub.enums.LoginTypeEnum;
import com.chengkun.agenthub.exception.BizException;
import com.chengkun.agenthub.model.vo.QQLoginVO;
import com.chengkun.agenthub.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.chengkun.agenthub.constant.SocialLoginConstant.*;
import static com.chengkun.agenthub.enums.StatusCodeEnum.QQ_LOGIN_ERROR;

@Service("qqLoginStrategyImpl")
public class QQLoginStrategyImpl extends AbstractSocialLoginStrategyImpl {

    @Autowired
    private QQConfigProperties qqConfigProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public SocialTokenDTO getSocialToken(String data) {
        // 将传入的 JSON 数据 data 解析为 QQLoginVO 对象，包含了 QQ 登录所需的 OpenID 和 Access Token。
        QQLoginVO qqLoginVO = JSON.parseObject(data, QQLoginVO.class);
        // 调用 checkQQToken(qqLoginVO) 方法验证并检查 QQ 的 Access Token 是否有效。
        checkQQToken(qqLoginVO);
        // 构建并返回 SocialTokenDTO 对象，包含了 OpenID、Access Token 和登录类型。
        return SocialTokenDTO.builder()
                .openId(qqLoginVO.getOpenId())
                .accessToken(qqLoginVO.getAccessToken())
                .loginType(LoginTypeEnum.QQ.getType())
                .build();
    }

    @Override
    public SocialUserInfoDTO getSocialUserInfo(SocialTokenDTO socialTokenDTO) {
        // 构建一个包含 OpenID、Access Token 和 AppId 的表单数据 formData。
        Map<String, String> formData = new HashMap<>(3);
        formData.put(QQ_OPEN_ID, socialTokenDTO.getOpenId());
        formData.put(ACCESS_TOKEN, socialTokenDTO.getAccessToken());
        formData.put(OAUTH_CONSUMER_KEY, qqConfigProperties.getAppId());
        // 使用 RestTemplate 发送 GET 请求到 QQ 的用户信息获取 URL，并将返回的 JSON 数据解析为 QQUserInfoDTO 对象。
        QQUserInfoDTO qqUserInfoDTO = JSON.parseObject(restTemplate.getForObject(qqConfigProperties.getUserInfoUrl(), String.class, formData), QQUserInfoDTO.class);
        // 将 QQUserInfoDTO 中的昵称和头像信息封装到 SocialUserInfoDTO 对象中，并返回。
        return SocialUserInfoDTO.builder()
                .nickname(Objects.requireNonNull(qqUserInfoDTO).getNickname())
                .avatar(qqUserInfoDTO.getFigureurl_qq_1())
                .build();
    }

    private void checkQQToken(QQLoginVO qqLoginVO) {
        // 构建一个包含 Access Token 的参数 qqData。
        Map<String, String> qqData = new HashMap<>(1);
        qqData.put(SocialLoginConstant.ACCESS_TOKEN, qqLoginVO.getAccessToken());
        try {
            // 使用 RestTemplate 发送 GET 请求到 QQ 的 Token 验证 URL，并解析返回的 JSON 数据为 QQTokenDTO 对象。
            String result = restTemplate.getForObject(qqConfigProperties.getCheckTokenUrl(), String.class, qqData);
            QQTokenDTO qqTokenDTO = JSON.parseObject(CommonUtil.getBracketsContent(Objects.requireNonNull(result)), QQTokenDTO.class);
            // 检查返回的 OpenID 是否与登录时获取的 OpenID 匹配，如果不匹配则抛出业务异常。
            if (!qqLoginVO.getOpenId().equals(qqTokenDTO.getOpenid())) {
                throw new BizException(QQ_LOGIN_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(QQ_LOGIN_ERROR);
        }
    }

}
