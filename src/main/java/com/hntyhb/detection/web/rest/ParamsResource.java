package com.hntyhb.detection.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hntyhb.detection.domain.Params;
import com.hntyhb.detection.repository.ParamsRepository;
import com.hntyhb.detection.web.rest.errors.BadRequestAlertException;
import com.hntyhb.detection.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Params.
 */
@RestController
@RequestMapping("/api")
public class ParamsResource {

    private final Logger log = LoggerFactory.getLogger(ParamsResource.class);

    private static final String ENTITY_NAME = "params";

    private final ParamsRepository paramsRepository;

    public ParamsResource(ParamsRepository paramsRepository) {
        this.paramsRepository = paramsRepository;
    }

    /**
     * POST  /params : Create a new params.
     *
     * @param params the params to create
     * @return the ResponseEntity with status 201 (Created) and with body the new params, or with status 400 (Bad Request) if the params has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/params")
    @Timed
    public ResponseEntity<Params> createParams(@Valid @RequestBody Params params) throws URISyntaxException {
        log.debug("REST request to save Params : {}", params);
        if (params.getId() != null) {
            throw new BadRequestAlertException("A new params cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Params result = paramsRepository.save(params);
        return ResponseEntity.created(new URI("/api/params/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /params : Updates an existing params.
     *
     * @param params the params to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated params,
     * or with status 400 (Bad Request) if the params is not valid,
     * or with status 500 (Internal Server Error) if the params couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/params")
    @Timed
    public ResponseEntity<Params> updateParams(@Valid @RequestBody Params params) throws URISyntaxException {
        log.debug("REST request to update Params : {}", params);
        if (params.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Params result = paramsRepository.save(params);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, params.getId().toString()))
            .body(result);
    }

    /**
     * GET  /params : get all the params.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of params in body
     */
    @GetMapping("/params")
    @Timed
    public List<Params> getAllParams() {
        log.debug("REST request to get all Params");
        return paramsRepository.findAll();
    }

    /**
     * GET  /params/:id : get the "id" params.
     *
     * @param id the id of the params to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the params, or with status 404 (Not Found)
     */
    @GetMapping("/params/{id}")
    @Timed
    public ResponseEntity<Params> getParams(@PathVariable Long id) {
        log.debug("REST request to get Params : {}", id);
        Optional<Params> params = paramsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(params);
    }

    /**
     * DELETE  /params/:id : delete the "id" params.
     *
     * @param id the id of the params to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/params/{id}")
    @Timed
    public ResponseEntity<Void> deleteParams(@PathVariable Long id) {
        log.debug("REST request to delete Params : {}", id);

        paramsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
