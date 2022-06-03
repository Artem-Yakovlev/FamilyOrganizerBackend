package com.badger.familyorgbe.models.entity.task

import javax.persistence.*

@Entity
@Table(name = "taskcategories")
class TaskCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    val type: TaskCategoryType,

    @Column(name = "time_important")
    val isTimeImportant: Boolean,

    @Column(name = "date_time")
    val dateTime: Long?,

    @Column(name = "time")
    val time: Long?,

    @Column(name = "days")
    val days: String?,
)

enum class TaskCategoryType {
    ONE_SHOT,
    ONE_TIME,
    DAYS_OF_WEEK,
    EVERY_WEEK
}
