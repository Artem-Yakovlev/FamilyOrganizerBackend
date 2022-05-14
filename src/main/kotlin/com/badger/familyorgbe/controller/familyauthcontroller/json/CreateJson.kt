package com.badger.familyorgbe.controller.familyauthcontroller.json

import com.badger.familyorgbe.models.usual.Family

class CreateJson {

    data class Form(
        val familyName: String
    )

    data class Response(
        val family: Family
    )
}