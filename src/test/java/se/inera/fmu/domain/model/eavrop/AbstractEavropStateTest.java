package se.inera.fmu.domain.model.eavrop;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviation;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponse;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationType;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.document.DocumentOriginType;
import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSignedInformation;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.eavrop.note.NoteType;
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

public abstract class AbstractEavropStateTest {
	
	abstract Eavrop getEavrop();
	
	abstract EavropStateType getEavropStateType();
	
	@Test(expected=IllegalStateException.class)
	public void testAssignEavropToVardgivarenhet() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.assignEavropToVardgivarenhet(createVardgivarenhet());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAcceptEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.acceptEavropAssignment();
	}

	@Test(expected=IllegalStateException.class)
	public void testAddBooking() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addBooking(createBooking());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAddReceivedDocumentToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addReceivedDocument(createReceivedDocument());
	}

	@Test(expected=IllegalStateException.class)
	public void testAddRequestedDocumentToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addRequestedDocument(createRequestedDocument());
	}

	@Test(expected=IllegalStateException.class)
	public void testAddIntygSignedInformationToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addIntygSignedInformation(createIntygSignedInformation());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAddIntygComplementRequestToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addIntygComplementRequestInformation(createIntygComplementRequestInformation());
	}

	@Test(expected=IllegalStateException.class)
	public void testAddIntygApprovedToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addIntygApprovedInformation(createIntygApprovedInformation());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAddNoteToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addNote(createNote());
	}

	@Test(expected=IllegalStateException.class)
	public void testApproveEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.approveEavrop(createEavropApproval());
	}

	@Test(expected=IllegalStateException.class)
	public void testApproveEavropCompensation() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.approveEavropCompensation(createEavropCompensationApproval());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testCancelBooking() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.cancelBooking(createBooking().getBookingId(), createBookingDevation());
	}

	@Test(expected=IllegalStateException.class)
	public void testAddBookingDeviationResponse() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addBookingDeviationResponse(createBooking().getBookingId(), createBookingDevationResponse());
	}

	
	@Test(expected=IllegalStateException.class)
	public void testRejectEavropAssignment() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.rejectEavropAssignment();
	}

	@Test(expected=IllegalStateException.class)
	public void testSetDocumentsSentFromBestallare() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.setDateTimeDocumentsSentFromBestallare(new LocalDateTime());
	}
	
	protected Eavrop createUnassignedEavrop(){
		return 	EavropBuilder.eavrop()
				.withArendeId(new ArendeId("TEST"))
				.withUtredningType(UtredningType.AFU) 
				.withInvanare(createInvanare())
				.withLandsting(new Landsting(new LandstingCode(99) ,"Testlän"))
				.withBestallaradministrator(new Bestallaradministrator("Nils Peterson", "Handläggare", "LFC Stockholm", "+46333333", "1@2"))
				.build();

	}
	
	protected Eavrop createAssignedEavrop(){
		Eavrop eavrop = createUnassignedEavrop();
		eavrop.assignEavropToVardgivarenhet(createVardgivarenhet());
		return eavrop;
	}

	protected Eavrop createAcceptedEavrop(){
		Eavrop eavrop = createAssignedEavrop();
		eavrop.acceptEavropAssignment();
		return eavrop;
	}

	protected Eavrop createOnHoldEavrop(){
		Eavrop eavrop = createAcceptedEavrop();
		Booking booking = createBooking(); 
		eavrop.addBooking(booking);
		eavrop.cancelBooking(booking.getBookingId(), createBookingDevation());
		return eavrop;
	}

	protected Eavrop createApprovedEavrop(){
		Eavrop eavrop = createAcceptedEavrop();
		eavrop.approveEavrop(createEavropApproval());
		return eavrop;
	}

	protected Eavrop createClosedEavrop(){
		Eavrop eavrop = createApprovedEavrop();
		eavrop.approveEavropCompensation(createEavropCompensationApproval());
		return eavrop;
	}

	
	protected Invanare createInvanare(){
		PersonalNumber pnr = new PersonalNumber("5604262214");
		Name name = new Name("Oscar",  "II", "Adolf");
		Address address = new Address("Slottsbacken 1","11122","Stockholm","SWEDEN");
		return new Invanare(pnr,name, Gender.MALE, address,"test@test.com",null);
	}
	
	protected Vardgivarenhet createVardgivarenhet(){
		HsaId hsaId = new HsaId("SE160000000000-HAHAHHSAA");
		Vardgivare vardgivare = new Vardgivare(hsaId,"TEST CARE");
		Address address = new Address("Slottsbacken 2","11123","Stockholm","SWEDEN");
		return new Vardgivarenhet(vardgivare, hsaId, "TEST UNIT", address);
	}
	
	protected Booking createBooking(){
		//Set<Person> persons = new HashSet<Person>();
		//persons.add(createPerson());
		return new Booking(BookingType.EXAMINATION, new LocalDateTime(), new LocalDateTime(),createPerson());
	}
	
	protected ReceivedDocument createReceivedDocument(){
		return new ReceivedDocument(DocumentOriginType.RECEIVED_EXTERNAL, "RECEIVED_DOCUMENT", createPerson());
	}

	protected RequestedDocument createRequestedDocument(){
		return new RequestedDocument("REQUESTED_DOCUMENT", createPerson());
	}
	
	protected Person createPerson(){
		return new HoSPerson("Petter Olovsson", "Läkare", "Stafettläkarna");
	}
	
	protected IntygSignedInformation createIntygSignedInformation(){
		return new IntygSignedInformation(new LocalDateTime(), createPerson());
	}

	protected IntygComplementRequestInformation createIntygComplementRequestInformation(){
		return new IntygComplementRequestInformation(new LocalDateTime(), createPerson());
	}

	protected IntygApprovedInformation createIntygApprovedInformation(){
		return new IntygApprovedInformation(new LocalDateTime(), createPerson());
	}

	protected Note createNote(){
		return new Note(NoteType.EAVROP, "Comment", createPerson());
	}

	protected EavropApproval createEavropApproval(){
		return new EavropApproval(new LocalDateTime(), createPerson());
	}

	protected EavropCompensationApproval createEavropCompensationApproval(){
		return new EavropCompensationApproval(Boolean.TRUE, new LocalDateTime(), createPerson());
	}

	protected BookingDeviation createBookingDevation(){
		return new BookingDeviation(BookingDeviationType.INVANARE_CANCELLED_LT_48, createNote());
	}

	protected BookingDeviationResponse createBookingDevationResponse(){
		BookingDeviationResponse response = new BookingDeviationResponse(BookingDeviationResponseType.RESTART, new LocalDateTime(), createPerson()); 
		
		response.setDeviationResponseNote(createNote());
		
		return response; 
	}

}
