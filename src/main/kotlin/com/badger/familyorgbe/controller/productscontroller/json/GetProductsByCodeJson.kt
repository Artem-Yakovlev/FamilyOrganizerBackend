package com.badger.familyorgbe.controller.productscontroller.json

import com.badger.familyorgbe.models.usual.Product

class GetProductsByCodeJson {

    data class Form(
        val code: String,
    )

    data class Response(
        val products: List<Product>
    )
}