package com.badger.familyorgbe.controller.familycontroller

import com.badger.familyorgbe.controller.familycontroller.json.GetAllMembersJson
import com.badger.familyorgbe.controller.familycontroller.json.GetFamilyJson
import com.badger.familyorgbe.core.base.BaseController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/family")
class FamilyController : BaseController() {


    @PostMapping("getFamily")
    fun getFamily(@RequestBody form: GetFamilyJson.Form): GetFamilyJson.Response {
        //TODO
        throw NotImplementedError()
    }

    @PostMapping("getAllMembers")
    fun getAllMembers(@RequestBody form: GetAllMembersJson.Form): GetAllMembersJson.Response {
        //TODO
        throw NotImplementedError()
    }
}