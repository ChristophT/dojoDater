package de.hardtthelen

/**
 * Dojo attendee
 */
class Attendee(val name : String) {

    fun getNumberOfPossibleDates(dates : List<EventDate>) : Int {
        return dates.filter { it.attendees.contains(this) }.count()
    }

    override fun toString(): String {
        return name
    }
}