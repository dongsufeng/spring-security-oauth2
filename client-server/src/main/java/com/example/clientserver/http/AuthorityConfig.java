package com.example.clientserver.http;//package com.example.clienttemp.http;
//
//import feign.Client;
//import feign.Feign;
//import jdk.nashorn.internal.runtime.GlobalConstants;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
//
///**
// * @author dongsufeng
// * @create 2018/12/13 16:49
// */
//public class AuthorityConfig {
//
//    /**
//     * 授权信息Header的key
//     */
//    public static final String OAUTH_KEY = "token";
//
//    /**
//     * 授权信息Header的值的前缀
//     */
//    public static final String OAUTH_VALUE_PREFIX = "Bearer ";
//
//    @Autowired
//    private Client client;
//
//    public AuthorizationCodeTokenService userInfoFeignClient(String token) {
//        AuthorizationCodeTokenService authorityServiceLoginInvoker = Feign.builder().client(client)
//                .contract(new SpringMvcContract())
//                .requestInterceptor(template -> template.header(OAUTH_KEY, OAUTH_VALUE_PREFIX + token));
//        return authorityServiceLoginInvoker;
//    }
//}
