package se.inera.fmu.domain.model.eavrop;

import java.util.concurrent.Executors;

import org.joda.time.DateTime;
import org.junit.Test;

import com.google.common.eventbus.AsyncEventBus;

import junit.framework.TestCase;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignment;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviation;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponse;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationType;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
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
	
	private Vardgivarenhet vardgivarenhet;
	
	private Vardgivare vardgivare;
	
	private AsyncEventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());

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
		
		eavrop.addRequestedDocument(new RequestedDocument("REQ", new HoSPerson("Dr Hudson", "Surgeon", "Danderyds sjukhus")));

		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());

		Booking booking = createBooking();
		eavrop.addBooking(booking);
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		eavrop.setBookingStatus(booking.getBookingId(), BookingStatusType.CANCELLED_NOT_PRESENT, null);

		assertEquals(EavropStateType.ON_HOLD, eavrop.getStatus());

		eavrop.addBookingDeviationResponse(booking.getBookingId(), createBookingDeviationResponse());
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		eavrop.addIntygSignedInformation(createIntygSignedInformation());
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		eavrop.addIntygComplementRequestInformation(createIntygComplementRequestInformation());
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());

		eavrop.addIntygApprovedInformation(createIntygApprovedInformation());;
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());
		
		Note note = new Note(NoteType.EAVROP,"",null);
		
		eavrop.addNote(new Note(NoteType.EAVROP,"",null));

		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());

		note = eavrop.getNote(note.getNoteId());
		
		eavrop.removeNote(note);

		assertEquals(EavropStateType.ACCEPTED, eavrop.getStatus());

		eavrop.approveEavrop(createEavropApproval());
		
		assertEquals(EavropStateType.APPROVED, eavrop.getStatus());

		eavrop.approveEavropCompensation(createEavropCompensationApproval());

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
		
		eavrop.addIntygSignedInformation(new IntygSignedInformation(new DateTime(2014,11,14,23,59),new HoSPerson("G", "H","I")));
		
		assertEquals(false, eavrop.isEavropAssesmentDaysDeviated());
		
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
	
	private Booking createBooking(){
    	DateTime start = new DateTime();
    	Person person = new HoSPerson("Dr Hudson", "Surgeon", "Danderyds sjukhus");
    	return new Booking(BookingType.EXAMINATION, start,start.plusHours(1), person, Boolean.FALSE);
	}
	
    private BookingDeviation createBookingDeviation(){
    	return new BookingDeviation(BookingDeviationType.INVANARE_CANCELLED_LT_48, new Note(NoteType.DEVIATION, "No Show", new HoSPerson("Lasse Kongo", "Läkare", "Danderydssjukhus")));
    }
    
    private BookingDeviationResponse createBookingDeviationResponse(){
    	
    	Bestallaradministrator adm = new Bestallaradministrator("Törn Valdegård", "Driver", "Försäkringskassan", "STCC", "555-123456", "rattmuff@saab.se");
    	BookingDeviationResponse deviationResponse = new BookingDeviationResponse( BookingDeviationResponseType.RESTART, new DateTime(), adm, null );
    	
    	deviationResponse.setDeviationResponseNote(new Note(NoteType.DEVIATION_RESPONSE, "Kör på", adm));
    	return deviationResponse;
    }

	protected IntygSignedInformation createIntygSignedInformation(){
		return new IntygSignedInformation(new DateTime(), new HoSPerson("","",""));
	}

	private IntygComplementRequestInformation createIntygComplementRequestInformation() {
		return new IntygComplementRequestInformation();
	}

	private IntygApprovedInformation createIntygApprovedInformation() {
		return new IntygApprovedInformation(new DateTime(), bestallaradministrator);
	}

	private EavropApproval createEavropApproval() {
		return new EavropApproval();
	}

	private EavropCompensationApproval createEavropCompensationApproval() {
		return new EavropCompensationApproval() ;
	}

}
