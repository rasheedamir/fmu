package se.inera.fmu.domain.model.eavrop;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;

import se.inera.fmu.application.util.EavropPropertiesUtil;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponse;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSentInformation;
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
		eavrop.assignEavropToVardgivarenhet(createVardgivarenhet(), createHoSPerson());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAcceptEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.acceptEavropAssignment(createHoSPerson());
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
	public void testAddIntygSentInformationToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addIntygSentInformation(createIntygSentInformation());
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
	public void testRemoveNoteToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.removeNote(createNote());
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
		eavrop.setBookingStatus(createBooking().getBookingId(), BookingStatusType.CANCELLED_NOT_PRESENT, createNote());
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
		eavrop.rejectEavropAssignment(createHoSPerson(), null);
	}

	@Test(expected=IllegalStateException.class)
	public void testSetDocumentsSentFromBestallare() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.setDateTimeDocumentsSentFromBestallare(new DateTime());
	}
	
	protected Eavrop createUnassignedEavrop(){
		return 	EavropBuilder.eavrop()
				.withArendeId(new ArendeId("990000000101"))
				.withUtredningType(UtredningType.AFU) 
				.withInvanare(createInvanare())
				.withLandsting(new Landsting(new LandstingCode(99) ,"Testlän"))
				.withBestallaradministrator(new Bestallaradministrator("Nils Peterson", "Handläggare", "Försäkringskassan", "LFC Stockholm", "+46333333", "1@2"))
				.withEavropProperties(getEavropProperties())
				.build();

	}
	
    private EavropProperties getEavropProperties() {
		return EavropPropertiesUtil.createEavropProperties();
	}
	
	protected Eavrop createAssignedEavrop(){
		Eavrop eavrop = createUnassignedEavrop();
		eavrop.assignEavropToVardgivarenhet(createVardgivarenhet(),createHoSPerson());
		return eavrop;
	}

	protected Eavrop createAcceptedEavrop(){
		Eavrop eavrop = createAssignedEavrop();
		eavrop.acceptEavropAssignment(createHoSPerson());
		return eavrop;
	}

	protected Eavrop createOnHoldEavrop(){
		Eavrop eavrop = createAcceptedEavrop();
		Booking booking = createBooking(); 
		eavrop.addBooking(booking);
		eavrop.setBookingStatus(booking.getBookingId(), BookingStatusType.CANCELLED_NOT_PRESENT, createNote());
		return eavrop;
	}

	protected Eavrop createSentEavrop(){
		Eavrop eavrop =  createAcceptedEavrop();
		eavrop.addIntygSentInformation(createIntygSentInformation());
		return eavrop;
	}

	
	protected Eavrop createApprovedEavrop(){
		Eavrop eavrop =  createSentEavrop();
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
		return new Invanare(pnr,name, Gender.MALE, address, "555-1233445", "test@test.com",null);
	}
	
	protected Vardgivarenhet createVardgivarenhet(){
		HsaId hsaId = new HsaId("SE160000000000-HAHAHHSAA");
		Vardgivare vardgivare = new Vardgivare(hsaId,"TEST CARE");
		Address address = new Address("Slottsbacken 2","11123","Stockholm","SWEDEN");
		String email = "epost@epost.se";
		String phone = "08123123";
		return new Vardgivarenhet(vardgivare, hsaId, "TEST UNIT", address, email, phone);
	}
	
	protected Booking createBooking(){
		DateTime now = DateTime.now();
		DateTime to = now.plusHours(1);
		return new Booking(BookingType.EXAMINATION, now, to, Boolean.FALSE, createHoSPerson().getName(), createHoSPerson().getRole(), Boolean.FALSE);
	}
	
	protected ReceivedDocument createReceivedDocument(){
		return new ReceivedDocument( "RECEIVED_DOCUMENT", createHoSPerson(), true);
	}

	protected RequestedDocument createRequestedDocument(){
		return new RequestedDocument("REQUESTED_DOCUMENT", createHoSPerson(), createDocumentRequestNote());
	}
	
	
	protected HoSPerson createHoSPerson(){
		return new HoSPerson(new HsaId("SE160000000000-HAHAHHSAL"), "Petter Olovsson", "Läkare", "Stafettläkarna", "Ortopeden");
	}
	
	protected Note createDocumentRequestNote(){
		return new Note(NoteType.DOCUMENT_REQUEST, "Ge hit!!", createHoSPerson());
	}
	
	protected IntygSentInformation createIntygSentInformation(){
		return new IntygSentInformation(new DateTime(), createHoSPerson());
	}

	protected IntygComplementRequestInformation createIntygComplementRequestInformation(){
		return new IntygComplementRequestInformation(new DateTime(), createHoSPerson());
	}

	protected IntygApprovedInformation createIntygApprovedInformation(){
		return new IntygApprovedInformation(new DateTime(), createHoSPerson());
	}

	protected Note createNote(){
		return new Note(NoteType.EAVROP, "Comment", createHoSPerson());
	}

	protected EavropApproval createEavropApproval(){
		return new EavropApproval(new DateTime(), createHoSPerson());
	}

	protected EavropCompensationApproval createEavropCompensationApproval(){
		return new EavropCompensationApproval(Boolean.TRUE, new DateTime(), createHoSPerson());
	}

	protected BookingDeviationResponse createBookingDevationResponse(){
		BookingDeviationResponse response = new BookingDeviationResponse(BookingDeviationResponseType.RESTART, new DateTime(), createHoSPerson()); 
		
		response.setDeviationResponseNote(createNote());
		
		return response; 
	}

}
