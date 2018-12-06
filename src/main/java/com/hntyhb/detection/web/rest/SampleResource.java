package com.hntyhb.detection.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hntyhb.detection.domain.Sample;
import com.hntyhb.detection.repository.SampleRepository;
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
 * REST controller for managing Sample.
 */
@RestController
@RequestMapping("/api")
public class SampleResource {

    private final Logger log = LoggerFactory.getLogger(SampleResource.class);

    private static final String ENTITY_NAME = "sample";

    private final SampleRepository sampleRepository;

    public SampleResource(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    /**
     * POST  /samples : Create a new sample.
     *
     * @param sample the sample to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sample, or with status 400 (Bad Request) if the sample has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/samples")
    @Timed
    public ResponseEntity<Sample> createSample(@Valid @RequestBody Sample sample) throws URISyntaxException {
        log.debug("REST request to save Sample : {}", sample);
        if (sample.getId() != null) {
            throw new BadRequestAlertException("A new sample cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sample result = sampleRepository.save(sample);
        return ResponseEntity.created(new URI("/api/samples/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /samples : Updates an existing sample.
     *
     * @param sample the sample to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sample,
     * or with status 400 (Bad Request) if the sample is not valid,
     * or with status 500 (Internal Server Error) if the sample couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/samples")
    @Timed
    public ResponseEntity<Sample> updateSample(@Valid @RequestBody Sample sample) throws URISyntaxException {
        log.debug("REST request to update Sample : {}", sample);
        if (sample.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sample result = sampleRepository.save(sample);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sample.getId().toString()))
            .body(result);
    }

    /**
     * GET  /samples : get all the samples.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of samples in body
     */
    @GetMapping("/samples")
    @Timed
    public List<Sample> getAllSamples() {
        log.debug("REST request to get all Samples");
        return sampleRepository.findAll();
    }

    /**
     * GET  /samples/:id : get the "id" sample.
     *
     * @param id the id of the sample to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sample, or with status 404 (Not Found)
     */
    @GetMapping("/samples/{id}")
    @Timed
    public ResponseEntity<Sample> getSample(@PathVariable Long id) {
        log.debug("REST request to get Sample : {}", id);
        Optional<Sample> sample = sampleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sample);
    }

    /**
     * DELETE  /samples/:id : delete the "id" sample.
     *
     * @param id the id of the sample to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/samples/{id}")
    @Timed
    public ResponseEntity<Void> deleteSample(@PathVariable Long id) {
        log.debug("REST request to delete Sample : {}", id);

        sampleRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
