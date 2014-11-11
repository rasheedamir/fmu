package se.inera.fmu.application;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.person.Person;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropAssignmentDomainService {
	
	public void assignEavropToVardgivarenhet(EavropId eavropId, HsaId vardgivarenhetId);
	
	public void acceptEavropAssignment(EavropId eavropId, HsaId vardgivarenhetId);
	
	public void rejectEavropAssignment(EavropId eavropId, HsaId vardgivarenhetId);
	
}
