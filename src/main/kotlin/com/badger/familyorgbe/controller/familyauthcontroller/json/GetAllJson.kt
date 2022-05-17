package com.badger.familyorgbe.controller.familyauthcontroller.json

import com.badger.familyorgbe.models.usual.Family

class GetAllJson {

    class Form(

    )

    data class Response(
        val families: List<Family>,
        val invites: List<Family>
    )
}