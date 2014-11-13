package se.inera.fmu.application;

import java.util.List;

import org.springframework.data.domain.Pageable;

import se.inera.fmu.application.impl.command.CreateEavropCommand;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEventDTO;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.interfaces.managing.rest.EavropResource.OverviewEavropStates;
import se.inera.fmu.interfaces.managing.rest.dto.AllEventsDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropPageDTO;
import se.inera.fmu.interfaces.managing.rest.dto.OrderDTO;

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

	EavropPageDTO getOverviewEavrops(long fromDateMillis, long toDateMillis, OverviewEavropStates status, Pageable paginationSpecs);

	public List<EavropEventDTO> getEavropEvents(String eavropId);

	public AllEventsDTO getAllEvents(EavropId eavropId);

	public OrderDTO getOrderInfo(EavropId eavropId);

}
