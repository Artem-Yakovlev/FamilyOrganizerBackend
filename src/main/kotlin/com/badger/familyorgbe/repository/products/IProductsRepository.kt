package com.badger.familyorgbe.repository.products

import com.badger.familyorgbe.models.entity.FamilyEntity
import com.badger.familyorgbe.models.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IProductsRepository : JpaRepository<ProductEntity, Long> {
}
