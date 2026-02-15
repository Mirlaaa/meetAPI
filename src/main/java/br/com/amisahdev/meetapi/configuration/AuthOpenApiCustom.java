package br.com.amisahdev.meetapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.stereotype.Component;

@Component
public class AuthOpenApiCustom implements OpenApiCustomizer {

    @Override
    public void customise(final OpenAPI openAPI) {
        final var securitySchemeName = "bearerAuth";
        final var securitySchemeoAuth = "oauth2";

        final String authUrl = "http://localhost:8088/realms/meetAPI/protocol/openid-connect/auth";
        final String tokenUrl = "http://localhost:8088/realms/meetAPI/protocol/openid-connect/token";

        openAPI.getComponents().addSecuritySchemes(securitySchemeName,
                new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
        );

        openAPI.getComponents().addSecuritySchemes(securitySchemeoAuth,
                new SecurityScheme()
                        .type(SecurityScheme.Type.OAUTH2)
                        .flows(new OAuthFlows()
                                .authorizationCode(new OAuthFlow()
                                        .authorizationUrl(authUrl)
                                        .tokenUrl(tokenUrl)
                                        .scopes(new Scopes().addString("email", "openid")))));

        openAPI.addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
        openAPI.addSecurityItem(new SecurityRequirement().addList(securitySchemeoAuth));
    }
}
