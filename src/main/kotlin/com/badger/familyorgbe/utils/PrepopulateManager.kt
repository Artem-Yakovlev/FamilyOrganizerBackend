package com.badger.familyorgbe.utils

import com.badger.familyorgbe.repository.roles.IRolesRepository

class PrepopulateManager(
    private val rolesRepository: IRolesRepository
) {

    fun prepopulate() {
        rolesRepository.save(RolesHelper.userRole)
    }
}