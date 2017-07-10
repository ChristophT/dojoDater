package de.hardtthelen

/**
 * Event attendee
 */
class Attendee(val name : String) {

    var selectedEvent: EventDate? = null

    private var availableDates: List<EventDate> = ArrayList()

    fun getNumberOfAvailableDates(): Int {
        return availableDates.count() ?: 0
    }

    fun calculateAvailableDates(dates: List<EventDate>) {
        availableDates = dates.filter { it.attendees.contains(this) }.sortedBy { it.startTime }
    }

    fun getAvailableDates(): List<EventDate> {
        return availableDates
    }

    fun selectNextDate(): Boolean {
        var eventIndex: Int = availableDates.indexOf(selectedEvent)
        eventIndex++
        if (eventIndex < availableDates.size) {
            selectedEvent = availableDates[eventIndex]
            return true
        } else {
            selectedEvent = null
        }
        return false
    }

    override fun toString(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (other != null && other is Attendee) {
            return name == other.name
        }
        return false
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}