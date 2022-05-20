package com.badger.familyorgbe.controller.familycontroller.json

import com.badger.familyorgbe.models.usual.OnlineUser

class GetAllMembersJson {

    data class Form(
        val familyId: Long
    )

    data class Response(
        val onlineUsers: List<OnlineUser>
    )
}