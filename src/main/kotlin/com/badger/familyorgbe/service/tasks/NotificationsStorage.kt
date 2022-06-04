package com.badger.familyorgbe.service.tasks

class NotificationsStorage : INotificationStorage {

    private val storage = HashMap<Long, Pair<Long, String>>()

    override fun getAllMatureTasks(): List<Pair<Long, String>> {
        return storage
            .filterValues { System.currentTimeMillis() - it.first > MATURE_BORDER }
            .map { it.key to it.value.second }
    }

    override fun setChangedTaskTime(taskId: Long, authorEmail: String) {
        storage[taskId] = System.currentTimeMillis() to authorEmail
    }

    companion object {
        private const val MATURE_BORDER = 5000L
    }
}