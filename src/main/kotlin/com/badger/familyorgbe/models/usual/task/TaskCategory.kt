package com.badger.familyorgbe.models.usual.task

import com.badger.familyorgbe.models.entity.task.TaskCategoryEntity
import com.badger.familyorgbe.models.entity.task.TaskCategoryType
import com.badger.familyorgbe.utils.converters.convertToIdsList
import com.badger.familyorgbe.utils.converters.convertToString
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

sealed class TaskCategory {
    abstract val isTimeImportant: Boolean

    object OneShot : TaskCategory() {
        override val isTimeImportant = false
    }

    data class OneTime(
        val localDateTime: Long,
        override val isTimeImportant: Boolean
    ) : TaskCategory()

    sealed class Recurring : TaskCategory() {

        data class DaysOfWeek(
            val days: List<Long>,
            val time: Long,
            override val isTimeImportant: Boolean
        ) : Recurring()

        data class EveryYear(
            val localDateTime: Long,
            override val isTimeImportant: Boolean
        ) : Recurring()
    }

    fun toEntity(taskId: Long) = when (this) {
        is OneShot -> TaskCategoryEntity(
            type = TaskCategoryType.ONE_SHOT,
            isTimeImportant = isTimeImportant,
            taskId = taskId
        )
        is OneTime -> TaskCategoryEntity(
            type = TaskCategoryType.ONE_TIME,
            dateTime = localDateTime,
            isTimeImportant = isTimeImportant,
            taskId = taskId

        )
        is Recurring.DaysOfWeek -> TaskCategoryEntity(
            type = TaskCategoryType.DAYS_OF_WEEK,
            days = days.convertToString(),
            time = time,
            isTimeImportant = isTimeImportant,
            taskId = taskId
        )
        is Recurring.EveryYear -> TaskCategoryEntity(
            type = TaskCategoryType.EVERY_WEEK,
            dateTime = localDateTime,
            isTimeImportant = isTimeImportant,
            taskId = taskId
        )
    }

    companion object {
        fun fromEntity(entity: TaskCategoryEntity) = when (entity.type) {
            TaskCategoryType.ONE_SHOT -> OneShot
            TaskCategoryType.ONE_TIME -> OneTime(
                localDateTime = entity.dateTime ?: 0,
                isTimeImportant = entity.isTimeImportant
            )
            TaskCategoryType.DAYS_OF_WEEK -> Recurring.DaysOfWeek(
                days = entity.days?.convertToIdsList().orEmpty(),
                time = entity.time ?: 0,
                isTimeImportant = entity.isTimeImportant
            )
            TaskCategoryType.EVERY_WEEK -> Recurring.EveryYear(
                localDateTime = entity.dateTime ?: 0,
                isTimeImportant = entity.isTimeImportant
            )
        }

    }
}

