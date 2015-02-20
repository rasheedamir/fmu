package se.inera.fmu.domain.model.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.ToString;
import se.inera.fmu.domain.shared.ValueObject;

/**
 * A Name
 */
@ToString
@Embeddable
public final class Name implements ValueObject<Name> {

    //~ Instance fields ================================================================================================

    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Size(min = 0, max = 50)
    @NotNull
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    //~ Constructors ===================================================================================================

    Name() {
        // Needed by Hibernate
    }

    public Name(String firstName, String middleName, String lastName) {
        this.setFirstName(firstName);
        this.setMiddleName(middleName);
        this.setLastName(lastName);
    }

    //~ Property Methods ===============================================================================================

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    private void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getInitials(){
    	StringBuilder sb = new StringBuilder();
    	sb.append((getFirstName()!=null)?getFirstName().charAt(0):"");
    	sb.append((getLastName()!=null)?getLastName().charAt(0):"");
    	
    	return sb.toString();
    }
    
    public String getFullName(){
    	StringBuilder sb = new StringBuilder();
    	sb.append(getFirstName());
    	
    	if(getMiddleName() != null){
    		sb.append(' ');
    		sb.append(getMiddleName());
    	}
    	
    	sb.append(' ');
    	sb.append(getLastName());
    	return sb.toString();
    }

    //~ Other Methods ==================================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o){
        	return true;
        }
        if (o == null || getClass() != o.getClass()){
        	return false;
        }

        Name name = (Name) o;

        if (firstName != null ? !firstName.equals(name.firstName) : name.firstName != null){
        	return false;
        }
        if (lastName != null ? !lastName.equals(name.lastName) : name.lastName != null){
        	return false;
        }
        if (!middleName.equals(name.middleName)){
        	return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + middleName.hashCode();
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public boolean sameValueAs(Name other) {
        return other != null && this.equals(other);
    }
}
