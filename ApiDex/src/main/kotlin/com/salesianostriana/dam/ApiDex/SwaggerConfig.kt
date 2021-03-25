package com.salesianostriana.dam.ApiDex

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.annotation.AuthenticationPrincipal
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@Configuration
@EnableSwagger2
class SwaggerConfig{

    fun apiKey(): ApiKey {
        return ApiKey("JWT", "Authorization", "header");
    }

    fun securityContext() : SecurityContext {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private fun defaultAuth(): List<SecurityReference?>? {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("JWT", authorizationScopes))
    }

    @Bean
    fun api() : Docket {

        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))
            .useDefaultResponseMessages(false)
            .ignoredParameterTypes(AuthenticationPrincipal::class.java)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.salesianostriana.dam.ApiDex.controllers"))
            .paths(PathSelectors.any())
            .build()
    }

    fun apiInfo() : ApiInfo?{
            return ApiInfo(
                "My REST API",
                "Some custom description of API.",
                "1.0",
                "Terms of service",
                Contact("Pablo González", "www.url.com", "pabloggm00@gmail.com"),
                "License of API",
                "API license URL",
                Collections.emptyList())
    }

}

