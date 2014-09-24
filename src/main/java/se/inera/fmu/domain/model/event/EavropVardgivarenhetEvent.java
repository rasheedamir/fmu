package se.inera.fmu.domain.model.event;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;

public class EavropVardgivarenhetEvent extends EavropEvent {
	//TODO: Implement properties concerning this event and listeners listening to this event
	@OneToOne 
    @JoinColumn(name="VARDGIVARENHET_ID") 
	private Vardgivarenhet vardgivarenhet;

	
	//~ Constructors ===================================================================================================

	EavropVardgivarenhetEvent() {
	   //Needed by hibernate
    }

    
	public EavropVardgivarenhetEvent(final LocalDateTime eventDateTime, final Vardgivarenhet vardgivarenhet ) {
		super(eventDateTime);
		this.setVardgivarenhet(vardgivarenhet);
   }


	public Vardgivarenhet getVardgivarenhet() {
		return vardgivarenhet;
	}


	private void setVardgivarenhet(Vardgivarenhet vardgivarenhet) {
		this.vardgivarenhet = vardgivarenhet;
	}
	
	

}
