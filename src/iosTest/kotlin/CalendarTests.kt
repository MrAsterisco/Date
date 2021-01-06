package io.github.mrasterisco.Date

import io.github.mrasterisco.Date.platform.Calendar
import io.github.mrasterisco.Date.platform.CalendarUnit
import io.github.mrasterisco.Date.utils.TimeZoneUtils
import kotlin.test.*

class CalendarTests {

    @Test
    fun constructorMustWork() {
        // 2019-08-23T00:00:00Z
        val date = Date(1566518400L * 1000)
        val timeZone = TimeZoneUtils.getTimeZone("UTC")

        val calendar = Calendar(date, timeZone)

        assertSame(date, calendar.date)
        assertSame(timeZone, timeZone)
    }

    @Test
    fun getMustWork() {
        // 2019-08-23T00:00:00Z
        val date = Date(1566518400L * 1000)
        val timeZone = TimeZoneUtils.getTimeZone("UTC")

        val calendar = Calendar(date, timeZone)

        assertEquals(2019, calendar.get(CalendarUnit.Year))
        assertEquals(8, calendar.get(CalendarUnit.Month))
        assertEquals(23, calendar.get(CalendarUnit.Day))
        assertEquals(0, calendar.get(CalendarUnit.Hour))
        assertEquals(0, calendar.get(CalendarUnit.Minute))
        assertEquals(0, calendar.get(CalendarUnit.Second))
    }

    @Test
    fun setMustWork() {
        // 2019-08-23T00:00:00Z
        val date = Date(1566518400L * 1000)
        val timeZone = TimeZoneUtils.getTimeZone("UTC")

        val calendar = Calendar(date, timeZone)
        calendar.set(CalendarUnit.Hour, 18)
        calendar.set(CalendarUnit.Minute, 30)

        assertEquals(2019, calendar.get(CalendarUnit.Year))
        assertEquals(8, calendar.get(CalendarUnit.Month))
        assertEquals(23, calendar.get(CalendarUnit.Day))
        assertEquals(18, calendar.get(CalendarUnit.Hour))
        assertEquals(30, calendar.get(CalendarUnit.Minute))
        assertEquals(0, calendar.get(CalendarUnit.Second))
    }

    @Test
    fun isSameDayAsMustWork() {
        // 2020-04-03T15:30:00Z
        val dateAt330 = Date(1585927800L * 1000)
        // 2020-04-03T02:25:30Z
        val dateAt225 = Date(1585880730L * 1000)
        // 2020-04-04T00:00:00Z
        val nextDay = Date(1585958400L * 1000)

        val timeZone = TimeZoneUtils.getTimeZone("UTC")
        val calendar = Calendar(dateAt330, timeZone)

        assertTrue(calendar.isSameDayAs(dateAt225))
        assertFalse(calendar.isSameDayAs(nextDay))
    }

    @Test
    fun setToMidnightMustWork() {
        // 2020-04-03T15:30:00Z
        val date = Date(1585927800L * 1000)
        val timeZone = TimeZoneUtils.getTimeZone("UTC")

        val calendar = Calendar(date, timeZone)
        calendar.setToMidnight()

        assertEquals(2020, calendar.get(CalendarUnit.Year))
        assertEquals(4, calendar.get(CalendarUnit.Month))
        assertEquals(3, calendar.get(CalendarUnit.Day))
        assertEquals(0, calendar.get(CalendarUnit.Hour))
        assertEquals(0, calendar.get(CalendarUnit.Minute))
        assertEquals(0, calendar.get(CalendarUnit.Second))
    }

}