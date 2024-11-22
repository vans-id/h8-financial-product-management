package id.co.cimbniaga.financialproductmanagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ForwardedHeaderFilter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Financial Product Management Application",
                description = "Financial Product Management Open API",
                version = "v1.1"
        )
)
public class SwaggerConfig {
        @Bean
        public OpenAPI customOpenApi() {
                return new OpenAPI()
                        .addSecurityItem(new SecurityRequirement().addList(AUTHORIZATION))
                        .components(new Components().addSecuritySchemes(AUTHORIZATION,
                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                        .name("Authorization")
                                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
        }

        @Bean
        ForwardedHeaderFilter forwardedHeaderFilter() {
                return new ForwardedHeaderFilter();
        }

}
