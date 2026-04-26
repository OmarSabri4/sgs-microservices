package com.sgs.docenti;

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
                        .title("SGS - Docenti Service API")
                        .version("1.0.0")
                        .description("Microservizio per la gestione dei docenti del Sistema Gestione Scolastica")
                        .contact(new Contact()
                                .name("SGS Team")
                                .email("admin@scuola.it")));
    }
}
