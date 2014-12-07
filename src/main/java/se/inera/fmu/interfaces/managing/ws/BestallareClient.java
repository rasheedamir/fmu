package se.inera.fmu.interfaces.managing.ws;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.interfaces.managing.command.PublishFmuAssignmentResponseCommand;
import se.inera.fmu.interfaces.managing.command.PublishFmuBookingCommand;
import se.inera.fmu.interfaces.managing.command.PublishFmuBookingDeviationCommand;
import se.inera.fmu.interfaces.managing.command.PublishFmuDocumentRequestedCommand;
import ws.fk.fmu.admin.eavrop.AdressType;
import ws.fk.fmu.admin.eavrop.BegarKompletteringFmuHandlingRequest;
import ws.fk.fmu.admin.eavrop.BegarKompletteringFmuHandlingResponse;
import ws.fk.fmu.admin.eavrop.BookingDeviationType;
import ws.fk.fmu.admin.eavrop.FmuBokningRequest;
import ws.fk.fmu.admin.eavrop.FmuBokningResponse;
import ws.fk.fmu.admin.eavrop.FmuBokningsavvikelseRequest;
import ws.fk.fmu.admin.eavrop.FmuBokningsavvikelseResponse;
import ws.fk.fmu.admin.eavrop.FmuVardgivarenhetTilldelningRequest;
import ws.fk.fmu.admin.eavrop.FmuVardgivarenhetTilldelningResponse;
import ws.fk.fmu.admin.eavrop.Notering;
import ws.fk.fmu.admin.eavrop.Person;
import ws.fk.fmu.admin.eavrop.StatusCode;
import ws.fk.fmu.admin.eavrop.VardgivarenhetType;

/**
 * Created by Rasheed on 12/3/14.
 */
@Slf4j
public class BestallareClient extends WebServiceGatewaySupport {

	
	private final DatatypeFactory dateTypeFactory;
	
	public BestallareClient() throws DatatypeConfigurationException{
		
		dateTypeFactory = DatatypeFactory.newInstance();
	}
	
	/**
	 * 
	 * @param aCommand
	 */
	public void publishFmuAssignmentResponse(final PublishFmuAssignmentResponseCommand aCommand){
		
		FmuVardgivarenhetTilldelningRequest request = new FmuVardgivarenhetTilldelningRequest();
		
		request.setArendeId(aCommand.getArendeId().toString());
		request.setAccepterad(aCommand.getAccepted());
		
		AdressType address = new AdressType();
		address.setPostadress(aCommand.getPostalAddress());
		address.setPostnummer(aCommand.getPostalCode());
		address.setStad(aCommand.getCity());
		address.setLand(aCommand.getCountry());
		
		VardgivarenhetType vardgivarenhet = new VardgivarenhetType();
		vardgivarenhet.setAdress(address);
		vardgivarenhet.setEnhetsnamn(aCommand.getUnit());
		vardgivarenhet.setOrganisation(aCommand.getOrganisation());
		vardgivarenhet.setTelefon(aCommand.getPhone());
		vardgivarenhet.setEpostAdress(aCommand.getEmailAddress());
		
		request.setVardgivarenhet(vardgivarenhet);
		
	    //Send request and receive response
	    try {
	    	FmuVardgivarenhetTilldelningResponse response =
		  	      (FmuVardgivarenhetTilldelningResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		    if(response == null ||
		    		response.getServiceResponse() == null ||!StatusCode.OK.equals(response.getServiceResponse().getStatusCode())){
		    	log.error("Assignment response on eavrop with arendeId:{} not sent to customer, message: {} ", aCommand.getArendeId().toString(),  (response!=null && response.getServiceResponse()!=null)?response.getServiceResponse().getErrorMessage():"");
		    }
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param aCommand
	 */
	public void publishFmuBooking(PublishFmuBookingCommand aCommand) {
	    //Create request
		FmuBokningRequest request = new FmuBokningRequest();
	    request.setArendeId(aCommand.getArendeId().toString());
	    request.setBokningsId(aCommand.getBookingId().getId());
	    request.setBokningsType(ws.fk.fmu.admin.eavrop.BookingType.valueOf(aCommand.getBookingType().toString()));
	    request.setStartDateTime(getDateTime(aCommand.getStartDateTime()));
	    request.setSlutDateTime(getDateTime(aCommand.getEndDateTime()));
	    request.setNamn(aCommand.getResourceName());
	    request.setRoll(aCommand.getResourceRole());
	    request.setTolk(aCommand.getInterpreterBooked());
	    
	    //Send request and receive response
	    try {
		    FmuBokningResponse response =
		  	      (FmuBokningResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		    
		    if(response == null ||
		    		response.getServiceResponse() == null ||!StatusCode.OK.equals(response.getServiceResponse().getStatusCode())){
		    	log.error("Booking with id:{} on eavrop with ArendeId {} not sent to customer, message: {} ", aCommand.getBookingId().getId(), aCommand.getArendeId().toString(), (response!=null && response.getServiceResponse()!=null)?response.getServiceResponse().getErrorMessage():"");
		    }
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	  }

	/**
	 * 
	 * @param aCommand
	 */
	public void publishFmuDocumentRequested(PublishFmuBookingDeviationCommand aCommand) {
		//Create request
		FmuBokningsavvikelseRequest request = new FmuBokningsavvikelseRequest();
		request.setArendeId(aCommand.getArendeId().toString());
	    request.setBokningsId(aCommand.getBookingId().getId());
	    request.setBokningsavvikelse(BookingDeviationType.valueOf(aCommand.getBookingDeviationType().toString()));
		request.setSvarBokningsavvikelseErfordras(aCommand.getBookingDeviationResponseRequired());
		request.setNotering(getNoteType(aCommand.getBookingDeviationNote()));
		
	    //Send request and receive response
	    try {
	    	FmuBokningsavvikelseResponse response =
		  	      (FmuBokningsavvikelseResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		    
		    if(response == null ||
		    		response.getServiceResponse() == null ||!StatusCode.OK.equals(response.getServiceResponse().getStatusCode())){
		    	log.error("Booking devation on booking with id:{} on eavrop with ArendeId {} not sent to customer, message: {} ", aCommand.getBookingId().getId(), aCommand.getArendeId().toString(), (response!=null && response.getServiceResponse()!=null)?response.getServiceResponse().getErrorMessage():"");
		    }
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	
	/**
	 * 
	 * @param aCommand
	 */
	public void publishFmuDocumentRequested(PublishFmuDocumentRequestedCommand aCommand) {
		//Create request
		BegarKompletteringFmuHandlingRequest request = new BegarKompletteringFmuHandlingRequest();
		request.setArendeId(aCommand.getArendeId().toString());
		request.setHandling(aCommand.getDocumentRequest());
		request.setHandlingBegardAv(getPersonType(aCommand.getRequestedBy()));
		request.setNotering(getNoteType(aCommand.getRequestNote()));
	    //Send request and receive response
	    try {
	    	BegarKompletteringFmuHandlingResponse response =
		  	      (BegarKompletteringFmuHandlingResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		    
		    if(response == null ||
		    		response.getServiceResponse() == null ||!StatusCode.OK.equals(response.getServiceResponse().getStatusCode())){
		    	log.error("Document request on eavrop with arendeId:{} not sent to customer, message: {} ", aCommand.getArendeId().toString(),  (response!=null && response.getServiceResponse()!=null)?response.getServiceResponse().getErrorMessage():"");
		    }
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	private Notering getNoteType(Note requestNote) {
		Notering notering= null;
		
		if(requestNote!=null){
			notering= new Notering();
			notering.setNotering(requestNote.getText());
			notering.setNoteradAv(getPersonType(requestNote.getPerson()));
		}
		
		return notering;
	}

	private Person getPersonType(se.inera.fmu.domain.model.person.Person person){
		Person personType = null;
		if(person != null){
			personType = new Person();
			personType.setNamn(person.getName());
			personType.setBefattning(person.getRole());
			personType.setEnhet(person.getUnit());
			personType.setOrganisation(person.getOrganisation());
			personType.setTelefon(person.getPhone());
			personType.setEpostAdress(person.getEmail());
		} 
		return personType;
	}
	
	private XMLGregorianCalendar getDateTime(DateTime dateTime){
		return (dateTime!=null)?dateTypeFactory.newXMLGregorianCalendar(dateTime.toGregorianCalendar()):null;
	}

}
