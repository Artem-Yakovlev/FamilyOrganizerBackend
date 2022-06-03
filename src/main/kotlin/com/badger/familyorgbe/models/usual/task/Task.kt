package com.badger.familyorgbe.models.usual.task

import com.badger.familyorgbe.models.entity.task.TaskEntity
import com.badger.familyorgbe.models.entity.task.TaskStatus

data class Task(
    val id: Long = 0,
    val category: TaskCategory,
    val status: TaskStatus,
    val title: String,
    val description: String,
    val notifications: String,
    val products: List<TaskProduct>,
    val subtasks: List<Subtask>
) {

    companion object {
        fun fromEntity(entity: TaskEntity) = Task(
            id = entity.id,
            category = TaskCategory.fromEntity(entity.category),
            status = entity.status,
            title = entity.title,
            description = entity.description,
            notifications = entity.notifications,
            products = entity.products.map(TaskProduct::fromEntity),
            subtasks = entity.subtasks.map(Subtask::fromEntity)
        )
    }
}






