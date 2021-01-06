package io.github.mrasterisco.Date.platform

import io.github.mrasterisco.Date.utils.TimeZoneUtils
import java.util.*
import java.util.Calendar

@Suppress("unused")
actual class Calendar actual constructor() {

    private var calendar: Calendar = Calendar.getInstance().apply { timeZone = TimeZoneUtils.getDefault() as TimeZone }

    actual constructor(date: Date, timeZone: Any?): this() {
        calendar.apply {
            this.timeZone = timeZone as? TimeZone ?: TimeZoneUtils.getDefault() as TimeZone
            this.time = date
        }
    }

    actual var timeZone: Any
        get() = calendar.timeZone
        set(value) {
            calendar.timeZone = value as TimeZone
        }

    actual var date: Date
        get() = calendar.time
        set(value) {
            calendar.time = value
        }

    actual fun get(calendarUnit: CalendarUnit): Long {
        val value = calendar.get(calendarUnitToPlatformUnit(calendarUnit)).toLong()
        return if (calendarUnit == CalendarUnit.Month) value+1 else value
    }

    actual fun set(calendarUnit: CalendarUnit, value: Long) {
        val realValue = if (calendarUnit == CalendarUnit.Month) value-1 else value
        calendar.set(calendarUnitToPlatformUnit(calendarUnit), realValue.toInt())
    }

    actual fun set(unitsAndValues: Map<CalendarUnit, Long>) {
        unitsAndValues.entries.forEach { set(it.key, it.value) }
    }

    actual fun isSameDayAs(otherDate: Date): Boolean {
        val otherCalendar = Calendar(otherDate, timeZone)

        return get(CalendarUnit.Day) == otherCalendar.get(CalendarUnit.Day)
                && get(CalendarUnit.Year) == otherCalendar.get(CalendarUnit.Year)
    }

    actual fun setToMidnight() {
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }

    actual override fun toString(): String {
        return "Calendar<$date>"
    }

    actual fun add(calendarUnit: CalendarUnit, value: Long) {
        calendar.add(calendarUnitToPlatformUnit(calendarUnit), value.toInt())
    }

    private fun calendarUnitToPlatformUnit(calendarUnit: CalendarUnit): Int =
        when (calendarUnit) {
            CalendarUnit.Year -> Calendar.YEAR
            CalendarUnit.Month -> Calendar.MONTH
            CalendarUnit.Day -> Calendar.DAY_OF_MONTH
            CalendarUnit.Hour -> Calendar.HOUR_OF_DAY
            CalendarUnit.Minute -> Calendar.MINUTE
            CalendarUnit.Second -> Calendar.SECOND
            CalendarUnit.Millisecond -> Calendar.MILLISECOND
        }

}