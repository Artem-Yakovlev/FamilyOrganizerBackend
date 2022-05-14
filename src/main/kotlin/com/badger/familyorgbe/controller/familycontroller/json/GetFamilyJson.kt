package com.badger.familyorgbe.controller.familycontroller.json

import com.badger.familyorgbe.models.usual.Family

class GetFamilyJson {

    data class Form(
        val familyId: Long
    )

    data class Response(
        val family: Family?
    )
}