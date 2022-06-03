package com.badger.familyorgbe.models.entity.task

import javax.persistence.*

@Entity
@Table(name = "tasks")
class TaskEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @MapsId
    val category: TaskCategoryEntity,

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    val status: TaskStatus,

    @Column(name = "title")
    val title: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "notifications")
    val notifications: String,

    @Column(name = "products")
    @OneToMany
    @JoinColumn(name = "task_id", nullable = false)
    val products: List<TaskProductEntity>,

    @Column(name = "subtasks")
    @OneToMany
    @JoinColumn(name = "task_id", nullable = false)
    val subtasks: List<SubtaskEntity>
)






