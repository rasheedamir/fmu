package se.inera.fmu.domain.model.eavrop;

import org.joda.time.LocalDate;

import se.inera.fmu.domain.model.landsting.LandstingCode;


public class EavropCreatedEvent extends EavropEvent {
	
	private final ArendeId arendeId;
	//TODO:Remove variables below when event handling is performed after the event creating transaction has finished
	private final LandstingCode landstingCode;
	private final UtredningType utredningType; 
	private final LocalDate lastDayOfAcceptance;
	
	//~ Constructors ===================================================================================================
    
	public EavropCreatedEvent(final EavropId eavropId, final ArendeId arendeid,  final UtredningType utredningType, final LandstingCode landstingCode, final LocalDate lastDayOfAcceptance) {
		super(eavropId);
		this.arendeId = arendeid;
		this.landstingCode = landstingCode;
		this.utredningType = utredningType;
		this.lastDayOfAcceptance = lastDayOfAcceptance;
		
	}

	//~ Property Methods ===============================================================================================
	
	public ArendeId getArendeId() {
		return this.arendeId;
	}

	public LandstingCode getLandstingCode() {
		return this.landstingCode;
	}

	public UtredningType getUtredningType() {
		return utredningType;
	}

	public LocalDate getLastDayOfAcceptance() {
		return lastDayOfAcceptance;
	}

	//~ Other Methods ==================================================================================================
}
