package com.badger.familyorgbe.config

import com.badger.familyorgbe.controller.usercontroller.UserController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.nio.file.Paths
import java.util.*


private const val LOCAL_CHANGE_INTERCEPTOR_PARAM = "lang"

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
        exposeDirectory(registry)
    }

    private fun exposeDirectory(registry: ResourceHandlerRegistry) {
        var dir = UserController.USER_PHOTOS
        val uploadDir = Paths.get(dir)
        val uploadPath: String = uploadDir.toFile().absolutePath
        if (dir.startsWith("../")) {
            dir = dir.replace("../", "")
        }
        registry.addResourceHandler("/$dir/**").addResourceLocations("file:/$uploadPath/")
    }
}