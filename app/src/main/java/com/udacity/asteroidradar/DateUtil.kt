package com.udacity.asteroidradar

import java.text.SimpleDateFormat
import java.util.*

private val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.ROOT)

private val defaultTimeZone = TimeZone.getDefault()
private val nasaTimeZone = TimeZone.getTimeZone("US/Eastern")

fun getCurrentDate(plus: Int = 0): Date = with(Calendar.getInstance()) {
    add(Calendar.DAY_OF_YEAR, plus)
    return@with time
}

val Date.formatWithDefaultTimeZone: String
    get() = dateFormat.apply { timeZone = defaultTimeZone }.format(this)

val Date.formatWithNasaTimeZone: String
    get() = dateFormat.apply { timeZone = nasaTimeZone }.format(this)
