package com.badger.familyorgbe.controller.familycontroller.json

class InviteFamilyMemberJson {

    data class Form(
        val familyId: Long,
        val memberEmail: String
    )

    data class Response(
        val success: Boolean
    )

}