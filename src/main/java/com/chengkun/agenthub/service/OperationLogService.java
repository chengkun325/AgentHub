package com.chengkun.agenthub.service;

import com.chengkun.agenthub.model.dto.OperationLogDTO;
import com.chengkun.agenthub.entity.OperationLog;
import com.chengkun.agenthub.model.vo.ConditionVO;
import com.chengkun.agenthub.model.dto.PageResultDTO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OperationLogService extends IService<OperationLog> {

    PageResultDTO<OperationLogDTO> listOperationLogs(ConditionVO conditionVO);

}
