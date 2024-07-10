package com.chengkun.agenthub.service;

import com.chengkun.agenthub.model.dto.RoleDTO;
import com.chengkun.agenthub.model.dto.UserRoleDTO;
import com.chengkun.agenthub.entity.Role;
import com.chengkun.agenthub.model.vo.ConditionVO;
import com.chengkun.agenthub.model.dto.PageResultDTO;
import com.chengkun.agenthub.model.vo.RoleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<UserRoleDTO> listUserRoles();

    PageResultDTO<RoleDTO> listRoles(ConditionVO conditionVO);

    void saveOrUpdateRole(RoleVO roleVO);

    void deleteRoles(List<Integer> ids);

}
