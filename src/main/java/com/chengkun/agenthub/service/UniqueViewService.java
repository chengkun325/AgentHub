package com.chengkun.agenthub.service;

import com.chengkun.agenthub.model.dto.UniqueViewDTO;
import com.chengkun.agenthub.entity.UniqueView;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UniqueViewService extends IService<UniqueView> {

    List<UniqueViewDTO> listUniqueViews();

}
