package com.cosmos.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * JWT 를 사용할때 반드시 해주기
 */
@Configuration
public class CorConfig {

    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); //내서버가 응답을 할때 json을 자바스크립트에서 처리할 수 있게 할지
        //config.addAllowedOrigin("*"); //모든 아이피를 응답허용 - 호스트/포트
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*"); //모든 header 응답허용 - 헤더
        config.addExposedHeader("*");
        config.addAllowedMethod("*"); //모든 post,get,put 허용 - 프로토콜

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

}
