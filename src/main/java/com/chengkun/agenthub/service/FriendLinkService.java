package com.chengkun.agenthub.service;

import com.chengkun.agenthub.model.dto.FriendLinkAdminDTO;
import com.chengkun.agenthub.model.dto.FriendLinkDTO;
import com.chengkun.agenthub.entity.FriendLink;
import com.chengkun.agenthub.model.vo.ConditionVO;
import com.chengkun.agenthub.model.vo.FriendLinkVO;
import com.chengkun.agenthub.model.dto.PageResultDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FriendLinkService extends IService<FriendLink> {

    List<FriendLinkDTO> listFriendLinks();

    PageResultDTO<FriendLinkAdminDTO> listFriendLinksAdmin(ConditionVO conditionVO);

    void saveOrUpdateFriendLink(FriendLinkVO friendLinkVO);

}
