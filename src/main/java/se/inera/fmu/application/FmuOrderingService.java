package se.inera.fmu.application;

import java.util.List;

import se.inera.fmu.application.impl.CreateEavropCommand;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.ArendeId;

/**
 * Created by Rasheed on 7/7/14.
 */
public interface FmuOrderingService {

    /**
     * Registers a new Eavrop in the tracking system.
     * @param aCommand
     * @return
     */
    public ArendeId createEavrop(CreateEavropCommand aCommand);

	public List<Eavrop> getOverviewEavrops();

}