package com.badger.familyorgbe.service.users

class OnlineStorage : IOnlineStorage {

    private val storage = mutableMapOf<String, Long>()

    override fun registerRequest(email: String) {
        storage[email] = System.currentTimeMillis()
    }

    override fun getLastRegisterTime(email: String): Long? {
        return storage[email]
    }
}