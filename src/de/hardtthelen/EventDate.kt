package de.hardtthelen

import com.fasterxml.jackson.annotation.JsonFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Date of an event
 */
const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

class EventDate(@JsonFormat(shape= JsonFormat.Shape.STRING, pattern=DATE_FORMAT, timezone = "Europe/Berlin") val startTime: Date,
                @JsonFormat(shape= JsonFormat.Shape.STRING, pattern=DATE_FORMAT, timezone = "Europe/Berlin") val endTime: Date) {
    val attendees: ArrayList<Attendee> = ArrayList()

    override fun toString(): String {
        val formatter : SimpleDateFormat = SimpleDateFormat(DATE_FORMAT)
        val sb : StringBuilder = StringBuilder()
        sb.append("Timeslot: ")
        sb.append(formatter.format(startTime))
        sb.append(" - ")
        sb.append(formatter.format(endTime))
        sb.append("\nAttendees:\n")
        for (attendee in attendees) {
            sb.append(attendee.name)
            sb.append("\n")
        }
        return sb.toString()
    }
}