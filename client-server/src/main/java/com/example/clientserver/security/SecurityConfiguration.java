package com.example.clientserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author dongsufeng
 * @create 2018/12/13 15:14
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //自定义登陆页，登陆成功后跳转/login
        http
                .authorizeRequests().antMatchers("/", "/index.html","/loginpage")
                .permitAll().anyRequest().authenticated().and()
                .formLogin().loginPage("/loginpage").successHandler(loginSuccessHandler).loginProcessingUrl("/login").and()
                //自定义退出
                .logout().logoutUrl("/loginout").logoutSuccessUrl("/logout").permitAll().and()
                .csrf().disable();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("xyz"));
    }
}
