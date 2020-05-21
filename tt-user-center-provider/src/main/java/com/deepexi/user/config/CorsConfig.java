package com.deepexi.user.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @version V1.0
 * @author: LuoGuang
 * @Package com.deepexi.member.config
 * @Description:
 * @date: 2019/3/4 16:01
 */
@Configuration
public class CorsConfig {

    public static final String ORIGIN = "*";
    public static final String PATH = "/**";

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(ORIGIN);
        corsConfiguration.addAllowedHeader(ORIGIN);
        corsConfiguration.addAllowedMethod(ORIGIN);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(PATH, buildConfig());
        return new CorsFilter(source);
    }
}