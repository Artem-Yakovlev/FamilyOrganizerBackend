package com.badger.familyorgbe.models.entity.task

import com.badger.familyorgbe.models.entity.Measure
import javax.persistence.*

@Entity
@Table(name = "taskproducts")
class TaskProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "checked")
    val checked: Boolean,

    @Column(name = "title")
    val title: String,

    @Column(name = "amount")
    val amount: Double?,

    @Column(name = "measure")
    @Enumerated(value = EnumType.STRING)
    val measure: Measure?,

    @Column(name = "task_id", insertable = false, updatable = false)
    val taskId: Long = 0
)