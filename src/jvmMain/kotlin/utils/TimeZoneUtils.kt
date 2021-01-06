package io.github.mrasterisco.Date.utils

import java.util.*

@Suppress("unused")
actual class TimeZoneUtils {

    actual companion object {

        actual fun getDefault(): Any {
            return TimeZone.getDefault()
        }

        actual fun getTimeZone(identifier: String): Any? {
            return try {
                TimeZone.getTimeZone(identifier)
            } catch (exc: Throwable) {
                null
            }
        }

        actual fun getIdentifier(timeZone: Any): String {
            return (timeZone as TimeZone).id
        }

    }

}