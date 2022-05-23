package com.badger.familyorgbe.controller.productscontroller.json

import com.badger.familyorgbe.models.usual.Product

class GetProductsJson {

    data class Form(
        val familyId: Long,
    )

    data class Response(
        val products: List<Product>
    )
}