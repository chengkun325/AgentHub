package com.chengkun.agenthub.service;

import com.chengkun.agenthub.model.dto.PageResultDTO;
import com.chengkun.agenthub.model.dto.UserInfoDTO;
import com.chengkun.agenthub.model.dto.UserOnlineDTO;
import com.chengkun.agenthub.entity.UserInfo;
import com.chengkun.agenthub.model.vo.*;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface UserInfoService extends IService<UserInfo> {

    void updateUserInfo(UserInfoVO userInfoVO);

    String updateUserAvatar(MultipartFile file);

    void saveUserEmail(EmailVO emailVO);

    void updateUserSubscribe(SubscribeVO subscribeVO);

    void updateUserRole(UserRoleVO userRoleVO);

    void updateUserDisable(UserDisableVO userDisableVO);

    PageResultDTO<UserOnlineDTO> listOnlineUsers(ConditionVO conditionVO);

    void removeOnlineUser(Integer userInfoId);

    UserInfoDTO getUserInfoById(Integer id);

}
