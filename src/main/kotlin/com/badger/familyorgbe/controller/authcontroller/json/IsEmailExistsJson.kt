package com.badger.familyorgbe.controller.authcontroller.json

class IsEmailExistsJson {

    data class Form(
        val email: String
    )

    data class Response(
        val email: String,
        val isExists: Boolean
    )
}

