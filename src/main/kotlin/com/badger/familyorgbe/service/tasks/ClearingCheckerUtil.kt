package com.badger.familyorgbe.service.tasks

import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ClearingCheckerUtil {

    @Autowired
    private lateinit var tasksService: TasksService

    @Scheduled(fixedRate = CLEARING_CHECK_RATE)
    fun runExpirationChecker() {
        runBlocking {
            tasksService.clearTasksIfNeed()
        }
    }

    companion object {
        private const val CLEARING_CHECK_RATE = 1000 * 60 * 15L
    }
}