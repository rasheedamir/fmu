package se.inera.fmu.domain.model.eavrop;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.ToString;
import se.inera.fmu.domain.shared.ValueObject;

/**
*
* A value object that holds information about needed interpreter language skills 
*
*/
@ToString
@Embeddable
public class Interpreter implements ValueObject<Interpreter>{
 
	private static final long serialVersionUID = 1L;

	//~ Instance fields ================================================================================================

	@Column(name = "INTERPRETER_DESC")
	private String interpreterDescription;

    //~ Constructors ===================================================================================================

	public Interpreter(){
		//Needed by hibernate
	}
	
	public Interpreter(final String interpreterDescription) {
		super();
		this.setInterpreterDescription(interpreterDescription);
	}

	//~ Property Methods ===============================================================================================
	
    public String getInterpreterDescription() {
		return interpreterDescription;
	}

	private void setInterpreterDescription(String interpreterDescription) {
		this.interpreterDescription = interpreterDescription;
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

        return sameValueAs((Interpreter) o);
    }

	@Override
    public boolean sameValueAs(Interpreter other) {
        return other != null && this.interpreterDescription.equals(other.interpreterDescription);
    }

    @Override
    public int hashCode() {
        return this.interpreterDescription.hashCode();
    }
}
