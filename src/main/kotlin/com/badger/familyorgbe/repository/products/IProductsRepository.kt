package com.badger.familyorgbe.repository.products

import com.badger.familyorgbe.models.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface IProductsRepository : JpaRepository<ProductEntity, Long> {

    @Query("select product from ProductEntity product where product.id in :ids")
    fun getAllByIds(
        @Param(value = "ids") ids: List<Long>
    ): List<ProductEntity>


}
