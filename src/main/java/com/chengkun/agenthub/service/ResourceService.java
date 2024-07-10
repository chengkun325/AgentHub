package com.chengkun.agenthub.service;

import com.chengkun.agenthub.model.dto.LabelOptionDTO;
import com.chengkun.agenthub.model.dto.ResourceDTO;
import com.chengkun.agenthub.entity.Resource;
import com.chengkun.agenthub.model.vo.ConditionVO;
import com.chengkun.agenthub.model.vo.ResourceVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ResourceService extends IService<Resource> {

    void importSwagger();

    void saveOrUpdateResource(ResourceVO resourceVO);

    void deleteResource(Integer resourceId);

    List<ResourceDTO> listResources(ConditionVO conditionVO);

    List<LabelOptionDTO> listResourceOption();

}
