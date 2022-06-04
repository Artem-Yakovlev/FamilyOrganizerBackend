package com.badger.familyorgbe.service.tasks

import com.badger.familyorgbe.repository.tasks.IFamilyTaskRepository
import com.badger.familyorgbe.service.fcm.FirebaseMessagingService
import com.badger.familyorgbe.service.users.UserService
import com.badger.familyorgbe.utils.converters.convertToEmailList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class TaskNotificationsCheckerUtil {

    @Autowired
    private lateinit var notificationsStorage: INotificationStorage

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var tasksRepository: IFamilyTaskRepository

    @Autowired
    private lateinit var firebaseMessagingService: FirebaseMessagingService

    @Scheduled(fixedRate = CHECK_RATE)
    fun runExpirationChecker() {
        runBlocking {
            val matureTasks = notificationsStorage.getAllMatureTasks()
            with(Dispatchers.IO) {
                tasksRepository.getAllTasks()
            }.filter {
                it.id in matureTasks.map { pair -> pair.first }
            }.forEach { task ->
                val author = matureTasks.find { it.first == task.id }?.second
                val notificationsEmails = convertToEmailList(task.notifications).filter { it != author }

                userService.getTokensForEmails(notificationsEmails).forEach { tokenEntity ->
                    firebaseMessagingService.sendChangedTaskNotification(
                        token = tokenEntity.token,
                        taskTitle = task.title
                    )
                }
            }
        }
    }

    companion object {
        private const val CHECK_RATE = 1000 * 60 * 3L
    }
}