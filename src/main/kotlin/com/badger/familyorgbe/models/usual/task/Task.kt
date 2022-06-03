package com.badger.familyorgbe.models.usual.task

import com.badger.familyorgbe.models.entity.task.TaskEntity
import com.badger.familyorgbe.models.entity.task.TaskStatus
import com.badger.familyorgbe.utils.converters.convertToEmailList
import com.badger.familyorgbe.utils.converters.convertToEmailString

data class Task(
    val id: Long = 0,
    val category: TaskCategory,
    val status: TaskStatus,
    val title: String,
    val description: String,
    val notifications: List<String>,
    val products: List<TaskProduct>,
    val subtasks: List<Subtask>
) {

    fun toSavingEntity() = TaskEntity(
        id = id,
        category = category.toEntity(),
        status = status,
        title = title,
        description = description,
        notifications = convertToEmailString(notifications),
        products = emptyList(),
        subtasks = emptyList()
    )

    companion object {
        fun fromEntity(entity: TaskEntity) = Task(
            id = entity.id,
            category = TaskCategory.fromEntity(entity.category),
            status = entity.status,
            title = entity.title,
            description = entity.description,
            notifications = convertToEmailList(entity.notifications),
            products = entity.products.map(TaskProduct::fromEntity),
            subtasks = entity.subtasks.map(Subtask::fromEntity)
        )
    }
}






