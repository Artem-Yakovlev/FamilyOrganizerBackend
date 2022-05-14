package com.badger.familyorgbe.controller.familyauthcontroller

import com.badger.familyorgbe.controller.familyauthcontroller.json.AcceptInviteJson
import com.badger.familyorgbe.controller.familyauthcontroller.json.CreateJson
import com.badger.familyorgbe.controller.familyauthcontroller.json.GetAllJson
import com.badger.familyorgbe.controller.familyauthcontroller.json.LeaveJson
import com.badger.familyorgbe.core.base.BaseAuthedController
import com.badger.familyorgbe.core.base.BaseController
import com.badger.familyorgbe.repository.jwt.IJwtRepository
import com.badger.familyorgbe.service.family.FamilyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/familyAuth")
class FamilyAuthController : BaseAuthedController() {

    @Autowired
    private lateinit var familyService: FamilyService

    @PostMapping("getAll")
    fun getAll(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: GetAllJson.Form
    ): GetAllJson.Response {
        val email = authHeader.getAuthEmail()
        val families = familyService.getAllFamiliesForEmail(email)
        return GetAllJson.Response(
            families = families
        )
    }

    @PostMapping("acceptInvite")
    fun acceptInvite(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: AcceptInviteJson.Form
    ): AcceptInviteJson.Response {
        val email = authHeader.getAuthEmail()
        return familyService.getFamilyById(form.familyId)
            ?.takeIf { family -> family.invites.contains(email) }
            ?.let {
                familyService.excludeMemberFromFamily(familyId = form.familyId, email = email)
                    ?.let(AcceptInviteJson.Response::successful)
                    ?: AcceptInviteJson.Response.unsuccessful

            } ?: AcceptInviteJson.Response.unsuccessful
    }

    @PostMapping("leave")
    fun leave(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: LeaveJson.Form
    ): LeaveJson.Response {
        val email = authHeader.getAuthEmail()
        return familyService.getFamilyById(form.familyId)
            ?.takeIf { family -> family.members.contains(email) || family.invites.contains(email) }
            ?.let {
                familyService.excludeMemberFromFamily(familyId = form.familyId, email = email)
                    ?.let(LeaveJson.Response::successful)
                    ?: LeaveJson.Response.unsuccessful

            } ?: LeaveJson.Response.unsuccessful
    }

    @PostMapping("create")
    fun create(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: CreateJson.Form
    ): CreateJson.Response {
        val email = authHeader.getAuthEmail()
        val family = familyService.createFamily(authorEmail = email, familyName = form.familyName)
        return CreateJson.Response(
            family = family
        )
    }
}