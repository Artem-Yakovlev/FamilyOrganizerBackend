package com.badger.familyorgbe.models.entity

import javax.persistence.*

@Entity
@Table(name = "messages")
class Message(

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "title")
    val title: String,

    @Column(name = "message")
    val message: String
)