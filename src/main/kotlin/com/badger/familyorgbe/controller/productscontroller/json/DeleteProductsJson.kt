package com.badger.familyorgbe.controller.productscontroller.json

import com.badger.familyorgbe.models.usual.Product

class DeleteProductsJson {

    data class Form(
        val familyId: Long,
        val deleteIds: List<Long>
    )

    data class Response(
        val products: List<Product>
    )
}