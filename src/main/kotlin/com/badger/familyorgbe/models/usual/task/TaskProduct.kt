package com.badger.familyorgbe.models.usual.task

import com.badger.familyorgbe.models.entity.Measure
import com.badger.familyorgbe.models.entity.task.TaskProductEntity
import javax.persistence.*

data class TaskProduct(
    val id: Long?,
    val checked: Boolean,
    val title: String,
    val amount: Double?,
    val measure: Measure?
) {
    fun toEntity() = TaskProductEntity(
        checked = checked,
        title = title,
        amount = amount,
        measure = measure
    )

    companion object {
        fun fromEntity(entity: TaskProductEntity) = TaskProduct(
            id = entity.id,
            checked = entity.checked,
            title = entity.title,
            amount = entity.amount,
            measure = entity.measure
        )
    }
}