package se.inera.fmu.domain.model.eavrop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import se.inera.fmu.application.util.BusinessDaysUtil;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignment;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponse;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;
import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSentInformation;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.medicalexamination.PriorMedicalExamination;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.eavrop.note.NoteId;
import se.inera.fmu.domain.model.eavrop.note.NoteType;
import se.inera.fmu.domain.model.eavrop.properties.EavropProperties;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.Landstingssamordnare;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.person.HoSPerson;
import se.inera.fmu.domain.model.person.Person;
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
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "EAVROP_ID", updatable = false, nullable = false)
//	private Long eavropId;
	@EmbeddedId
	private EavropId eavropId;
	
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


	@Embedded
	private EavropProperties eavropProperties;
	
	// A log of all assignments to vardgivarenheter and their replies
	@OneToMany
	@JoinTable(name = "R_EAVROP_ASSIGNMENT", joinColumns = @JoinColumn(name = "EAVROP_ID"), inverseJoinColumns = @JoinColumn(name = "ASSIGNMENT_ID"))
	private List<EavropAssignment> assignments;

	// Maps the current assignment of the eavrop
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CURRENT_ASSIGNMENT_ID")
	private EavropAssignment currentAssignment;

	//The main character of the Eavrop
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "INVANARE_ID")
	private Invanare invanare;

	// TODO: set as embeded object or create a relation to value object or only
	// handle as event?
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
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
	@JoinTable(name = "R_EAVROP_REC_DOCUMENT", joinColumns = @JoinColumn(name = "EAVROP_ID"), inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
	private Set<ReceivedDocument> receivedDocuments;

	// Documents requested to this FMU
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "R_EAVROP_REQ_DOCUMENT", joinColumns = @JoinColumn(name = "EAVROP_ID"), inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
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
	@Column(name = "CURRENT_INTYG_SENT_DATE")
	private DateTime intygSentDate;

	
	// A log of all intyg events
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "R_EAVROP_INTYG", joinColumns = @JoinColumn(name = "EAVROP_ID"), inverseJoinColumns = @JoinColumn(name = "INTYG_INFORMATION_ID"))
	private Set<IntygInformation> intygInformations;

	// The approval of this eavrop
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EAVROP_APPROVAL_ID")
	private EavropApproval eavropApproval;

	// The compensation approval of this eavrop
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EAVROP_COMP_APPROVAL_ID")
	private EavropCompensationApproval eavropCompensationApproval;
	
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
		Validate.notNull(builder.eavropProperties);
		this.setEavropId(new EavropId(UUID.randomUUID().toString()));
		this.setArendeId(builder.arendeId);
        this.setUtredningType(builder.utredningType);
        this.setInvanare(builder.invanare);
        this.setLandsting(builder.landsting);
        this.setBestallaradministrator(builder.bestallaradministrator);
        this.setEavropProperties(builder.eavropProperties);
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
	 * Adds eavrop calculation properties
	 * @param eavropProperties
	 */
	public void setEavropProperties(EavropProperties eavropProperties){
		this.eavropProperties =  eavropProperties;
	}
	
	private EavropProperties getEavropProperties(){
		return this.eavropProperties;
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

	/**
	 * Method that returns when the eavrop was approved by the bestallare.
	 * Returns null if the eavrop has not yet been aproved
	 * @return
	 */
	public DateTime getEavropApprovalDateTime() {
		if(getEavropApproval()!=null){
			return getEavropApproval().getApprovalTimestamp();
		}
		return null;
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
	 * Returns number of business days spent during assessment period.
	 * If  
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
	public void assignEavropToVardgivarenhet(Vardgivarenhet vardgivarenhet, HoSPerson assigningPerson) {
		this.getEavropState().assignEavropToVardgivarenhet(this, vardgivarenhet, assigningPerson);
	}
	
	
	// TODO: Object security, ensure that its okay for the user to accept or reject the assigmnent
	/**
	 * The currently assigned vardgivarenhet accepts the assigned Eavrop
	 *
	 */
	public void acceptEavropAssignment(HoSPerson acceptingPerson) {
		this.getEavropState().acceptEavropAssignment(this , acceptingPerson);
	}
	
		
	/**
	 * Calculates if the number of days used for getting acceptance of eavrop assignment is greater than the allowed number of days
	 * defined in the eavrop properties
	 * @return
	 */
	public boolean isEavropAcceptDaysDeviated(){
		
		//The last day that it is okay to accept;
		LocalDate lastValidDay = getLastValidEavropAssignmentAcceptDay();
		
		LocalDate toDate = new LocalDate();
		
		//if assignment is accepted, get the acceptDate as end date otherwisé stick with today
		if(isAssignmentAccepted()){
			toDate = new LocalDate(getCurrentAssignment().getLastModifiedDate());
		}
		
		//if to date is after the last valid day the assignment has been accepted to late
		if(toDate.isAfter(lastValidDay)){
			return true;
		}else{
			return false;
		}
	}
	
	private LocalDate getLastValidEavropAssignmentAcceptDay(){

		//From date is the day after the Eavrop was received from orderer.
		LocalDate fromDate = new LocalDate(this.getCreatedDate()).plusDays(1);
		int maxNumberOfDaysUntilAccept = this.getEavropProperties().getAcceptanceValidLength();
		
		//The last day that it is okay to accept;
		LocalDate lastValidDay = BusinessDaysUtil.calculateBusinessDayDate(fromDate, maxNumberOfDaysUntilAccept);
		
		return lastValidDay;
	}

	private LocalDate getLastValidEavropAssessmentDay(){
		
		//From date is the day that the assessment started
		LocalDate fromDate = getStartDate();
		if(fromDate==null){
			return null;
		}
		int maxNumberOfAssessmentDays = this.getEavropProperties().getAssessmentValidLength();
		
		//The last day that it is okay to accept;
		LocalDate lastValidDay = BusinessDaysUtil.calculateBusinessDayDate(fromDate, maxNumberOfAssessmentDays);
		
		return lastValidDay;
	}

	
	/**
	 * Get number of days used for getting acceptance of eavrop assignment
	 * @return
	 */
	public int getNoOfAcceptDays(){
		
		LocalDate fromDate = new LocalDate(this.getCreatedDate()).plusDays(1);
		LocalDate toDate = new LocalDate();
		
		if(isAssignmentAccepted()){
			toDate = new LocalDate(getCurrentAssignment().getLastModifiedDate());
		}

		int noOfBusinessDays = BusinessDaysUtil.numberOfBusinessDays(fromDate, toDate);
		
		return noOfBusinessDays;
	}

	
	/**
	 * Method retrives when the Eavrop assignment was accepted by Vårdgivare 
	 * @return
	 */
	public DateTime getEavropAssignmentAcceptanceDateTime(){
		if(isAssignmentAccepted()){
			return getCurrentAssignment().getAssignmentResponseDateTime();
		}
		return null; 
	}
	
	/**
	 * Method tells if eavrop has Assignment that has been accepted
	 * @return
	 */
	public boolean isAssignmentAccepted() {
		if(this.getCurrentAssignment()!=null){
			return this.getCurrentAssignment().isAccepted();
		}
		
		return false;	
	}

	public int getAssignmentNumberOFResponsDaysFromOrderDate(EavropAssignment assignment){
		LocalDate fromDate = new LocalDate(this.getCreatedDate()).plusDays(1);
		LocalDate toDate = new LocalDate();
		
		if(assignment.isAccepted()|| assignment.isRejected()){
			toDate = new LocalDate(assignment.getLastModifiedDate());
		}

		int noOfBusinessDays = BusinessDaysUtil.numberOfBusinessDays(fromDate, toDate);
		
		return noOfBusinessDays;
	}

	public boolean isAssignmentNumberOFResponsDaysFromOrderDateDeviated(EavropAssignment assignment){
		
		//from date is the day after the Eavrop was received from orderer.
		LocalDate fromDate = new LocalDate(this.getCreatedDate()).plusDays(1);
		int maxNumberOfDaysUntilAccept = this.getEavropProperties().getAcceptanceValidLength();
		
		//The last day that it is okay to accept;
		LocalDate lastValidDay = BusinessDaysUtil.calculateBusinessDayDate(fromDate, maxNumberOfDaysUntilAccept);
		
		LocalDate toDate = new LocalDate();
		
		//if assignment is accepted, get the acceptDate as end date otherwise stick with today
		if(assignment.isAccepted()|| assignment.isRejected()){
			toDate = new LocalDate(assignment.getLastModifiedDate());
		}
		
		//if to date is after the last valid day the assignment has been accepted to late
		if(toDate.isAfter(lastValidDay)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Get number of business days between the the time that the order was registered and the time for acceptence 
	 * or if not yet accepted current time  
	 */
	public int getNumberOfAcceptanceDaysFromOrderDate(){
		LocalDate fromDate = new LocalDate(this.getCreatedDate()).plusDays(1);
		LocalDate toDate = new LocalDate();
		
		if(getCurrentAssignment() != null && getCurrentAssignment().isAccepted()){
			toDate = new LocalDate(getCurrentAssignment().getLastModifiedDate());
		}

		int noOfBusinessDays = BusinessDaysUtil.numberOfBusinessDays(fromDate, toDate);
		
		return noOfBusinessDays;
	}

	
	/**
	 * Get number of business days between the the time that the order was registered and the time for acceptance 
	 * or if not yet accepted current time  
	 */
	public boolean isNumberOfAcceptanceDaysFromOrderDateDeviated(){
		LocalDate fromDate = new LocalDate(this.getCreatedDate()).plusDays(1);
		LocalDate toDate = new LocalDate();
		int maxNumberOfDaysUntilAccept = this.getEavropProperties().getAcceptanceValidLength();
		
		if(getCurrentAssignment() != null && getCurrentAssignment().isAccepted()){
			toDate = new LocalDate(getCurrentAssignment().getLastModifiedDate());
		}

		//The last day that it is okay to accept;
		LocalDate lastValidDay = BusinessDaysUtil.calculateBusinessDayDate(fromDate, maxNumberOfDaysUntilAccept);
		
		//if assignment is accepted, get the acceptDate as end date otherwise stick with today
		if(getCurrentAssignment() != null && getCurrentAssignment().isAccepted()){
			toDate = new LocalDate(getCurrentAssignment().getLastModifiedDate());
		}
		
		//if to date is after the last valid day the assignment has been accepted to late
		if(toDate.isAfter(lastValidDay)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * The currently assigned vardgivarenhet, rejects the assigned Eavrop
	 *
	 */
	public void rejectEavropAssignment(HoSPerson rejectingPerson, String rejectionComment) {
		this.getEavropState().rejectEavropAssignment(this, rejectingPerson, rejectionComment);
	}

	/**
	 * Returns the current eavrop assignment, if eavrop does not have assignment null is returned
	 * @return
	 */
	public EavropAssignment getCurrentAssignment() {
		return currentAssignment;
	}

	protected void setCurrentAssignment(EavropAssignment currentAssignment) {
		this.currentAssignment = currentAssignment;
	}

	/**
	 * Returns a Set<EavropAssignment> with all the assignments made to this
	 * eavrop
	 *
	 * @return a set with all EavropAssignments related to the eavrop
	 * @see EavropAssignment
	 */
	public List<EavropAssignment> getAssignments() {
		if(assignments != null){
			Collections.sort(assignments);
		}
		
		return assignments;
	}

	private void setAssignments(List<EavropAssignment> assignments) {
		this.assignments = assignments;
	}

	protected void addAssignment(EavropAssignment assignment) {
		if (this.assignments == null) {
			this.assignments = new ArrayList<EavropAssignment>();
		}
		this.assignments.add(assignment);
	}

	/**
	 * Returns the current assigned vårdgivarenhet
	 * @return
	 */
	public Vardgivarenhet getCurrentAssignedVardgivarenhet(){
		Vardgivarenhet vardgivarenhet = null;
		if(getCurrentAssignment()!=null){
			vardgivarenhet = getCurrentAssignment().getVardgivarenhet();
		}
		return vardgivarenhet;
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
	 * Return number of deviation
	 * Possible deviations are:
	 * * Booking canceled > 96 hours (AFU)
	 * * Booking canceled < 96 hours (AFU)
	 * * Booking canceled > 48 hours (TMU/SLU)
	 * * Booking canceled < 48 hours (TMU/SLU)
	 * * Booking canceled citizen not present
	 * * Booking canceled interpreter not present
	 * * Exceeded assessment time limit
	 * * Exceeded time limit for completing certificates
	 */
	//TODO:is there anything else that that qualifies as a deviation, and should all booking deviations
	//be counted here
	public int getNumberOfDeviationsOnEavrop() {
		int count = 0;

		if(this.isEavropAcceptDaysDeviated()){
			count++;
		}

		for (Booking booking : getBookings()) {
			if(booking.hasDeviation()){
				count++;
			}
			if(booking.hasInterpreterDeviation()){
				count++;
			}
		} 
		
		if(this.isEavropAssessmentDaysDeviated()){
			count++;
		}
		
		count = count +  this.noOfIntygComplementDaysDeviations();
		
		return count;	
	}
	
	public boolean isDeviated(){
		return getNumberOfDeviationsOnEavrop() > 0;
	}
	
	public List<EavropDeviationEventDTO> getEavropDeviationEventDTOs() {
		List<EavropDeviationEventDTO> deviationEvents = new ArrayList<EavropDeviationEventDTO>();
		
		if(this.isEavropAcceptDaysDeviated()){
			deviationEvents.add(createEavropAcceptDaysDeviationEvent());
		}
		
		for (Booking booking : getBookings()) {
			if(booking.hasDeviation()){
				deviationEvents.add(createBookingDeviationEvent(booking));
			}
			if(booking.hasInterpreterDeviation()){
				deviationEvents.add(createInterpreterBookingDeviationEvent(booking));
			}
		}
		
		if(isEavropAssessmentDaysDeviated()){
			deviationEvents.add(createEavropAssessmentDaysDeviationEvent());
		}
		
		if(this.isIntygComplementDaysDeviated()){
			deviationEvents.addAll(createIntygComplementDaysDeviationEvents());
		}

		if(!deviationEvents.isEmpty()){
			Collections.sort(deviationEvents);
			return deviationEvents;
		}
		return null;
	}
	


	/**
	 * 
	 * @return
	 */
	private EavropDeviationEventDTO createEavropAcceptDaysDeviationEvent() {
		if(this.isEavropAcceptDaysDeviated()){
			LocalDate lastValidDay = getLastValidEavropAssignmentAcceptDay();
			DateTime devationDateTime = lastValidDay.toDateTime(new LocalTime(0,0));
			return new EavropDeviationEventDTO(EavropDeviationEventDTOType.EAVROP_ASSIGNMENT_ACCEPT_DEVIATION, devationDateTime.getMillis());
		}
		return null;
	}

	private EavropDeviationEventDTO createBookingDeviationEvent(Booking booking) {
		if(booking.hasDeviation()){
			return new EavropDeviationEventDTO(EavropDeviationEventDTOType.convertToEavropDeviationEventDTOType(booking.getBookingStatus()), booking.getLastModifiedDate().getMillis());
		}
		return null;
	}

	private EavropDeviationEventDTO createInterpreterBookingDeviationEvent(Booking booking) {
		if(booking.hasInterpreterDeviation()){
			return new EavropDeviationEventDTO(EavropDeviationEventDTOType.INTERPRETER_NOT_PRESENT, booking.getLastModifiedDate().getMillis());
		}
		return null;
	}


	/**
	 * 
	 * @return
	 */
	private EavropDeviationEventDTO createEavropAssessmentDaysDeviationEvent() {
		if(this.isEavropAssessmentDaysDeviated()){
			LocalDate lastValidDay = getLastValidEavropAssessmentDay();
			if(lastValidDay!=null){
				DateTime devationDateTime = lastValidDay.plusDays(1).toDateTime(new LocalTime(0,0));
				return new EavropDeviationEventDTO(EavropDeviationEventDTOType.EAVROP_ASSESSMENT_LENGHT_DEVIATION, devationDateTime.getMillis());
			}
		}
		return null;
	}
	
	public List<EavropDeviationEventDTO> createIntygComplementDaysDeviationEvents(){
		List<EavropDeviationEventDTO> deviations = new ArrayList<EavropDeviationEventDTO>();		
		
		//from date is the day after the Eavrop was received from orderer.
		int maxNumberOfDaysUntilIntygSent = this.getEavropProperties().getCompletionValidLength();
		
		List<IntygComplementRequestInformation> complementRequests = this.getAllIntygComplementRequestInformation();
		Collections.sort(complementRequests);

		List<IntygSentInformation> sentIntygs = this.getAllIntygSentInformation();
		Collections.sort(sentIntygs);

		
		for (IntygComplementRequestInformation intygComplementRequestInformation : complementRequests) {
			
			DateTime fromDateTime = intygComplementRequestInformation.getInformationTimestamp();
			//Find first intyg sent after request
			DateTime toDateTime  = null;
			
			for (IntygSentInformation intygSentInformation : sentIntygs) {
				if(intygSentInformation.getInformationTimestamp().isAfter(fromDateTime)){
					toDateTime = intygSentInformation.getInformationTimestamp();
					
				}else{
					toDateTime  = null;
				}
			}
			
			LocalDate fromDate = new  LocalDate(intygComplementRequestInformation.getInformationTimestamp()).plusDays(1);
			//The last day that it is okay to send;
			LocalDate lastValidDay = BusinessDaysUtil.calculateBusinessDayDate(fromDate, maxNumberOfDaysUntilIntygSent);
			
			LocalDate toDate = new LocalDate();
			
			//if an intygSentInformation has been found after the request use that otherwise stick with today
			if(toDateTime != null){
				toDate = new LocalDate(toDateTime);
			}
			
			if(toDate.isAfter(lastValidDay)){
				DateTime devationDateTime = lastValidDay.plusDays(1).toDateTime(new LocalTime(0,0));
				deviations.add(new EavropDeviationEventDTO(EavropDeviationEventDTOType.INTYG_COMPLEMENT_RESPONSE_DEVIATION, devationDateTime.getMillis())) ;
			}
		}
		return deviations;
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
	 * Set status of specified booking
	 * @param bookingId
	 * @param bookingStatus
	 * @param cancellationNote
	 */
	public void setBookingStatus(BookingId bookingId, BookingStatusType bookingStatus, Note cancellationNote){
		getEavropState().setBookingStatus(this, bookingId, bookingStatus, cancellationNote);
	}
	
	/**
	 * Set status of interpreter booking of specified booking 
	 * @param bookingId
	 * @param interpreterStatus
	 * @param cancellationNote
	 */
	public void setInterpreterBookingStatus(BookingId bookingId, InterpreterBookingStatusType interpreterStatus, Note cancellationNote){
		getEavropState().setInterpreterBookingStatus(this, bookingId, interpreterStatus, cancellationNote);
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
	 * Method for adding a received document to the eavrop
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
			doDocumentsSentFromBestallare(receivedDocument.getDocumentDateTime());
		}
	}
	
	protected void doDateTimeDocumentsSentFromBestallare(DateTime receivedDocumentDateTime) {
		doDocumentsSentFromBestallare(receivedDocumentDateTime);
	}

	
	private void doDocumentsSentFromBestallare(DateTime receivedDocumentDateTime) {
		if(getDateTimeDocumentsSentFromBestallare()==null && receivedDocumentDateTime !=null ){
			this.documentsSentFromBestallareDateTime =  receivedDocumentDateTime;
		}
		if(getStartDate()==null && receivedDocumentDateTime !=null ){
			
			LocalDate tomorrow =  new LocalDate(receivedDocumentDateTime).plusDays(1);
			int startDateOffset = this.getEavropProperties().getStartDateOffset();
			
			//Since the should start the first business day after the offset we need to plus one on the offset
			startDateOffset++;
			
			LocalDate startDate = BusinessDaysUtil.calculateBusinessDayDate(tomorrow, startDateOffset);
			this.setStartDate(startDate);
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
	 * Add a document request to the eavrop 
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
	protected void setDocumentsSentFromBestallare(DateTime documentsSentFromBestallareDateTime) {
		this.documentsSentFromBestallareDateTime = documentsSentFromBestallareDateTime;
	}

	/**
	 * To bypass the addDocumentReceived functionality to set the start date, this method can be used if we get a signal that 
	 * document have been sent but we dont get the document names
	 * 
	 * @param documentsSentFromBestallareDateTime
	 */
	public void setDateTimeDocumentsSentFromBestallare(DateTime documentsSentFromBestallareDateTime) {
		this.getEavropState().setDocumentsSentFromBestallareDateTime(this, documentsSentFromBestallareDateTime);
	}

	
	/**
	 * The internal id of the Eavrop
	 * @return  EavropId
	 */
	public EavropId getEavropId() {
		return this.eavropId;
	}
	
	private void setEavropId(EavropId eavropId) {
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

	/**
	 * Return the date time of when the eavrop compensation was approved by the beställare
	 * @return
	 */
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
	 * @param intygSentInformation 
	 */
	public void addIntygSentInformation(IntygSentInformation intygSentInformation) {
		this.getEavropState().addIntygSentInformation(this, intygSentInformation);
	}

	/**
	 * 
	 * @param intygSentInformation 
	 */
	protected void addToIntygSentInformation(IntygSentInformation intygSentInformation) {
		addToIntygInformation(intygSentInformation);
		
		// set the intyg sent date once for assessment days calculation
		if(intygSentInformation != null && intygSentInformation.getInformationTimestamp() != null && getIntygSentDateTime() == null){
			this.intygSentDate = intygSentInformation.getInformationTimestamp();
		}
	}
	
	public DateTime getIntygSentDateTime(){
		return this.intygSentDate;
	}
	
	public boolean isintygSent(){
		return (getIntygSentDateTime() != null)?Boolean.TRUE:Boolean.FALSE;
	}

	/**
	 * Method for retrieving responsible physician, if intyg is not signed / sent we return null
	 * @return
	 */
	public Person getIntygSigningPerson(){
		if(isintygSent() && getAllIntygSentInformation() != null && !getAllIntygSentInformation().isEmpty()){
			return getAllIntygSentInformation().iterator().next().getPerson();
		}
		return null;
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
		//this.intygSignedDate = null;
		
	}

	private List<IntygComplementRequestInformation> getAllIntygComplementRequestInformation() {

		ArrayList<IntygComplementRequestInformation> result = new  ArrayList<IntygComplementRequestInformation>();
		
		for(IntygInformation intygInformation : getIntygInformations()){
			if(intygInformation instanceof IntygComplementRequestInformation){
				result.add((IntygComplementRequestInformation)intygInformation);
			}
		}
		
		return result;
	}

	private List<IntygSentInformation> getAllIntygSentInformation() {

		ArrayList<IntygSentInformation> result = new  ArrayList<IntygSentInformation>();
		
		for(IntygInformation intygInformation : getIntygInformations()){
			if(intygInformation instanceof IntygSentInformation){
				result.add((IntygSentInformation)intygInformation);
			}
		}
		
		return result;
	}
	

	private List<IntygApprovedInformation> getAllIntygApprovedInformation() {
		ArrayList<IntygApprovedInformation> result = new  ArrayList<IntygApprovedInformation>();
		for(IntygInformation intygInformation : getIntygInformations()){
			if(intygInformation instanceof IntygApprovedInformation){
				result.add((IntygApprovedInformation)intygInformation);
			}
		}
		return result;
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
	 * Method for checking if the Eavrop intyg is considered complete. 
	 * 
	 * @return true if the customer has sent a intyg approved information message
	 */
	public boolean isIntygComplete(){
		List<IntygApprovedInformation>  intygApprovedList = getAllIntygApprovedInformation();
		
		if(intygApprovedList != null &&  ! intygApprovedList.isEmpty()){
			return true;
		}
		return false;
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

	
//	/**
//	 * Returns all notes added to the eavrop or its child entities
//	 * @return
//	 */
//	public List<Note> getAllNotes(){
//		
//		List<Note> result = new ArrayList<Note>(getNotes());
//		
//		for (BookingDeviation deviation : getBookingDeviations()) {
//			if(deviation.getDeviationNote()!=null){
//				result.add(deviation.getDeviationNote());
//			}
//			if(deviation.getBookingDeviationResponse()!=null && deviation.getBookingDeviationResponse().getDeviationResponseNote()!=null){
//				result.add(deviation.getBookingDeviationResponse().getDeviationResponseNote());
//			}
//		}
//		
//		if(getEavropApproval() != null && getEavropApproval().getNote() != null){
//			result.add(getEavropApproval().getNote());
//		}
//
//		if(getEavropCompensationApproval() != null && getEavropCompensationApproval().getNote() != null){
//			result.add(getEavropCompensationApproval().getNote());
//		}
//		
//		Collections.sort(result);
//		return result;
//	}

	
	/**
	 * Returns all notes added to the eavrop or its child entities
	 * @return
	 */
	public List<Note> getAllNotes(){
		
		List<Note> result = new ArrayList<Note>(getNotes());
		
		for (Booking booking : getBookings()) {
			if(booking.getDeviationNote()!=null){
				result.add(booking.getDeviationNote());
			}
			if(booking.getBookingDeviationResponse()!=null && booking.getBookingDeviationResponse().getDeviationResponseNote()!=null){
				result.add(booking.getBookingDeviationResponse().getDeviationResponseNote());
			}
			if(booking.getInterpreterBooking()!=null && booking.getInterpreterBooking().getDeviationNote()!=null){
				result.add(booking.getInterpreterBooking().getDeviationNote());
			}

		}
		
		if(getEavropApproval() != null && getEavropApproval().getNote() != null){
			result.add(getEavropApproval().getNote());
		}

		if(getEavropCompensationApproval() != null && getEavropCompensationApproval().getNote() != null){
			result.add(getEavropCompensationApproval().getNote());
		}
		
		for (EavropAssignment assignment : getAssignments()) {
			if(assignment.getRejectionNote()!=null){
				result.add(assignment.getRejectionNote());
			}
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
	
	
	/**
	 * Calculates the number of times the assessmentperiod/eavrop has been restarted.
	 * As the documents have been sent from customer, the startdate is set and the assessment period is considered started
	 * 
	 * If deviations happens happens that put the eavrop "On hold" a respondse is required from frpm the customer if the 
	 * Eavrop should continue, if so, the assessment period is restarted
	 * 
	 * @return int, the number of essessment starts.
	 */
	public int getNoOfEavropAssessmentsStarts(){
		int noOfStarts = 0;
		if(getStartDate()==null){
			//Not yet started
			return noOfStarts;
		}else if(LocalDate.now().isAfter(getStartDate())){
			//Started
			noOfStarts++;
		}
		for (Booking booking : getBookings()) {
			if(booking.getBookingDeviationResponse() !=null && BookingDeviationResponseType.RESTART.equals(booking.getBookingDeviationResponse().getResponseType())){
				//Restarted
				noOfStarts++;
			}
		}
		return noOfStarts;
	}
	
	
	public boolean isEavropAssessmentDaysDeviated(){
		
		LocalDate lastValidDay = this.getLastValidEavropAssessmentDay();
		LocalDate toDate = new LocalDate();
		
		//if assigenment is accepted, get the acceptDate as end date otherwisé stick with today
		if(isintygSent()){
			toDate = new LocalDate(getIntygSentDateTime());
		}
		
		//if to date is after the last valid day the assignment has been accepted to late
		if(lastValidDay != null && toDate.isAfter(lastValidDay)){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * Method for calculating the number of business days used during assessment;
	 * Will return null if the assessment has not started
	 * 
	 * @return
	 */
	public Integer getNoOfAssesmentDays(){

		//get the start date, if no start date can be found return null
		LocalDate fromDate = getStartDate();
		if(fromDate==null){
			return null;
		}
		
		//To date is either when the assessment finished, indicated by first intyg signed, or its still ongoing, then we use today
		LocalDate toDate = new LocalDate();
		//if assigenment is accepted, get the acceptDate as end date otherwisé stick with today
		if(isintygSent()){
			toDate = new LocalDate(getIntygSentDateTime());
		}
		
		Integer days = new Integer(BusinessDaysUtil.numberOfBusinessDays(fromDate, toDate));
		return days;
		
	}

	public boolean isIntygComplementDaysDeviated(){
		return noOfIntygComplementDaysDeviations() > 0;
	}

	public int noOfIntygComplementDaysDeviations(){
		int result = 0;		
		
		//from date is the day after the Eavrop was received from orderer.
		int maxNumberOfDaysUntilIntygSent = this.getEavropProperties().getCompletionValidLength();
		
		List<IntygComplementRequestInformation> complementRequests = this.getAllIntygComplementRequestInformation();
		Collections.sort(complementRequests);

		List<IntygSentInformation> sentIntygs = this.getAllIntygSentInformation();
		Collections.sort(sentIntygs);

		
		for (IntygComplementRequestInformation intygComplementRequestInformation : complementRequests) {
			
			DateTime fromDateTime = intygComplementRequestInformation.getInformationTimestamp();
			//Find first intyg sent after request
			DateTime toDateTime  = null;
			
			for (IntygSentInformation intygSentInformation : sentIntygs) {
				if(intygSentInformation.getInformationTimestamp().isAfter(fromDateTime)){
					toDateTime = intygSentInformation.getInformationTimestamp();
					
				}else{
					toDateTime  = null;
				}
			}
			
			LocalDate fromDate = new  LocalDate(intygComplementRequestInformation.getInformationTimestamp()).plusDays(1);
			//The last day that it is okay to send;
			LocalDate lastValidDay = BusinessDaysUtil.calculateBusinessDayDate(fromDate, maxNumberOfDaysUntilIntygSent);
			
			LocalDate toDate = new LocalDate();
			
			//if an intygSentInformation has been found after the request use that otherwise stick with today
			if(toDateTime != null){
				toDate = new LocalDate(toDateTime);
			}
			
			if(toDate.isAfter(lastValidDay)){
				result++ ;
			}
		}
		return result;
		
	}

	/**
	 * Return the number of days used for the last complementRequest
	 * @return
	 */
	public Integer getNoOfDaysUsedForLastComplementRequest(){
		
		IntygComplementRequestInformation lastComplementRequest = null;
		IntygSentInformation lastIntygSent = null;
		
		List<IntygComplementRequestInformation> complementRequests = this.getAllIntygComplementRequestInformation();
		Collections.sort(complementRequests);
		if (complementRequests != null && !complementRequests.isEmpty()) {
			lastComplementRequest = complementRequests.get(complementRequests.size()-1);
		}
		
		if(lastComplementRequest == null){
			return null;
		}
		
		List<IntygSentInformation> sentIntygs = this.getAllIntygSentInformation();
		Collections.sort(sentIntygs);
		if (sentIntygs != null && !sentIntygs.isEmpty()) {
			lastIntygSent = sentIntygs.get(sentIntygs.size()-1);
		}
		
		DateTime fromDateTime = lastComplementRequest.getInformationTimestamp();
		DateTime toDateTime  = new DateTime();
		if(lastIntygSent!=null && lastIntygSent.getInformationTimestamp().isAfter(fromDateTime)){
			toDateTime = lastIntygSent.getInformationTimestamp();
		}
		
		LocalDate fromDate = new LocalDate(fromDateTime);
		LocalDate toDate = new LocalDate(toDateTime);
		
		return BusinessDaysUtil.numberOfBusinessDays(fromDate, toDate);
		
	}

	
	public int getNoOfIntygComplementRequests(){
		return this.getAllIntygComplementRequestInformation().size();
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
	 * Returns true if an interpreter has been booked 
	 * @return
	 */
	public boolean hasInterpreterBooking() 
	{	
		for (Booking booking : this.getBookings()) {
			if(booking.hasInterpreterBooking()){
				return true;
			}
		}
		return false;
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
	
	// ~ util
	
//	public Integer getAssesmentDay(){
//		
//		if(hasStarted()){
//			LocalDate from =  this.getStartDate();
//			LocalDate to = (isintygSigned())?new LocalDate(getIntygSignedDateTime()).plusDays(1):new LocalDate().plusDays(1);
//			
//			return BusinessDaysUtil.numberOfBusinessDays(from,to); 
//		}
//		
//		return null;
//	}
//
//	public LocalDate getAssesmentPeriodEnd(){
//		
//		if(hasStarted()){
//			LocalDate from =  this.getStartDate();
//			LocalDate to = (isintygSigned())?new LocalDate(getIntygSignedDateTime()).plusDays(1):new LocalDate().plusDays(1);
//			
//			return BusinessDaysUtil.numberOfBusinessDays(from,to); 
//		}
//		
//		null;
//	}
	
	
	/**
	 * Method retrives bookings marked as additional service and has performed status
	 * @return
	 */
	public List<Booking> getAdditionalServiceBookings(){
		List<Booking> serviceBookings = null;
		
		if(UtredningType.AFU.equals(this.getUtredningType())){
			for (Booking booking : getBookings()) {
				if(booking.isAdditionalService() && booking.getBookingStatus().isPerformed()){
					if(serviceBookings==null){
						serviceBookings = new ArrayList<Booking>();
					}
					serviceBookings.add(booking);
				}
			}
		}
		return serviceBookings;
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


	
	/**
	 * Creates a list of DTO objects from entities in the eavrop
	 * Bookings
	 * InterpreterBookings
	 * BookingDeviationResponses
	 * IntygInformations
	 * EavropApproval
	 * EavropCompensationApproval
	 * 
	 * @return
	 */
	public List<EavropEventDTO> getEavropEvents() {
		
		List<EavropEventDTO> events = new ArrayList<EavropEventDTO>();
		
		//Bookings as DTO
		events.addAll(getEavropBookingsAsEvents());
		
		//IntygInformations as DTO
		for (IntygInformation intygInformation : getIntygInformations()) {
			events.add(intygInformation.getAsEavropEvent());
		}
		
		//Approval as DTO
		if(getEavropApproval()!=null){
			events.add(getEavropApproval().getAsEavropEvent());
		}

		//Compensation approval as DTO
		if(getEavropCompensationApproval()!=null){
			events.add(getEavropCompensationApproval().getAsEavropEvent());
		}

		//Sort collection on datetime
		Collections.sort(events);
		return events;
	}
	
	private List<EavropEventDTO> getEavropBookingsAsEvents(){
		List<EavropEventDTO> events = new ArrayList<EavropEventDTO>();
		for (Booking booking : this.getBookings()) {
			List<EavropEventDTO> eavropBookingEvents = booking.getAsEavropEvents(getUtredningType());
			events.addAll(eavropBookingEvents);
		}
		return events;
		
	}
	
	
}
