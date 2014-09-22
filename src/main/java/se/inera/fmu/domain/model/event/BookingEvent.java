package se.inera.fmu.domain.model.event;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.ToString;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.booking.Booking;
import se.inera.fmu.domain.model.eavrop.ArendeId;

@Entity
@ToString
public class BookingEvent extends EavropEvent {
	
	//TODO: Implement properties concerning this event and listeners listening to this event
	@OneToOne
	private Booking booking; 
	
	//~ Constructors ===================================================================================================

	BookingEvent() {
        //Needed by hibernate
    }

    
	BookingEvent(final LocalDateTime eventDateTime, final Booking booking ) {
		super(eventDateTime);
		this.setBooking(booking);
   }

	private void setBooking(Booking booking){
		this.booking = booking;
	}

	private Booking getBooking(){
		return this.booking;
	}
	

}
