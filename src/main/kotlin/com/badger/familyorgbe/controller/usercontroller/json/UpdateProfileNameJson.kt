package com.badger.familyorgbe.controller.usercontroller.json

import com.badger.familyorgbe.models.usual.User

class UpdateProfileNameJson {

    class Form(
        val updatedName: String
    )

    class Response(
        val user: User
    )
}