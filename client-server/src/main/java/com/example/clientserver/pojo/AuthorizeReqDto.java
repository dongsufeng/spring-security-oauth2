package com.example.clientserver.pojo;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author dongsufeng
 * @create 2018/12/13 16:10
 */
@Data
public class AuthorizeReqDto implements Serializable {
    private String client_id;
    private String response_type;
    private String redirect_uri;
    private String scope;

    public String encodeCredentials(String username, String password) {
        String credentials = username + ":" + password;
        String encoded = new String(Base64.getEncoder().encode(
                credentials.getBytes()));

        return encoded;
    }
    public HttpHeaders getHeader(String clientAuthentication) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.add("Authorization", "Basic " + clientAuthentication);

        return httpHeaders;
    }
}
