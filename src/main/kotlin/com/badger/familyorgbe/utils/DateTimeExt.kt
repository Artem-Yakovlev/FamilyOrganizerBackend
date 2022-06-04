package com.badger.familyorgbe.utils

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

fun Long.toInstantAtZone(): ZonedDateTime =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault())

fun betweenDayOfWeeks(currentDayOfWeek: Int, dayOfWeek: Int): Int {
    return if (currentDayOfWeek <= dayOfWeek) {
        dayOfWeek - currentDayOfWeek
    } else {
        dayOfWeek + 7 - currentDayOfWeek
    }
}