package com.badger.familyorgbe.models.entity.task

import javax.persistence.*

@Entity
@Table(name = "taskcategories")
class TaskCategoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    val type: TaskCategoryType,

    @Column(name = "time_important")
    val isTimeImportant: Boolean = false,

    @Column(name = "date_time")
    val dateTime: Long? = null,

    @Column(name = "time")
    val time: Long? = null,

    @Column(name = "days")
    val days: String? = null,

    @Column(name = "clearing_time")
    val clearingTime: Long = 0,

    @Column(name = "task_id", insertable = false, updatable = false)
    val taskId: Long = 0
)

enum class TaskCategoryType {
    ONE_SHOT,
    ONE_TIME,
    DAYS_OF_WEEK,
    EVERY_YEAR
}
