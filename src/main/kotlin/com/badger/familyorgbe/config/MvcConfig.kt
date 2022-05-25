package com.badger.familyorgbe.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*

@Configuration
class MvcConfig : WebMvcConfigurer {

    @Bean
    fun localeResolver(): LocaleResolver? {
        val slr = SessionLocaleResolver()
        val defaultLocale = Locale("ru")
        slr.setDefaultLocale(defaultLocale)
        return slr
    }

    @Bean
    fun messageSource(): ReloadableResourceBundleMessageSource? {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:messages")
        messageSource.setCacheSeconds(3600)
        messageSource.setDefaultEncoding("UTF-8") // Add this
        return messageSource
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/user-photos/**")
            .addResourceLocations("file:./user-photos/")
    }
}