package com.badger.familyorgbe.core

fun <T> T.toSingleItemList() = listOf(this)

public fun <T> List<T>.second(): T {
    if (isEmpty())
        throw NoSuchElementException("List is empty.")
    return this[1]
}