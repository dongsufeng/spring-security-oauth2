package com.example.clientserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
@EnableFeignClients("com.example.clientserver.http")
public class ClientServerApplication implements ServletContextInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ClientServerApplication.class, args);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.getSessionCookieConfig().setName("client-session");
	}
}

