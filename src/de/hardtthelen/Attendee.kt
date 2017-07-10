package de.hardtthelen

/**
 * Event attendee
 */
class Attendee(val name : String) {

    private var selectedEvent: EventDate? = null

    private var availableDates: List<EventDate>? = null

    fun getNumberOfAvailableDates(): Int {
        return availableDates?.count() ?: 0
    }

    fun calculateAvailableDates(dates: List<EventDate>) {
        availableDates = dates.filter { it.attendees.contains(this) }.sortedBy { it.startTime }
    }

    fun getAvailableDates(): List<EventDate> {
        return availableDates!!
    }

    fun selectNextDate() {
        var eventIndex: Int = 0;
        if (selectedEvent == null) {

        }
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