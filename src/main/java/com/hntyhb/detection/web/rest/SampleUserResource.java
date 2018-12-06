package com.hntyhb.detection.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hntyhb.detection.domain.SampleUser;
import com.hntyhb.detection.repository.SampleUserRepository;
import com.hntyhb.detection.web.rest.errors.BadRequestAlertException;
import com.hntyhb.detection.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SampleUser.
 */
@RestController
@RequestMapping("/api")
public class SampleUserResource {

    private final Logger log = LoggerFactory.getLogger(SampleUserResource.class);

    private static final String ENTITY_NAME = "sampleUser";

    private final SampleUserRepository sampleUserRepository;

    public SampleUserResource(SampleUserRepository sampleUserRepository) {
        this.sampleUserRepository = sampleUserRepository;
    }

    /**
     * POST  /sample-users : Create a new sampleUser.
     *
     * @param sampleUser the sampleUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sampleUser, or with status 400 (Bad Request) if the sampleUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sample-users")
    @Timed
    public ResponseEntity<SampleUser> createSampleUser(@RequestBody SampleUser sampleUser) throws URISyntaxException {
        log.debug("REST request to save SampleUser : {}", sampleUser);
        if (sampleUser.getId() != null) {
            throw new BadRequestAlertException("A new sampleUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SampleUser result = sampleUserRepository.save(sampleUser);
        return ResponseEntity.created(new URI("/api/sample-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sample-users : Updates an existing sampleUser.
     *
     * @param sampleUser the sampleUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sampleUser,
     * or with status 400 (Bad Request) if the sampleUser is not valid,
     * or with status 500 (Internal Server Error) if the sampleUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sample-users")
    @Timed
    public ResponseEntity<SampleUser> updateSampleUser(@RequestBody SampleUser sampleUser) throws URISyntaxException {
        log.debug("REST request to update SampleUser : {}", sampleUser);
        if (sampleUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SampleUser result = sampleUserRepository.save(sampleUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sampleUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sample-users : get all the sampleUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sampleUsers in body
     */
    @GetMapping("/sample-users")
    @Timed
    public List<SampleUser> getAllSampleUsers() {
        log.debug("REST request to get all SampleUsers");
        return sampleUserRepository.findAll();
    }

    /**
     * GET  /sample-users/:id : get the "id" sampleUser.
     *
     * @param id the id of the sampleUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sampleUser, or with status 404 (Not Found)
     */
    @GetMapping("/sample-users/{id}")
    @Timed
    public ResponseEntity<SampleUser> getSampleUser(@PathVariable Long id) {
        log.debug("REST request to get SampleUser : {}", id);
        Optional<SampleUser> sampleUser = sampleUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sampleUser);
    }

    /**
     * DELETE  /sample-users/:id : delete the "id" sampleUser.
     *
     * @param id the id of the sampleUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sample-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteSampleUser(@PathVariable Long id) {
        log.debug("REST request to delete SampleUser : {}", id);

        sampleUserRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
