package com.example.authorizationserver.security;

import com.example.authorizationserver.mapper.ClientUserMapper;
import com.example.authorizationserver.pojo.ClientUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author dongsufeng
 * @create 2018/12/13 15:02
 */
@Service("userDetailsService")
public class ClientUserDetailsService implements UserDetailsService {

    @Autowired
    ClientUserMapper clientUserMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ClientUser clientUser = clientUserMapper.selectByUsername(username);
        return new ClientUserDetails(clientUser);
    }
}
