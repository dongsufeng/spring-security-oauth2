package com.example.authorizationserver.pojo;

import lombok.Data;

import java.util.Calendar;

/**
 * @author dongsufeng
 * @create 2018/12/13 14:50
 */
@Data
public class ClientUser {

    private Long id;

    private String username;

    private String password;

    private String accessToken;

    private Calendar accessTokenValidity;

    private String refreshToken;
}
