package com.badger.familyorgbe.core.base.rest

enum class ResponseError {
    NONE,
    FAMILY_MEMBER_DOES_NOT_EXIST,
    INVALID_SCANNING_CODE,
    FAMILY_DOES_NOT_EXISTS,
    USER_ALREADY_IN_FAMILY,
    USER_ALREADY_INVITED,
}

data class BaseResponse<T>(
    val error: ResponseError = ResponseError.NONE,
    val data: T
)