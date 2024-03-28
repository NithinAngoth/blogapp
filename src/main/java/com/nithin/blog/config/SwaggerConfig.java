package com.nithin.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    // private ApiKey apiKey() {
    // return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    // }

    // private List<SecurityContext> securityContexts() {
    // return
    // Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
    // }

    // private List<SecurityReference> sf() {
    // AuthorizationScope scope = new AuthorizationScope("global", "access
    // everythig");
    // return Arrays.asList(new SecurityReference("scope", new AuthorizationScope[]
    // {scope}));
    // }

    // public Docket api() {

    // return new Docket(DocumentationType.SWAGGER_2)
    // .apiInfo(getInfo())
    // .securityContexts(securityContexts())
    // .securitySchemes(Arrays.asList(apiKey()))
    // .select()
    // .apis(RequestHandlerSelectors.any())
    // .paths(PathSelectors.any())
    // .build();
    // }

    // private ApiInfo getInfo() {
    // return new ApiInfo("Blogging application : Backend", "This project is
    // developed by Nithin", "1.0", null, null,
    // null, null, Collections.emptyList());
    // }

    @Bean
    public OpenAPI openAPI() {

        String schemeName = "bearerScheme";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .components(new Components()
                        .addSecuritySchemes(schemeName, new SecurityScheme()
                                .name(schemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer")
                        )
                )
                .info(new Info()
                        .title("Blogging appication API")
                        .description("This is an blogging  RESTful web services API.")
                        .version("v0.0.1")
                        .contact(new Contact().name("Nithin Angoth").email("nithin@gmail.com"))
                        .license(new License().name("Apache")))
                .externalDocs(new ExternalDocumentation().description("this is an blog application from springboot"));
    }
}
