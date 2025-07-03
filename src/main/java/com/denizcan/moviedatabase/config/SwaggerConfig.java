package com.denizcan.moviedatabase.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI movieDatabaseOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8081");
        devServer.setDescription("Development server");

        Contact contact = new Contact();
        contact.setEmail("denizcan@example.com");
        contact.setName("Deniz Can");
        contact.setUrl("https://github.com/denizcan");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Movie Database API")
                .version("1.0")
                .contact(contact)
                .description("Bu API, film veritabanı yönetimi için geliştirilmiştir. " +
                        "Filmler, oyuncular, yönetmenler ve ödüller hakkında bilgi alabilir, " +
                        "yeni kayıtlar ekleyebilir, mevcut kayıtları güncelleyebilir ve silebilirsiniz.")
                .termsOfService("https://www.example.com/terms")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}