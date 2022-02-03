package com.badger.familyorgbe.utils

import com.badger.familyorgbe.models.entity.Role

object RolesHelper {

    private const val USER_ROLE_NAME = "ROLE_USER"
    private const val USER_ROLE_ID = 1L

    val userRole by lazy {
        Role(USER_ROLE_ID, USER_ROLE_NAME)
    }
}