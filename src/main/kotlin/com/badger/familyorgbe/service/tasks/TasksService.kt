package com.badger.familyorgbe.service.tasks

import com.badger.familyorgbe.infoLog
import com.badger.familyorgbe.models.usual.task.Subtask
import com.badger.familyorgbe.models.usual.task.Task
import com.badger.familyorgbe.repository.tasks.IFamilySubtaskRepository
import com.badger.familyorgbe.repository.tasks.IFamilyTaskProductsRepository
import com.badger.familyorgbe.repository.tasks.IFamilyTaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TasksService {

    @Autowired
    private lateinit var tasksRepository: IFamilyTaskRepository

    @Autowired
    private lateinit var subtasksRepository: IFamilySubtaskRepository

    @Autowired
    private lateinit var taskProductsRepository: IFamilyTaskProductsRepository

    @Transactional
    suspend fun createFamilyTask(task: Task) = coroutineScope {
        val savedTaskEntity = with(Dispatchers.IO) { tasksRepository.save(task.toSavingEntity()) }
    }

    suspend fun getById(taskId: Long): Task? {
        return with(Dispatchers.IO) { Task.fromEntity(tasksRepository.getById(taskId)) }
    }
}