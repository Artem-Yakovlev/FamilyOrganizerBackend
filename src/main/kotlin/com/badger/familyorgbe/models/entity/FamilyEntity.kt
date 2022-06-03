package com.badger.familyorgbe.models.entity

import com.badger.familyorgbe.models.entity.task.TaskCategoryEntity
import com.badger.familyorgbe.models.entity.task.TaskEntity
import javax.persistence.*

@Entity
@Table(name = "families")
data class FamilyEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "name")
    val name: String,

    @Column(name = "members")
    val members: String,

    @Column(name = "invites")
    val invites: String,

    @Column(name = "products_ids")
    val productsIds: String?,

    @Column(name = "tasks")
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "family_id", nullable = false)
    val tasks: List<TaskEntity>,
)