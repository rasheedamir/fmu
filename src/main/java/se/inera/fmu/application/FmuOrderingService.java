package se.inera.fmu.application;

import java.util.List;

import se.inera.fmu.application.impl.CreateEavropCommand;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;

/**
 * Created by Rasheed on 7/7/14.
 */
public interface FmuOrderingService {

	/**
	 * Retrieves all Eavrops that belongs to a landsting
	 * 
	 * * @param landsting
	 * */
	public List<Eavrop> findAllUnassignedEavropByLandsting(Landsting landsting);

    /**
     * Registers a new Eavrop in the tracking system.
     * @param aCommand
     * @return
     */
    public ArendeId createEavrop(CreateEavropCommand aCommand);

	public List<Eavrop> getOverviewEavrops();

}
