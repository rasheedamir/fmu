package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.note.NoteId;


/**
 * Created by Rickard on 11/12/14.
 *
 * Command remove a note from an eavrop.
 */
@Getter
@AllArgsConstructor
public class RemoveNoteCommand {
	@NonNull private ArendeId arendeId;
	@NonNull private NoteId noteId;
}
