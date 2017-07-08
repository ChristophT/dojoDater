package de.hardtthelen

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Date of an event
 */
class EventDate(val startTime: LocalDateTime, val endTime: LocalDateTime) {
    val attendees: ArrayList<Attendee> = ArrayList()

    override fun toString(): String {
        val formatter : DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val sb : StringBuilder = StringBuilder()
        sb.append("Timeslot: ")
        sb.append(startTime.format(formatter))
        sb.append(" - ")
        sb.append(endTime.format(formatter))
        sb.append("\nAttendees:\n")
        for (attendee in attendees) {
            sb.append(attendee.name)
            sb.append("\n")
        }
        return sb.toString()
    }
}