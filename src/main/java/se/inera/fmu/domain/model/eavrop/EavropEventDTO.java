package se.inera.fmu.domain.model.eavrop;

import org.joda.time.DateTime;

public class EavropEventDTO implements Comparable<EavropEventDTO>{ 

	
	private EavropEventDTOType eventType;
    private DateTime eventTime;
    private String eventStatus;
    private String personName;
    private String personRole;
    private String personOrganistation;
    private String personUnit;
    
    
    
	public EavropEventDTO(EavropEventDTOType eventType, DateTime eventTime, String eventStatus, String personName,
			String personRole, String personOrganistation, String personUnit) {
		super();
		this.eventType = eventType;
		this.eventTime = eventTime;
		this.eventStatus = eventStatus;
		this.personName = personName;
		this.personRole = personRole;
		this.personOrganistation = personOrganistation;
		this.personUnit = personUnit;
	}



	public EavropEventDTOType getEventType() {
		return eventType;
	}



	public DateTime getEventTime() {
		return eventTime;
	}



	public String getEventStatus() {
		return eventStatus;
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



	@Override
	public int compareTo(EavropEventDTO o) {
		return getEventTime().compareTo(o.getEventTime());
	}


}
