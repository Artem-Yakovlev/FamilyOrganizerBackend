package com.badger.familyorgbe.controller.usercontroller.json

class SetTokenJson {

    data class Form(
        val token: String
    )

    data class Response(
        val success: Boolean
    )
}