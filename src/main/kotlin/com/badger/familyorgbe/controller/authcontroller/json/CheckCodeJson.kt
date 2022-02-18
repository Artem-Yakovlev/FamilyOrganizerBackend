package com.badger.familyorgbe.controller.authcontroller.json

class CheckCodeJson {

    data class Form(
        val email: String,
        val code: String
    )

    data class Response(
        val email: String,
        val success: Boolean,
        val token: String?
    )

}