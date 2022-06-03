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

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    val task: TaskEntity,

    @Column(name = "checked")
    val checked: Boolean
)