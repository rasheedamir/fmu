package se.inera.fmu.application;

import org.springframework.data.domain.Pageable;

import se.inera.fmu.application.impl.CreateEavropCommand;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.interfaces.managing.rest.EavropResource.OVERVIEW_EAVROPS_STATES;
import se.inera.fmu.interfaces.managing.rest.dto.EavropPageDTO;

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

	EavropPageDTO getOverviewEavrops(long fromDateMillis, long toDateMillis, OVERVIEW_EAVROPS_STATES status, Pageable paginationSpecs);

}
