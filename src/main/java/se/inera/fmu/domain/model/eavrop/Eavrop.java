package se.inera.fmu.domain.model.eavrop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.DateTime;

import se.inera.fmu.application.util.BusinessDaysUtil;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAcceptedByVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignedToVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignment;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingCreatedEvent;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviation;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationEvent;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponse;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.document.DocumentSentByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSignedInformation;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.medicalexamination.PriorMedicalExamination;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.eavrop.note.NoteId;
import se.inera.fmu.domain.model.eavrop.note.NoteType;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.IEntity;

/**
 * Created by Rasheed on 7/7/14.
 * 
 * Aggregate Root - 
 */
@Entity
@Table(name = "T_EAVROP", uniqueConstraints = @UniqueConstraint(columnNames = "ARENDE_ID"))
@ToString
public class Eavrop extends AbstractBaseEntity implements IEntity<Eavrop> {

	// ~ Instance fields
	// ================================================================================================

	private static final long serialVersionUID = 1L;

//	// database primary key
//	//@Id
//	@EmbeddedId
//	private EavropId eavropId;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EAVROP_ID", updatable = false, nullable = false)
	private Long eavropId;
	
	// business key! Received from client in create request
	@NotNull
	@Embedded
	private ArendeId arendeId;

	// TODO:remove? in DIM but not in prototype, ask FK or Mattias about it
	@Column(name = "DESCRIPTION")
	private String description;

	@NotNull
	@Column(name = "STATE", nullable = false)
	@Convert(converter = EavropStateConverter.class)
	private EavropState eavropState; 

	// Type of utredning. An utredning can be one of tree types
	@Column(name = "UTREDNING_TYPE", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private UtredningType utredningType;

	// Defines if there might be a need for an interpreter and also the language skills needed
    @Embedded
    private Interpreter interpreter;

	// If UtredningType is SLU there might be a focus of the examination set by
	// the beställare. Corresponds to GUI value 'Val av inriktning' property in
	// the GUI
	@Column(name = "EXAMINATION_FOCUS")
	private String utredningFocus;

	// Additional information related to the eavrop
	@Column(name = "ADDITIONAL_INFO")
	private String additionalInformation;

	// A log of all assignments to vardgivarenheter and there replies
	@OneToMany
	@JoinTable(name = "R_EAVROP_ASSIGNMENT", joinColumns = @JoinColumn(name = "EAVROP_ID"), inverseJoinColumns = @JoinColumn(name = "ASSIGNMENT_ID"))
	private Set<EavropAssignment> assignments;

	// Maps the current assignment of the eavrop
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CURRENT_ASSIGNMENT_ID")
	private EavropAssignment currentAssignment;

	//The main character of the Eavrop
	@OneToOne
	@NotNull
	@JoinColumn(name = "INVANARE_ID")
	private Invanare invanare;

	// TODO: set as embeded object or create a relation to value object or only
	// handle as event?
	@OneToOne(cascade = CascadeType.ALL)
	@NotNull
	@JoinColumn(name = "BESTALLAR_PERSON_ID")
	private Bestallaradministrator bestallaradministrator;

	// The Landsting that this eavrop has ordered at
	@ManyToOne
	@JoinColumn(name = "LANDSTING_ID")
	private Landsting landsting;

	// The bookings made
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "R_EAVROP_BOOKING", joinColumns = @JoinColumn(name = "EAVROP_ID"), inverseJoinColumns = @JoinColumn(name = "BOOKING_ID"))
	private Set<Booking> bookings;

	// The notes related to this eavrop
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "R_EAVROP_NOTE", joinColumns = @JoinColumn(name = "EAVROP_ID"), inverseJoinColumns = @JoinColumn(name = "NOTE_ID"))
	private Set<Note> notes;

	// Examination that led up to this FMU
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PRIOR_EXAMINATION_ID")
	private PriorMedicalExamination priorMedicalExamination;

	// Documents received received this FMU
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "R_EAVROP_DOCUMENT", joinColumns = @JoinColumn(name = "EAVROP_ID"), inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
	private Set<ReceivedDocument> receivedDocuments;

	// Documents requested to this FMU
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "R_EAVROP_DOCUMENT", joinColumns = @JoinColumn(name = "EAVROP_ID"), inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
	private Set<RequestedDocument> requestedDocuments;
	
	//TODO: as list
	// When documents were sent from bestallare
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "DOCUMENTS_SENT_DATE_TIME")
	private DateTime documentsSentFromBestallareDateTime;
	
	//When document have been received, we can calculate the start date, 
	//BookingDevaition and their responses also affect the start date 
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "CURRENT_START_DATE")
	private LocalDate startDate;

	//When the latest intyg has been signed/sent 
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "CURRENT_INTYG_SIGNED_DATE")
	private DateTime intygSignedDate;

	
	// A log of all intyg events
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "R_EAVROP_INTYG", joinColumns = @JoinColumn(name = "EAVROP_ID"), inverseJoinColumns = @JoinColumn(name = "INTYG_INFORMATION_ID"))
	private Set<IntygInformation> intygInformations;

	// The compensation approval of this eavrop
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EAVROP_COMP_APPROVAL_ID")
	private EavropCompensationApproval eavropCompensationApproval;

	// The approval of this eavrop
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EAVROP_APPROVAL_ID")
	private EavropApproval eavropApproval;
	
	// ~ Constructors
	// ===================================================================================================

	Eavrop() {
		// Needed by hibernate
	}

	Eavrop(EavropBuilder builder){
		//Required properties
		Validate.notNull(builder.arendeId);
		Validate.notNull(builder.utredningType);
		Validate.notNull(builder.invanare);
		Validate.notNull(builder.landsting);
		Validate.notNull(builder.bestallaradministrator);
		//this.setEavropId(new EavropId());
		this.setArendeId(builder.arendeId);
        this.setUtredningType(builder.utredningType);
        this.setInvanare(builder.invanare);
        this.setLandsting(builder.landsting);
        this.setBestallaradministrator(builder.bestallaradministrator);
        //Optional properties
        this.setDescription(builder.description);
        this.setInterpreter(builder.interpreter);
    	this.setUtredningFocus(builder.utredningFocus);
    	this.setAdditionalInformation(builder.additionalInformation);
    	this.setPriorMedicalExamination(builder.priorMedicalExamination);
    	
    	//Set initial state
    	this.setEavropState(new UnassignedEavropState());
	}
	
	// ~ Property Methods
	// ===============================================================================================
	
	/**
	 * Returns the additional information connected to the eavrop order 
	 */
	public String getAdditionalInformation() {
		return additionalInformation;
	}

	private void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	/**
	 * Retuns the EavropApproval object if the Eavrop has been approved by the
	 * orderer/bestallare
	 * 
	 * @return the eavrop approval
	 * @see EavropApproval
	 */
	public EavropApproval getEavropApproval() {
		return eavropApproval;
	}

	protected void setEavropApproval(EavropApproval eavropApproval) {
		this.eavropApproval = eavropApproval;
	}
	
	/**
	 * Approves the Eavrop or the utredning, 
	 * 
	 * @param eavropApproval, an entity that represents the approval of 
	 * the eavrop, what time it was approved and by who it was approved
	 */
	// TODO:How is this related businesswise to the intyg and intygApprovedInformation,
	// Does intygApprovedInformation exist
	public void approveEavrop(EavropApproval eavropApproval) {
		this.getEavropState().approveEavrop(this, eavropApproval);
	}
	
	/**
	 *	Retuns true if the eavrop has received an evarop approval from bestallare 
	 */
	public boolean isApproved(){
		return (getEavropApproval()!=null)?Boolean.TRUE:Boolean.FALSE;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Integer getNumberOfDaysUsedDuringAssessment(){
		Integer result = null;
		
		if(isApproved() && getStartDate() !=null &&  getEavropApproval().getApprovalTimestamp()!=null){
			
			//TODO: move calculations to domain service....
			result = new Integer(BusinessDaysUtil.numberOfBusinessDays(getStartDate(), getEavropApproval().getApprovalTimestamp().toLocalDate()));
		} 
		
		return result;
		
	}
	
	/**
	 * The arendeId is the identity of this entity, and is unique.
	 *
	 * @return the unique arendeId that specifies this eavrop
	 * @see ArendeId
	 */
	public ArendeId getArendeId() {
		return arendeId;
	}

	private void setArendeId(ArendeId arendeId) {
		this.arendeId = arendeId;
	}

	/**
	 * Assigns the eavrop to the specified vardgivarenhet
	 *
	 * @param vardgivarenhet, the care giver unit that the eavrop should be assigned to
	 * @see Vardgivarenhet
	 */
	public void assignEavropToVardgivarenhet(Vardgivarenhet vardgivarenhet) {
		this.getEavropState().assignEavropToVardgivarenhet(this, vardgivarenhet);
	}
	
	// TODO: Object security, ensure that its okay for the user to accept or reject the assigmnent
	/**
	 * The currently assigned vardgivarenhet accepts the assigned Eavrop
	 *
	 */
	public void acceptEavropAssignment() {
		this.getEavropState().acceptEavropAssignment(this);
	}

	/**
	 * The currently assigned vardgivarenhet, rejects the assigned Eavrop
	 *
	 */
	public void rejectEavropAssignment() {
		this.getEavropState().rejectEavropAssignment(this);
	}

	protected EavropAssignment getCurrentAssignment() {
		return currentAssignment;
	}

	protected void setCurrentAssignment(EavropAssignment currentAssignment) {
		this.currentAssignment = currentAssignment;
	}

	/**
	 * Returns a Set<EavropAssignment> with all the assignments made to this
	 * evarop
	 *
	 * @return a set with all EavropAssignments related to the eavrop
	 * @see EavropAssignment
	 */
	public Set<EavropAssignment> getAssignments() {
		return assignments;
	}

	private void setAssignments(Set<EavropAssignment> assignments) {
		this.assignments = assignments;
	}

	protected void addAssignment(EavropAssignment assignment) {
		if (this.assignments == null) {
			this.assignments = new HashSet<EavropAssignment>();
		}
		this.assignments.add(assignment);
	}

	/**
	 * Returns the bestallaradministrator created at the eavrop order
	 *
	 * @return bestallaradministrator
	 * @see Bestallaradministrator
	 */

	public Bestallaradministrator getBestallaradministrator() {
		return bestallaradministrator;
	}

	private void setBestallaradministrator(Bestallaradministrator bestallaradministrator) {
		this.bestallaradministrator = bestallaradministrator;
	}

	/**
	 * Returns all the bookings made to this eavrop
	 *
	 * @return bookings
	 * @see Booking
	 */
	public Set<Booking> getBookings() {
		return bookings;
	}

	/**
	 * Return the booking that corresponds to the specified booking id
	 *
	 * @return booking
	 * @see Booking
	 */
	public Booking getBooking(BookingId bookingId) {
		for (Booking booking : getBookings()) {
			if(booking.getBookingId().equals(bookingId)){
				return booking;
			}
		}
		return null;
	}

	
	/**
	 * Return the bookingDeviation that corresponds to the specified booking id.
	 * Will return null if no booking or booking deviation exists
	 *
	 * @return bookingDeviation
	 * @see BookingDevaition
	 */
	public BookingDeviation getBookingDeviation(BookingId bookingId) {
		Booking booking = this.getBooking(bookingId);
		if(booking!=null){
			return booking.getBookingDeviation();
		}
		return null;
	}

	/**
	 * Return all booking deviations on all bookings
	 */
	public Set<BookingDeviation> getBookingDeviations() {
		Set<BookingDeviation> result = new HashSet<BookingDeviation>();
		
		for (Booking booking : getBookings()) {
			if(booking.getBookingDeviation()!=null){
				result.add(booking.getBookingDeviation());
			}
		}
		
		return result;
	}


	/**
	 * Return number of deviation 
	 */
	//TODO:is there anything esle that that qualifies as a deviation, and should all booking deviations
	//be counted here
	public int getNumberOfDeviations() {
		
		if(getBookingDeviations()!=null){
			return getBookingDeviations().size();
		}
		return 0;	
	}
	
	private void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}

	/**
	 * Adds a booking to the eavrop
	 *
	 * @param booking
	 *            , the booking to be added
	 * @see Booking
	 */
	public void addBooking(Booking booking) {
		this.getEavropState().addBooking(this, booking);
	}
	

	/**
	 * Only to be used from EavropState
	 */
	protected void addToBookings(Booking booking) {
		if (this.bookings == null) {
			this.bookings = new HashSet<Booking>();
		}
		this.bookings.add(booking);
	}


	/**
	 * Cancel the specified booking with deviation
	 *
	 */
	public void cancelBooking(BookingId bookingId, BookingDeviation deviation) {
		getEavropState().cancelBooking(this, bookingId, deviation);
	}

	/**
	 * Respond to devaition of the specified booking with deviationResponse
	 *
	 */
	public void addBookingDeviationResponse(BookingId bookingId, BookingDeviationResponse deviationResponse) {
		getEavropState().addBookingDeviationResponse(this, bookingId, deviationResponse);
	}
	
	/**
	 * Returns the description property of this eavrop
	 *
	 * @return description, as a String
	 */
	public String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns a set with received document information entities added to this eavrop
	 *
	 * @return receivedDocuments, as a Set of receivedDocuments
	 */
	public Set<ReceivedDocument> getReceivedDocuments() {
		return this.receivedDocuments;
	}

	private void setReceivedDocuments(Set<ReceivedDocument> receivedDocuments){
		this.receivedDocuments = receivedDocuments;
	}
	
	/**
	 * 
	 */
	public void addReceivedDocument(ReceivedDocument receivedDocument){
		this.getEavropState().addReceivedDocument(this, receivedDocument);
	}
	
	/**
	 * 
	 * 
	 */
	protected void addToReceivedDocuments(ReceivedDocument receivedDocument) {
		if (this.receivedDocuments == null) {
			this.receivedDocuments = new HashSet<ReceivedDocument>();
		}
		this.receivedDocuments.add(receivedDocument);
		
		if(receivedDocument.isDocumentOriginExternal()){
			doDocumentsSentFromBestallare(receivedDocument);
		}
	}

	
	private void doDocumentsSentFromBestallare(ReceivedDocument receivedDocument) {
		if(getDateTimeDocumentsSentFromBestallare()==null && receivedDocument.getDocumentDateTime() !=null ){
			this.documentsSentFromBestallareDateTime =  receivedDocument.getDocumentDateTime();
		}
		if(getStartDate()==null && receivedDocument.getDocumentDateTime() !=null ){
			//TODO figure out how to get Service
			//this.startDate = eavropService.calculateEavropStartDate(receivedDocument.getDocumentDateTime());
			
			this.startDate = receivedDocument.getDocumentDateTime().plusDays(3).toLocalDate();
		}

	}

	/**
	 * Returns a set with requested document information entities added to this eavrop
	 *
	 * @return requestedDocuments, as a Set of receivedDocuments
	 */
	public Set<RequestedDocument> getRequestedDocuments() {
		return requestedDocuments;
	}

	private void setRequestedDocuments(Set<RequestedDocument> requestedDocuments){
		this.requestedDocuments = requestedDocuments;
	}
	
	/**
	 * 
	 */
	public void addRequestedDocument(RequestedDocument requestedDocument){
		this.getEavropState().addRequestedDocument(this, requestedDocument);
	}
	
	/**
	 * 
	 * 
	 */
	protected void addToRequestedDocuments(RequestedDocument requestedDocument) {
		if (this.requestedDocuments == null) {
			this.requestedDocuments = new HashSet<RequestedDocument>();
		}
		this.requestedDocuments.add(requestedDocument);
	}

	/**
	 * This property represents a point in time when the orderer, bestallare, notified that they sent documents 
	 * @return DateTime, when the documents were sent
	 */
	public DateTime getDateTimeDocumentsSentFromBestallare() {
		return this.documentsSentFromBestallareDateTime;
	}

	/**
	 * This property represents a point in time when the orderer, bestallare, notified that they sent documents 
	 * @param  documentsSentFromBestallareDateTime
	 */
//	//TODO: This can be sent from the orderer several times ans should probably be logged in a list, possibly with who the sender is as well
//	//TODO: Maybe the startdate should olny be set through addDocument? 
	protected void setDocumentsSentFromBestallare(DateTime documentsSentFromBestallareDateTime) {
		this.documentsSentFromBestallareDateTime = documentsSentFromBestallareDateTime;
	}
//
	public void setDateTimeDocumentsSentFromBestallare(DateTime documentsSentFromBestallareDateTime) {
		this.getEavropState().setDocumentsSentFromBestallareDateTime(this, documentsSentFromBestallareDateTime);
	}

	
//	/**
//	 * The internal id of the Eavrop
//	 * @return  EavropId
//	 */
//	public EavropId getEavropId() {
//		return this.eavropId;
//	}
//	
//	private void setEavropId(EavropId eavropId) {
//		this.eavropId = eavropId;
//	}

	/**
	 * The internal id of the Eavrop
	 * @return  EavropId
	 */
	public Long getEavropId() {
		return this.eavropId;
	}
	
	private void setEavropId(Long eavropId) {
		this.eavropId = eavropId;
	}

	
	/**
	 * A description of the focus of the utredning
	 * @return
	 */
	public String getUtredningFocus() {
		return utredningFocus;
	}

	/**
	 * In certain UtredningTypes there might be a need for a direction or focus of the utredning. 
	 * That focus should be specified by the orderer at ordering time.
	 * @param utredningFocus, a String that describes the focus of the utredning
	 */
	private void setUtredningFocus(String utredningFocus) {
		this.utredningFocus = utredningFocus;
	}

	/**
	 * This property represents that the orderer has approved the pay the compensation of the utredning eavrop.  
	 * 
	 * @return EavropCompensationApproval
	 */
	public EavropCompensationApproval getEavropCompensationApproval() {
		return eavropCompensationApproval;
	}

	protected void setEavropCompensationApproval(EavropCompensationApproval eavropCompensationApproval) {
		this.eavropCompensationApproval = eavropCompensationApproval;
	}
	
	/**
	 * This property represents that the orderer approves the compensation of the utredning.  
	 * 
	 * @param eavropCompensationApproval 
	 */
	public void approveEavropCompensation(EavropCompensationApproval eavropCompensationApproval) {
		this.getEavropState().approveEavropCompensation(this, eavropCompensationApproval);
	}

	public DateTime getEavropCompensationApprovalDateTime() {
		if(getEavropCompensationApproval()!=null){
			return getEavropCompensationApproval().getCompensationDateTime();
		}
		
		return null;
	}

	
	// TODO:Probably not necessary, but exists in the DIM
	public FmuKod getFmuKod() {
		return FmuKod.EAVROP;
	}

	/**
	 * Returns all the information about the intyg
	 * @return
	 */
	public Set<IntygInformation> getIntygInformations() {
		return intygInformations;
	}

	private void setIntygInformations(Set<IntygInformation> intygInformations) {
		this.intygInformations = intygInformations;
	}

	/**
	 * Adds a intyginformation to the eavrop
	 * @param intygInformation
	 */
	private void addToIntygInformation(IntygInformation intygInformation) {
		if (this.intygInformations == null) {
			this.intygInformations = new HashSet<IntygInformation>();
		}
		this.intygInformations.add(intygInformation);
	}
	
	/**
	 * 
	 * @param intygSignedInformation 
	 */
	public void addIntygSignedInformation(IntygSignedInformation intygSignedInformation) {
		this.getEavropState().addIntygSignedInformation(this, intygSignedInformation);
	}

	/**
	 * 
	 * @param intygSignedInformation 
	 */
	protected void addToIntygSignedInformation(IntygSignedInformation intygSignedInformation) {
		addToIntygInformation(intygSignedInformation);
		
		//TODO: set startdate here or in state
		if(intygSignedInformation != null && intygSignedInformation.getIntformationTimestamp() != null)
		this.intygSignedDate = intygSignedInformation.getIntformationTimestamp();
	}
	
	public DateTime getIntygSignedDateTime(){
		return this.intygSignedDate;
	}
	
	public boolean isintygSigned(){
		return (getIntygSignedDateTime() != null)?Boolean.TRUE:Boolean.FALSE;
	}

	
	/**
	 * 
	 * @param intygComplementRequestInformation 
	 */
	public void addIntygComplementRequestInformation(IntygComplementRequestInformation intygComplementRequestInformation) {
		this.getEavropState().addIntygComplementRequestInformation(this, intygComplementRequestInformation);
	}

	/**
	 * 
	 * @param intygComplementRequestInformation 
	 */
	protected void addToIntygComplementRequestInformation(IntygComplementRequestInformation intygComplementRequestInformation) {
		addToIntygInformation(intygComplementRequestInformation);
		
		//TODO: Remove intyg signed timestamp?
		this.intygSignedDate = null;
		
	}

	/**
	 * 
	 * @param intygApprovedInformation 
	 */
	//TODO: Is this entity valid? 
	public void addIntygApprovedInformation(IntygApprovedInformation intygApprovedInformation) {
		this.getEavropState().addIntygApprovedInformation(this, intygApprovedInformation);
	}

	/**
	 * 
	 * @param intygApprovedInformation 
	 */
	protected void addToIntygApprovedInformation(IntygApprovedInformation intygApprovedInformation) {
		addToIntygInformation(intygApprovedInformation);
	}

	/**
	 * Returns the invanare that the eavrop/utredning concerns
	 * @return
	 */
	public Invanare getInvanare() {
		return invanare;
	}

	private void setInvanare(Invanare invanare) {
		this.invanare = invanare;
	}

	/**
	 * Returns the landsting that this evarop is ordered at
	 * @return
	 */
	public Landsting getLandsting() {
		return this.landsting;
	}

	private void setLandsting(Landsting landsting) {
		this.landsting = landsting;
	}

	/**
	 * Returns the notes made on this eavrop
	 * @return
	 */
	public Set<Note> getNotes() {
		return notes;
	}


	/**
	 * Returns a note specified by NoteId if it exists on the eavrop
	 * @return
	 */
	public Note getNote(NoteId noteId) {
		if(this.notes!=null){
			for (Note note : this.notes) {
				if(note.getNoteId().equals(noteId)){
					return note;
				}
			}
		}
		return null;
	}

	
	private void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

	/**
	 * Adds a note to this eavrop
	 * @return
	 */
	public void addNote(Note note) {
		this.getEavropState().addNote(this, note);
	}
	

	/**
	 * Only to be used from eavropState
	 */
	protected void doAddNote(Note note) {
		if(this.notes == null){
			this.notes = new HashSet<Note>();
		} 
		this.notes.add(note);
	}

	
	/**
	 * Remove a note from this eavrop
	 * @return
	 */
	public void removeNote(Note note) {
		this.getEavropState().removeNote(this, note);
	}
	

	/**
	 * Only to be used from eavropState
	 */
	protected void doRemoveNote(Note note) {
		if(this.notes != null && this.notes.contains(note) ){
			if(NoteType.EAVROP.equals(note.getNoteType())){
				notes.remove(note);
			}else{
				throw new IllegalArgumentException("Note ["+note.getNoteId()+"] of NotetType " +note.getNoteType() + " in Eavrop with ArendeId ["+ this.getArendeId()+"] is not allowed to be deleted");
			}
		} 
	}

	
	/**
	 * Returns all notes added to the eavrop or its child entities
	 * @return
	 */
	public List<Note> getAllNotes(){
		
		List<Note> result = new ArrayList<Note>(getNotes());
		
		for (BookingDeviation deviation : getBookingDeviations()) {
			if(deviation.getDeviationNote()!=null){
				result.add(deviation.getDeviationNote());
			}
			if(deviation.getBookingDeviationResponse()!=null && deviation.getBookingDeviationResponse().getDeviationResponseNote()!=null){
				result.add(deviation.getBookingDeviationResponse().getDeviationResponseNote());
			}
		}
		
		if(getEavropApproval() != null && getEavropApproval().getNote() != null){
			result.add(getEavropApproval().getNote());
		}

		if(getEavropCompensationApproval() != null && getEavropCompensationApproval().getNote() != null){
			result.add(getEavropCompensationApproval().getNote());
		}
		
		Collections.sort(result);
		return result;
	}
	
	/**
	 * 
	 * 
	 * Returns information about prior medical examination leading up to this eavrop
	 * 
	 * @return priorMedicalExamination
	 * @see PriorMedicalExamination
	 */
	public PriorMedicalExamination getPriorMedicalExamination() {
		return priorMedicalExamination;
	}

	private void setPriorMedicalExamination(
			PriorMedicalExamination priorMedicalExamination) {
		this.priorMedicalExamination = priorMedicalExamination;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	protected void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public EavropState getEavropState() {
		return this.eavropState;
	}

	
	private EavropStateType getEavropStateType(){
		return this.getEavropState().getEavropStateType();
	}

	/**
	 * Sets the current status of the eavrop
	 * @return status
	 */
	protected void setEavropState(EavropState state) {
		this.eavropState = state;
	}
	
	public EavropStateType getStatus(){
		
		//TODO: add Additional statuses
		return this.getEavropState().getEavropStateType();
	}
	
	private void setInterpreter(Interpreter interpreter) {
		this.interpreter = interpreter; 
	}

	/**
	 * Returns a true value if an interpreter is needed according to the bestallare
	 * @return
	 */
	public boolean isInterpreterNeeded() {
		return (this.interpreter != null)?Boolean.TRUE:Boolean.FALSE;
	}

	/**
	 * Returns a descriptive String about the language requirements of the interpreter
	 * @return
	 */
	public String getIterpreterDescription() 
	{
		if(isInterpreterNeeded()){
			return this.interpreter.getInterpreterDescription();
		} 
		return null;
	}

	/**
	 * Returns the type of utredning this Eavrop concern
	 *
	 * @return utredningType
	 */
	public UtredningType getUtredningType() {
		return utredningType;
	}

	private void setUtredningType(final UtredningType utredningType) {
		this.utredningType = utredningType;
	}
	
	// ~ handlers
	
	protected void handleEavropAssignedToVardgivarenhet(){
		EavropAssignedToVardgivarenhetEvent event = new EavropAssignedToVardgivarenhetEvent(this.getArendeId(),getCurrentAssignment().getVardgivarenhet().getHsaId());
		
		//TODO: Post event on bus
	}

	protected void handleEavropAccept(){
		EavropAcceptedByVardgivarenhetEvent event = new EavropAcceptedByVardgivarenhetEvent(this.getArendeId(),getCurrentAssignment().getVardgivarenhet().getHsaId());
		
		//TODO: Post event on bus
	}

	protected void handleEavropReject(EavropAssignment eavropAssignment){
		EavropAcceptedByVardgivarenhetEvent event = new EavropAcceptedByVardgivarenhetEvent(this.getArendeId(), eavropAssignment.getVardgivarenhet().getHsaId());
		
		//TODO: Post event on bus
	}

	protected void handleDocumentsSent(){
		//TODO: dont know if this event should be created... 
		DocumentSentByBestallareEvent event = new DocumentSentByBestallareEvent(this.getArendeId(), getDateTimeDocumentsSentFromBestallare());

		//TODO: Post event on bus
	}

	protected void handleBookingAdded(BookingId bookingId){
		BookingCreatedEvent event = new BookingCreatedEvent(this.getArendeId(), bookingId);
		
		//TODO: Post event on bus
	}

	protected void handleBookingDeviation(BookingId bookingId){
		BookingDeviationEvent event = new BookingDeviationEvent(this.getArendeId(), bookingId);
		
		//TODO: Post event on bus
	}

	protected void handleEavropRestarted(){
		EavropRestartedByBestallareEvent event = new EavropRestartedByBestallareEvent(this.getArendeId());
		//TODO: Post event on bus
	}

	protected void handleEavropStoppedByBestallare(){
		EavropClosedByBestallareEvent event = new EavropClosedByBestallareEvent(this.getArendeId());
		//TODO: Post event on bus
	}

	protected void handleEavropApproval(){
		//TODO:Should there be an event?
	}
	
	protected void handleEavropCompensationApproval(){
		//TODO:Should there be an event?
	}
	
	// ~ Other Methods ==================================================================================================

	@Override
	public boolean sameIdentityAs(final Eavrop other) {
		return other != null
				&& this.getArendeId().sameValueAs(other.getArendeId());
	}

	/**
	 * @param object
	 *            to compare
	 * @return True if they have the same identity
	 * @see #sameIdentityAs(Eavrop)
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final Eavrop other = (Eavrop) object;
		return sameIdentityAs(other);
	}

	/**
	 * @return Hash code .
	 */
	@Override
	public int hashCode() {
		return arendeId.hashCode();
	}
}
