package com.chengkun.agenthub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuMapper> {

    List<String> selectPermsByUserId(Long userId);
}