package com.badger.familyorgbe.controller.usercontroller

import com.badger.familyorgbe.controller.usercontroller.json.GetProfileJson
import com.badger.familyorgbe.controller.usercontroller.json.SetTokenJson
import com.badger.familyorgbe.controller.usercontroller.json.UpdateProfileNameJson
import com.badger.familyorgbe.controller.usercontroller.json.UpdateStatusJson
import com.badger.familyorgbe.core.base.BaseController
import com.badger.familyorgbe.core.exception.LogicException
import com.badger.familyorgbe.models.entity.UserStatus
import com.badger.familyorgbe.repository.jwt.IJwtRepository
import com.badger.familyorgbe.service.users.UserService
import com.badger.familyorgbe.utils.saveFile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File

@RestController
@RequestMapping("/user")
class UserController : BaseController() {

    @Autowired
    private lateinit var jwtRepository: IJwtRepository

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("getProfile")
    suspend fun getProfile(
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
    suspend fun updateProfileName(
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
    suspend fun updateProfileImage(
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
//
//        @RequestMapping("/picture/{id}")
//    @ResponseBody
//    public HttpEntity<byte[]> getArticleImage(@PathVariable String id) {
//
//        logger.info("Requested picture >> " + id + " <<");
//
//        // 1. download img from http://internal-picture-db/id.jpg ...
//        byte[] image = ...
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        headers.setContentLength(image.length);
//
//        return new HttpEntity<byte[]>(image, headers);
//    }

    @RequestMapping("/picture/{id}")
    @ResponseBody
    suspend fun getProfileImage(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @PathVariable
        id: String
    ): HttpEntity<ByteArray> {
        val file = File("./user-photos/13.zrka@gmail.com/family-org-father.jpg")
        val imageBytes = file.readBytes()

        val headers = HttpHeaders().apply {
            contentType = MediaType.IMAGE_JPEG
            contentLength = imageBytes.size.toLong()
        }

        return HttpEntity<ByteArray>(imageBytes, headers)
    }

    @PostMapping("updateStatus")
    suspend fun updateStatus(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: UpdateStatusJson.Form
    ): UpdateStatusJson.Response {
        val token = authHeader.getBearerTokenIfExist()
        val email = jwtRepository.getEmail(token)
        val status = UserStatus.valueOf(form.status)
        userService.updateStatusOfUser(email, status)
        return UpdateStatusJson.Response(
            success = true
        )
    }

    @PostMapping("setToken")
    suspend fun setToken(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: SetTokenJson.Form
    ): SetTokenJson.Response {
        val token = authHeader.getBearerTokenIfExist()
        val email = jwtRepository.getEmail(token)

        userService.saveToken(email, form.token)
        return SetTokenJson.Response(
            success = true
        )
    }

    companion object {
        const val USER_PHOTOS = "user-photos"
    }

}