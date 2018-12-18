package com.example.resourceserver.api;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	// 资源API
    @RequestMapping("/api/userinfo")
    @ResponseBody
    public UserInfo getUserInfo() {
        System.out.println("===================进入资源服务器===============================");
        String principal = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("========================"+ JSONObject.toJSONString(principal));
//        User user = (User) SecurityContextHolder.getContext()
//                .getAuthentication().getPrincipal();
        String email = principal + "@spring2go.com";

        UserInfo userInfo = new UserInfo();
        userInfo.setName(principal);
        userInfo.setEmail(email);

        return userInfo;
    }

}