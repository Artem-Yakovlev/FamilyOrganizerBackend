package com.badger.familyorgbe.models.usual.task

import com.badger.familyorgbe.models.entity.task.TaskCategoryEntity
import com.badger.familyorgbe.models.entity.task.TaskCategoryType
import com.badger.familyorgbe.utils.converters.convertToIdsList
import com.badger.familyorgbe.utils.converters.convertToNumbersList
import com.badger.familyorgbe.utils.converters.convertToString
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import javax.persistence.*

class TaskCategory(
    val type: TaskCategoryType,
    val isTimeImportant: Boolean = false,
    val dateTime: Long? = null,
    val time: Long? = null,
    val days: List<Int>? = null,
) {
    fun toEntity(taskId: Long) = TaskCategoryEntity(
        type = type,
        isTimeImportant = isTimeImportant,
        days = days?.map(Int::toLong)?.convertToString(),
        dateTime = dateTime,
        time = time,
        taskId = taskId
    )

    companion object {
        fun fromEntity(entity: TaskCategoryEntity) = TaskCategory(
            type = entity.type,
            isTimeImportant = entity.isTimeImportant,
            dateTime = entity.dateTime,
            days = entity.days?.convertToNumbersList().orEmpty(),
            time = entity.time
        )
    }
}

