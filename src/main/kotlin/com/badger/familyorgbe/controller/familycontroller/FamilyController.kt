package com.badger.familyorgbe.controller.familycontroller

import com.badger.familyorgbe.controller.familycontroller.json.ExcludeFamilyMemberJson
import com.badger.familyorgbe.controller.familycontroller.json.GetAllMembersJson
import com.badger.familyorgbe.controller.familycontroller.json.GetFamilyJson
import com.badger.familyorgbe.controller.familycontroller.json.InviteFamilyMemberJson
import com.badger.familyorgbe.core.base.BaseAuthedController
import com.badger.familyorgbe.core.base.rest.BaseResponse
import com.badger.familyorgbe.core.base.rest.ResponseError
import com.badger.familyorgbe.infoLog
import com.badger.familyorgbe.models.usual.OnlineUser
import com.badger.familyorgbe.service.family.FamilyService
import com.badger.familyorgbe.service.users.IOnlineStorage
import com.badger.familyorgbe.service.users.UserService
import com.fasterxml.jackson.databind.ser.Serializers.Base
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/family")
class FamilyController : BaseAuthedController() {

    @Autowired
    private lateinit var familyService: FamilyService

    @Autowired
    private lateinit var onlineStorage: IOnlineStorage

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("getFamily")
    suspend fun getFamily(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody form: GetFamilyJson.Form
    ): BaseResponse<GetFamilyJson.Response> {
        val email = authHeader.getAuthEmail()

        return familyService.getFamilyById(form.familyId)
            ?.takeIf { family -> family.members.contains(email) }
            ?.let { family ->
                BaseResponse(
                    data = GetFamilyJson.Response(family = family)
                )
            } ?: BaseResponse(
            error = ResponseError.FAMILY_DOES_NOT_EXISTS,
            data = GetFamilyJson.Response(family = null)
        )
    }

    @PostMapping("getAllMembers")
    suspend fun getAllMembers(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: GetAllMembersJson.Form
    ): BaseResponse<GetAllMembersJson.Response> {
        val email = authHeader.getAuthEmail()

        return familyService.getFamilyById(form.familyId)
            ?.takeIf { family -> family.members.contains(email) }
            ?.let { family ->
                val onlineUsers = familyService
                    .getAllMembersForFamily(family.id)
                    .map { user ->
                        OnlineUser(user = user, lastRegisterTime = onlineStorage.getLastRegisterTime(user.email) ?: 0)
                    }
                BaseResponse(
                    data = GetAllMembersJson.Response(onlineUsers)
                )
            } ?: BaseResponse(
            error = ResponseError.FAMILY_DOES_NOT_EXISTS,
            data = GetAllMembersJson.Response(onlineUsers = emptyList())
        )
    }

    @PostMapping("excludeFamilyMember")
    suspend fun excludeFamilyMember(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: ExcludeFamilyMemberJson.Form
    ): BaseResponse<ExcludeFamilyMemberJson.Response> {
        val email = authHeader.getAuthEmail()

        return familyService.getFamilyById(form.familyId)
            ?.takeIf { family -> family.members.contains(email) }
            ?.let { _ ->
                familyService.excludeMemberFromFamily(familyId = form.familyId, email = form.memberEmail)
                BaseResponse(
                    data = ExcludeFamilyMemberJson.Response(success = true)
                )
            } ?: BaseResponse(
            error = ResponseError.FAMILY_DOES_NOT_EXISTS,
            data = ExcludeFamilyMemberJson.Response(success = false)
        )
    }

    @PostMapping("inviteFamilyMember")
    suspend fun inviteFamilyMember(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: InviteFamilyMemberJson.Form
    ): BaseResponse<InviteFamilyMemberJson.Response> {
        infoLog(form.toString())
        val email = authHeader.getAuthEmail()

        val responseError: ResponseError = familyService.getFamilyById(form.familyId)
            ?.takeIf { family -> family.members.contains(email) }
            ?.let { _ ->
                userService.findUserByEmail(form.memberEmail)?.let {
                    familyService.inviteMemberToFamily(
                        familyId = form.familyId, email = form.memberEmail
                    ) ?: ResponseError.NONE

                } ?: ResponseError.FAMILY_MEMBER_DOES_NOT_EXIST
            } ?: ResponseError.FAMILY_DOES_NOT_EXISTS


        return BaseResponse(
            error = responseError,
            data = InviteFamilyMemberJson.Response(success = responseError == ResponseError.NONE)
        )
    }
}