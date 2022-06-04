package com.badger.familyorgbe.service.tasks

import com.badger.familyorgbe.models.entity.task.*
import com.badger.familyorgbe.models.usual.task.Task
import com.badger.familyorgbe.repository.family.IFamilyRepository
import com.badger.familyorgbe.repository.tasks.IFamilySubtaskRepository
import com.badger.familyorgbe.repository.tasks.IFamilyTaskProductsRepository
import com.badger.familyorgbe.repository.tasks.IFamilyTaskRepository
import com.badger.familyorgbe.utils.betweenDayOfWeeks
import com.badger.familyorgbe.utils.toInstantAtZone
import kotlinx.coroutines.Dispatchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

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
        with(Dispatchers.IO) {
            tasksRepository.deleteById(taskId)
        }
    }

    @Transactional
    suspend fun checkSubtask(familyId: Long, taskId: Long, subtaskId: Long?, productId: Long?) {
        with(Dispatchers.IO) {
            familyRepository.getFamilyById(id = familyId)?.let { family ->
                val task = family.tasks.find { it.id == taskId }

                val subtasks = task?.subtasks?.map { subtask ->
                    if (subtask.id == subtaskId) {
                        subtask.copy(checked = !subtask.checked)
                    } else {
                        subtask
                    }
                }
                val products = task?.products?.map { product ->
                    if (product.id == productId) {
                        product.copy(checked = !product.checked)
                    } else {
                        product
                    }
                }
                familyRepository.save(family.copy(tasks = family.tasks.map { entity ->
                    if (entity.id == taskId) {
                        entity.copy(
                            subtasks = subtasks.orEmpty(),
                            products = products.orEmpty()
                        )
                    } else {
                        entity
                    }
                }))
            }
        }
    }

    @Transactional
    suspend fun changeTaskStatus(familyId: Long, taskId: Long, status: TaskStatus) {
        with(Dispatchers.IO) {
            familyRepository.getFamilyById(id = familyId)?.let { family ->
                val task = family.tasks.find { it.id == taskId }

                familyRepository.save(family.copy(tasks = family.tasks.map { entity ->
                    if (entity.id == taskId) {
                        entity.copy(
                            status = status
                        )
                    } else {
                        entity
                    }
                }))
            }
        }
    }

    @Transactional
    suspend fun updateFamilyTasksStatus(familyId: Long) {
        with(Dispatchers.IO) {
            familyRepository.getFamilyById(familyId)?.let { family ->
                family.tasks.map { task -> updateTaskStatus(familyId, task) }
            }
        }
    }

    @Transactional
    suspend fun updateTaskStatus(familyId: Long, task: TaskEntity) {
        if (task.status == TaskStatus.ACTIVE) {
            val finished = task.products.all(TaskProductEntity::checked) && task.subtasks.all(SubtaskEntity::checked)
            if (finished) {
                changeTaskStatus(familyId, task.id, TaskStatus.FINISHED)
            } else {
                val category = task.category.first()
                val failed = when (category.type) {
                    TaskCategoryType.ONE_SHOT -> {
                        false
                    }
                    TaskCategoryType.ONE_TIME -> {
                        (category.dateTime ?: 0) > System.currentTimeMillis()
                    }
                    TaskCategoryType.DAYS_OF_WEEK -> {
                        false
                    }
                    TaskCategoryType.EVERY_YEAR -> {
                        val now = LocalDateTime.now()
                        (category.dateTime ?: 0).toInstantAtZone().toLocalDateTime().withYear(now.year).isBefore(now)
                    }
                }
                if (failed) {
                    changeTaskStatus(familyId, task.id, TaskStatus.FAILED)
                }
            }
        }
    }
}