package com.example.authorizationserver.security;

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
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     *  1、authorizeRequests 返回一个ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry  然后我们就可以进行对各种url 的进行权限配置。 注意， 它是配置权限的。requestMatchers 配置一个request Mather数组，参数为RequestMatcher 对象，其match 规则自定义。
        2、antMatchers 配置一个request Mather 的 string数组，参数为 ant 路径格式， 直接匹配url。
        3、mvcMatchers 同上，但是用于匹配 @RequestMapping 的value
        4、regexMatchers 同上，但是为正则表达式
        4、anyRequest 匹配任意url，无参。

     hasAnyRole 是否有（参数数组中的）任一角色
     hasRole 是否有某个角色
     hasAuthority 是否有某个权限
     hasAnyAuthority 是否有（参数数组中的）任一权限
     hasIpAddress ip是否匹配参数
     permitAll 允许所有情况，即相当于没做任何security限制
     denyAll 拒绝所有情况。 这情况比较奇怪， 如果拒绝所有情况的话， 那的存在有什么意义？
     anonymous 可以以匿名身份登录
     authenticated 必须要进行身份验证
     fullyAuthenticated 进行严格身份验证，即不能使用缓存/cookie之类的
     rememberMe
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/index.html")
                .permitAll().anyRequest()
                .authenticated().and()
                .formLogin().and()
                .logout().permitAll().and()
                .csrf().disable();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("xyz"));
    }
}
