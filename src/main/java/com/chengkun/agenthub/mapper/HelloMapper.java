package com.chengkun.agenthub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chengkun.agenthub.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HelloMapper extends BaseMapper<User> {
}
