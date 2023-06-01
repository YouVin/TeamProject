package com.example.demo.security;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.Service.OAuth2Service;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class MySecurityConfig{
	
	
	private final OAuth2Service customOAuth2UserService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.csrf().disable() // csrf 보안 설정 사용 X
        		
        	    .authorizeHttpRequests()
        	    .requestMatchers("/").permitAll()
                .anyRequest().authenticated()
        	    .and()
        		.logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and() 
                
                .formLogin().loginPage("/").permitAll().and()
                .oauth2Login()
                .defaultSuccessUrl("/home")
                .failureUrl("/")
                .userInfoEndpoint() // 사용자가 로그인에 성공하였을 경우,
                .userService(customOAuth2UserService); // 해당 서비스 로직을 타도록 설정

         return http.build();
    }

}
