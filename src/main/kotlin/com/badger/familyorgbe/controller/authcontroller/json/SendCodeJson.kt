package com.badger.familyorgbe.controller.authcontroller.json

class SendCodeJson {

    data class Form(
        val email: String
    )

    data class Response(
        val success: Boolean
    )

}

