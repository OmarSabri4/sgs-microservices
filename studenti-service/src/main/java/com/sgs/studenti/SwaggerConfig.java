package com.sgs.studenti;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SGS - Studenti Service API")
                        .version("1.0.0")
                        .description("Microservizio per la gestione degli studenti del Sistema Gestione Scolastica")
                        .contact(new Contact()
                                .name("SGS Team")
                                .email("admin@scuola.it")));
    }
}
