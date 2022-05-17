package com.badger.familyorgbe.security

import JwtTokenFilter
import com.badger.familyorgbe.service.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class SpringSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var service: UserService

    @Autowired
    private lateinit var jwtTokenFilter: JwtTokenFilter

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private lateinit var resolver: HandlerExceptionResolver

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(service)
    }

    @Throws(java.lang.Exception::class)
    override fun configure(http: HttpSecurity) {
        // Enable CORS and disable CSRF
        http.cors().and().csrf().disable()

            // Set session management to stateless
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            // Set unauthorized requests exception handler
            .exceptionHandling()
            .authenticationEntryPoint { request: HttpServletRequest, response: HttpServletResponse, ex: AuthenticationException ->
                resolver.resolveException(request, response, null, ex)
            }
            .and()

            // Set permissions on endpoints
            .authorizeRequests() // Our public endpoints
            .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
            .antMatchers(HttpMethod.GET, "/user-photos/**").permitAll()

            // Our private endpoints
            .anyRequest()
            .authenticated()
            .and()

            // Add JWT token filter
            .addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )
    }

    @Bean
    fun corsFilter(): CorsFilter? {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    @Bean
    @Throws(java.lang.Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }
}