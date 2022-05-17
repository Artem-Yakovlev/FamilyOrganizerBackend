package com.badger.familyorgbe.controller.usercontroller

import com.badger.familyorgbe.controller.usercontroller.json.GetProfileJson
import com.badger.familyorgbe.controller.usercontroller.json.UpdateProfileNameJson
import com.badger.familyorgbe.core.base.BaseController
import com.badger.familyorgbe.core.exception.LogicException
import com.badger.familyorgbe.infoLog
import com.badger.familyorgbe.repository.jwt.IJwtRepository
import com.badger.familyorgbe.service.users.UserService
import com.badger.familyorgbe.utils.saveFile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

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
        val user = userService.findUserByEmail(email = email)

        return GetProfileJson.Response(
            user = user
        )
    }

    @PostMapping("updateProfileName")
    fun updateProfileName(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: UpdateProfileNameJson.Form
    ): UpdateProfileNameJson.Response {
        val token = authHeader.getBearerTokenIfExist()
        val email = jwtRepository.getEmail(token)

        val user = userService.updateNameOfUser(
            email = email,
            name = form.updatedName
        )
        if (user != null) {
            return UpdateProfileNameJson.Response(
                user = user
            )
        } else {
            throw LogicException("User does not exists!")
        }
    }

    @PostMapping("updateProfileImage")
    fun updateProfileImage(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestParam("profileImage")
        multipartFile: MultipartFile
    ) {
        val fileName = StringUtils.cleanPath(multipartFile.originalFilename.orEmpty())
        val token = authHeader.getBearerTokenIfExist()
        val email = jwtRepository.getEmail(token)

        val uploadDir = "$USER_PHOTOS/$email"

        saveFile(uploadDir, fileName, multipartFile)
    }

    companion object {
        const val USER_PHOTOS = "user-photos"
    }

}