package de.hardtthelen

/**
 * Schedules attendees to events so that as many people as possible get a spot at an event.
 */
class AttendenceScheduler(val eventDates: List<EventDate>) {
    init {
        val allAttendees: List<Attendee>
        allAttendees = collectEqualAttendees()
        calculateAvailabelDates(allAttendees)
    }

    /**
     * clean duplicate objects for the same attendee (identified as defined in Attendee)
     */
    private fun collectEqualAttendees(): List<Attendee> {
        val knownAttendees: MutableList<Attendee> = ArrayList()

        for (event in eventDates) {
            if (knownAttendees.size == 0) {
                // first event, simply add all attendees
                knownAttendees.addAll(event.attendees)
                continue
            }

            val attendeeList: List<Attendee> = event.attendees.clone() as List<Attendee>
            for (candidate in attendeeList) {
                val index = knownAttendees.indexOf(candidate)
                if (index >= 0) {
                    // swap for known attendee
                    val knownAttendee = knownAttendees.get(index)
                    event.attendees.remove(candidate)
                    event.attendees.add(knownAttendee)
                } else {
                    // add to known attendees
                    knownAttendees.add(candidate)
                }
            }
        }

        return knownAttendees
    }

    /**
     * Calculate the evenDates for each known attendee where he/she is available
     */
    private fun calculateAvailabelDates(allAttendees: List<Attendee>) {
        for (attendee in allAttendees) {
            attendee.calculateAvailableDates(eventDates)
        }
    }

    fun scheduleAttendence(maxAttendeesPerEvent: Int) {
        val allAttendees: MutableList<Attendee> = eventDates.flatMap { it.attendees }.toSet().toMutableList()

        println("Scheduling ${allAttendees.size} attendees to ${eventDates.size} events.")

        allAttendees.sortBy { it.getNumberOfAvailableDates() }

        scheduleAttendeeRange(allAttendees, 0, maxAttendeesPerEvent)
    }

    private fun scheduleAttendeeRange(allAttendees: MutableList<Attendee>, startIndex: Int, maxAttendeesPerEvent: Int,
                                      allowBacktracking: Boolean = true) {
        for (i in startIndex .. allAttendees.size - 1) {
            var success: Boolean = selectNextAvailabeEvent(allAttendees, i, maxAttendeesPerEvent)
            if (!success && allowBacktracking) {
                success = backtrackEventSelection(allAttendees, i, maxAttendeesPerEvent)
            }
            if (!success){
                throw RuntimeException("Can not schedule all attendees")
            }
        }
    }

    private fun backtrackEventSelection(allAttendees: MutableList<Attendee>, currentIndex: Int, maxAttendeesPerEvent: Int): Boolean{
        for (i in currentIndex-1 downTo 0) {
            selectNextAvailabeEvent(allAttendees, currentIndex, maxAttendeesPerEvent, true)
        }
        return true
    }

    private fun selectNextAvailabeEvent(allAttendees: MutableList<Attendee>, currentIndex: Int, maxAttendeesPerEvent: Int,
                                        restartIfNeccessary: Boolean = false): Boolean {
        var success: Boolean
        val candidate = allAttendees[currentIndex]
        do {
            success = candidate.selectNextDate()
            val selectedEvent = candidate.selectedEvent
            if (selectedEvent != null && selectedEvent.acceptedAttendees.size < maxAttendeesPerEvent) {
                selectedEvent.acceptedAttendees.add(candidate)
                break
            }
        } while (!success)

        if (!success && restartIfNeccessary) {
            candidate.selectedEvent == null
            success = selectNextAvailabeEvent(allAttendees, currentIndex, maxAttendeesPerEvent, false);
        }
        return success
    }
}