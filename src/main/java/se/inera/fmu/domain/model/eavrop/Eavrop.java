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
@Table(name = "T_EAVROP",  uniqueConstraints=@UniqueConstraint(columnNames="ARENDE_ID"))
@ToString
public class Eavrop extends AbstractBaseEntity implements IEntity<Eavrop> {

    //~ Instance fields ================================================================================================

    // database primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EAVROP_ID", updatable = false, nullable = false)
    private Long eavropId;

    // business key! Received from client in create request
    @NotNull
    @Embedded
    private ArendeId arendeId;

    //TODO:remove? in DIM but not in prototype, ask FK or Mattias about it
    @Column(name = "DESCRIPTION")
    private String description;

    //Local status of Eavrop, initially set to 'NEW'
    @Column(name = "STATUS", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private EavropStatus status = EavropStatus.NEW;
    
    //Type of utredning. An utredning can be one of tree types
    @Column(name = "UTREDNING_TYPE", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private UtredningType utredningType;

    //Defines if there might be a need for an interpreter
    @Column(name = "TOLK")
    private boolean tolk;
    
    //Defines the needed language skills of the interpreter. //TODO:Check with FK if maybe only this is needed and not the boolean 'TOLK'
    @Column(name = "TOLK_LANG")
    private String tolkLanguage;

    //TODO: Neccessary? Present in DIM but not in gui prototype. //TODO: Ask FK or Mattias about it.
//    @Column(name = "ELEVATOR")
//    private boolean elevator;

    //If UtredningType is SLU there might be a focus of the examination set by the beställare. Corresponds to GUI value 'Val av inriktning' property in the GUI 
    @Column(name = "EXAMINATION_FOCUS")
    private String examinationFocus;

    //Additional information related to the eavrop   
    @Column(name = "ADDITIONAL_INFO")
    private String additionalInformation;
    
    // A log of all assignments to vardgivarenheter and there replies  
    @OneToMany
    private Set<EavropAssignment> assignments;
    
    //Maps the current assignment of the eavrop  
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="CURRENT_ASSIGNMENT_ID")
    private EavropAssignment currentAssignment;

    
    //TODO:Maybe the relation is wrong, maybe only a OneToOne relation. and treat invånare as Value Object
    @ManyToOne 
    @NotNull
    private Invanare invanare;
    
    //TODO: set as embeded object or create a relation to value object or only handle as event?
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name="BESTALLAR_PARTY_ID")
    private Bestallaradministrator bestallaradministrator;
    
    //The Landsting that this eavrop has directed to 
    @ManyToOne
    private Landsting landsting;
 
    //The bookings made
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Booking> bookings;

    //The notes related to this eavrop
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Note> notes;

    //Examination that led up to this FMU 
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PRIOR_EXAMINATION_ID")
    private PriorMedicalExamination priorMedicalExamination;

    //Documents received or requested regarding this FMU
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Document> documents;
    
    //When documents were sent from bestallare 
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @Column(name = "DOCUMENTS_SENT_DATE_TIME")
    private LocalDateTime documentsSentFromBestallareDateTime;
    
    // A log of all assignments to vardgivarenheter and there replies  
    @OneToMany(cascade = CascadeType.ALL)
    private Set<BookingDeviationResponse> bookingDeviationResponses;

    // A log of all intyg events  
    @OneToMany(cascade = CascadeType.ALL)
    private Set<IntygInformation> intygInformations;
    
    //The compensation approval of this eavrop 
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="EAVROP_COMP_APPROVAL_ID")
    private EavropCompensationApproval eavropCompensationApproval;

    //The approval of this eavrop 
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="EAVROP_APPROVAL_ID")
    private EavropApproval eavropApproval;

    
    //~ Constructors ===================================================================================================

    Eavrop() {
    	//Needed by hibernate
    }

    /**
     *
     * @param arendeId,
     * @param utredningType, 
     * @param invanare, 
     * @param landsting, 
     * @param bestallaradministrator, 
     */
    public Eavrop(final ArendeId arendeId, final UtredningType utredningType, final Invanare invanare, final Landsting landsting, final Bestallaradministrator bestallaradministrator) {
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

    //~ Property Methods ===============================================================================================

    

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

    
	public EavropApproval getEavropApproval() {
		return eavropApproval;
	}

	public void setEavropApproval(	EavropApproval eavropApproval) {
		this.eavropApproval = eavropApproval;
	}

	/**
     * The arendeId is the identity of this entity, and is unique.
     *
     * @return arendeId
     */
    public ArendeId getArendeId() {
        return arendeId;
    }
    
    private void setArendeId(ArendeId arendeId) {
        this.arendeId = arendeId;
    }

    public void assignEavropToVardgivare(Vardgivarenhet vardgivarenhet){
    	EavropAssignment eavropAssignment = new EavropAssignment(vardgivarenhet);
    	this.setCurrentAssignment(eavropAssignment);
    	this.addAssignment(eavropAssignment);
    }

    public void acceptEavropAssignment(){
    	this.getCurrentAssignment().acceptAssignment(); ;
    }
    
    public void rejectEavropAssignment(){
    	this.getCurrentAssignment().rejectAssignment(); ;
    	this.setCurrentAssignment(null);
    }

	private EavropAssignment getCurrentAssignment() {
		return currentAssignment;
	}

	private void setCurrentAssignment(EavropAssignment currentAssignment) {
		this.currentAssignment = currentAssignment;
	}

	public Set<EavropAssignment> getAssignmens() {
		return assignments;
	}

	private void setAssignments(Set<EavropAssignment> assignmens) {
		this.assignments = assignments;
	}

	public void addAssignment(EavropAssignment assignment) {
		if(this.assignments == null){
			this.assignments = new HashSet<EavropAssignment>();
		}
		this.assignments.add(assignment);
	}
	
	public Bestallaradministrator getBestallaradministrator() {
		return bestallaradministrator;
	}

    private void setBestallaradministrator(Bestallaradministrator bestallaradministrator) {
		this.bestallaradministrator = bestallaradministrator;
	}

	public Set<Booking> getBookings() {
		return bookings;
	}

	private void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}

	public void addBooking(Booking booking) {
		if(this.bookings == null){
			this.bookings = new HashSet<Booking>();
		}
		this.bookings.add(booking);
	}
	
    public Set<BookingDeviationResponse> getBookingDeviationResponses() {
		return bookingDeviationResponses;
	}

	private void setBookingDeviationResponses(Set<BookingDeviationResponse> bookingDeviationResponses) {
		this.bookingDeviationResponses = bookingDeviationResponses;
	}
	
	public void addBookingDeviationResponse(BookingDeviationResponse bookingDeviationResponse){
		if(this.bookingDeviationResponses == null){
			this.bookingDeviationResponses = new HashSet<BookingDeviationResponse>();
		}
		this.bookingDeviationResponses.add(bookingDeviationResponse);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	private void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	public void addDocument(Document document) {
		if(this.documents == null){
			this.documents = new HashSet<Document>();
		}
		this.documents.add(document);
	}

	public LocalDateTime getDocumentsSentFromBestallareDateTime(){
		return this.documentsSentFromBestallareDateTime;
	}
	
	public void setDocumentsSentFromBestallareDateTime(LocalDateTime documentsSentFromBestallareDateTime){
		this.documentsSentFromBestallareDateTime = documentsSentFromBestallareDateTime;
	}
	
	public EavropId getEavropId() {
        return new EavropId(this.eavropId);
    }
    
	private void setEavropId(Long eavropId) {
		this.eavropId = eavropId;
	}

	public String getExaminationFocus() {
		return examinationFocus;
	}

	public void setExaminationFocus(String examinationFocus) {
		this.examinationFocus = examinationFocus;
	}


	public EavropCompensationApproval getEavropCompensationApproval() {
		return eavropCompensationApproval;
	}

	public void setEavropCompensationApproval(	EavropCompensationApproval eavropCompensationApproval) {
		this.eavropCompensationApproval = eavropCompensationApproval;
	}

	//TODO:Probably not necessary
	public FmuKod getFmuKod() {
		return FmuKod.EAVROP;
	}

	public Set<IntygInformation> getIntygInformations() {
		return intygInformations;
	}

	private void setIntygInformations(Set<IntygInformation> intygInformations) {
		this.intygInformations = intygInformations;
	}

	public void addIntygInformation(IntygInformation intygInformation){
		if(this.intygInformations == null){
			this.intygInformations = new HashSet<IntygInformation>();
		}
		this.intygInformations.add(intygInformation);
	}

    public Invanare getInvanare() {
        return invanare;
    }

    private void setInvanare(Invanare invanare) {
        this.invanare = invanare;
    }
    
    public Landsting getLandsting() {
		return this.landsting;
	}

  	private void setLandsting(Landsting landsting) {
		this.landsting = landsting;
	}
    
	public Set<Note> getNotes() {
		return notes;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

	public PriorMedicalExamination getPriorMedicalExamination() {
		return priorMedicalExamination;
	}

	public void setPriorMedicalExamination(
			PriorMedicalExamination priorMedicalExamination) {
		this.priorMedicalExamination = priorMedicalExamination;
	}
	
    public EavropStatus getStatus() {
		return status;
	}

    public void setStatus(EavropStatus status) {
		this.status = status;
	}

	public boolean getTolk() {
        return tolk;
    }

    public void setTolk(boolean tolk) {
        this.tolk = tolk;
    }

    public String getTolkLanguage() {
		return this.tolkLanguage;
	}

	public void setTolkLanguage(String tolkLanguage) {
		this.tolkLanguage = tolkLanguage;
	}

	public UtredningType getUtredningType() {
        return utredningType;
    }

    private void setUtredningType(final UtredningType utredningType) {
        this.utredningType = utredningType;
    }
    
    //~ Other Methods ==================================================================================================

	@Override
    public boolean sameIdentityAs(final Eavrop other) {
        return other != null && this.getArendeId().sameValueAs(other.getArendeId());
    }

    /**
     * @param object to compare
     * @return True if they have the same identity
     * @see #sameIdentityAs(Eavrop)
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

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
