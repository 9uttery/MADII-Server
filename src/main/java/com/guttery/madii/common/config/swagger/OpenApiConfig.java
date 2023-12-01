package com.guttery.madii.common.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class OpenApiConfig {
    private static final String TITLE = "MADII API";
    private static final String VERSION = "0.0.1"; // API 문서 업데이트 시 버전 수정
    private static final String DESCRIPTION = "API 명세서";
    private static final String ACCESS_TOKEN = "Access Token";
    private static final String BEARER = "Bearer";
    private static final String JWT = "JWT";
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title(TITLE)
                .version(VERSION)
                .description(DESCRIPTION);

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(ACCESS_TOKEN);

        SecurityScheme accessTokenSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme(BEARER)
                .bearerFormat(JWT)
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        Components components = new Components()
                .addSecuritySchemes(ACCESS_TOKEN, accessTokenSecurityScheme);

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components)
                ;
    }
}
