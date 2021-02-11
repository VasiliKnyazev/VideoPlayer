package com.luxoft.videoplayer.security.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Profile("oauth2")
public class Oauth2SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .antMatcher("/**")
                .authorizeRequests()
                //.antMatchers("/", "/login")
                //.permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login();
    }

}
