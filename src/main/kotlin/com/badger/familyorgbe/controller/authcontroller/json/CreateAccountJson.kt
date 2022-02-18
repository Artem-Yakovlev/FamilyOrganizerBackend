package com.badger.familyorgbe.controller.authcontroller.json

class CreateAccountJson {

    data class Form(
        val email: String
    )

    class Response(
        val success: Boolean,
    )
}