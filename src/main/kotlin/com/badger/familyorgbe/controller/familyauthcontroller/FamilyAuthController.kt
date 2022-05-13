package com.badger.familyorgbe.controller.familyauthcontroller

import com.badger.familyorgbe.controller.familyauthcontroller.json.AcceptInviteJson
import com.badger.familyorgbe.controller.familyauthcontroller.json.CreateJson
import com.badger.familyorgbe.controller.familyauthcontroller.json.GetAllJson
import com.badger.familyorgbe.controller.familyauthcontroller.json.LeaveJson
import com.badger.familyorgbe.core.base.BaseController
import com.badger.familyorgbe.repository.jwt.IJwtRepository
import com.badger.familyorgbe.service.family.FamilyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/familyAuth")
class FamilyAuthController : BaseController() {

    @Autowired
    private lateinit var jwtRepository: IJwtRepository

    @Autowired
    private lateinit var familyService: FamilyService

    @PostMapping("getAll")
    fun getAll(@RequestBody form: GetAllJson.Form): GetAllJson.Response {
        //TODO
        throw NotImplementedError()
    }

    @PostMapping("acceptInvite")
    fun acceptInvite(@RequestBody form: AcceptInviteJson.Form): AcceptInviteJson.Response {
        //TODO
        throw NotImplementedError()
    }

    @PostMapping("leave")
    fun leave(@RequestBody form: LeaveJson.Form): LeaveJson.Response {
        //TODO
        throw NotImplementedError()
    }

    @PostMapping("create")
    fun create(@RequestBody form: CreateJson.Form): CreateJson.Response {
        //TODO
        throw NotImplementedError()
    }
}