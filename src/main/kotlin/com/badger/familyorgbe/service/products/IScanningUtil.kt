package com.badger.familyorgbe.service.products

import com.badger.familyorgbe.models.usual.Product

interface IScanningUtil {

    fun getProductsByCode(code: String): Result

    data class Result(
        val success: Boolean,
        val products: List<Product>
    )
}