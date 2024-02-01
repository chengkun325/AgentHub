package com.chengkun.agenthub;

import com.chengkun.agenthub.mapper.SysUserMapper;
import com.chengkun.agenthub.pojo.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MapperTest {

    @Autowired
    private SysUserMapper userMapper;

    @Test
    public void testUserMapper(){
        List<SysUser> sysUsers = userMapper.selectList(null);
        System.out.println(sysUsers);

    }
}
