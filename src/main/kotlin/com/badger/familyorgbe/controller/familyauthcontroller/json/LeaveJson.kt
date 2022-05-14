package com.badger.familyorgbe.controller.familyauthcontroller.json

import com.badger.familyorgbe.models.usual.Family

class LeaveJson {

    data class Form(
        val familyId: Long
    )

    data class Response(
        val success: Boolean,
        val family: Family?
    ) {
        companion object {
            fun successful(family: Family) = Response(success = true, family = family)
            val unsuccessful = Response(success = false, family = null)
        }
    }
}