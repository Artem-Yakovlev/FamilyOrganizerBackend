package com.badger.familyorgbe.controller.authcontroller

import com.badger.familyorgbe.models.usual.User
import com.badger.familyorgbe.service.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @Autowired
    private lateinit var service: UserService

    @PostMapping("/createUser")
    fun createUser(
        @RequestBody body: User
    ): Boolean {
        return service.saveUser(body)
    }


}