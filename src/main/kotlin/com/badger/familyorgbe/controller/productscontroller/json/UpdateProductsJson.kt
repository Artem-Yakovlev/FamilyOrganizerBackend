package com.badger.familyorgbe.controller.productscontroller.json

import com.badger.familyorgbe.models.usual.Product

class UpdateProductsJson {

    data class Form(
        val familyId: Long,
        val product: Product
    )

    data class Response(
        val success: Boolean
    )
}