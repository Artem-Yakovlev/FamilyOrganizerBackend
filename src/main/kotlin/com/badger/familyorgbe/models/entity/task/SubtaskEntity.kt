package com.badger.familyorgbe.models.entity.task

import javax.persistence.*

@Entity
@Table(name = "tasksubtasks")
class SubtaskEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "text")
    val text: String,

    @Column(name = "checked")
    val checked: Boolean,

    @Column(name = "task_id", insertable = false, updatable = false)
    val taskId: Long = 0
)