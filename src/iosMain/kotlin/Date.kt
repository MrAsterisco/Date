package io.github.mrasterisco.Date

import io.github.mrasterisco.time.milliseconds
import io.github.mrasterisco.time.seconds
import platform.Foundation.*

actual class Date: Comparable<Date> {

    private var date: NSDate

    actual constructor() {
        date = NSDate()
    }

    actual constructor(time: Long) {
        date = NSDate.dateWithTimeIntervalSince1970(time.milliseconds.inSeconds.value)
    }

    constructor(platformDate: NSDate) {
        date = platformDate
    }

    actual override fun compareTo(other: Date): Int {
        return when (this.date.compare(other.date)) {
            NSOrderedAscending -> -1
            NSOrderedDescending -> 1
            else -> 0
        }
    }

    actual fun getTime(): Long = date.timeIntervalSince1970.seconds.inMilliseconds.longValue

    fun toPlatformDate() = date

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Date

        return getTime() == other.getTime()
    }

    override fun hashCode(): Int {
        return date.hashCode()
    }

    /**
     * Return the platform date string representation.
     */
    override fun toString(): String {
        return toPlatformDate().description() ?: super.toString()
    }

}