package se.inera.fmu.domain.model.eavrop.properties;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.ToString;

import org.apache.commons.lang3.Validate;

import se.inera.fmu.domain.shared.ValueObject;

@Embeddable
@ToString
public class EavropProperties implements ValueObject<EavropProperties> {
	
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
