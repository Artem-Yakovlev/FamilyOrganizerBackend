package com.badger.familyorgbe.models.entity

import javax.persistence.*

@Entity
@Table(name = "families")
class FamilyEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "name")
    val name: String,
    @Column(name = "fridge_id")
    val fridgeId: String
)