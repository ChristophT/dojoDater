package de.hardtthelen

import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDateTime

/**
 * Tests for Attendee
 */
class AttendeeTest {
    @Test
    fun getNumberOfPossibleDates_0() {
        val cand : Attendee = Attendee("tester")
        val dates: ArrayList<DojoDate> = fillDateList()

        val result = cand.getNumberOfPossibleDates(dates)

        assertEquals(0, result)
    }

    @Test
    fun getNumberOfPossibleDates_1() {
        val cand : Attendee = Attendee("tester")
        val dates: ArrayList<DojoDate> = fillDateList()

        dates[1].attendees.add(cand)
        val result = cand.getNumberOfPossibleDates(dates)

        assertEquals(1, result)
    }

    @Test
    fun getNumberOfPossibleDates_2() {
        val cand : Attendee = Attendee("tester")
        val dates: ArrayList<DojoDate> = fillDateList()

        dates[1].attendees.add(cand)
        dates[3].attendees.add(cand)

        val result = cand.getNumberOfPossibleDates(dates)
        assertEquals(2, result)
    }

    @Test
    fun getNumberOfPossibleDates_4() {
        val cand : Attendee = Attendee("tester")
        val dates: ArrayList<DojoDate> = fillDateList()

        dates[0].attendees.add(cand)
        dates[1].attendees.add(cand)
        dates[2].attendees.add(cand)
        dates[3].attendees.add(cand)

        val result = cand.getNumberOfPossibleDates(dates)
        assertEquals(4, result)
    }

    private fun fillDateList(): ArrayList<DojoDate> {
        val dummyTime = LocalDateTime.now()
        val other1: Attendee = Attendee("other1")
        val other2: Attendee = Attendee("other2")
        val other3: Attendee = Attendee("other3")
        val dates: ArrayList<DojoDate> = ArrayList()
        val date1: DojoDate = DojoDate(dummyTime, dummyTime)
        val date2: DojoDate = DojoDate(dummyTime, dummyTime)
        val date3: DojoDate = DojoDate(dummyTime, dummyTime)
        val date4: DojoDate = DojoDate(dummyTime, dummyTime)

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