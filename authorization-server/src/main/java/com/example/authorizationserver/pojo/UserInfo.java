package com.example.authorizationserver.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable{

    private String name;

    private String email;


}
