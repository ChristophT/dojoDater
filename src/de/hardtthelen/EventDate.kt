package de.hardtthelen

import com.fasterxml.jackson.annotation.JsonFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Date of an event
 */
const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

class EventDate(@JsonFormat(shape= JsonFormat.Shape.STRING, pattern=DATE_FORMAT, timezone = "Europe/Berlin") val startTime: Date,
                @JsonFormat(shape= JsonFormat.Shape.STRING, pattern=DATE_FORMAT, timezone = "Europe/Berlin") val endTime: Date) {
    val attendees: ArrayList<Attendee> = ArrayList()

    val acceptedAttendees: MutableList<Attendee> = ArrayList()

    override fun toString(): String {
        return toString(false)
    }

    fun toString(onlyAccepted: Boolean): String {
        val formatter : SimpleDateFormat = SimpleDateFormat(DATE_FORMAT)
        val sb : StringBuilder = StringBuilder()
        sb.append("Timeslot: ")
        sb.append(formatter.format(startTime))
        sb.append(" - ")
        sb.append(formatter.format(endTime))
        if (!onlyAccepted) {
            sb.append("\nPossible attendees:\n")
            for (attendee in attendees) {
                sb.append(attendee.name)
                sb.append("\n")
            }
        }
        sb.append("\nAccepted attendees:\n")
        for (attendee in acceptedAttendees) {
            sb.append(attendee.name)
            sb.append("\n")
        }
        return sb.toString()
    }
}