package com.badger.familyorgbe

import com.badger.familyorgbe.repository.roles.IRolesRepository
import com.badger.familyorgbe.utils.PrepopulateManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.annotation.PostConstruct

@SpringBootApplication
class MainApplication {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Autowired
    private lateinit var rolesRepository: IRolesRepository

    @PostConstruct
    fun postConstruct() {
        PrepopulateManager(
            rolesRepository = rolesRepository
        ).prepopulate()
    }
}

fun main(args: Array<String>) {
    runApplication<MainApplication>(*args)
}