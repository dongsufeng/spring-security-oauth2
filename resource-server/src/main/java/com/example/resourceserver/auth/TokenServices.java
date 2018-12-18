package com.example.resourceserver.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import java.io.Serializable;
import java.util.*;

/**
 * @author dongsufeng
 * @create 2018/12/14 18:51
 */
public class TokenServices implements ResourceServerTokenServices {
    @Override
    public OAuth2Authentication loadAuthentication(String s) throws AuthenticationException, InvalidTokenException {
        Map<String, String> requestParameters=new HashMap<>();
        String clientId="";
        Collection<? extends GrantedAuthority > authorities=null;
        boolean approved=true;
        Set scope=new HashSet();
        Set<String> resourceIds=new HashSet();
        String redirectUri=null;
        Set<String> responseTypes=new HashSet();
        Map<String, Serializable > extensionProperties=new HashMap<>();
        OAuth2Request oAuth2Request=new OAuth2Request(requestParameters,clientId,authorities,approved,scope,resourceIds,redirectUri,responseTypes,extensionProperties);

        Authentication userAuthentication=new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }

        };
        OAuth2Authentication oAuth2Authentication=new OAuth2Authentication(oAuth2Request,userAuthentication);
        return null;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String s) {
        return null;
    }
}
