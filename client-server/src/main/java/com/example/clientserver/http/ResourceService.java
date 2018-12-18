package com.example.clientserver.http;

import com.example.clientserver.configuration.ResourceFeignConfiguration;
import com.example.clientserver.pojo.UserInfo;
import feign.Headers;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author dongsufeng
 * @create 2018/12/13 18:06
 */
@FeignClient(url = "http://localhost:9002",name = "resourceService",primary = false,configuration = ResourceFeignConfiguration.class)
public interface ResourceService {

    @RequestMapping(value = "/api/userinfo",method = RequestMethod.GET)
    @Headers({"Content-Type: application/x-www-form-urlencoded;charset=UTF-8","Accept: application/json"})
    UserInfo getUserInfo();

}
