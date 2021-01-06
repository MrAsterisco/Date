package io.github.mrasterisco.Date.utils

import platform.Foundation.NSTimeZone
import platform.Foundation.localTimeZone
import platform.Foundation.timeZoneWithName

@Suppress("unused")
actual class TimeZoneUtils {

    actual companion object {

        actual fun getDefault(): Any {
            return NSTimeZone.localTimeZone
        }

        actual fun getTimeZone(identifier: String): Any? {
            return NSTimeZone.timeZoneWithName(identifier)
        }

        actual fun getIdentifier(timeZone: Any): String {
            return (timeZone as NSTimeZone).name
        }

    }

}