package com.chengkun.agenthub.controller;

import com.chengkun.agenthub.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping("/hello")
    public String Hello() {
        return helloService.helloService().toString();
    }
}
