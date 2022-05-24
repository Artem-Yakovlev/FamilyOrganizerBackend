package com.badger.familyorgbe.controller.productscontroller

import com.badger.familyorgbe.controller.familycontroller.json.GetFamilyJson
import com.badger.familyorgbe.controller.productscontroller.json.AddProductsJson
import com.badger.familyorgbe.controller.productscontroller.json.DeleteProductsJson
import com.badger.familyorgbe.controller.productscontroller.json.GetProductsJson
import com.badger.familyorgbe.controller.productscontroller.json.UpdateProductsJson
import com.badger.familyorgbe.core.base.BaseAuthedController
import com.badger.familyorgbe.core.base.rest.BaseResponse
import com.badger.familyorgbe.core.base.rest.ResponseError
import com.badger.familyorgbe.repository.jwt.IJwtRepository
import com.badger.familyorgbe.service.family.FamilyService
import com.badger.familyorgbe.service.products.ProductsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductsController : BaseAuthedController() {

    @Autowired
    private lateinit var jwtRepository: IJwtRepository

    @Autowired
    private lateinit var productsService: ProductsService

    @Autowired
    private lateinit var familyService: FamilyService

    @PostMapping("getProducts")
    suspend fun getProducts(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: GetProductsJson.Form
    ): BaseResponse<GetProductsJson.Response> {
        val email = authHeader.getAuthEmail()

        return familyService.getFamilyById(form.familyId)
            ?.takeIf { family -> family.members.contains(email) }
            ?.let { family ->
                val productsIds = familyService.getAllProductsIdsForFamily(family.id)
                val products = productsService.getAllProducts(productsIds)
                BaseResponse(
                    data = GetProductsJson.Response(products = products)
                )
            } ?: BaseResponse(
            error = ResponseError.FAMILY_DOES_NOT_EXISTS,
            data = GetProductsJson.Response(products = emptyList())
        )
    }

    @PostMapping("addProducts")
    suspend fun addProducts(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: AddProductsJson.Form
    ): BaseResponse<AddProductsJson.Response> {
        val email = authHeader.getAuthEmail()

        return familyService.getFamilyById(form.familyId)
            ?.takeIf { family -> family.members.contains(email) }
            ?.let { family ->
                val products = productsService.addProducts(familyId = family.id, products = form.products).orEmpty()
                BaseResponse(
                    data = AddProductsJson.Response(products = products)
                )
            } ?: BaseResponse(
            error = ResponseError.FAMILY_DOES_NOT_EXISTS,
            data = AddProductsJson.Response(products = emptyList())
        )
    }

    @PostMapping("updateProducts")
    suspend fun updateProducts(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: UpdateProductsJson.Form
    ): BaseResponse<UpdateProductsJson.Response> {
        val email = authHeader.getAuthEmail()

        return familyService.getFamilyById(form.familyId)
            ?.takeIf { family -> family.members.contains(email) }
            ?.let { family ->
                val success = productsService.updateProduct(familyId = family.id, product = form.product)
                BaseResponse(
                    data = UpdateProductsJson.Response(success = success ?: false)
                )
            } ?: BaseResponse(
            error = ResponseError.FAMILY_DOES_NOT_EXISTS,
            data = UpdateProductsJson.Response(success = false)
        )
    }

    @PostMapping("deleteProduct")
    suspend fun deleteProduct(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authHeader: String,
        @RequestBody
        form: DeleteProductsJson.Form
    ): BaseResponse<DeleteProductsJson.Response> {
        val email = authHeader.getAuthEmail()

        return familyService.getFamilyById(form.familyId)
            ?.takeIf { family -> family.members.contains(email) }
            ?.let { family ->
                val products = productsService.deleteProducts(
                    familyId = family.id,
                    deleteIds = form.deleteIds
                ).orEmpty()

                BaseResponse(
                    data = DeleteProductsJson.Response(products = products)
                )
            } ?: BaseResponse(
            error = ResponseError.FAMILY_DOES_NOT_EXISTS,
            data = DeleteProductsJson.Response(products = emptyList())
        )
    }
}