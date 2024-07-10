package com.chengkun.agenthub.strategy;


import com.chengkun.agenthub.model.dto.UserInfoDTO;

public interface SocialLoginStrategy {

    UserInfoDTO login(String data);

}
