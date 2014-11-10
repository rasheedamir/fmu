package se.inera.fmu.domain.model.shared;

import lombok.ToString;
import se.inera.fmu.domain.shared.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Created by Rasheed on 7/23/14.
 */
@Embeddable
@ToString
public final class Address implements ValueObject<Address> {

    //~ Instance fields ================================================================================================

    @Column(name = "address_1", nullable = false)
    @NotNull
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "postal_code", nullable = false)
    @NotNull
    private String postalCode;

    @Column(name = "state")
    private String state;

    @Column(name = "city", nullable = false)
    @NotNull
    private String city;

    @Column(name = "country", nullable = false)
    @NotNull
    private String country;

    //~ Constructors ===================================================================================================

    Address() {
        // Needed by Hibernate
    }

    /**
    *
    * @param address1
    * @param postalCode
    * @param city
    */
    public Address(final String address1, final String postalCode, final String city) {
        this(address1, null, postalCode, null, city, null);
    }

    /**
     *
     * @param address1
     * @param postalCode
     * @param city
     * @param country
     */
    public Address(final String address1, final String postalCode, final String city, final String country) {
        this(address1, null, postalCode, null, city, country);
    }

    /**
     *
     * @param address1
     * @param address2
     * @param postalCode
     * @param state
     * @param city
     * @param country
     */
    public Address(final String address1, final String address2, final String postalCode, final String state, final String city, final String country) {
        setAddress1(address1);
        setAddress2(address2);
        setPostalCode(postalCode);
        setState(state);
        setCity(city);
        setCountry(country);
    }

    //~ Property Methods ===============================================================================================

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    private void setAddress1(String address1) {
        this.address1 = address1;
    }

    private void setAddress2(String address2) {
        this.address2 = address2;
    }

    private void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    private void setState(String state) {
        this.state = state;
    }

    private void setCity(String city) {
        this.city = city;
    }

    private void setCountry(String country) {
        this.country = country;
    }

    //~ Other Methods ===============================================================================================

    @Override
    public boolean sameValueAs(Address other) {
        return other != null && this.equals(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!address1.equals(address.address1)) return false;
        if (address2 != null ? !address2.equals(address.address2) : address.address2 != null) return false;
        if (!city.equals(address.city)) return false;
        if (!country.equals(address.country)) return false;
        if (!postalCode.equals(address.postalCode)) return false;
        if (state != null ? !state.equals(address.state) : address.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = address1.hashCode();
        result = 31 * result + (address2 != null ? address2.hashCode() : 0);
        result = 31 * result + postalCode.hashCode();
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + city.hashCode();
        result = 31 * result + country.hashCode();
        return result;
    }

}
