package com.badger.familyorgbe.models.usual.task

import com.badger.familyorgbe.models.entity.task.SubtaskEntity

data class Subtask(
    val id: Long?,
    val text: String,
    val checked: Boolean
) {
    fun toEntity(taskId: Long) = SubtaskEntity(
        id = id ?: 0,
        text = text,
        checked = checked,
        taskId = taskId
    )

    companion object {
        fun fromEntity(entity: SubtaskEntity) = Subtask(
            id = entity.id,
            text = entity.text,
            checked = entity.checked
        )
    }
}