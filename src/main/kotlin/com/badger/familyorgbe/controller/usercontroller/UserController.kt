package com.badger.familyorgbe.controller.usercontroller

import com.badger.familyorgbe.controller.usercontroller.json.GetProfileJson
import com.badger.familyorgbe.core.base.BaseController
import com.badger.familyorgbe.models.usual.User
import com.badger.familyorgbe.repository.jwt.IJwtRepository
import com.badger.familyorgbe.service.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController : BaseController() {

    @Autowired
    private lateinit var jwtRepository: IJwtRepository

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("getProfile")
    fun getProfile(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: GetProfileJson.Form
    ): GetProfileJson.Response {
        val token = authHeader.getBearerTokenIfExist()
        val email = jwtRepository.getEmail(token)
        val userEntity = userService.findUserByEmail(email = email)

        return GetProfileJson.Response(
            user = userEntity?.let(User::fromEntity)
        )
    }

}