package io.github.mrasterisco.Date.serialization

import io.github.mrasterisco.Date.Date
import io.github.mrasterisco.Date.platform.Calendar
import io.github.mrasterisco.Date.platform.CalendarUnit
import io.github.mrasterisco.Date.utils.TimeZoneUtils

@Suppress("RedundantVisibilityModifier")
public object ISO8601DateFormat {

    private enum class Group {
        Year {
            override val index = 1
        },
        Month {
            override val index = 2
        },
        Day {
            override val index = 3
        },
        Time {
            override val index = 4
        },
        Hour {
            override val index = 5
        },
        Minute {
            override val index = 6
        },
        Second {
            override val index = 7
        },
        Millisecond {
            override val index = 9
        },
        @Suppress("unused")
        TimeZone {
            override val index = 10
        };

        abstract val index: Int
    }

    /// https://regex101.com/r/Bm4XAZ/5
    private val regex = Regex(
        """([0-9]{4})-?([0-9]{2})-?([0-9]{2})(T([0-9]{2}):?([0-9]{2}):?([0-9]{2})(\.([0-9]*))*(Z|\+[0-9]{2}:[0-9]{2})?)?"""
    )

    fun parse(string: String?): Date? {
        if (string.isNullOrBlank()) return null

        val groups = regex.matchEntire(string)?.groups
        if (groups != null) {
            val year = getLongValue(Group.Year, groups)
            val month = getLongValue(Group.Month, groups)
            val day = getLongValue(Group.Day, groups)

            var hour = 0L
            var minute = 0L
            var second = 0L
            var millisecond = 0L
            val timeZone = TimeZoneUtils.getTimeZone("UTC")!!

            if (groups[Group.Time.index] != null) {
                hour = getLongValue(Group.Hour, groups)
                minute = getLongValue(Group.Minute, groups)
                second = getLongValue(Group.Second, groups)
                millisecond = getLongValue(Group.Millisecond, groups)

//                groups[Group.TimeZone.index]?.value?.let {
//                    if (it != "Z") {
//                        // TODO: parse difference from UTC.
//                    }
//                }
            }

            val calendar = Calendar().apply {
                this.timeZone = timeZone
                this.set(CalendarUnit.Year, year)
                this.set(CalendarUnit.Month, month)
                this.set(CalendarUnit.Day, day)
                this.set(CalendarUnit.Hour, hour)
                this.set(CalendarUnit.Minute, minute)
                this.set(CalendarUnit.Second, second)
                this.set(CalendarUnit.Millisecond, millisecond)
            }

            return calendar.date
        }
        return null
    }

    private fun getLongValue(group: Group, groups: MatchGroupCollection): Long {
        val groupString = groups[group.index]?.value
        if (groupString.isNullOrBlank()) return 0

        return try {
            groupString.toLong()
        } catch (_: NumberFormatException) {
            0
        }
    }

    fun format(date: Date, style: DateStyle = DateStyle.Full, timeZone: Any? = null): String {
        val calendar = Calendar(date, timeZone ?: TimeZoneUtils.getTimeZone("UTC"))
        val timeZoneIdentifier = if (timeZone == null) "Z" else "//TODO"

        return when (style) {
            DateStyle.Full -> getDateString(calendar) + "T" + getTimeString(calendar) + timeZoneIdentifier
            DateStyle.DateOnly -> getDateString(calendar)
            DateStyle.TimeOnly -> getTimeString(calendar) + timeZoneIdentifier
        }
    }

    private fun getDateString(calendar: Calendar): String {
        return "${calendar.get(CalendarUnit.Year).toString().padStart(4, '0')}-${calendar.get(CalendarUnit.Month)
            .toString().padStart(2, '0')}-${calendar.get(CalendarUnit.Day).toString().padStart(2, '0')}"
    }

    private fun getTimeString(calendar: Calendar): String {
        return "${calendar.get(CalendarUnit.Hour).toString().padStart(2, '0')}:${calendar.get(CalendarUnit.Minute)
            .toString().padStart(2, '0')}:${calendar.get(CalendarUnit.Second).toString()
            .padStart(2, '0')}.${calendar.get(CalendarUnit.Millisecond).toString().padStart(3, '0')}"
    }

}