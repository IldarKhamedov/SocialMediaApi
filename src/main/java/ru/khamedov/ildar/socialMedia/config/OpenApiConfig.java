package ru.khamedov.ildar.socialMedia.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Social Media Api",
                description = "RESTful API для социальной медиа платформы", version = "1.0.0",
                contact = @Contact(
                        name = "Khamedov Ildar",
                        email = "ildarhamedov@gmail.com"
                )
        )
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "BASIC",
        scheme = "basic")
public class OpenApiConfig {

}