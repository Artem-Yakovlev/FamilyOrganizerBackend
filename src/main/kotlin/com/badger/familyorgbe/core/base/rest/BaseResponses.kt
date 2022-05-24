package com.badger.familyorgbe.core.base.rest

enum class ResponseError {
    NONE,
    INVALID_SCANNING_CODE,
    FAMILY_DOES_NOT_EXISTS
}

data class BaseResponse<T>(
    val error: ResponseError = ResponseError.NONE,
    val data: T
)