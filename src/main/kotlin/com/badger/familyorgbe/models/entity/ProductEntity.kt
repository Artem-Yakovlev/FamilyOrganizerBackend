package com.badger.familyorgbe.models.entity

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

    @Column(name = "expiryMillis")
    val expiryMillis: Long?,

    @Column(name = "family_id", insertable = false, updatable = false)
    val familyId: Long = 0
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
