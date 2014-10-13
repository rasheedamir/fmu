package se.inera.fmu.domain.model.eavrop;

import static org.junit.Assert.*;

import org.junit.Test;


public class ClosedEavropStateTest extends AbstractEavropStateTest{

	@Override
	Eavrop getEavrop() {
		return createClosedEavrop();
	}

	@Override
	EavropStateType getEavropStateType() {
		return EavropStateType.CLOSED;
	}
}
