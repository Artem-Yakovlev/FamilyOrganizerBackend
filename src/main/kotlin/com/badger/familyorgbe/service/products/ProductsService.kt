package com.badger.familyorgbe.service.products

import com.badger.familyorgbe.models.entity.ProductEntity
import com.badger.familyorgbe.models.usual.Product
import com.badger.familyorgbe.repository.family.IFamilyRepository
import com.badger.familyorgbe.repository.products.IProductsRepository
import com.badger.familyorgbe.utils.converters.convertToIdsList
import com.badger.familyorgbe.utils.converters.convertToString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductsService {

    @Autowired
    private lateinit var productRepository: IProductsRepository

    @Autowired
    private lateinit var familyRepository: IFamilyRepository

    private fun getFamilyById(familyId: Long) = familyRepository.getFamilyById(familyId)

    fun getAllProducts(productsIds: List<Long>) = productRepository.getAllByIds(productsIds).map(Product::fromEntity)

    fun addProducts(familyId: Long, products: List<Product>) = getFamilyById(familyId)?.let { entity ->
        val savedEntities = productRepository.saveAll(products.map(Product::toEntity))
        val actualIds = (entity.productsIds.convertToIdsList() + savedEntities.map(ProductEntity::id)).sorted()

        val savedEntity = familyRepository.save(
            entity.copy(productsIds = actualIds.convertToString())
        )
        return@let getAllProducts(savedEntity.productsIds.convertToIdsList())
    }

    fun deleteProducts(familyId: Long, deleteIds: List<Long>) = getFamilyById(familyId)?.let { entity ->
        productRepository.deleteAllById(deleteIds)
        val actualIds = entity.productsIds.convertToIdsList().filter { it !in deleteIds }.sorted().convertToString()

        val savedEntity = familyRepository.save(
            entity.copy(productsIds = actualIds)
        )
        return@let getAllProducts(savedEntity.productsIds.convertToIdsList())
    }
}