package com.example.clientserver.configuration;

import com.alibaba.fastjson.JSONObject;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import okhttp3.ConnectionPool;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

import javax.sound.midi.Soundbank;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * @author dongsufeng
 * @create 2018/12/14 11:48
 */
public class FeignConfiguration implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String credentials = "barClientIdPassword" + ":" + "secret";
        String encoded = new String(Base64.getEncoder().encode(
                credentials.getBytes()));
        requestTemplate.header("Authorization","Basic "+encoded);
        requestTemplate.header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        requestTemplate.header("Accept",MediaType.APPLICATION_JSON_VALUE);
        System.out.println("headers======="+ JSONObject.toJSONString(requestTemplate.headers()));
    }
    @Bean
    public okhttp3.OkHttpClient okHttpClient() {
        okhttp3.OkHttpClient.Builder clientBuilder = new okhttp3.OkHttpClient.Builder()
                // 读取超时
                .readTimeout(3, TimeUnit.SECONDS)
                // 连接超时
                .connectTimeout(200, TimeUnit.MILLISECONDS)
                // 写入超时
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(10 , 30, TimeUnit.SECONDS));
        return clientBuilder.build();
    }
}
