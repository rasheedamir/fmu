package se.inera.fmu.domain.model.eavrop;

import javax.persistence.AttributeConverter;

public class EavropStateConverter implements AttributeConverter<EavropState, String> {

	@Override
		public String convertToDatabaseColumn(EavropState state) {
			switch(state.getEavropStateType()){
			case UNASSIGNED:
				return EavropStateType.UNASSIGNED.name();
			case ACCEPTED:
				return EavropStateType.ACCEPTED.name();
			case APPROVED:
				return EavropStateType.APPROVED.name();
			case ASSIGNED:
				return EavropStateType.ASSIGNED.name();
			case CLOSED:
				return EavropStateType.CLOSED.name();
			case ON_HOLD:
				return EavropStateType.ON_HOLD.name();
			case SENT:
				return EavropStateType.SENT.name();
			default:
				 throw new IllegalArgumentException("Unknown value: " + state.getEavropStateType());
			}
		}

		@Override
		public EavropState convertToEntityAttribute(String dbData) {
			switch (dbData) {
			  case "UNASSIGNED":
				  return new UnassignedEavropState();
			  case "ACCEPTED":
				  return new AcceptedEavropState();
			  case "APPROVED":
				  return new ApprovedEavropState();
			  case "ASSIGNED":
				  return new AssignedEavropState();
			  case "CLOSED":
				   return new ClosedEavropState();
			  case "ON_HOLD":
				   return new OnHoldEavropState();
			  case "SENT":
				   return new SentEavropState();
			  default:
			   throw new IllegalArgumentException("Unknown value: " + dbData);
			  }
		}	
	

}
