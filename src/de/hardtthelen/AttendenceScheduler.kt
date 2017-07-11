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
                    val knownAttendee = knownAttendees[index]
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
        var firstAttendeeWithMultipleDates: Int = 0
        println("Scheduling ${allAttendees.size} attendees to ${eventDates.size} events.")

        allAttendees.sortBy { it.getNumberOfAvailableDates() }

        for (i in 0 .. allAttendees.size) {
            val candidate = allAttendees[i]
            if (candidate.getNumberOfAvailableDates() > 1) {
                firstAttendeeWithMultipleDates = i
                break
            } else {
                candidate.selectNextDate()
                val selectedEvent: EventDate = candidate.selectedEvent!!
                if (selectedEvent.acceptedAttendees.size < maxAttendeesPerEvent) {
                    selectedEvent.acceptedAttendees.add(candidate)
                } else {
                    throw RuntimeException("Can not schedule all attendees with a single available date.")
                }
            }
        }

        scheduleAttendeeRange(allAttendees, firstAttendeeWithMultipleDates, maxAttendeesPerEvent)
    }

    private fun scheduleAttendeeRange(allAttendees: MutableList<Attendee>, startIndex: Int, maxAttendeesPerEvent: Int,
                                      allowBacktracking: Boolean = true) {
        for (i in startIndex .. allAttendees.size - 1) {
            println("in scheduleAttTange with index $i")
            var success: Boolean = selectNextAvailabeEvent(allAttendees[i], maxAttendeesPerEvent)
            if (!success && allowBacktracking) {
                success = backtrackEventSelection(allAttendees, i, maxAttendeesPerEvent)
            }
            if (!success){
                throw RuntimeException("Can not schedule all attendees")
            }
        }
    }

    private fun backtrackEventSelection(allAttendees: MutableList<Attendee>, currentIndex: Int, maxAttendeesPerEvent: Int): Boolean{
        println("Backtracking from index $currentIndex")

        for (i in currentIndex-1 downTo 0) {
            val success = selectNextAvailabeEvent(allAttendees[currentIndex], maxAttendeesPerEvent, true)
            if (success) {
                scheduleAttendeeRange(allAttendees, i+1, maxAttendeesPerEvent, false)
            }
        }
        return true
    }

    private fun selectNextAvailabeEvent(candidate: Attendee, maxAttendeesPerEvent: Int,
                                        restartIfNeccessary: Boolean = false): Boolean {
        var success: Boolean = false
        for (i in 0 .. candidate.getNumberOfAvailableDates()) {
            success = candidate.selectNextDate()
            println("Selecting next event for att ${candidate.name} with success==${success}")

            val selectedEvent = candidate.selectedEvent
            if (selectedEvent != null && selectedEvent.acceptedAttendees.size < maxAttendeesPerEvent) {
                selectedEvent.acceptedAttendees.add(candidate)
                break
            } else {
                success = false
                if (selectedEvent == null) {
                    break
                }
            }
        }

        if (!success && restartIfNeccessary) {
            candidate.selectedEvent = null
            success = selectNextAvailabeEvent(candidate, maxAttendeesPerEvent, false)
        }
        return success
    }
}