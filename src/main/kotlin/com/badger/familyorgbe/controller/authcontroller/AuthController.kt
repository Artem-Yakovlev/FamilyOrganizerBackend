package com.badger.familyorgbe.controller.authcontroller

import com.badger.familyorgbe.infoLog
import com.badger.familyorgbe.models.usual.User
import com.badger.familyorgbe.service.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    private lateinit var service: UserService

    @PostMapping(path = ["/createUser"])
    @ResponseBody
    fun createUser(
        @RequestBody body: User
    ): Boolean {
        return service.saveUser(body)
    }

    @PostMapping(path = ["/login"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getAuthUser(): User? {
        infoLog("login")
        val auth = SecurityContextHolder.getContext().authentication ?: return null
        return (auth.principal as? User)
            ?.email
            ?.let(service::getUserByEmail)
            ?.let(User::fromEntity)
    }
}