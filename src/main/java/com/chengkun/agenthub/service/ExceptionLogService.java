package com.chengkun.agenthub.service;

import com.chengkun.agenthub.model.dto.ExceptionLogDTO;
import com.chengkun.agenthub.entity.ExceptionLog;
import com.chengkun.agenthub.model.vo.ConditionVO;
import com.chengkun.agenthub.model.dto.PageResultDTO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ExceptionLogService extends IService<ExceptionLog> {

    PageResultDTO<ExceptionLogDTO> listExceptionLogs(ConditionVO conditionVO);

}
