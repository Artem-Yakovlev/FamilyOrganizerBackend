package com.badger.familyorgbe.controller.authcontroller

import com.badger.familyorgbe.models.usual.User
import com.badger.familyorgbe.repository.jwt.IJwtRepository
import com.badger.familyorgbe.service.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var jwtRepository: IJwtRepository

    data class AuthResponse(
        val token: String,
        val confirmed: Boolean
    )

    @GetMapping("test")
    fun getSome() = "HM?"

    @PostMapping("login")
    fun loginUser(@RequestBody user: User): AuthResponse {
        return AuthResponse(
            token = jwtRepository.generateToken(user.email),
            confirmed = true
        )
    }
}