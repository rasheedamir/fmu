package se.inera.fmu.domain.model.eavrop;

import java.util.List;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;

public class EavropEventDTO implements Comparable<EavropEventDTO>{ 

	
	private final EavropEventDTOType eventType;
    private final DateTime eventTime;
    private final BookingStatusType eventStatus;
    private final String comment;
    private final String personName;
    private final String personRole;
    private final String personOrganistation;
    private final String personUnit;
    private List<BookingStatusType> validBookingStatuses;
    private InterpreterBookingEventDTO interpreterBookingEventDTO;
    
    //TODO: Remove DTO?
    
	public EavropEventDTO(EavropEventDTOType eventType, DateTime eventTime, BookingStatusType eventStatus, String comment, String personName,
			String personRole, String personOrganistation, String personUnit) {
		super();
		this.eventType = eventType;
		this.eventTime = eventTime;
		this.eventStatus = eventStatus;
		this.comment = comment;
		this.personName = personName;
		this.personRole = personRole;
		this.personOrganistation = personOrganistation;
		this.personUnit = personUnit;
	}

	public EavropEventDTO(EavropEventDTOType eventType, DateTime eventTime, BookingStatusType eventStatus, String comment, String personName,
			String personRole, String personOrganistation, String personUnit, List<BookingStatusType> validBookingStatuses) {
		this(eventType, eventTime, eventStatus, comment, personName, personRole, personOrganistation, personUnit);
		this.validBookingStatuses = validBookingStatuses;
	}

	
	public EavropEventDTO(EavropEventDTOType eventType, DateTime eventTime, BookingStatusType eventStatus, String comment, String personName,
			String personRole, String personOrganistation, String personUnit, List<BookingStatusType> validBookingStatuses, 
			InterpreterBookingEventDTO interpreterBookingEventDTO) {
		this(eventType, eventTime, eventStatus, comment, personName, personRole, personOrganistation, personUnit, validBookingStatuses);
		this.interpreterBookingEventDTO = interpreterBookingEventDTO;
	}

	public EavropEventDTOType getEventType() {
		return eventType;
	}

	public DateTime getEventTime() {
		return eventTime;
	}

	public BookingStatusType getEventStatus() {
		return eventStatus;
	}

	public String getComment() {
		return comment;
	}

	public String getPersonName() {
		return personName;
	}

	public String getPersonRole() {
		return personRole;
	}
	
	public String getPersonOrganistation() {
		return personOrganistation;
	}

	public String getPersonUnit() {
		return personUnit;
	}
	
	public List<BookingStatusType> getValidBookingStatuses(){
		return validBookingStatuses;
	}

	public InterpreterBookingEventDTO getInterpreterBookingEventDTO() {
		return interpreterBookingEventDTO;
	}
	
	@Override
	public int compareTo(EavropEventDTO o) {
		return getEventTime().compareTo(o.getEventTime());
	}
	
	/**
	 * @param other to compare
	 * @return True if they have the same identity
	 */
	@Override
	public boolean equals(final Object other) {
		if (this == other){
			return true;
		}
		if (other == null || getClass() != other.getClass()){
			return false;
		}
		return sameEventAs((EavropEventDTO)other);
	}
	 
	private	 boolean sameEventAs(final EavropEventDTO other) {
		//TODO: Add some kind of unique id
		return other != null 
				&& this.getEventType().equals(other.getEventType())
				&& this.getEventTime().equals(other.getEventTime());
	}
	
	/**
	 * @return HashCode.
	 */
	@Override
	public int hashCode() {
		int result = getEventType().hashCode();
		result = result * this.getEventTime().hashCode() ;
		return result;
	}
}
