package com.salesianostriana.dam.ApiDex.security

import com.salesianostriana.dam.ApiDex.security.jwt.JwtAuthenticationEntryPoint
import com.salesianostriana.dam.ApiDex.security.jwt.JwtAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ConfigurePasswordEncoder(){

    @Bean
    fun passwordEncoder() : PasswordEncoder = BCryptPasswordEncoder()
}

@Configuration
class ConfigureCors() {

    @Bean
    fun corsConfigurer() = object: WebMvcConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600)
        }
    }
}


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration(
    private val userDetailsService: UserDetailsService,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAuthorizationFilter: JwtAuthorizationFilter,
    private val passwordEncoder: PasswordEncoder

) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        // @formater:off
        http
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers(POST, "/auth/login", "/auth/token", "/auth/register").permitAll()
            .antMatchers(GET, "/pokemon", "/pokemon/{id}", "/pokemon/favs",
                "/pokemon/capturados", "/pokemon/equipos", "/pokemon/equipos/{id}").hasRole("USER")
            .antMatchers(POST, "/pokemon/favs/{id}", "/pokemon/capturados/{id}", "/pokemon/equipos").hasRole("USER")
            .antMatchers(PUT, "/pokemon/{id}", "/pokemon/equipos/{id}").hasRole("USER")
            .antMatchers(DELETE, "/pokemon/favs/{id}", "/pokemon/capturados/{id}", "/pokemon/equipos/{id}").hasRole("USER")


        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)

        http.headers().frameOptions().disable()
        // @formater:on
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}