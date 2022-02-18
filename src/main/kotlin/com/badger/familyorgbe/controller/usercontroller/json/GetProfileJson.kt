package com.badger.familyorgbe.controller.usercontroller.json

import com.badger.familyorgbe.models.usual.User

class GetProfileJson {

    class Form

    data class Response(
        val user: User?
    )
}
