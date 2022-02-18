package com.badger.familyorgbe.security

private const val BEARER_PREFIX = "Bearer "
private const val SPACE_REGEX = "\\s+"
private const val BEARER_WORDS_SIZE = 2

fun String.isBearer() = startsWith(BEARER_PREFIX) && split(regex = SPACE_REGEX.toRegex()).size == BEARER_WORDS_SIZE

fun String.getTokenFromBearer() = split(regex = SPACE_REGEX.toRegex()).getOrNull(1)