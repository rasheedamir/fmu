package se.inera.fmu.domain.model.eavrop.assignment;
/**
 * Enum describing possible assignment status types
 * ASSIGNED: The assignment has assigned status and are awaiting response from the care giving unit 
 * which might be one of the following values.
 * ACCEPTED: A care giving unit has accepted the assignment.    
 * ACCEPTED: A care giving unit has rejected the assignment.
 */
public enum EavropAssignmentStatusType {
	ASSIGNED,
	ACCEPTED,
	REJECTED;
}
