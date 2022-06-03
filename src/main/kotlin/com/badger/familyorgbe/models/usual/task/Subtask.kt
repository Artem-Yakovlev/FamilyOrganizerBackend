package com.badger.familyorgbe.models.usual.task

import com.badger.familyorgbe.models.entity.task.SubtaskEntity

data class Subtask(
    val id: Long?,
    val text: String,
    val checked: Boolean
) {
    fun toEntity() = SubtaskEntity(
        text = text,
        checked = checked,
    )

    companion object {
        fun fromEntity(entity: SubtaskEntity) = Subtask(
            id = entity.id,
            text = entity.text,
            checked = entity.checked
        )
    }
}