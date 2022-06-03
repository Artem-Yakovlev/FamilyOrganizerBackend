package com.badger.familyorgbe.models.entity.task

import com.badger.familyorgbe.models.entity.Measure
import javax.persistence.*

@Entity
@Table(name = "taskproducts")
class TaskProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "checked")
    val checked: Boolean,

    @Column(name = "title")
    val title: String,

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    val task: TaskEntity,

    @Column(name = "amount")
    val amount: Double?,

    @Column(name = "measure")
    @Enumerated(value = EnumType.STRING)
    val measure: Measure?
)