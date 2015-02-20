package se.inera.fmu.domain.model.eavrop.note;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import se.inera.fmu.domain.shared.ValueObject;

/**
 * Internal id of Note
 *
 */
@Embeddable
public class NoteId  implements ValueObject<NoteId>, Serializable {

	private static final long serialVersionUID = 1L;
	
	//~ Instance fields ================================================================================================

	@Column(name = "NOTE_ID", nullable = false, updatable = false, unique = true, columnDefinition="char(36)")
    private String id;
    
    //~ Constructors ===================================================================================================

	NoteId() {

	}

	public NoteId(final String noteId){
		this.setNoteId(noteId);
	}

	
    //~ Property Methods ===============================================================================================

	public String getNoteId(){
		return this.id;
	}

	private void setNoteId(String noteId) {
		this.id = noteId;
	}

    //~ Other Methods ==================================================================================================


	@Override
	public String toString() {
		return this.id;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o){
        	return true;
        }
        if (o == null || getClass() != o.getClass()){
        	return false;
        }

        NoteId other = (NoteId) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean sameValueAs(NoteId other) {
        return other != null && this.id.equals(other.id);
    }
}
