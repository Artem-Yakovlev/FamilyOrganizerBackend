package com.badger.familyorgbe.models.entity.task

import javax.persistence.*

@Entity
@Table(name = "tasks")
class TaskEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    val category: TaskCategory,

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    val status: TaskStatus,

    @Column(name = "title")
    val title: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "notifications")
    val notifications: String,

    @OneToMany(mappedBy = "task")
    @Column(name = "products")
    val products: List<TaskProduct>,

    @OneToMany(mappedBy = "task")
    @Column(name = "subtasks")
    val subtasks: List<SubtaskEntity>
)






