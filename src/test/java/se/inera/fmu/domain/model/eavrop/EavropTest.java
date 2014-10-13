package se.inera.fmu.domain.model.eavrop;

import junit.framework.TestCase;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;

public class EavropTest extends TestCase {
	private Eavrop eavrop;
	
	private Invanare invanare;
	private PersonalNumber personalNumber; 
	private Gender gender;

	private String email;
	private String specialNeeds; 
	
	
	private Address address;
	private String address1;
	private String address2;
	private String postalCode;
	private String state;
	private String city;
	private String country;

	private Name name;
	private String firstName;
	private String middleName;
	private String lastName;

	private ArendeId arendeId;

	private UtredningType utredningType;

	private String tolk;
	
	private Landsting landsting;
	
	private Bestallaradministrator bestallaradministrator;

	@Override
	protected void setUp() throws Exception {
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
		personalNumber = new PersonalNumber("6677665577");
		gender = Gender.MALE;
		invanare = new Invanare(personalNumber, name, gender, address, email, specialNeeds);
		arendeId = new ArendeId("1312421532151");
		utredningType = UtredningType.SLU;
		tolk = "Swedish";
		landsting = new Landsting (new LandstingCode(1), "Stockholms läns landsting");
		bestallaradministrator = new Bestallaradministrator("Per Elofsson","Handläggare", "LFC Stockholm", "08123456", "per.elofsson@forsakringskassan.se");

	}
	
	public void testCreateName() {
		name = new Name(firstName, middleName, lastName);
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
	
	public void testInvanare() {
		invanare = new Invanare(personalNumber, name, gender, address, email, specialNeeds);
		assertEquals(personalNumber, invanare.getPersonalNumber());
		assertEquals(name, invanare.getName());
		assertEquals(gender, invanare.getGender());
		assertEquals(address, invanare.getHomeAddress());
		assertEquals(email, invanare.getEmail());
	}
	
	public void  testCreateEavrop() {
		
		eavrop = EavropBuilder.eavrop()
		.withArendeId(arendeId)
		.withUtredningType(utredningType) 
		.withInvanare(invanare)
		.withLandsting(landsting)
		.withBestallaradministrator(bestallaradministrator)
		.build();
		
		assertEquals(invanare, eavrop.getInvanare());
		assertEquals(utredningType, eavrop.getUtredningType());
		assertEquals(arendeId, eavrop.getArendeId());
		
		//TODO:Test more properties when added	
		}
}
