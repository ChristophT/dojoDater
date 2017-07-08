package de.hardtthelen

/**
 * Schedules attendees to events so that as many people as possible get a spot at an event.
 */
class AttendenceScheduler(val eventDates: List<EventDate>) {
    init {
        collectEqualAttendees()
    }

    /**
     * clean duplicate objects for the same attendee (identified as defined in Attendee)
     */
    private fun collectEqualAttendees() {
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
    }

    fun scheduleAttendence(maxAttendeesPerEvent: Int) {
        val allAttendees: MutableList<Attendee> = eventDates.flatMap { it.attendees }.toSet().toMutableList()
        println("Scheduling ${allAttendees.size} attendees to ${eventDates.size} events.")

        allAttendees.sortBy { it.getNumberOfPossibleDates(eventDates) }


    }
}