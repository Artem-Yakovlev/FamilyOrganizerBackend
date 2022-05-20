package com.badger.familyorgbe.controller.usercontroller.json

class UpdateStatusJson {

    data class Form(
        val status: String
    )

    data class Response(
        val success: Boolean
    )
}