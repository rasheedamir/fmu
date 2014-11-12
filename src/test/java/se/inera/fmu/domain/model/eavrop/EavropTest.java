package se.inera.fmu.domain.model.eavrop;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executors;

import org.joda.time.DateTime;
import org.junit.Test;

import com.google.common.eventbus.AsyncEventBus;

import junit.framework.TestCase;
import se.inera.fmu.application.DomainEventPublisher;
import se.inera.fmu.application.impl.DomainEventPublisherImpl;
import se.inera.fmu.application.impl.command.AcceptEavropAssignmentCommand;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignment;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviation;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponse;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationType;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;
import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSignedInformation;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.eavrop.note.NoteType;
import se.inera.fmu.domain.model.eavrop.properties.EavropProperties;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivare;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.person.HoSPerson;
import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;

public class EavropTest extends TestCase {
	private Eavrop eavrop;
	
	private Invanare invanare;
	private PersonalNumber personalNumber; 
	private Gender gender;

	private String email;
	private String specialNeeds; 
	
	
	private Address address;
	private String address1;
	private String address2;
	private String postalCode;
	private String state;
	private String city;
	private String country;

	private Name name;
	private String firstName;
	private String middleName;
	private String lastName;

	private ArendeId arendeId;

	private UtredningType utredningType;

	private String tolk;
	
	private Landsting landsting;
	
	private Bestallaradministrator bestallaradministrator;
	
	private HoSPerson doctorPerson;
	
	private Vardgivarenhet vardgivarenhet;
	
	private Vardgivare vardgivare;
	
	private DomainEventPublisher domainEventPublisher = new DomainEventPublisherImpl(); //TODO: one for test

	@Override
	protected void setUp() throws Exception {
		firstName = "john";
		middleName = "k";
		lastName = "lars";
		email = "email@test.se";
		address1 = "testgatan 1";
		address2 = "testgatan 2";
		postalCode = "33144";
		state = "test county";
		city = "test city";
		country = "testland";
		personalNumber = new PersonalNumber("6677665577");
		gender = Gender.MALE;
		invanare = new Invanare(personalNumber, name, gender, address, email, specialNeeds);
		arendeId = new ArendeId("1312421532151");
		utredningType = UtredningType.SLU;
		tolk = "Swedish";
		landsting = new Landsting (new LandstingCode(1), "Stockholms läns landsting");
		bestallaradministrator = new Bestallaradministrator("Per Elofsson","Handläggare", "Försäkringskassan", "LFC Stockholm", "08123456", "per.elofsson@forsakringskassan.se");
		vardgivare = new Vardgivare(new HsaId("SE160000000000-HAHAHHSAA"), "Cary Care");
		vardgivarenhet = new Vardgivarenhet(vardgivare, new HsaId("SE160000000000-HAHAHHSAB"), "CareIt", new Address("","","",""));
		doctorPerson = new HoSPerson("Dr Hudson", "Surgeon", "Danderyds sjukhus","FMU enheten");

	}
	
	@Test
	public void testCreateName() {
		name = new Name(firstName, middleName, lastName);
		assertEquals(firstName, name.getFirstName());
		assertEquals(middleName, name.getMiddleName());
		assertEquals(lastName, name.getLastName());
	}
	
	@Test
	public void  testCreateAddress1() {
		address = new Address(address1, postalCode, city, country);
		assertEquals(address1, address.getAddress1());
		assertEquals(null, address.getAddress2());
		assertEquals(city, address.getCity());
		assertEquals(country, address.getCountry());
		assertEquals(postalCode, address.getPostalCode());
		assertEquals(null, address.getState());
	}
	
	@Test
	public void  testCreateAddress2() {
		address = new Address(address1, address2, postalCode, state, city, country);
		assertEquals(address1, address.getAddress1());
		assertEquals(address2, address.getAddress2());
		assertEquals(city, address.getCity());
		assertEquals(country, address.getCountry());
		assertEquals(postalCode, address.getPostalCode());
		assertEquals(state, address.getState());
	}
	
	@Test
	public void testInvanare() {
		invanare = new Invanare(personalNumber, name, gender, address, email, specialNeeds);
		assertEquals(personalNumber, invanare.getPersonalNumber());
		assertEquals(name, invanare.getName());
		assertEquals(gender, invanare.getGender());
		assertEquals(address, invanare.getHomeAddress());
		assertEquals(email, invanare.getEmail());
	}
	
	@Test
	public void  testCreateEavrop() {
		
		eavrop = EavropBuilder.eavrop()
		.withArendeId(arendeId)
		.withUtredningType(utredningType) 
		.withInvanare(invanare)
		.withLandsting(landsting)
		.withBestallaradministrator(bestallaradministrator)
		.withEavropProperties(new EavropProperties(3,5,25,10))
		.build();
		
		assertEquals(invanare, eavrop.getInvanare());
		assertEquals(utredningType, eavrop.getUtredningType());
		assertEquals(arendeId, eavrop.getArendeId());
		
		//TODO:Test more properties when added	
		}
	
	@Test
	public void  testEavropStateFlow() {
		
		eavrop = EavropBuilder.eavrop()
		.withArendeId(arendeId)
		.withUtredningType(utredningType) 
		.withInvanare(invanare)
		.withLandsting(landsting)
		.withBestallaradministrator(bestallaradministrator)
		.withEavropProperties(new EavropProperties(3,5,25,10))
		.build();
		
		assertEquals(invanare, eavrop.getInvanare());
		assertEquals(utredningType, eavrop.getUtredningType());
		assertEquals(arendeId, eavrop.getArendeId());
		
		assertEquals(EavropStateType.UNASSIGNED, eavrop.getStatus());
	
		eavrop.assignEavropToVardgivarenhet(vardgivarenhet);
		
		assertEquals(EavropStateType.ASSIGNED, eavrop.getStatus());

		eavrop.rejectEavropAssignment();;

		assertEquals(EavropStateType.UNASSIGNED, eavrop.getStatus());

		eavrop.assignEavropToVardgivarenhet(vardgivarenhet);
		
		assertEquals(EavropStateType.ASSIGNED, eavrop.getStatus());

		eavrop.acceptEavropAssignment();;

		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		eavrop.addReceivedDocument(new ReceivedDocument("Internal", bestallaradministrator,Boolean.FALSE));
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		eavrop.addReceivedDocument(new ReceivedDocument("External", bestallaradministrator,Boolean.TRUE));

		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		eavrop.addRequestedDocument(new RequestedDocument("Journal", doctorPerson));

		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());

		Booking booking = createBooking();
		eavrop.addBooking(booking);
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		eavrop.setBookingStatus(booking.getBookingId(), BookingStatusType.CANCELLED_NOT_PRESENT, null);

		assertEquals(EavropStateType.ON_HOLD, eavrop.getStatus());

		eavrop.addBookingDeviationResponse(booking.getBookingId(), createBookingDeviationResponse(null));
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		eavrop.addIntygSignedInformation(createIntygSignedInformation(null));
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		eavrop.addIntygComplementRequestInformation(createIntygComplementRequestInformation(null));
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());

		eavrop.addIntygApprovedInformation(createIntygApprovedInformation(null));;
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		Note note = new Note(NoteType.EAVROP,"",null);
		
		eavrop.addNote(new Note(NoteType.EAVROP,"",null));

		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());

		note = eavrop.getNote(note.getNoteId());
		
		eavrop.removeNote(note);

		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());

		eavrop.approveEavrop(createEavropApproval(null));
		
		assertEquals(EavropStateType.APPROVED, eavrop.getStatus());

		eavrop.approveEavropCompensation(createEavropCompensationApproval(null));

		assertEquals(EavropStateType.CLOSED, eavrop.getStatus());
	}

	
	@Test
	public void  testEavropAccept() {
		
		eavrop = EavropBuilder.eavrop()
		.withArendeId(arendeId)
		.withUtredningType(utredningType) 
		.withInvanare(invanare)
		.withLandsting(landsting)
		.withBestallaradministrator(bestallaradministrator)
		.withEavropProperties(new EavropProperties(3,5,25,10))
		.build();
		
		eavrop.setCreatedDate(new DateTime(2014,10,1,10,30));

		// Today is more than 5 business days since createDateTime 
		assertEquals(true, eavrop.isEavropAcceptDaysDeviated());

		
		eavrop.assignEavropToVardgivarenhet(vardgivarenhet);
		eavrop.acceptEavropAssignment();
		assertEquals(true, eavrop.isEavropAcceptDaysDeviated());
		
		
		eavrop.getCurrentAssignment().setLastModifiedDate(new DateTime(2014,10,8,23,59));
		assertEquals(false, eavrop.isEavropAcceptDaysDeviated());

		eavrop.getCurrentAssignment().setLastModifiedDate(new DateTime(2014,10,9,0,0));
		assertEquals(true, eavrop.isEavropAcceptDaysDeviated());
		
	}
	
	@Test
	public void  testEavropAssessment() {
		
		eavrop = EavropBuilder.eavrop()
		.withArendeId(arendeId)
		.withUtredningType(utredningType) 
		.withInvanare(invanare)
		.withLandsting(landsting)
		.withBestallaradministrator(bestallaradministrator)
		.withEavropProperties(new EavropProperties(3,5,25,10))
		.build();
		
		eavrop.setCreatedDate(new DateTime(2014,10,1,10,30));

		eavrop.assignEavropToVardgivarenhet(vardgivarenhet);
		
		eavrop.acceptEavropAssignment();
		
		assertNull(eavrop.getStartDate());
		
		ReceivedDocument document = new ReceivedDocument(new DateTime(2014,10,7,10,30), "DOC", new Bestallaradministrator("A","B","C","D","E","F"),Boolean.TRUE);
		eavrop.addReceivedDocument(document);
		
		assertNotNull(eavrop.getStartDate());
		
		eavrop.addIntygSignedInformation(new IntygSignedInformation(new DateTime(2014,11,14,23,59),doctorPerson));
		
		assertEquals(false, eavrop.isEavropAssesmentDaysDeviated());
		
	}

//	@Test
//	public void  testFMU_64_View_Samordnare() {
//		
//		eavrop = EavropBuilder.eavrop()
//		.withArendeId(arendeId)
//		.withUtredningType(utredningType) 
//		.withInvanare(invanare)
//		.withLandsting(landsting)
//		.withBestallaradministrator(bestallaradministrator)
//		.withEavropProperties(new EavropProperties(3,5,25,10))
//		.build();
//		
//		eavrop.setCreatedDate(new DateTime(2014,10,1,10,30));
//
//		eavrop.assignEavropToVardgivarenhet(vardgivarenhet);
//
//		eavrop.rejectEavropAssignment();
//
//		//1. Ärende-ID
//		assertNotNull(eavrop.getArendeId());
//		//2. Typ
//		assertNotNull(eavrop.getUtredningType());
//		//3. Beställare -> Organisation
//		assertNotNull(eavrop.getBestallaradministrator().getOrganisation());
//		//4.Beställare -> Enhet/Avdelning
//		assertNotNull(eavrop.getBestallaradministrator().getUnit());
//		//5.Beställare -> Förfrågan inkommit, datum
//		assertNotNull(eavrop.getCreatedDate());
//		//6.Beställare -> Den försäkrades bostadsort
//		assertNotNull(eavrop.getInvanare().getHomeAddress().getCity());
//		//7. Beställning mottagen av -> Organisation
//		assertNotNull(eavrop.getLandsting().getName());
//		
//		//A row in the view list should be created for 	1. all the assigments in the eavrop
//		//										  		2. one row for the unassingned eavrop   
//		
//		if(eavrop.getAssignments()!=null){
//			for (EavropAssignment eavropAssignment : eavrop.getAssignments()) {
//				//8. Förfrågan om utredning skickad till -> Utförare
//				assertNotNull(eavropAssignment.getVardgivarenhet().getUnitName());
//				//9. Status
//				assertNotNull(eavropAssignment.getAssignmentStatus());
//				//10. Antal dagar efter förfrågan om utredning.
//				assertNotNull(eavropAssignment.getAssignmentReplyDays());
//			}
//		} 
//	
//		if(eavrop.getStatus().equals(EavropStateType.UNASSIGNED)){
//			//8. Förfrågan om utredning skickad till -> Utförare
//			//"Förfrågan ej skickad"
//			//TODO:
//			//assertNotNull(eavropAssignment.getVardgivarenhet().getUnitName());
//			//9. Status "Förfrågan om utredning har inkommit" 
//			assertEquals(EavropStateType.UNASSIGNED,eavrop.getStatus());
//			//10. Antal dagar efter förfrågan om utredning.
//			//TODO: Eavrop create until now
//			//assertNotNull(eavropAssignment.getAssignmentReplyDays());
//		}
//		
//	}
//
//	@Test
//	public void  testFMU_64_View_Utredare() {
//		
//		eavrop = EavropBuilder.eavrop()
//		.withArendeId(arendeId)
//		.withUtredningType(utredningType) 
//		.withInvanare(invanare)
//		.withLandsting(landsting)
//		.withBestallaradministrator(bestallaradministrator)
//		.withEavropProperties(new EavropProperties(3,5,25,10))
//		.build();
//		
//		eavrop.setCreatedDate(new DateTime(2014,10,1,10,30));
//
//		eavrop.assignEavropToVardgivarenhet(vardgivarenhet);
//		
//		//1. Ärende-ID
//		assertNotNull(eavrop.getArendeId());
//		//2. Typ
//		assertNotNull(eavrop.getUtredningType());
//		//3. Beställare -> Organisation
//		assertNotNull(eavrop.getBestallaradministrator().getOrganisation());
//		//4.Beställare -> Enhet/Avdelning
//		assertNotNull(eavrop.getBestallaradministrator().getUnit());
//		//5.Beställare -> Förfrågan inkommit, datum
//		assertNotNull(eavrop.getCreatedDate());
//		//6. Beställning mottagen av -> Organisation
//		assertNotNull(eavrop.getLandsting().getName());
//		//7. Status
//		assertNotNull(eavrop.getStatus());
//		//8. Antal dagar
//		assertNotNull(eavrop.getCurrentAssignment().getStatus());
//		
//		if(eavrop.getAssignments()!=null){
//			for (EavropAssignment eavropAssignment : eavrop.getAssignments()) {
//				//8. Förfrågan om utredning skickad till -> Utförare
//				assertNotNull(eavropAssignment.getVardgivarenhet().getUnitName());
//				//9. Status
//				assertNotNull(eavropAssignment.getAssignmentStatus());
//				//10. Antal dagar efter förfrågan om utredning.
//				assertNotNull(eavropAssignment.getAssignmentReplyDays());
//			}
//		} 
//	
//		if(eavrop.getStatus().equals(EavropStateType.UNASSIGNED)){
//			//8. Förfrågan om utredning skickad till -> Utförare
//			//"Förfrågan ej skickad"
//			//TODO:
//			//assertNotNull(eavropAssignment.getVardgivarenhet().getUnitName());
//			//9. Status "Förfrågan om utredning har inkommit" 
//			assertEquals(EavropStateType.UNASSIGNED,eavrop.getStatus());
//			//10. Antal dagar efter förfrågan om utredning.
//			//TODO: Eavrop create until now
//			//assertNotNull(eavropAssignment.getAssignmentReplyDays());
//		}
//		
//	}
//	
//
//	
	@Test
	public void  testFMU_66_Alla_Handelser() {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		eavrop = EavropBuilder.eavrop()
		.withArendeId(arendeId)
		.withUtredningType(utredningType) 
		.withInvanare(invanare)
		.withLandsting(landsting)
		.withBestallaradministrator(bestallaradministrator)
		.withEavropProperties(new EavropProperties(3,5,25,10))
		.build();
		
		
		//Eavrop was ordered first of october
		eavrop.setCreatedDate(new DateTime(2014,10,1,10,30));

		//Assigned to care giver the following day second of october
		assignToVardgivarenhet(eavrop, vardgivarenhet, new DateTime(2014,10,2,10,30));
		
		assertEquals(EavropStateType.ASSIGNED, eavrop.getStatus());

		//The care giver rejects the eavrop on third of october
		rejectEavropAssignment(eavrop, new DateTime(2014,10,3,10,30));

		assertEquals(EavropStateType.UNASSIGNED, eavrop.getStatus());

		//Assigned again to a care giver on the sixth of october
		assignToVardgivarenhet(eavrop, vardgivarenhet, new DateTime(2014,10,6,10,30));
		
		assertEquals(EavropStateType.ASSIGNED, eavrop.getStatus());

		acceptEavropAssignment(eavrop, new DateTime(2014,10,10,10,30));
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		//adding documents internally
		eavrop.addReceivedDocument(new ReceivedDocument( new DateTime(2014,10,10,10,30), "SASSAM ", doctorPerson, Boolean.FALSE));
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		//Fk signals that thet have sent documents on the thirteenth then the fist day of the assessment is on the 17th?
		eavrop.addReceivedDocument(new ReceivedDocument(new DateTime(2014,10,13,10,30), "FK 7325", bestallaradministrator, Boolean.TRUE));

		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		//doctor adds another document
		eavrop.addRequestedDocument(new RequestedDocument(new DateTime(2014,10,17,10,30),"Journal", doctorPerson));

		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		//a booking is made
		DateTime start = new DateTime(2014,10,20,8,0);
		Booking booking = new Booking(BookingType.EXAMINATION, start,start.plusHours(1), doctorPerson, Boolean.TRUE);
		eavrop.addBooking(booking);
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		eavrop.setInterpreterBookingStatus(booking.getBookingId(), InterpreterBookingStatusType.NOT_PRESENT, new Note(NoteType.BOOKING_DEVIATION, "Tolk uteblev",doctorPerson));
		eavrop.setBookingStatus(booking.getBookingId(), BookingStatusType.CANCELLED_NOT_PRESENT, new Note(NoteType.BOOKING_DEVIATION, "Patient uteblev",doctorPerson));
		
		booking.getDeviationNote().setCreatedDate(start);
		booking.getInterpreterBooking().getDeviationNote().setCreatedDate(start);
		
		assertEquals(EavropStateType.ON_HOLD, eavrop.getStatus());

		eavrop.addBookingDeviationResponse(booking.getBookingId(), createBookingDeviationResponse(new DateTime(2014,10,21,8,0)));
		
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		//New booking is made
		start = new DateTime(2014,10,24,8,0);
		booking = new Booking(BookingType.EXAMINATION, start,start.plusHours(1), doctorPerson, Boolean.TRUE);
		eavrop.addBooking(booking);

		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		
		eavrop.addIntygSignedInformation(createIntygSignedInformation(start.plusHours(5)));
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		eavrop.addIntygComplementRequestInformation(createIntygComplementRequestInformation(new DateTime(2014,10,27,8,0)));
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());

		eavrop.addIntygSignedInformation(createIntygSignedInformation(new DateTime(2014,10,28,8,0)));
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());

		eavrop.addIntygApprovedInformation(createIntygApprovedInformation(new DateTime(2014,10,30,8,0)));;
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		eavrop.addNote(new Note(NoteType.EAVROP,"Utredning utförd",null));

		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());

//		aNote = eavrop.getNote(aNote.getNoteId());
//		
//		eavrop.removeNote(aNote);

		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());

		eavrop.approveEavrop(createEavropApproval(new DateTime(2014,11,3,8,0)));
		
		assertEquals(EavropStateType.APPROVED, eavrop.getStatus());

		eavrop.approveEavropCompensation(createEavropCompensationApproval(new DateTime(2014,11,7,8,0)));

		assertEquals(EavropStateType.CLOSED, eavrop.getStatus());
		System.out.println("Alla händelser\n");
		System.out.println("Sammanfattning");
		//Sammanfattning
		//1. Förfrågan om utredning
		assertNotNull(eavrop.getCreatedDate());
		//2. Handlingar översändes
		assertNotNull(eavrop.getDateTimeDocumentsSentFromBestallare());
		//3. Utredning, antal dagar, 
		assertNotNull(eavrop.getNoOfAssesmentDays());
		assertNotNull(eavrop.isEavropAssesmentDaysDeviated()); //Line indicator
		//4.Begärda tillägg, handlingar.
		assertNotNull(eavrop.getRequestedDocuments().size());
		assertNotNull(eavrop.getReceivedDocuments().size());
		//5.Acceptans av utredningen
		assertNotNull(eavrop.getCurrentAssignment().isAccepted());
		assertNotNull(eavrop.getCurrentAssignment().getAssignmentResponseDateTime());
		//6.Intyg skickat till beställaren
		assertNotNull(eavrop.getIntygSignedDateTime());
		//7. Antal avvikelser
		assertNotNull(eavrop.getNumberOfDeviationsOnEavrop());
		//8. Begärda kompletteringar till utredning.
		assertNotNull(eavrop.getNoOfIntygComplementRequests());

		System.out.println("Förfrågan om utreding\tHandlingar översändes\tUtredning antal dagar\tBegärda tillägg / handlingar");
		System.out.println(format.format(eavrop.getCreatedDate().toDate())+"\t\t"+format.format(eavrop.getDateTimeDocumentsSentFromBestallare().toDate())+"\t\t"+eavrop.getNoOfAssesmentDays()+"\t\t\t"+(eavrop.getRequestedDocuments().size()+eavrop.getReceivedDocuments().size()));
		System.out.println();
		System.out.println("Acceptans av utred.\tIntyg skickat\t\tAntal avvikelser\tBegärda kompletteringar");
		System.out.println(format.format(eavrop.getCurrentAssignment().getAssignmentResponseDateTime().toDate())+"\t\t"+format.format(eavrop.getIntygSignedDateTime().toDate())+"\t\t"+eavrop.getNumberOfDeviationsOnEavrop()+"\t\t\t"+eavrop.getNoOfIntygComplementRequests());
		System.out.println();
		
		//Beställning
		//9. Ärende-ID
		assertNotNull(eavrop.getArendeId());
		//10. Typ
		assertNotNull(eavrop.getUtredningType());
		//11. Beställare -> Organisation
		assertNotNull(eavrop.getBestallaradministrator().getOrganisation());
		//12.Beställare -> Enhet/Avdelning
		assertNotNull(eavrop.getBestallaradministrator().getUnit());
		//13.Beställare -> Förfrågan inkommit, datum
		assertNotNull(eavrop.getCreatedDate());
		//14. Beställning mottagen av -> Organisation
		assertNotNull(eavrop.getLandsting().getName());
		//15 Beställning mottagen av --> Utredare/Enhet
		assertNotNull(eavrop.getCurrentAssignment().getVardgivarenhet().getUnitName());
		//16. Acceptans av utredning
		assertNotNull(eavrop.getCurrentAssignment().getAssignmentResponseDateTime());
		//17. Antal dagar efter förfrågan om utredning
		assertNotNull(eavrop.getCurrentAssignment().getNoOfAssignmentResponseDays());
		System.out.println("Beställning");
		boolean first = true;
		System.out.println("Ärende-ID\tTyp\tOrganisation\t\tEnhet\t\tFörfrågan inkommit\tUtredare/Enhet\tAcceptans\tAntal dagar efter förfrågan om utredning");
		for (EavropAssignment assignment : eavrop.getAssignments()) {
			System.out.println(""+((first)?eavrop.getArendeId()+"\t":("\t\t\t"))
								 +((first)?eavrop.getUtredningType()+"\t":("\t\t")) 
								 +((first)?eavrop.getBestallaradministrator().getOrganisation()+"\t":("\t\t"))
								 +((first)?eavrop.getBestallaradministrator().getUnit()+"\t":("\t\t"))
								 +((first)?format.format(eavrop.getCreatedDate().toDate())+"\t\t":("\t\t"))
								 +assignment.getVardgivarenhet().getUnitName()+"\t\t"
								 +assignment.getAssignmentStatus()+"\t"
								 +eavrop.getAssignmentNumberOFResponsDaysFromOrderDate(assignment)+ ((eavrop.isAssignmentNumberOFResponsDaysFromOrderDateDeviated(assignment))?"(*)":"")); //TODO
			first = false;
		}

		
		
		//Handlingar
		System.out.println("Handlingar");
		System.out.println("Handling\tDelad av\tDelad Datum\tMottagen Datum");
		for (ReceivedDocument receivedDocument : eavrop.getReceivedDocuments()) {
			//18. Handling
			assertNotNull(receivedDocument.getDocumentName());
			//19. ??? Delad av
			assertNotNull(receivedDocument.getPerson());
			//20. ??? Delad, datum
			//Finns ej....//TODO:
			//21. Mottagen, datum
			assertNotNull(receivedDocument.getDocumentDateTime());
			System.out.println(receivedDocument.getDocumentName()+"\t\t"+receivedDocument.getPerson().getName()+"\tN/A\t\t"+format.format(receivedDocument.getDocumentDateTime().toDate()));
			
		}

		System.out.println("");
		
		//Tillägg
		System.out.println("Tillägg");
		System.out.println("Handling\tBegäran av\tBegäran Datum\tBegäran till\tMottagen Datum");
		for (RequestedDocument requestedDocument : eavrop.getRequestedDocuments()) {
			//22. Handling
			assertNotNull(requestedDocument.getDocumentName());
			//23. Begäran, skickad av
			assertNotNull(requestedDocument.getPerson().toString());
			//24. Begäran skickad, datum
			assertNotNull(requestedDocument.getDocumentDateTime());
			//24. Begäran skickad till
			assertNotNull(eavrop.getBestallaradministrator().getName());
			//25. Tillägg mottaget, datum
			//Finns ej...
			System.out.println(requestedDocument.getDocumentName()+"\t\t"+requestedDocument.getPerson().getName()+"\t"+format.format(requestedDocument.getDocumentDateTime().toDate())+"\t"+eavrop.getBestallaradministrator().getName()+"\t\tN/A");
		}
		System.out.println("");
		
		//Utredning EavropEventDTO
		System.out.println("Utredning");
		System.out.println("Händelse\t\t\tDatum\tPerson/Roll/Organisation\tStatus\t");
		for (EavropEventDTO eavropEventDTO : eavrop.getEavropEvents()) {
			//26. Händelse
			assertNotNull(eavropEventDTO.getEventType());
			//27. Datum
			assertNotNull(eavropEventDTO.getEventTime());
			//28. Person/Roll/Organisation/Unit
			//assertNotNull(eavropEventDTO.getPersonName());
			//assertNotNull(eavropEventDTO.getPersonRole());
			//assertNotNull(eavropEventDTO.getPersonOrganistation());
			//assertNotNull(eavropEventDTO.getPersonUnit());
			//29. Status
			//assertNotNull(eavropEventDTO.getEventStatus());
			System.out.println(eavropEventDTO.getEventType().toString()+"\t\t"+format.format(eavropEventDTO.getEventTime().toDate())+"\t"+eavropEventDTO.getPersonName()+"/"+eavropEventDTO.getPersonRole()+"/"+eavropEventDTO.getPersonOrganistation()+"/"+eavropEventDTO.getPersonUnit()+"\t"+eavropEventDTO.getEventStatus());

		}
	
		System.out.println("");
		
		//Anteckningar
		System.out.println("Anteckning");
		System.out.println("Innehåll\t\t\tSkapad av \tDatum\t");
		for (Note note : eavrop.getAllNotes()) {
			//??. Typ
			assertNotNull(note.getNoteType());
			//23. Innehåll
			assertNotNull(note.getText());
			//24. Skapad av
//			assertNotNull(note.getPerson().getName());
//			assertNotNull(note.getPerson().getRole());
//			assertNotNull(note.getPerson().getOrganisation());
//			assertNotNull(note.getPerson().getUnit());
			//24. Datum
			assertNotNull(eavrop.getBestallaradministrator().getName());
			
			System.out.println(note.getText()+"\t\t"+((note.getPerson()!=null)?note.getPerson().getName():"")+"\t\t"+format.format(note.getCreatedDate().toDate()));
		}
	
		
		

	
	}

	
	
	

private void acceptEavropAssignment(Eavrop eavrop, DateTime dateTime) {
		eavrop.acceptEavropAssignment();
		EavropAssignment assignment = eavrop.getCurrentAssignment();
		assignment.setLastModifiedDate(dateTime);
		
}

private void rejectEavropAssignment(Eavrop eavrop, DateTime dateTime) {
	EavropAssignment assignment = eavrop.getCurrentAssignment();
	eavrop.rejectEavropAssignment();
	assignment.setLastModifiedDate(dateTime);
	
}

//	@Test
//	public void  testEavropStartDate() {
//		
//		eavrop = EavropBuilder.eavrop()
//		.withArendeId(arendeId)
//		.withUtredningType(utredningType) 
//		.withInvanare(invanare)
//		.withLandsting(landsting)
//		.withBestallaradministrator(bestallaradministrator)
//		.withEavropProperties(new EavropProperties(3,5,25,10))
//		.build();
//		
//		eavrop.setCreatedDate(new DateTime(2014,10,1,10,30));
//		
//		ReceivedDocument doc = new ReceivedDocument(new DateTime(2014,10,1,10,30), "TEST", new Bestallaradministrator("","","","",""),true );
//		eavrop.addReceivedDocument(doc);
//		
//		assertEquals(invanare, eavrop.getInvanare());
//		assertEquals(utredningType, eavrop.getUtredningType());
//		assertEquals(arendeId, eavrop.getArendeId());
//		
//		assertEquals(EavropStateType.UNASSIGNED, eavrop.getStatus());
//	
//		eavrop.assignEavropToVardgivarenhet(vardgivarenhet);
//		
//		assertEquals(EavropStateType.ASSIGNED, eavrop.getStatus());
//
//		eavrop.rejectEavropAssignment();;
//
//		assertEquals(EavropStateType.UNASSIGNED, eavrop.getStatus());
//
//		eavrop.assignEavropToVardgivarenhet(vardgivarenhet);
//		
//		assertEquals(EavropStateType.ASSIGNED, eavrop.getStatus());
//
//		eavrop.acceptEavropAssignment();;
//
//		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
//		
//		eavrop.addReceivedDocument(new ReceivedDocument("Internal", bestallaradministrator,Boolean.FALSE));
//		
//		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
//		
//		eavrop.addReceivedDocument(new ReceivedDocument("External", bestallaradministrator,Boolean.TRUE));
//
//		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
//		
//		eavrop.addRequestedDocument(new RequestedDocument("REQ", new HoSPerson("Dr Hudson", "Surgeon", "Danderyds sjukhus")));
//
//		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
//
//		Booking booking = createBooking();
//		eavrop.addBooking(booking);
//		
//		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
//		
//		eavrop.cancelBooking(booking.getBookingId(), createBookingDeviation());
//
//		assertEquals(EavropStateType.ON_HOLD, eavrop.getStatus());
//
//		eavrop.addBookingDeviationResponse(booking.getBookingId(), createBookingDeviationResponse());
//		
//		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
//		
//		eavrop.addIntygSignedInformation(createIntygSignedInformation());
//		
//		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
//		
//		eavrop.addIntygComplementRequestInformation(createIntygComplementRequestInformation());
//		
//		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
//
//		eavrop.addIntygApprovedInformation(createIntygApprovedInformation());;
//		
//		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
//		
//		Note note = new Note(NoteType.EAVROP,"",null);
//		
//		eavrop.addNote(new Note(NoteType.EAVROP,"",null));
//
//		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
//
//		note = eavrop.getNote(note.getNoteId());
//		
//		eavrop.removeNote(note);
//
//		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
//
//		eavrop.approveEavrop(createEavropApproval());
//		
//		assertEquals(EavropStateType.APPROVED, eavrop.getStatus());
//
//		eavrop.approveEavropCompensation(createEavropCompensationApproval());
//
//		assertEquals(EavropStateType.CLOSED, eavrop.getStatus());
//	}
//
	

	private void assignToVardgivarenhet(Eavrop eavrop, Vardgivarenhet enhet,
			DateTime dateTime) {
		
		eavrop.assignEavropToVardgivarenhet(enhet);
		EavropAssignment assignment = eavrop.getCurrentAssignment();
		assignment.setCreatedDate(dateTime);
	}

	private Booking createBooking(){
    	DateTime start = new DateTime();
    	return new Booking(BookingType.EXAMINATION, start,start.plusHours(1), doctorPerson, Boolean.FALSE);
	}
	
    private BookingDeviation createBookingDeviation(){
    	return new BookingDeviation(BookingDeviationType.INVANARE_CANCELLED_LT_48, new Note(NoteType.BOOKING_DEVIATION, "No Show", doctorPerson));
    }
    
    private BookingDeviationResponse createBookingDeviationResponse(DateTime dateTime){
    	
    	Bestallaradministrator adm = new Bestallaradministrator("Törn Valdegård", "Driver", "Försäkringskassan", "STCC", "555-123456", "rattmuff@saab.se");
    	BookingDeviationResponse deviationResponse = new BookingDeviationResponse( BookingDeviationResponseType.RESTART, (dateTime!=null)?dateTime:new DateTime(), adm, null );
    	
    	deviationResponse.setDeviationResponseNote(new Note(NoteType.BOOKING_DEVIATION_RESPONSE, "Kör på", adm));
    	return deviationResponse;
    }

	protected IntygSignedInformation createIntygSignedInformation(DateTime dt){
		return new IntygSignedInformation((dt!=null)?dt:new DateTime(), doctorPerson);
	}

	private IntygComplementRequestInformation createIntygComplementRequestInformation(DateTime dateTime) {
		return new IntygComplementRequestInformation((dateTime!=null)?dateTime:new DateTime(),  bestallaradministrator);
	}

	private IntygApprovedInformation createIntygApprovedInformation(DateTime dateTime) {
		return new IntygApprovedInformation((dateTime!=null)?dateTime:new DateTime(), bestallaradministrator);
	}

	private EavropApproval createEavropApproval(DateTime dateTime) {
		return new EavropApproval((dateTime!=null)?dateTime:new DateTime(), bestallaradministrator);
	}

	private EavropCompensationApproval createEavropCompensationApproval(DateTime dateTime) {
		return new EavropCompensationApproval(Boolean.TRUE,(dateTime!=null)?dateTime:new DateTime(), bestallaradministrator) ;
	}

}
