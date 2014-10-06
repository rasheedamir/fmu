package se.inera.fmu.domain.model.eavrop;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.ToString;
import se.inera.fmu.domain.shared.ValueObject;

/**
* Created by Rickard on 1/10/14.
*
* A value object that holds information about needed interpreter language skills 
*
*/
@ToString
@Embeddable
public class Interpreter implements ValueObject<Interpreter>{

	//~ Instance fields ================================================================================================

	// Defines the needed language skills of the interpreter.
	@Column(name = "INTERPRETER_LANG")
	private String interpreterLanguage;

    //~ Constructors ===================================================================================================

	public Interpreter(){
		//Needed by hibernate
	}
	
	public Interpreter(final String interpreterLanguage) {
		super();
		this.setInterpreterLanguage(interpreterLanguage);
	}

	//~ Property Methods ===============================================================================================
	
    public String getInterpreterLanguage() {
		return interpreterLanguage;
	}

	private void setInterpreterLanguage(String interpreterLanguage) {
		this.interpreterLanguage = interpreterLanguage;
	}
	
    //~ Other Methods ==================================================================================================

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return sameValueAs((Interpreter) o);
    }

	@Override
    public boolean sameValueAs(Interpreter other) {
        return other != null && this.interpreterLanguage.equals(other.interpreterLanguage);
    }

    @Override
    public int hashCode() {
        return this.interpreterLanguage.hashCode();
    }

}
