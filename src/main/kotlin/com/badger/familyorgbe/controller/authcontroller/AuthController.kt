package com.badger.familyorgbe.controller.authcontroller

import com.badger.familyorgbe.controller.authcontroller.json.*
import com.badger.familyorgbe.models.usual.User
import com.badger.familyorgbe.repository.jwt.IJwtRepository
import com.badger.familyorgbe.service.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var jwtRepository: IJwtRepository


    @PostMapping("create")
    fun createAccount(@RequestBody form: CreateAccountJson.Form): CreateAccountJson.Response {
        val user = User.createEmpty(
            email = form.email,
            password = form.password
        )

        return CreateAccountJson.Response(
            success = userService.saveUser(user)
        )
    }

    @PostMapping("isEmailExists")
    fun isEmailExists(@RequestBody form: IsEmailExistsJson.Form): IsEmailExistsJson.Response {
        val user = userService.findUserByEmail(email = form.email)
        return IsEmailExistsJson.Response(
            email = form.email,
            isExists = user != null
        )
    }

    @PostMapping("login")
    fun login(@RequestBody form: LoginJson.Form): LoginJson.Response {
        return LoginJson.Response()
    }

//    @PostMapping("login-old")
//    fun loginUser(@RequestBody user: User): AuthResponse {
//        val token = jwtRepository.generateToken(user.email)
//
//        return AuthResponse(
//            token = token,
//            confirmed = jwtRepository.validateToken(token),
//            email = jwtRepository.getEmail(token).orEmpty()
//        )
//    }
}