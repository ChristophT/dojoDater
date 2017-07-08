package de.hardtthelen

import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneOffset
import java.util.*

/**
 * Tests for EventDate
 */
class EventDateTest {
    @Test
    fun testToString() {
        val eventDate: EventDate = EventDate(Date(LocalDateTime.of(2017, Month.JULY, 18, 14, 0).toEpochSecond(ZoneOffset.ofHours(2)) * 1000),
                Date(LocalDateTime.of(2017, Month.JULY, 18, 15, 30).toEpochSecond(ZoneOffset.ofHours(2)) * 1000))
        eventDate.attendees.add(Attendee("tester1"))
        eventDate.attendees.add(Attendee("tester2"))
        eventDate.attendees.add(Attendee("tester3"))
        eventDate.attendees.add(Attendee("tester4"))

        assertEquals("""Timeslot: 2017-07-18 14:00:00 - 2017-07-18 15:30:00
                    |Attendees:
                    |tester1
                    |tester2
                    |tester3
                    |tester4
                    |""".trimMargin()
        , eventDate.toString())
    }
}