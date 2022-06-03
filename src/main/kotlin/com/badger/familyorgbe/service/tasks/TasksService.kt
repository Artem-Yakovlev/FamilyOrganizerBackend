package com.badger.familyorgbe.service.tasks

import com.badger.familyorgbe.infoLog
import com.badger.familyorgbe.models.entity.task.TaskEntity
import com.badger.familyorgbe.repository.tasks.IFamilyTaskRepository
import kotlinx.coroutines.Dispatchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TasksService {

    @Autowired
    private lateinit var tasksRepository: IFamilyTaskRepository

    suspend fun createFamilyTask(task: TaskEntity) {
        val saved = with(Dispatchers.IO) { tasksRepository.save(task) }
        infoLog(saved.toString())
    }
}