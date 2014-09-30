package se.inera.fmu.domain.model.eavrop;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignment;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponse;
import se.inera.fmu.domain.model.eavrop.document.Document;
import se.inera.fmu.domain.model.eavrop.document.DocumentType;
import se.inera.fmu.domain.model.eavrop.intyg.IntygInformation;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.invanare.Invanare;
import se.inera.fmu.domain.model.invanare.medicalexamination.PriorMedicalExamination;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.party.Bestallaradministrator;
import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.IEntity;

/**
 * Created by Rasheed on 7/7/14.
 */
@Entity
@Table(name = "T_EAVROP", uniqueConstraints = @UniqueConstraint(columnNames = "ARENDE_ID"))
@ToString
public class Eavrop extends AbstractBaseEntity implements IEntity<Eavrop> {

	// ~ Instance fields
	// ================================================================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// database primary key
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

	// Local status of Eavrop, initially set to 'NEW'
	@Column(name = "STATUS", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private EavropStatus status = EavropStatus.NEW;

	// Type of utredning. An utredning can be one of tree types
	@Column(name = "UTREDNING_TYPE", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private UtredningType utredningType;

	// Defines if there might be a need for an interpreter
	@Column(name = "TOLK")
	private boolean tolk;

	// Defines the needed language skills of the interpreter. //TODO:Check with
	// FK if maybe only this is needed and not the boolean 'TOLK'
	@Column(name = "TOLK_LANG")
	private String tolkLanguage;

	// TODO: Neccessary? Present in DIM but not in gui prototype. //TODO: Ask FK
	// or Mattias about it.
	// @Column(name = "ELEVATOR")
	// private boolean elevator;

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
	private Set<EavropAssignment> assignments;

	// Maps the current assignment of the eavrop
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CURRENT_ASSIGNMENT_ID")
	private EavropAssignment currentAssignment;

	// TODO:Maybe the relation is wrong, maybe only a OneToOne relation. and
	// treat invånare as Value Object
	@ManyToOne
	@NotNull
	private Invanare invanare;

	// TODO: set as embeded object or create a relation to value object or only
	// handle as event?
	@OneToOne(cascade = CascadeType.ALL)
	@NotNull
	@JoinColumn(name = "BESTALLAR_PARTY_ID")
	private Bestallaradministrator bestallaradministrator;

	// The Landsting that this eavrop has directed to
	@ManyToOne
	private Landsting landsting;

	// The bookings made
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Booking> bookings;

	// The notes related to this eavrop
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Note> notes;

	// Examination that led up to this FMU
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PRIOR_EXAMINATION_ID")
	private PriorMedicalExamination priorMedicalExamination;

	// Documents received or requested regarding this FMU
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Document> documents;

	// When documents were sent from bestallare
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name = "DOCUMENTS_SENT_DATE_TIME")
	private LocalDateTime documentsSentFromBestallareDateTime;

	// A log of all assignments to vardgivarenheter and there replies
	@OneToMany(cascade = CascadeType.ALL)
	private Set<BookingDeviationResponse> bookingDeviationResponses;

	// A log of all intyg events
	@OneToMany(cascade = CascadeType.ALL)
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

	/**
	 * @param arendeId
	 *            ,
	 * @param utredningType
	 *            ,
	 * @param invanare
	 *            ,
	 * @param landsting
	 *            ,
	 * @param bestallaradministrator
	 *            ,
	 */
	public Eavrop(final ArendeId arendeId, final UtredningType utredningType,
			final Invanare invanare, final Landsting landsting,
			final Bestallaradministrator bestallaradministrator) {
		Validate.notNull(arendeId);
		setArendeId(arendeId);
		Validate.notNull(utredningType);
		setUtredningType(utredningType);
		Validate.notNull(invanare);
		setInvanare(invanare);
		Validate.notNull(landsting);
		setLandsting(landsting);
		Validate.notNull(bestallaradministrator);
		setBestallaradministrator(bestallaradministrator);
	}

	// ~ Property Methods
	// ===============================================================================================

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
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

	public void setEavropApproval(EavropApproval eavropApproval) {
		this.eavropApproval = eavropApproval;
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
	 * @param vardgivarenhet
	 *            , the care giver unit that the eavrop should be assigned to
	 * @see Vardgivarenhet
	 */
	public void assignEavropToVardgivare(Vardgivarenhet vardgivarenhet) {
		EavropAssignment eavropAssignment = new EavropAssignment(vardgivarenhet);
		this.setCurrentAssignment(eavropAssignment);
		this.addAssignment(eavropAssignment);
	}

	// TODO: check that the user accepting the eavrop belongs to the currently
	// assigned caregiver?
	/**
	 * Accepts the eavrop to the currently assigned vardgivare
	 *
	 */
	public void acceptEavropAssignment() {
		if (getCurrentAssignment() != null) {
			this.getCurrentAssignment().acceptAssignment();
		} else {
			// TODO:throw something or return quietly
		}
	}

	// TODO: check that the user rejecting the eavrop belongs to the currently
	// assigned caregiver?
	/**
	 * Accepts the eavrop to the currently assigned vardgivare
	 *
	 */
	public void rejectEavropAssignment() {
		if (getCurrentAssignment() != null) {
			this.getCurrentAssignment().rejectAssignment();
			this.setCurrentAssignment(null);
		} else {
			// TODO:throw something or return quietly
		}
	}

	private EavropAssignment getCurrentAssignment() {
		return currentAssignment;
	}

	private void setCurrentAssignment(EavropAssignment currentAssignment) {
		this.currentAssignment = currentAssignment;
	}

	/**
	 * Returns a Set<EavropAssignment> with all the assignments made to this
	 * evarop
	 *
	 * @return a set with all EavropAssignments related to the eavrop
	 * @see EavropAssignment
	 */
	public Set<EavropAssignment> getAssignmens() {
		return assignments;
	}

	private void setAssignments(Set<EavropAssignment> assignmens) {
		this.assignments = assignments;
	}

	public void addAssignment(EavropAssignment assignment) {
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

	private void setBestallaradministrator(
			Bestallaradministrator bestallaradministrator) {
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
		if (this.bookings == null) {
			this.bookings = new HashSet<Booking>();
		}
		this.bookings.add(booking);
	}

	/**
	 * When the orderer replies to a booking deviation event, the response is
	 * stored in the eavrop, this method return all of them.
	 *
	 * @return bookingDeviationResponses
	 */
	public Set<BookingDeviationResponse> getBookingDeviationResponses() {
		return bookingDeviationResponses;
	}

	private void setBookingDeviationResponses(Set<BookingDeviationResponse> bookingDeviationResponses) {
		this.bookingDeviationResponses = bookingDeviationResponses;
	}

	/**
	 * When a the orderer replies to a booking deviation, the response should be
	 * added to the eavrop using this method.
	 *
	 * @return bookingDeviationResponses
	 */
	public void addBookingDeviationResponse(BookingDeviationResponse bookingDeviationResponse) {
		if (this.bookingDeviationResponses == null) {
			this.bookingDeviationResponses = new HashSet<BookingDeviationResponse>();
		}
		this.bookingDeviationResponses.add(bookingDeviationResponse);
	}

	/**
	 * Returns the description property of this eavrop
	 *
	 * @return description, as a String
	 */
	public String getDescription() {
		return description;
	}

	//TODO: Maybe move to constructor an declare this method as private  
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns a set with document information entities added to this eavrop
	 *
	 * @return description, as a String
	 */
	public Set<Document> getDocuments() {
		return documents;
	}

	/**
	 * Returns a set with the document information of all the received documents
	 *
	 * @return documents
	 */
	public Set<Document> getReceivedDocuments() {
		return getDocumentsOfType(DocumentType.RECEIVED);
	}

	/**
	 * Returns a set with the document information of all the requested documents
	 *
	 * @return documents
	 */
	public Set<Document> getRequestedDocuments() {
		return getDocumentsOfType(DocumentType.REQUESTED);
	}
	
	private Set<Document> getDocumentsOfType(DocumentType documentType){
		Set<Document> result = new HashSet<Document>();
		for (Document document : getDocuments()) {
			if(documentType.equals(document.getDocumentType())){
				result.add(document);
			}
		}
		
		return result;
	}

	private void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	/**
	 * Returns a set with document information entities added to this eavrop
	 *
	 * @return description, as a String
	 */
	public void addDocument(Document document) {
		if (this.documents == null) {
			this.documents = new HashSet<Document>();
		}
		this.documents.add(document);
	}
	

	/**
	 * This property represents a point in time when the orderer, bastallare, notified that they sent documents 
	 * @return LocalDateTime, when the doucuments were sent
	 */
	public LocalDateTime getDocumentsSentFromBestallareDateTime() {
		return this.documentsSentFromBestallareDateTime;
	}

	/**
	 * This property represents a point in time when the orderer, bastallare, notified that they sent documents 
	 * @param  documentsSentFromBestallareDateTime
	 */
	public void setDocumentsSentFromBestallareDateTime(	LocalDateTime documentsSentFromBestallareDateTime) {
		this.documentsSentFromBestallareDateTime = documentsSentFromBestallareDateTime;
	}

	
	/**
	 * The internal id of the Eavrop
	 * @return  EavropId
	 */
	public EavropId getEavropId() {
		return new EavropId(this.eavropId);
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
	public void setUtredningFocus(String utredningFocus) {
		this.utredningFocus = utredningFocus;
	}

	/**
	 * This property represents that the orderer approves the compensation of the utredning.  
	 * 
	 * @return EavropCompensationApproval
	 */
	public EavropCompensationApproval getEavropCompensationApproval() {
		return eavropCompensationApproval;
	}

	/**
	 * This property represents that the orderer approves the compensation of the utredning.  
	 * 
	 * @param eavropCompensationApproval
	 */
	public void setEavropCompensationApproval(
			EavropCompensationApproval eavropCompensationApproval) {
		this.eavropCompensationApproval = eavropCompensationApproval;
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
	public void addIntygInformation(IntygInformation intygInformation) {
		if (this.intygInformations == null) {
			this.intygInformations = new HashSet<IntygInformation>();
		}
		this.intygInformations.add(intygInformation);
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
	 * Retunrns the landsting that this evarop is ordered at
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

	private void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

	/**
	 * Adds a note to this eavrop
	 * @return
	 */
	public void addNote(Note note) {
		if(this.notes == null){
			this.notes = new HashSet<Note>();
		} 
		this.notes.add(note);
	}
	
	/**
	 * Returns information about prior medical examination leading up to this eavrop
	 * 
	 * @return priorMedicalExamination
	 * @see PriorMedicalExamination
	 */
	public PriorMedicalExamination getPriorMedicalExamination() {
		return priorMedicalExamination;
	}

	//TODO: Set in constructor
	public void setPriorMedicalExamination(
			PriorMedicalExamination priorMedicalExamination) {
		this.priorMedicalExamination = priorMedicalExamination;
	}

	/**
	 * Returns the current status of the eavrop
	 * @return status
	 */
	public EavropStatus getStatus() {
		return status;
	}

	/**
	 * Sets the current status of the eavrop
	 * @return status
	 */
	public void setStatus(EavropStatus status) {
		this.status = status;
	}

	/**
	 * Returns a true value if the 
	 * @return
	 */
	public boolean getTolk() {
		return tolk;
	}

	//TODO: Maybe set in the constructor 
	public void setTolk(boolean tolk) {
		this.tolk = tolk;
	}

	/**
	 * Returns a descriptive String about the language requirements of the interpreter
	 * @return
	 */
	public String getTolkLanguage() {
		return this.tolkLanguage;
	}

	//TODO: Maybe set in the constructor
	public void setTolkLanguage(String tolkLanguage) {
		this.tolkLanguage = tolkLanguage;
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

	// ~ Other Methods
	// ==================================================================================================

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
	 * @return Hash code of tracking id.
	 */
	@Override
	public int hashCode() {
		return arendeId.hashCode();
	}
}
