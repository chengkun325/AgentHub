package com.chengkun.agenthub.service;


import com.chengkun.agenthub.model.dto.JobLogDTO;
import com.chengkun.agenthub.entity.JobLog;
import com.chengkun.agenthub.model.vo.JobLogSearchVO;
import com.chengkun.agenthub.model.dto.PageResultDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface JobLogService extends IService<JobLog> {

    PageResultDTO<JobLogDTO> listJobLogs(JobLogSearchVO jobLogSearchVO);

    void deleteJobLogs(List<Integer> ids);

    void cleanJobLogs();

    List<String> listJobLogGroups();

}
