package com.badger.familyorgbe.service.tasks

import com.badger.familyorgbe.infoLog
import com.badger.familyorgbe.models.entity.task.TaskEntity
import com.badger.familyorgbe.models.entity.task.TaskStatus
import com.badger.familyorgbe.models.usual.task.Subtask
import com.badger.familyorgbe.models.usual.task.Task
import com.badger.familyorgbe.repository.family.IFamilyRepository
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
    private lateinit var familyRepository: IFamilyRepository

    @Autowired
    private lateinit var subtasksRepository: IFamilySubtaskRepository

    @Autowired
    private lateinit var taskProductsRepository: IFamilyTaskProductsRepository

    @Transactional
    suspend fun createFamilyTask(familyId: Long, task: Task) {
        with(Dispatchers.IO) {
            familyRepository.getFamilyById(id = familyId)?.let { family ->
                familyRepository.save(family.copy(tasks = family.tasks + task.toSavingEntity()))
            }
        }
    }

    @Transactional
    suspend fun modifyFamilyTask(familyId: Long, task: Task) {
        with(Dispatchers.IO) {
            familyRepository.getFamilyById(id = familyId)?.let { family ->
                familyRepository.save(family.copy(tasks = family.tasks.map { entity ->
                    if (task.id == entity.id) {
                        task.toSavingEntity()
                    } else {
                        entity
                    }
                }))
            }
        }
    }


    suspend fun getById(taskId: Long): Task? {
        return with(Dispatchers.IO) { Task.fromEntity(tasksRepository.getById(taskId)) }
    }

    suspend fun getAllTasksByFamilyId(familyId: Long): List<Task> {
        return with(Dispatchers.IO) { tasksRepository.getAllTasksForFamily(familyId).map(Task::fromEntity) }
    }

    @Transactional
    suspend fun deleteFamilyTaskById(familyId: Long, taskId: Long) {
        with(Dispatchers.IO) {}
    }

    @Transactional
    suspend fun changeTaskStatus(familyId: Long, taskId: Task, status: TaskStatus) {
        with(Dispatchers.IO) {}
    }
}