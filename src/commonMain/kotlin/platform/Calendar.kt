package io.github.mrasterisco.Date.platform

import io.github.mrasterisco.Date.Date

/**
 * Represents a unit of a date.
 */
enum class CalendarUnit {
    Year,
    Month,
    Day,
    Hour, // Hour of Day
    Minute,
    Second,
    Millisecond
}

/**
 * A Calendar represents a way
 * to handle dates in a specific timeZone
 * on a certain calendar.
 *
 * @see Calendar https://developer.android.com/reference/java/util/Calendar
 * @see Calendar https://developer.apple.com/documentation/foundation/calendar
 */
expect class Calendar() {

    /**
     * Instantiate a new calendar at the
     * passed date in the specified timeZone.
     *
     * @param date: A date.
     * @param timeZone: The timeZone of the date.
     */
    constructor(date: Date, timeZone: Any?)

    /**
     * Get or set the timeZone of this calendar.
     */
    var timeZone: Any

    /**
     * Get or set the represented date.
     */
    var date: Date

    /**
     * Get the specified unit.
     *
     * @param calendarUnit: A unit.
     * @return The requested unit.
     */
    fun get(calendarUnit: CalendarUnit): Long

    /**
     * Set the specified unit to the passed value.
     *
     * @param calendarUnit: A unit.
     * @param value: The value to set.
     */
    fun set(calendarUnit: CalendarUnit, value: Long)

    /**
     * Set the specified units to their values.
     *
     * @param unitsAndValues: A map of units with their values.
     */
    fun set(unitsAndValues: Map<CalendarUnit, Long>)

    // Utilities

    /**
     * Get if the passed date is in the same
     * day as the one currently represented by this calendar.
     *
     * This function uses this calendar and its timeZone.
     *
     * @param otherDate: Another date.
     * @return `true` if the two dates are in the same day.
     */
    fun isSameDayAs(otherDate: Date): Boolean

    /**
     * Move the time to midnight without
     * changing the day.
     */
    fun setToMidnight()

    /**
     * Add the specified calendar unit, increasing the value of this calendar.
     *
     * @param calendarUnit: The calendar unit.
     * @param value: The value.
     */
    fun add(calendarUnit: CalendarUnit, value: Long)

    /**
     * Describe this calendar.
     */
    override fun toString(): String

}