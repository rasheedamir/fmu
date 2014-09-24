package se.inera.fmu.domain.model.eavrop;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.apache.commons.lang3.Validate;

import se.inera.fmu.domain.model.booking.Booking;
import se.inera.fmu.domain.model.document.Document;
import se.inera.fmu.domain.model.event.EavropEvent;
import se.inera.fmu.domain.model.invanare.Invanare;
import se.inera.fmu.domain.model.invanare.medicalexamination.PriorMedicalExamination;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.Landstingssamordnare;
import se.inera.fmu.domain.model.note.Note;
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

    // business key!
    @NotNull
    @Embedded
    private ArendeId arendeId;

    //TODO:remove? in DIM but not in prototype
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private EavropStatus status = EavropStatus.NEW;
        
    @Column(name = "UTREDNING_TYPE", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private UtredningType utredningType;

    //private FmuKod fmuKod = FmuKod.EAVROP;
    
    @Column(name = "TOLK")
    private boolean tolk;
    
    @Column(name = "TOLK_LANG")
    private String tolkLanguage;

    //TODO: Neccessary? Present in DIM but not in gui prototype.
//    @Column(name = "ELEVATOR")
//    private boolean elevator;

    @Column(name = "EXAMINATION_FOCUS")
    private String examinationFocus;

    @Column(name = "ADDITIONAL_INFO")
    private String additionalInformation;
    
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "eavrop")
    @OneToMany(cascade = CascadeType.ALL)
    private Set<EavropEvent> events;

    
    //TODO:Maybe the relation is wrong, maybe only a OneToOne relation. and treat invånare as Value Object
    @ManyToOne 
    @NotNull
    private Invanare invanare;
    
    //TODO: set as embeded object or create a relation to value object or only handle as event?
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name="BESTALLAR_PARTY_ID")
    private Bestallaradministrator bestallaradministrator;

    @ManyToOne
    private Landsting landsting;
 
    //TODO:Maybe not neccessary? Only an event?
    @ManyToOne
    private Landstingssamordnare landstingssamordnare;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Booking> bookings;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Note> notes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PRIOR_EXAMINATION_ID")
    private PriorMedicalExamination priorMedicalExamination;

    //TODO#1:FMU_TID, CreateTS or somehing else is it enough with audit creation_time?


    ///TODO: Check these values from DIM
    
    //TID? is this creation time or something else?
    
    //Ärende in ÄHS; What is this?
    
    //Tidigare Utredning; Can there be more than one? and is it connected to the Invånare or Eavrop 
    	//Tidigare utredd vid?
    	//Sjukskrivande Enhet
    	//Sjukskrivande läkare
    	
    //FK?	Is this the Bestallare?
    //LFC?
    //Namn?
    //Telefonnummer? 
    //Epostadress
    //
    
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public EavropId getEavropId() {
        return new EavropId(this.eavropId);
    }
    
	private void setEavropId(Long eavropId) {
		this.eavropId = eavropId;
	}

	public Set<EavropEvent> getEvents() {
		return events;
	}

	private void setEvents(Set<EavropEvent> events) {
		this.events = events;
	}

	public void addEvent(EavropEvent event) {
		if(this.events == null){
			this.events = new HashSet<EavropEvent>();
		}
		this.events.add(event);
	}

	public String getExaminationFocus() {
		return examinationFocus;
	}

	public void setExaminationFocus(String examinationFocus) {
		this.examinationFocus = examinationFocus;
	}

	public FmuKod getFmuKod() {
		return FmuKod.EAVROP;
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
    
	public Landstingssamordnare getLandstingssamordnare() {
		return this.landstingssamordnare;
	}

	public void setLandstingssamordnare(Landstingssamordnare landstingssamordnare) {
		this.landstingssamordnare = landstingssamordnare;
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
