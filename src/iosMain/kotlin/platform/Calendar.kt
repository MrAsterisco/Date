package io.github.mrasterisco.Date.platform

import io.github.mrasterisco.Date.Date
import io.github.mrasterisco.Date.utils.TimeZoneUtils
import io.github.mrasterisco.time.milliseconds
import io.github.mrasterisco.time.nanoseconds
import platform.Foundation.*

@Suppress("unused", "RedundantVisibilityModifier")
actual class Calendar actual constructor() {

    private val calendar = NSCalendar.currentCalendar

    private val allUnits = NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay or NSCalendarUnitHour or NSCalendarUnitMinute or NSCalendarUnitSecond

    actual constructor(date: Date, timeZone: Any?) : this() {
        this.date = date
        this.timeZone = timeZone ?: TimeZoneUtils.getDefault()
    }

    actual var timeZone: Any
        get() = calendar.timeZone
        set(value) {
            calendar.timeZone = value as NSTimeZone
        }

    actual var date: Date = Date()

    actual fun get(calendarUnit: CalendarUnit): Long {
        calendar.timeZone = this.timeZone as NSTimeZone
        calendarUnitToPlatformUnit(calendarUnit)?.let {
            return calendar.component(it, this.date.toPlatformDate())
        }
        return calendar.component(
            NSCalendarUnitNanosecond,
            this.date.toPlatformDate()
        ).nanoseconds.inMilliseconds.longValue
    }

    actual fun set(calendarUnit: CalendarUnit, value: Long) {
        set(HashMap<CalendarUnit, Long>().apply {
            put(calendarUnit, value)
        })
    }

    actual fun set(unitsAndValues: Map<CalendarUnit, Long>) {
        calendar.timeZone = this.timeZone as NSTimeZone
        val components = calendar.components(
            allUnits,
            this.date.toPlatformDate()
        )

        unitsAndValues.forEach {
            applyComponent(components, it.key, it.value)
        }

        this.date = Date(
            calendar.dateFromComponents(components)!!
        )
    }

    actual fun isSameDayAs(otherDate: Date): Boolean {
        calendar.timeZone = this.timeZone as NSTimeZone
        return calendar.compareDate(
            date.toPlatformDate(),
            otherDate.toPlatformDate(),
            NSCalendarUnitDay
        ) == NSOrderedSame
    }

    actual fun setToMidnight() {
        calendar.timeZone = this.timeZone as NSTimeZone

        this.date = Date(
            calendar.dateBySettingHour(0, 0, 0, this.date.toPlatformDate(), 0u)!!
        )
    }

    actual fun add(calendarUnit: CalendarUnit, value: Long) {
        calendar.timeZone = this.timeZone as NSTimeZone

        val platformUnit = calendarUnitToPlatformUnit(calendarUnit)
        val currentPlatformDate = this.date.toPlatformDate()

        if (platformUnit != null) {
            this.date = Date(
                calendar.dateByAddingUnit(
                    platformUnit,
                    value,
                    currentPlatformDate,
                    NSCalendarMatchNextTime
                ) ?: currentPlatformDate
            )
        } else {
            this.date = Date((currentPlatformDate.timeIntervalSince1970 + value).toLong())
        }
    }

    private fun calendarUnitToPlatformUnit(calendarUnit: CalendarUnit): NSCalendarUnit? {
        return when (calendarUnit) {
            CalendarUnit.Year -> NSCalendarUnitYear
            CalendarUnit.Month -> NSCalendarUnitMonth
            CalendarUnit.Day -> NSCalendarUnitDay
            CalendarUnit.Hour -> NSCalendarUnitHour
            CalendarUnit.Minute -> NSCalendarUnitMinute
            CalendarUnit.Second -> NSCalendarUnitSecond
            CalendarUnit.Millisecond -> null
        }
    }

    private fun applyComponent(
        components: NSDateComponents,
        calendarUnit: CalendarUnit,
        value: Long
    ): NSDateComponents {
        when (calendarUnit) {
            CalendarUnit.Year -> components.year = value
            CalendarUnit.Month -> components.month = value
            CalendarUnit.Day -> components.day = value
            CalendarUnit.Hour -> components.hour = value
            CalendarUnit.Minute -> components.minute = value
            CalendarUnit.Second -> components.second = value
            CalendarUnit.Millisecond -> components.nanosecond = value.milliseconds.inNanoseconds.longValue
        }

        return components
    }

    public fun toPlatformCalendar(): NSCalendar = calendar

}