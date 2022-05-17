package com.badger.familyorgbe.service.users

interface IOnlineStorage {

    fun registerRequest(email: String)

    fun getLastRegisterTime(email: String) : Long?
}