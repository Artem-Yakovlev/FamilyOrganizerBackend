package com.badger.familyorgbe.controller.productscontroller.json

import com.badger.familyorgbe.models.usual.Product

class AddProductsJson {

    data class Form(
        val familyId: Long,
        val products: List<Product>
    )

    data class Response(
        val products: List<Product>
    )
}