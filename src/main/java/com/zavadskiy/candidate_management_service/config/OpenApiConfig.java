package com.zavadskiy.candidate_management_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "candidate-management-service", version = "1.0",
                contact = @Contact(
                        name = "Zavadskiy Nazar",
                        email = "nazar.zavadskiy.05@mail.ru"
                )
        )
)
public class OpenApiConfig {
}
