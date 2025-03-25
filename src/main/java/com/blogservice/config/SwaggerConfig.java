package com.blogservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Swagger/OpenAPI documentation.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI blogServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blog Service API")
                        .description("REST API for managing blog posts")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Blog Service Team")
                                .email("support@blogservice.com")
                                .url("https://blogservice.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
