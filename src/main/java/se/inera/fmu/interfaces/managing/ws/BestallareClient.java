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
import se.inera.fmu.interfaces.managing.command.PublishFmuIntygSentCommand;
import se.inera.fmu.interfaces.managing.command.PublishFmuStartDate;
import fk.wsdl.*;

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
	 * Publishes web service message to bestallare with information about fmu assingnment responses
	 * Bestallare can be told about both accepted and rejected assignments
	 * 
	 * @param aCommand, a PublishFmuAssignmentResponseCommand with information about the Assignment response
	 */
	public void publishFmuAssignmentResponse(final PublishFmuAssignmentResponseCommand aCommand){
		
		//Set up request
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
		    		response.getServiceResponse() == null ||!StatusCodeType.OK.equals(response.getServiceResponse().getStatusCode())){
		    	log.error("Assignment response on eavrop with arendeId:{} not published, message: {} ", aCommand.getArendeId().toString(),  (response!=null && response.getServiceResponse()!=null)?response.getServiceResponse().getErrorMessage():"");
		    }else{
		    	log.debug("Assignment response on eavrop with arendeId:{} published ", aCommand.getArendeId().toString());
		    }
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Method publishes a web service message to bestallare with information about when the FMU will be considered started
	 * 
	 * @param aCommand, a PublishFmuStartDate with startDate information
	 */
	public void publishFmuStartDate(final PublishFmuStartDate aCommand){
		
		//Set up request
		FmuStartRequest request = new FmuStartRequest();
		request.setArendeId(aCommand.getArendeId().toString());
		request.setStartDateTime(getDateTime(aCommand.getStartDate()));
		
	    //Send request and receive response
	    try {
	    	FmuStartResponse response =
		  	      (FmuStartResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		    if(response == null ||
		    		response.getServiceResponse() == null ||!StatusCodeType.OK.equals(response.getServiceResponse().getStatusCode())){
		    	log.error("FMU start date on eavrop with arendeId:{} not published to customer, message: {} ", aCommand.getArendeId().toString(),  (response!=null && response.getServiceResponse()!=null)?response.getServiceResponse().getErrorMessage():"");
		    }else{
		    	log.debug("FMU start date on eavrop on eavrop with arendeId:{} published to customer ", aCommand.getArendeId().toString());
		    }
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	
	/**
	 * Method publishes a web service message to bestallare with information a booking added to the FMU Eavrop
	 * 
	 * @param aCommand, a PublishFmuBookingCommand with booking information
	 */
	public void publishFmuBooking(PublishFmuBookingCommand aCommand) {
	    //Set up request
		FmuBokningRequest request = new FmuBokningRequest();
	    request.setArendeId(aCommand.getArendeId().toString());
	    request.setBokningsId(aCommand.getBookingId().getId());
	    request.setBokningsType(fk.wsdl.BookingType.valueOf(aCommand.getBookingType().toString()));
	    request.setStartDateTime(getDateTime(aCommand.getStartDateTime()));
	    request.setSlutDateTime(getDateTime(aCommand.getEndDateTime()));
	    request.setNamn(aCommand.getResourceName());
	    request.setRoll(aCommand.getResourceRole());
	    request.setTolk(aCommand.getInterpreterBooked());
	    
	    //Send request and receive response
	    try {
		    FmuBokningResponse response = (FmuBokningResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		    if(response == null ||
		    		response.getServiceResponse() == null ||!StatusCodeType.OK.equals(response.getServiceResponse().getStatusCode())){
		    	log.error("Booking with id:{} on eavrop with ArendeId {} not published to customer, message: {} ", aCommand.getBookingId().getId(), aCommand.getArendeId().toString(), (response!=null && response.getServiceResponse()!=null)?response.getServiceResponse().getErrorMessage():"");
		    }else{
		    	log.debug("Booking with id:{} on eavrop with ArendeId {} not published to customer", aCommand.getBookingId().getId(), aCommand.getArendeId().toString());
		    }
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	  }

	/**
	 * Method publishes a web service message to bestallare with information about deviation on a booking added a FMU Eavrop
	 * 
	 * @param aCommand, a PublishFmuBookingDeviationCommand with booking deviation information
	 */
	public void publishFmuBookingDeviation(PublishFmuBookingDeviationCommand aCommand) {
		//Set up request
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
		    		response.getServiceResponse() == null ||!StatusCodeType.OK.equals(response.getServiceResponse().getStatusCode())){
		    	log.error("Booking devation on booking with id:{} on eavrop with ArendeId {} not published to customer, message: {} ", aCommand.getBookingId().getId(), aCommand.getArendeId().toString(), (response!=null && response.getServiceResponse()!=null)?response.getServiceResponse().getErrorMessage():"");
		    }else{
		    	log.debug("Booking devation on booking with id:{} on eavrop with ArendeId {} published to customer", aCommand.getBookingId().getId(), aCommand.getArendeId().toString());
		    }
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * Method publishes a web service message to bestallare with to request documents missing on a a FMU Eavrop
	 * 
	 * @param aCommand, a PublishFmuDocumentRequestedCommand with booking deviation information
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
		    		response.getServiceResponse() == null ||!StatusCodeType.OK.equals(response.getServiceResponse().getStatusCode())){
		    	log.error("Document request on eavrop with arendeId:{} not published to customer, message: {} ", aCommand.getArendeId().toString(),  (response!=null && response.getServiceResponse()!=null)?response.getServiceResponse().getErrorMessage():"");
		    }else{
		    	log.debug("Document request on eavrop with arendeId:{} published to customer", aCommand.getArendeId().toString());
		    }
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Method publishes a web service message to bestallare with information that an intyg has been sent that is related to a FMU Eavrop
	 * 
	 * @param aCommand, a PublishFmuDocumentRequestedCommand with booking deviation information
	 */
	public void publishFmuIntygSent(PublishFmuIntygSentCommand aCommand) {
		//Create request
		FmuIntygSentRequest request = new FmuIntygSentRequest();
		request.setArendeId(aCommand.getArendeId().toString());
		request.setIntygSentDateTime(getDateTime(aCommand.getIntygSendDateTime()));
	    //Send request and receive response
	    try {
	    	FmuIntygSentResponse response =
		  	      (FmuIntygSentResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		    
		    if(response == null ||
		    		response.getServiceResponse() == null ||!StatusCodeType.OK.equals(response.getServiceResponse().getStatusCode())){
		    	log.error("Intyg sent information on eavrop with arendeId:{} not published to customer, message: {} ", aCommand.getArendeId().toString(),  (response!=null && response.getServiceResponse()!=null)?response.getServiceResponse().getErrorMessage():"");
		    }else{
		    	log.debug("Intyg sent information on eavrop with arendeId:{} published to customer", aCommand.getArendeId().toString());
		    }
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	
	
	private NoteringType getNoteType(Note requestNote) {
		NoteringType notering= null;
		
		if(requestNote!=null){
			notering= new NoteringType();
			notering.setNotering(requestNote.getText());
			notering.setNoteradAv(getPersonType(requestNote.getPerson()));
		}
		
		return notering;
	}

	private PersonType getPersonType(se.inera.fmu.domain.model.person.Person person){
		PersonType personType = null;
		if(person != null){
			personType = new PersonType();
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
