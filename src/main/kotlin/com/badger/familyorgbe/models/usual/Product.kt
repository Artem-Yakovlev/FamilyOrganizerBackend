package com.badger.familyorgbe.models.usual

import com.badger.familyorgbe.models.entity.Category
import com.badger.familyorgbe.models.entity.Measure
import com.badger.familyorgbe.models.entity.ProductEntity
import java.time.LocalDateTime

data class Product(
    val id: Long,
    val name: String,
    val quantity: Double?,
    val measure: Measure?,
    val category: Category,
    val expiryDate: LocalDateTime?
) {

    fun toEntity() = ProductEntity(
        id = id,
        name = name,
        quantity = quantity,
        measure = measure,
        category = category,
        expiryDate = expiryDate
    )

    companion object {
        fun fromEntity(entity: ProductEntity) = Product(
            id = entity.id,
            name = entity.name,
            quantity = entity.quantity,
            measure = entity.measure,
            category = entity.category,
            expiryDate = entity.expiryDate
        )
    }
}