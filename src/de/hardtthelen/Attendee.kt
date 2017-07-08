package de.hardtthelen

/**
 * Event attendee
 */
class Attendee(val name : String) {

    var chosenEvent: EventDate? = null

    private var availableDates: List<EventDate>? = null

    fun getNumberOfAvailableDates(dates: List<EventDate>): Int {
        return getAvailableDates(dates).count()
    }

    fun getAvailableDates(dates: List<EventDate>): List<EventDate> {
        if (availableDates == null) {
            availableDates = dates.filter { it.attendees.contains(this) }.sortedBy { it.startTime }
        }
        return availableDates!!
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