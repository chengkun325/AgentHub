package com.chengkun.agenthub.controller;

import com.chengkun.agenthub.pojo.SysUser;
import com.chengkun.agenthub.service.LoginService;
import com.chengkun.agenthub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody SysUser user) {
        return loginService.login(user);
    }

    @RequestMapping("/logout")
    public Result<?> logout() {
        return loginService.logout();
    }
}
