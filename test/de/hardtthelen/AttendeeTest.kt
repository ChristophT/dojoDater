package de.hardtthelen

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Tests for Attendee
 */
class AttendeeTest {
    @Test
    fun getNumberOfPossibleDates_0() {
        val cand : Attendee = Attendee("tester")
        val dates: ArrayList<EventDate> = fillDateList()

        cand.calculateAvailableDates(dates)
        val result = cand.getNumberOfAvailableDates()

        assertEquals(0, result)
    }

    @Test
    fun getNumberOfPossibleDates_1() {
        val cand : Attendee = Attendee("tester")
        val dates: ArrayList<EventDate> = fillDateList()

        dates[1].attendees.add(cand)
        cand.calculateAvailableDates(dates)
        val result = cand.getNumberOfAvailableDates()

        assertEquals(1, result)
    }

    @Test
    fun getNumberOfPossibleDates_2() {
        val cand : Attendee = Attendee("tester")
        val dates: ArrayList<EventDate> = fillDateList()

        dates[1].attendees.add(cand)
        dates[3].attendees.add(cand)

        cand.calculateAvailableDates(dates)
        val result = cand.getNumberOfAvailableDates()
        assertEquals(2, result)
    }

    @Test
    fun getNumberOfPossibleDates_4() {
        val cand : Attendee = Attendee("tester")
        val dates: ArrayList<EventDate> = fillDateList()

        dates[0].attendees.add(cand)
        dates[1].attendees.add(cand)
        dates[2].attendees.add(cand)
        dates[3].attendees.add(cand)

        cand.calculateAvailableDates(dates)
        val result = cand.getNumberOfAvailableDates()
        assertEquals(4, result)
    }

    @Test
    fun testAvailableDates() {
        val cand : Attendee = Attendee("tester")
        val dates: ArrayList<EventDate> = fillDateList()

        dates[1].attendees.add(cand)
        dates[2].attendees.add(cand)

        cand.calculateAvailableDates(dates)

        assertEquals(listOf(dates[1], dates[2]), cand.getAvailableDates())
    }

    private fun fillDateList(): ArrayList<EventDate> {
        val dummyTime = Date()
        val other1: Attendee = Attendee("other1")
        val other2: Attendee = Attendee("other2")
        val other3: Attendee = Attendee("other3")
        val dates: ArrayList<EventDate> = ArrayList()
        val date1: EventDate = EventDate(dummyTime, dummyTime)
        val date2: EventDate = EventDate(dummyTime, dummyTime)
        val date3: EventDate = EventDate(dummyTime, dummyTime)
        val date4: EventDate = EventDate(dummyTime, dummyTime)

        date1.attendees.add(other1)
        date1.attendees.add(other2)
        date1.attendees.add(other3)

        date2.attendees.add(other2)
        date2.attendees.add(other3)

        date3.attendees.add(other3)

        date4.attendees.add(other2)

        dates.addAll(listOf(date1, date2, date3, date4))
        return dates
    }

}