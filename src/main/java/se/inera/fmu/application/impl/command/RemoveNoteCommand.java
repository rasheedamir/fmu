package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.note.NoteId;
import se.inera.fmu.domain.model.hos.hsa.HsaId;


/**
 * Created by Rickard on 11/12/14.
 *
 * Command remove a note from an eavrop.
 */
@Getter
@AllArgsConstructor
public class RemoveNoteCommand {
	@NonNull private EavropId eavropId;
	@NonNull private NoteId noteId;
	@NonNull private HsaId personHsaId;
}
