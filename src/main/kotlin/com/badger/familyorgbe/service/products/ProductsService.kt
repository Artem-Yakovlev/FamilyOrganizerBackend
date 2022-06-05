package com.badger.familyorgbe.service.products

import com.badger.familyorgbe.models.entity.FamilyEntity
import com.badger.familyorgbe.models.entity.ProductEntity
import com.badger.familyorgbe.models.usual.Product
import com.badger.familyorgbe.repository.family.IFamilyRepository
import com.badger.familyorgbe.repository.products.IProductsRepository
import com.badger.familyorgbe.utils.converters.convertToIdsList
import com.badger.familyorgbe.utils.converters.convertToString
import kotlinx.coroutines.Dispatchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductsService {

    @Autowired
    private lateinit var productRepository: IProductsRepository

    @Autowired
    private lateinit var familyRepository: IFamilyRepository

    private fun getFamilyById(familyId: Long): FamilyEntity? = familyRepository.getFamilyById(familyId)

    suspend fun getAllProducts(familyId: Long) = with(Dispatchers.IO) {
        getFamilyById(familyId)?.products.orEmpty()
    }.map(Product::fromEntity)

    suspend fun addProducts(familyId: Long, products: List<Product>) = getFamilyById(familyId)?.let { family ->
        val savingProducts = products.map { product ->
            ProductEntity(
                name = product.name,
                quantity = product.quantity,
                measure = product.measure,
                category = product.category,
                expiryMillis = product.expiryMillis
            )
        }
        val savedEntity = familyRepository.save(
            family.copy(
                products = family.products + savingProducts
            )
        )

        return@let savedEntity.products.map(Product::fromEntity)
    }

    suspend fun updateProduct(familyId: Long, product: Product) = getFamilyById(familyId)?.let {
        val productEntity = product.toEntity()
        productRepository.save(productEntity)
        true
    }

    suspend fun deleteProducts(familyId: Long, deleteIds: List<Long>) = getFamilyById(familyId)?.let { entity ->
        productRepository.deleteAllById(deleteIds)

        return@let entity.products.filter { it.id !in deleteIds }.map(Product::fromEntity)
    }
}