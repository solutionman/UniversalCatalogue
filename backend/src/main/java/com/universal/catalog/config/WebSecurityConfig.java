package com.universal.catalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:8081"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private CsrfTokenRepository getCsrfTokenRepository(){
        CookieCsrfTokenRepository tokenRepository =  CookieCsrfTokenRepository.withHttpOnlyFalse();
        tokenRepository.setCookiePath("/");
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .cors()
                .and()
                .csrf()
                .ignoringAntMatchers("/login")
                .csrfTokenRepository(getCsrfTokenRepository())
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll();
    }

}
