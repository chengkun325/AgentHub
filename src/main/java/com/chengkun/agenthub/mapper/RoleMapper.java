package com.chengkun.agenthub.mapper;

import com.chengkun.agenthub.model.dto.ResourceRoleDTO;
import com.chengkun.agenthub.model.dto.RoleDTO;
import com.chengkun.agenthub.entity.Role;
import com.chengkun.agenthub.model.vo.ConditionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role> {

    List<ResourceRoleDTO> listResourceRoles();

    List<String> listRolesByUserInfoId(@Param("userInfoId") Integer userInfoId);

    List<RoleDTO> listRoles(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);

}
