package br.com.amisahdev.meetapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.stereotype.Component;

@Component
public class AuthOpenApiCustom implements OpenApiCustomizer {

    @Override
    public void customise(final OpenAPI openAPI) {
        final var securitySchemeName = "bearerAuth";

        openAPI.getComponents().addSecuritySchemes(securitySchemeName,
                new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
        );
        openAPI.addSecurityItem(new SecurityRequirement().addList(securitySchemeName));

    }
}
