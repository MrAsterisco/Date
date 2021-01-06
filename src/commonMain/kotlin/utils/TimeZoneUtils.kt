package io.github.mrasterisco.Date.utils

expect class TimeZoneUtils {

    companion object {

        fun getDefault(): Any

        fun getTimeZone(identifier: String): Any?

        fun getIdentifier(timeZone: Any): String

    }

}