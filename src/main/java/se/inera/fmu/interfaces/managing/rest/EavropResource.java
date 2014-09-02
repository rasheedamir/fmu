package se.inera.fmu.interfaces.managing.rest;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropRepositoryStub;
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

    @Inject
    private EavropRepositoryStub eavropRepository;

    @RequestMapping(value = "/rest/eavrop",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EavropDTO>> getEavrops(){
    	ResponseEntity<List<EavropDTO>> list = new ResponseEntity<List<EavropDTO>>(HttpStatus.OK);
    	for (Eavrop e : eavropRepository.findAll()) {
			
		}
        return list;
    }
}
