package com.badger.familyorgbe.service.tasks

interface INotificationStorage {

    fun getAllMatureTasks(): List<Pair<Long, String>>

    fun setChangedTaskTime(taskId: Long, authorEmail: String)
}