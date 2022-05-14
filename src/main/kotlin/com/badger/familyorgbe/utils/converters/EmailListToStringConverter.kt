package com.badger.familyorgbe.utils.converters

import javax.persistence.AttributeConverter


class EmailListToStringConverter : AttributeConverter<List<String>, String> {

    companion object {
        private const val DELIMITER = ";"
    }

    override fun convertToDatabaseColumn(attribute: List<String>?): String {
        return attribute?.joinToString(DELIMITER).orEmpty()
    }

    override fun convertToEntityAttribute(dbData: String?): List<String> {
        return dbData?.split(DELIMITER) ?: emptyList()
    }
}