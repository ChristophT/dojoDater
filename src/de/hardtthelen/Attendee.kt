package de.hardtthelen

/**
 * Event attendee
 */
class Attendee(val name : String) {

    fun getNumberOfPossibleDates(dates : List<EventDate>) : Int {
        return dates.filter { it.attendees.contains(this) }.count()
    }

    override fun toString(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (other != null && other is Attendee) {
            return name.equals(other.name)
        }
        return false
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}