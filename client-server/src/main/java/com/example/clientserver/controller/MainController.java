package com.example.clientserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.clientserver.auth.OAuth2Token;
import com.example.clientserver.http.AuthorizationCodeTokenService;
import com.example.clientserver.http.ResourceService;
import com.example.clientserver.pojo.AuthorizeReqDto;
import com.example.clientserver.pojo.ClientUser;
import com.example.clientserver.pojo.UserInfo;
import com.example.clientserver.security.ClientUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;


/**
 * @author dongsufeng
 * @create 2018/12/13 15:45
 */
@Controller
@Slf4j
public class MainController {

    @Autowired
    AuthorizationCodeTokenService authorizationCodeTokenService;
    @Autowired
    ResourceService resourceService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/loginpage")
    public String login(){return "loginformurl";}

    @GetMapping("/callback")
    public ModelAndView callback(String code, String state) {
        ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        ClientUser clientUser = userDetails.getClientUser();
        Map<String, String> parameters=new HashMap<>(1);
        parameters.put("grant_type", "authorization_code");
        parameters.put("scope", "bar");
        parameters.put("code", code);
        parameters.put("redirect_uri", "http://localhost:9001/callback");
        OAuth2Token token =authorizationCodeTokenService.getToken(parameters);
        clientUser.setAccessToken(token.getAccessToken());

        Calendar tokenValidity = Calendar.getInstance();
        tokenValidity.setTime(new Date(Long.parseLong(token.getExpiresIn())));
        clientUser.setAccessTokenValidity(tokenValidity);
        log.info("clientUser={}", JSONObject.toJSONString(clientUser));
        return new ModelAndView("redirect:/mainpage");
    }
    @RequestMapping("/mainpage1")
    public ModelAndView mainPage1(){

        ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        ClientUser clientUser = userDetails.getClientUser();

        ModelAndView mv = new ModelAndView("mainpage");
        mv.addObject("user", clientUser);
        if (clientUser.getAccessToken() == null) {
            Map<String, String> parameters =new HashMap<>();
            parameters.put("username","susu");
            parameters.put("password","xyz");
            parameters.put("grant_type","password");
            parameters.put("client_id","barClientIdPassword");
            OAuth2Token token =authorizationCodeTokenService.getToken(parameters);
            log.info("========token={}",JSONObject.toJSONString(token));
            clientUser.setAccessToken(token.getAccessToken());
            clientUser.setRefreshToken(token.getRefreshToken());
            //刷新token
            parameters.clear();
            parameters.put("grant_type","refresh_token");
            parameters.put("refresh_token",token.getRefreshToken());
            token =authorizationCodeTokenService.getToken(parameters);
            log.info("========refreshToken={}",JSONObject.toJSONString(token));
            tryToGetUserInfo(mv, token.getAccessToken());

        }else {
            //刷新token
            Map<String, String> parameters =new HashMap<>();
            parameters.put("grant_type","refresh_token");
            parameters.put("refresh_token",clientUser.getRefreshToken());
            OAuth2Token token =authorizationCodeTokenService.getToken(parameters);
            log.info("========refreshToken={}",JSONObject.toJSONString(token));
            tryToGetUserInfo(mv, clientUser.getAccessToken());
        }
        return mv;
    }
    @GetMapping("/mainpage")
    public ModelAndView mainpage() {
        ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        ClientUser clientUser = userDetails.getClientUser();

        if (clientUser.getAccessToken() == null) {
            Map<String, String> parameters =new HashMap<>();
            parameters.put("client_id","barClientIdPassword");
            parameters.put("response_type","code");
            parameters.put("redirect_uri","http://localhost:9001/callback");
            parameters.put("scope","bar");
            String url = buildUrl("http://localhost:8089/oauth/authorize", parameters);
            log.info("==========url========{}",url);
            return new ModelAndView("redirect:" + url);
        }

        ModelAndView mv = new ModelAndView("mainpage");
        mv.addObject("user", clientUser);

        tryToGetUserInfo(mv, clientUser.getAccessToken());

        return mv;
    }
    private void tryToGetUserInfo(ModelAndView mv, String token) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);
        String endpoint = "http://localhost:9002/api/userinfo";
        try {
            UserInfo userInfo = resourceService.getUserInfo();
//            UserInfo userInfo=new UserInfo();
            mv.addObject("userInfo", userInfo);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("it was not possible to retrieve user profile");
        }
    }
    private String buildUrl(String endpoint, Map<String, String> parameters) {
        List<String> paramList = new ArrayList<>(parameters.size());

        parameters.forEach((name, value) -> {
            paramList.add(name + "=" + value);
        });

        return endpoint + "?" + paramList.stream()
                .reduce((a, b) -> a + "&" + b).get();
    }
}
