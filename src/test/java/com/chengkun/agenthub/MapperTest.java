package com.chengkun.agenthub;

import com.chengkun.agenthub.mapper.SysMenuMapper;
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

    @Autowired
    private SysMenuMapper menuMapper;

    @Test
    public void testUserMapper(){
        List<SysUser> sysUsers = userMapper.selectList(null);
        System.out.println(sysUsers);

    }

    @Test
    public void testSelectPermsByUserId(){
        List<String> list = menuMapper.selectPermsByUserId(3L);
        System.out.println(list);
    }
}
