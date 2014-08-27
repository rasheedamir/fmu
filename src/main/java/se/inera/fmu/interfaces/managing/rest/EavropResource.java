package se.inera.fmu.interfaces.managing.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Rasheed on 8/25/14.
 *
 * REST controller for tracking eavrops
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/app")
public class EavropResource {

    @Inject
    private EavropRepository eavropRepository;

    @RequestMapping(value = "/rest/eavrop",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EavropDTO>> getEavrops(){
        return null;
    }
}
