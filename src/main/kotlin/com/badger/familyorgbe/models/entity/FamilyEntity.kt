package com.badger.familyorgbe.models.entity

import com.badger.familyorgbe.utils.converters.EmailListToStringConverter
import javax.persistence.*

@Entity
@Table(name = "families")
data class FamilyEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "name")
    val name: String,

//    @Column(name = "fridge_id")
//    val fridgeId: String,

    @Column(name = "members")
    @Convert(converter = EmailListToStringConverter::class)
    val members: List<String>,

    @Column(name = "members")
    @Convert(converter = EmailListToStringConverter::class)
    val invites: List<String>
)