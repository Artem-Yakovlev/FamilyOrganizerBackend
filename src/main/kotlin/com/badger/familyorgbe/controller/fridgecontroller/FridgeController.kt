package com.badger.familyorgbe.controller.fridgecontroller

import com.badger.familyorgbe.controller.fridgecontroller.json.AddProductsJson
import com.badger.familyorgbe.controller.fridgecontroller.json.DeleteProductsJson
import com.badger.familyorgbe.controller.fridgecontroller.json.UpdateProductsJson
import com.badger.familyorgbe.core.base.BaseController
import com.badger.familyorgbe.repository.jwt.IJwtRepository
import com.badger.familyorgbe.service.fridge.FridgeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/fridge")
class FridgeController : BaseController() {

    @Autowired
    private lateinit var jwtRepository: IJwtRepository

    @Autowired
    private lateinit var fridgeService: FridgeService


    @PostMapping("addProducts")
    fun addProducts(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: AddProductsJson.Form
    ): AddProductsJson.Response {
        TODO()
    }

    @PostMapping("updateProducts")
    fun updateProducts(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: UpdateProductsJson.Form
    ): UpdateProductsJson.Response {
        TODO()
    }

    @PostMapping("deleteProduct")
    fun deleteProduct(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: DeleteProductsJson.Form
    ): DeleteProductsJson.Response {
        TODO()
    }
}