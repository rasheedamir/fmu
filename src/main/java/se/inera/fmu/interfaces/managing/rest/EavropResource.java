package se.inera.fmu.interfaces.managing.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.interfaces.managing.dtomapper.EavropDTOMapper;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;

import com.codahale.metrics.annotation.Timed;

/**
 * Created by Rasheed on 8/25/14.
 *
 * REST controller for tracking eavrops
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/app")
public class EavropResource {

	private FmuOrderingService fmuOrderingService;
	private EavropDTOMapper eavropMapper = new EavropDTOMapper();

    /**
     *
     * TODO#1: Add parameter date range.
     * TODO#2: Add parameter status of utredning (new, in-process or finished)
     *
     * @return
     */
	@RequestMapping(value = "/rest/eavrop", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<EavropDTO> getEavrops() {
		List<EavropDTO> retval = new ArrayList<EavropDTO>();
		List<Eavrop> eavrops = this.fmuOrderingService.getOverviewEavrops();
		for (Eavrop eavrop : eavrops) {
			retval.add(this.eavropMapper.mappToDTO(eavrop));
		}
		
		return retval;
	}
	
	public FmuOrderingService getFmuOrderingService() {
		return fmuOrderingService;
	}
	
	public void setFmuOrderingService(FmuOrderingService fmuOrderingService) {
		this.fmuOrderingService = fmuOrderingService;
	}
}
