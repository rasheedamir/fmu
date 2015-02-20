package se.inera.fmu.domain.model.eavrop.note;

/**
 * The Enum defines the types of Notes that the FMU applications handles.
 * EAVROP: A generic note added to the Eavrop
 * EAVROP_ASSIGNMENT_REJECTION: Note on assignment, set during assignmnet rejection
 * BOOKING_DEVIATION: Booking deviation note, set on booking when set to deviant
 * BOOKING_DEVIATION_RESPONSE: Note on booking deviation response, from customer, describing it
 * DOCUMENT_REQUEST: Note on document request, describing the request to the customer
 * APPROVAL: Note set on approval by customer
 * COMPENSATION_APPROVAL: Note set on compensation approval by customer 
 */
public enum NoteType {
	EAVROP,
	EAVROP_ASSIGNMENT_REJECTION,
	BOOKING_DEVIATION,
	BOOKING_DEVIATION_RESPONSE,
	DOCUMENT_REQUEST,
	APPROVAL,
	COMPENSATION_APPROVAL;
}
