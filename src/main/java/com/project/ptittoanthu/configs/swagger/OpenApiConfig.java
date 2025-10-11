package com.project.ptittoanthu.configs.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Quản lý tài liệu")
                        .version("v1")
                        .description("Ứng dụng quản lý và chia sẻ tài liệu học tập!")
                        .contact(new Contact()
                                .name("Truong Xuan Giang")
                                .email("giangtx.2003@gmail.com")
                                .url("url")))
                .addServersItem(new Server()
                        .description("DEV")
                        .url("http://localhost:8080"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .description("Jwt auth description")
                                        .scheme("bearer")
                                        .type(SecurityScheme.Type.HTTP)
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)));
    }

    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> {
            operation.addParametersItem(
                    new HeaderParameter()
                            .name("Accept-Language")
                            .description("Language preference (e.g., en-US, vi-VN)")
                            .required(false)
                            .schema(new io.swagger.v3.oas.models.media.StringSchema()
                                    ._default("vi-VN"))
            );
            return operation;
        };
    }
}
