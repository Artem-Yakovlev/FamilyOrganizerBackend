package com.badger.familyorgbe.utils.converters

private const val DELIMITER = ";"

fun convertToEmailString(attribute: List<String>?): String {
    return attribute?.joinToString(DELIMITER).orEmpty()
}

fun convertToEmailList(dbData: String?): List<String> {
    return dbData?.split(DELIMITER) ?: emptyList()
}


fun String.convertToIdsList() = split(DELIMITER).mapNotNull(String::toLongOrNull)
fun String.convertToNumbersList() = split(DELIMITER).mapNotNull(String::toIntOrNull)

fun List<Long>.convertToString() = sorted().distinct().joinToString(DELIMITER)