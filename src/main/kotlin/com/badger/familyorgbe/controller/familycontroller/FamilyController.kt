package com.badger.familyorgbe.controller.familycontroller

import com.badger.familyorgbe.controller.familycontroller.json.ExcludeFamilyMemberJson
import com.badger.familyorgbe.controller.familycontroller.json.GetAllMembersJson
import com.badger.familyorgbe.controller.familycontroller.json.GetFamilyJson
import com.badger.familyorgbe.core.base.BaseAuthedController
import com.badger.familyorgbe.core.base.rest.BaseResponse
import com.badger.familyorgbe.core.base.rest.ResponseError
import com.badger.familyorgbe.models.usual.OnlineUser
import com.badger.familyorgbe.service.family.FamilyService
import com.badger.familyorgbe.service.users.IOnlineStorage
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

    @PostMapping("getFamily")
    fun getFamily(
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
    fun getAllMembers(
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
    fun excludeFamilyMember(
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
}