package se.inera.fmu.domain.model.eavrop.properties;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.ToString;

import org.apache.commons.lang3.Validate;

import se.inera.fmu.domain.shared.ValueObject;
/**
 * Represents the calculation properties of the Eavrop.
 * START_DATE_OFFSET:  The number of business days between that the documentation has been sent and the Eavrop should 
 * be considered started
 * ACCEPTANCE_VALID_LENGTH: The number of business days that the Landstingssamordnare has to find a care giving unit 
 * that will accept the eavrop
 * ASSESSMENT_VALID_LENGTH: The number of business days that the care giving unit has to perform the assessment and
 * send an Intyg to the customer
 * COMPLETION_VALID_LENGTH: The number of business days that the care giving unit has to add completions to an Intyg and 
 * resend to customer    
 *
 */
@Embeddable
@ToString
public class EavropProperties implements ValueObject<EavropProperties> {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "START_DATE_OFFSET", updatable = false, nullable = false )
	private int startDateOffset;

	@Column(name = "ACCEPTANCE_VALID_LENGTH", updatable = false, nullable = false)
	private int acceptanceValidLength;
	
	@Column(name = "ASSESSMENT_VALID_LENGTH", updatable = false, nullable = false)
	private int assessmentValidLength;

	@Column(name = "COMPLETION_VALID_LENGTH", updatable = false, nullable = false)
	private int completionValidLength;

	public EavropProperties(){
		super();
	}
	
	public EavropProperties(int startDateOffset, int acceptanceValidLength, int assessmentValidLength, int completionValidLength) {
		super();
		Validate.notNull(startDateOffset);
		Validate.notNull(acceptanceValidLength);
		Validate.notNull(assessmentValidLength);
		Validate.notNull(completionValidLength);
		setStartDateOffset(startDateOffset);
		setAcceptanceValidLength(acceptanceValidLength);
		setAssessmentValidLength(assessmentValidLength);
		setCompletionValidLength(completionValidLength);
	}
	
	public int getStartDateOffset() {
		return startDateOffset;
	}

	private void setStartDateOffset(int startDateOffset) {
		this.startDateOffset = startDateOffset;
	}

	public int getAssessmentValidLength() {
		return assessmentValidLength;
	}

	private void setAssessmentValidLength(int assessmentValidLength) {
		this.assessmentValidLength = assessmentValidLength;
	}

	public int getAcceptanceValidLength() {
		return acceptanceValidLength;
	}

	private void setAcceptanceValidLength(int acceptanceValidLength) {
		this.acceptanceValidLength = acceptanceValidLength;
	}

	public int getCompletionValidLength() {
		return completionValidLength;
	}

	private void setCompletionValidLength(int completionValidLength) {
		this.completionValidLength = completionValidLength;
	}

	@Override
	public boolean sameValueAs(EavropProperties other) {
		return other != null 
			&& this.startDateOffset == other.startDateOffset
			&& this.assessmentValidLength == other.assessmentValidLength
			&& this.acceptanceValidLength == other.acceptanceValidLength
			&& this.completionValidLength == other.completionValidLength
			;
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object){
			return true;
		}
		if (object == null || getClass() != object.getClass()){
			return false;
		}

		final EavropProperties other = (EavropProperties) object;
		return sameValueAs(other);
	}

	/**
	 * @return Hash code .
	 */
	@Override
	public int hashCode() {
		 return startDateOffset * 31 * assessmentValidLength;
	}
}
