package se.inera.fmu.domain.model.eavrop.invanare.medicalexamination;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.ToString;
import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.shared.ValueObject;

@Entity
@Table(name = "T_PRIOR_MEDICAL_EXAMINATION")
@ToString
public class PriorMedicalExamination implements ValueObject<PriorMedicalExamination>, Serializable {  
	    //~ Instance fields ================================================================================================

	    // database primary key
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "PRIOR_MEDICAL_EXAM_ID", updatable = false, nullable = false)
		private Long id	;

	    @Column(name = "EXAMINED_AT")
	    private String examinedAt;

	    @Column(name = "MEDICAL_LEAVE_ISSUED_AT")
	    private String medicalLeaveIssuedAt;

//	    @Column(name = "MEDICAL_LEAVE_ISSUED_BY")
//	    private String medicalLeaveIssuedBy;
		@NotNull
	    @OneToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name="PERSON_ID")
		private Person medicalLeaveIssuedBy;

   
	    //~ Constructors ===================================================================================================

	    PriorMedicalExamination(){ 
	    	//Needed by Hibernate
	    }

	    /**
	     *
	     * @param examinedAt, 
	     * @param medicalLeaveIssuedAt, 
	     * @param medicalLeaveIssuedBy,  
	     */
	    public PriorMedicalExamination(String examinedAt, String medicalLeaveIssuedAt, Person medicalLeaveIssuedBy ) {
	    	this.setExaminedAt(examinedAt);
	    	this.setMedicalLeaveIssuedAt(medicalLeaveIssuedAt);
	    	this.setMedicalLeaveIssuedBy(medicalLeaveIssuedBy);
	    }

	    //~ Property Methods ===============================================================================================

		public String getExaminedAt() {
			return examinedAt;
		}

		private void setExaminedAt(String examinedAt) {
			this.examinedAt = examinedAt;
		}

		public String getMedicalLeaveIssuedAt() {
			return medicalLeaveIssuedAt;
		}

		private void setMedicalLeaveIssuedAt(String medicalLeaveIssuedAt) {
			this.medicalLeaveIssuedAt = medicalLeaveIssuedAt;
		}

		public Person getMedicalLeaveIssuedBy() {
			return medicalLeaveIssuedBy;
		}

		private void setMedicalLeaveIssuedBy(Person medicalLeaveIssuedBy) {
			this.medicalLeaveIssuedBy = medicalLeaveIssuedBy;
		}

	    //~ Other Methods ==================================================================================================

	    /**
	     * @param object to compare
	     * @return True if they have the same value
	     * @see #sameValueAs(PriorMedicalExamination)
	     */
	    @Override
	    public boolean equals(final Object object) {
	        if (this == object) return true;
	        if (object == null || getClass() != object.getClass()) return false;

	        final PriorMedicalExamination other = (PriorMedicalExamination) object;
	        return sameValueAs(other);
	    }

		/**
	     * @return Hash code of tracking id.
	     */
	    @Override
	    public int hashCode() {
	    	return this.id.hashCode() ;
	    }

		@Override
		//TODO:
		public boolean sameValueAs(PriorMedicalExamination other) {
			return other != null && this.id.equals(other.id);
		}
	}

