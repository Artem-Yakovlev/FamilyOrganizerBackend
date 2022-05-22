package com.badger.familyorgbe.models.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "products")
class ProductEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "name")
    val name: String,

    @Column(name = "quantity")
    val quantity: Double?,

    @Enumerated(EnumType.STRING)
    @Column(name = "measure")
    val measure: Measure?,

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    val category: Category,

    @Column(name = "expiryDate")
    val expiryDate: LocalDateTime?
)

enum class Measure {
    LITER,
    KILOGRAM,
    THINGS
}

enum class Category {
    JUNK,
    HEALTHY,
    DEFAULT,
    OTHER
}
