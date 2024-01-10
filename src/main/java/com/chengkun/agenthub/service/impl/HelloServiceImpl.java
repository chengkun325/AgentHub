package com.chengkun.agenthub.service.impl;

import com.chengkun.agenthub.domain.User;
import com.chengkun.agenthub.mapper.HelloMapper;
import com.chengkun.agenthub.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    private HelloMapper helloMapper;

    @Override
    public List<User> helloService() {
        List<User> users = helloMapper.selectList(null);
        return users;
    }
}
