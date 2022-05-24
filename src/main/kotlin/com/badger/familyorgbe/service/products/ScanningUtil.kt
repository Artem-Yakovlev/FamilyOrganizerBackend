package com.badger.familyorgbe.service.products

import com.badger.familyorgbe.models.entity.Category
import com.badger.familyorgbe.models.usual.Product
import kotlin.random.Random

class ScanningUtil : IScanningUtil {

    override fun getProductsByCode(code: String): IScanningUtil.Result {
        return IScanningUtil.Result(
            success = true,
            products = listOf(
                Product(
                    id = Random.nextLong(),
                    name = "Gamagong",
                    quantity = null,
                    measure = null,
                    category = Category.DEFAULT,
                    expiryMillis = null
                )
            )
        )
    }
}