package com.badger.familyorgbe.config

import com.badger.familyorgbe.controller.usercontroller.UserController
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.file.Paths


@Configuration
class MvcConfig : WebMvcConfigurer {
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