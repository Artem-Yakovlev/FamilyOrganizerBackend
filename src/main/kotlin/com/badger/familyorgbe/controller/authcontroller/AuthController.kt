package com.badger.familyorgbe.controller.authcontroller

import com.badger.familyorgbe.controller.authcontroller.json.*
import com.badger.familyorgbe.core.base.BaseController
import com.badger.familyorgbe.models.usual.User
import com.badger.familyorgbe.repository.jwt.IJwtRepository
import com.badger.familyorgbe.service.email.EmailService
import com.badger.familyorgbe.service.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController : BaseController() {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var jwtRepository: IJwtRepository

    @Autowired
    private lateinit var emailService: EmailService

    @PostMapping("isEmailExists")
    fun isEmailExists(@RequestBody form: IsEmailExistsJson.Form): IsEmailExistsJson.Response {
        val user = userService.findUserByEmail(email = form.email)
        return IsEmailExistsJson.Response(
            email = form.email,
            isExists = user != null
        )
    }

    @PostMapping("sendCode")
    fun sendCode(@RequestBody form: SendCodeJson.Form): SendCodeJson.Response {
        val isCodeSent = emailService.sendNewCodeTo(form.email)
        return SendCodeJson.Response(
            success = isCodeSent
        )
    }

    @PostMapping("checkCode")
    fun checkCode(@RequestBody form: CheckCodeJson.Form): CheckCodeJson.Response = with(form) {
        val isCodeApproved = emailService.checkCodeForEmail(
            email = email,
            code = code
        )

        return if (isCodeApproved) {
            val user = User.createEmpty(
                email = form.email
            )
            userService.saveUser(user)
            val token = jwtRepository.generateToken(email)
            CheckCodeJson.Response(
                email = email,
                success = true,
                token = token
            )
        } else {
            CheckCodeJson.Response(
                email = email,
                success = false,
                token = null
            )
        }
    }
}