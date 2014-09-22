package se.inera.fmu.domain.model.booking;

public enum BookingCancellationType {
	INVANARE_ABSENT,
	CANCELLED_GT_48,
	CANCELLED_LT_48,
	CANCELLED_INTERPRETER,
	INTERPRETER_ABSENT,
	PARTY_ABSENT;
}
