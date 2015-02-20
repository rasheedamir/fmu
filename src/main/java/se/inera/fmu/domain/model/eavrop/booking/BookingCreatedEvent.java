package se.inera.fmu.domain.model.eavrop.booking;


import lombok.ToString;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;
/**
 * Domain event describing that a booking has been created on the eavrop
 */
@ToString
public class BookingCreatedEvent extends EavropEvent{
	private final ArendeId arendeId;
	private final BookingId bookingId;
	private final BookingType bookingType;
	private final DateTime startDateTime;
	private final DateTime endDateTime;
	private final String resourceName;
	private final String resourceRole;
    private final boolean interpreter;

	
	
	//~ Constructors ===================================================================================================
	public BookingCreatedEvent(final EavropId eavropId, final ArendeId arendeId, final BookingId bookingId, BookingType bookingType, 
							   final DateTime startDateTime, final DateTime endDateTime, final String resourceName,
							   final String resourceRole, final boolean interpreter) {
		super(eavropId);
		this.arendeId = arendeId;
		this.bookingId = bookingId;
		this.bookingType = bookingType;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.resourceName = resourceName;
		this.resourceRole = resourceRole;
		this.interpreter = interpreter;
				
	}

	//~ Property Methods ===============================================================================================

	public BookingId getBookingId() {
		return this.bookingId;
	}

	public ArendeId getArendeId() {
		return arendeId;
	}

	public BookingType getBookingType() {
		return bookingType;
	}

	public DateTime getStartDateTime() {
		return startDateTime;
	}

	public DateTime getEndDateTime() {
		return endDateTime;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getResourceRole() {
		return resourceRole;
	}

	public boolean isInterpreter() {
		return interpreter;
	}
	
}
