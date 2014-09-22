package se.inera.fmu.application;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

public abstract class AbstractUtilsTest {

	private final Class<?> utilsClass;

	protected AbstractUtilsTest(Class<?> utilsClass) {
		this.utilsClass = utilsClass;
	}

	@Test
	public void verifyUtilsClass() {
		// utility class is assumed to be final
		verifyFinalUtilsClass();
		// utility class is assumed to have non-public default constructor
		verifyDefaultConstructor();
	}

	private void verifyFinalUtilsClass() {
		assertTrue("utility " + utilsClass + " is not final", Modifier.isFinal(utilsClass.getModifiers()));
	}

	private void verifyDefaultConstructor() {
		try
		{
			Constructor<?> constructor = getDefaultConstructor();
			assertFalse("default constructor for utility " + utilsClass + " is still public",
					Modifier.isPublic(constructor.getModifiers()));

			constructor.setAccessible(true);
			constructor.newInstance();
		}
		catch (Throwable ex) {
			fail("default constructor for utility " + utilsClass + " generated unexpected failure: " + ex);
		}
	}

	private Constructor<?> getDefaultConstructor() {
		try
		{
			return utilsClass.getDeclaredConstructor();
		}
		catch (NoSuchMethodException ex) {
			fail("utility " + utilsClass + " has no default constructor");
			return null;
		}
	}
}

