package com.chengkun.agenthub.strategy.context;


import com.chengkun.agenthub.model.dto.UserInfoDTO;
import com.chengkun.agenthub.enums.LoginTypeEnum;
import com.chengkun.agenthub.strategy.SocialLoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SocialLoginStrategyContext {

    @Autowired
    private Map<String, SocialLoginStrategy> socialLoginStrategyMap;

    public UserInfoDTO executeLoginStrategy(String data, LoginTypeEnum loginTypeEnum) {
        // 根据 loginTypeEnum 获取配置的登录策略标识
        // 从 socialLoginStrategyMap 中根据登录策略标识获取对应的 SocialLoginStrategy 实例。
        // 调用选择的登录策略的 login 方法来执行具体的登录操作，返回包含用户信息的 UserInfoDTO 对象。
        return socialLoginStrategyMap.get(loginTypeEnum.getStrategy()).login(data);
    }

}
