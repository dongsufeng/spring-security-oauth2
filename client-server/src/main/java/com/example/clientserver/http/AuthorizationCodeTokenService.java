package com.example.clientserver.http;

import com.example.clientserver.auth.OAuth2Token;
import com.example.clientserver.configuration.FeignConfiguration;
import feign.Headers;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(url = "http://localhost:8089",name = "authorizationCodeTokenService",primary = false,configuration = FeignConfiguration.class)
public interface AuthorizationCodeTokenService {


    @RequestMapping(value = "/oauth/token",method = RequestMethod.POST)
    @Headers({"Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
    OAuth2Token getToken(@RequestParam Map<String, String> parameters);

}
