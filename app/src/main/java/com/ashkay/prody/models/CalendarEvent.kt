package com.ashkay.prody.models

import java.util.*

data class CalendarEvent(
    val title: String,
    val begin: Date,
    val end: Date,
    val allDay: Boolean
) : Comparable<CalendarEvent> {
    override fun toString() = "$title $begin $end $allDay";

    override fun compareTo(other: CalendarEvent): Int {
        return begin.compareTo(other.begin);
    }
}