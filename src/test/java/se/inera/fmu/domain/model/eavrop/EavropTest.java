package se.inera.fmu.domain.model.eavrop;

import junit.framework.TestCase;
import se.inera.fmu.domain.model.patient.Address;
import se.inera.fmu.domain.model.patient.Gender;
import se.inera.fmu.domain.model.patient.Initials;
import se.inera.fmu.domain.model.patient.Name;
import se.inera.fmu.domain.model.patient.Patient;

public class EavropTest extends TestCase {
	private Eavrop eavrop;
	
	private Patient patient;
	private String personalNumber;
	private Gender gender;

	private String email;

	private Address address;
	private String address1;
	private String address2;
	private String postalCode;
	private String state;
	private String city;
	private String country;

	private Name name;
	private Initials initials;
	private String firstName;
	private String middleName;
	private String lastName;

	private ArendeId arendeId;

	private UtredningType utredningType;

	private String tolk;

	@Override
	protected void setUp() throws Exception {
		initials = Initials.MR;
		firstName = "john";
		middleName = "k";
		lastName = "lars";
		email = "email@test.se";
		address1 = "testgatan 1";
		address2 = "testgatan 2";
		postalCode = "33144";
		state = "test county";
		city = "test city";
		country = "testland";
		personalNumber = "6677665577";
		gender = Gender.MALE;
		arendeId = new ArendeId("1312421532151");
		utredningType = UtredningType.SLU;
		tolk = "Swedish";
	}
	
	public void testCreateName() {
		name = new Name(initials, firstName, middleName, lastName);
		assertEquals(initials, name.getInitials());
		assertEquals(firstName, name.getFirstName());
		assertEquals(middleName, name.getMiddleName());
		assertEquals(lastName, name.getLastName());
	}
	
	public void  testCreateAddress1() {
		address = new Address(address1, postalCode, city, country);
		assertEquals(address1, address.getAddress1());
		assertEquals(null, address.getAddress2());
		assertEquals(city, address.getCity());
		assertEquals(country, address.getCountry());
		assertEquals(postalCode, address.getPostalCode());
		assertEquals(null, address.getState());
	}
	
	public void  testCreateAddress2() {
		address = new Address(address1, address2, postalCode, state, city, country);
		assertEquals(address1, address.getAddress1());
		assertEquals(address2, address.getAddress2());
		assertEquals(city, address.getCity());
		assertEquals(country, address.getCountry());
		assertEquals(postalCode, address.getPostalCode());
		assertEquals(state, address.getState());
	}
	
	public void testPatient() {
		patient = new Patient(personalNumber, name, gender, address, email);
		assertEquals(personalNumber, patient.getPersonalNumber());
		assertEquals(name, patient.getName());
		assertEquals(gender, patient.getGender());
		assertEquals(address, patient.getHomeAddress());
		assertEquals(email, patient.getEmail());
	}
	
	public void  testCreateEavrop() {
		eavrop = new Eavrop(arendeId, utredningType, tolk, patient);
		assertEquals(patient, eavrop.getPatient());
		assertEquals(tolk, eavrop.getTolk());
		assertEquals(utredningType, eavrop.getUtredningType());
		assertEquals(arendeId, eavrop.getArendeId());
	}
}
