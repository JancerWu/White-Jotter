package com.jancer.wj.controller;

import com.jancer.wj.pojo.User;
import com.jancer.wj.vo.Result;
import com.jancer.wj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        User user = userService.get(username, requestUser.getPassword());
        if (null == user) {
            System.out.println("用户名或密码错误！！");
            return new Result(400);
        } else {
            return new Result(200);

        }
    }
}
