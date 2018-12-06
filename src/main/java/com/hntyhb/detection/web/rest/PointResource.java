package com.hntyhb.detection.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hntyhb.detection.domain.Point;
import com.hntyhb.detection.repository.PointRepository;
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
 * REST controller for managing Point.
 */
@RestController
@RequestMapping("/api")
public class PointResource {

    private final Logger log = LoggerFactory.getLogger(PointResource.class);

    private static final String ENTITY_NAME = "point";

    private final PointRepository pointRepository;

    public PointResource(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    /**
     * POST  /points : Create a new point.
     *
     * @param point the point to create
     * @return the ResponseEntity with status 201 (Created) and with body the new point, or with status 400 (Bad Request) if the point has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/points")
    @Timed
    public ResponseEntity<Point> createPoint(@Valid @RequestBody Point point) throws URISyntaxException {
        log.debug("REST request to save Point : {}", point);
        if (point.getId() != null) {
            throw new BadRequestAlertException("A new point cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Point result = pointRepository.save(point);
        return ResponseEntity.created(new URI("/api/points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /points : Updates an existing point.
     *
     * @param point the point to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated point,
     * or with status 400 (Bad Request) if the point is not valid,
     * or with status 500 (Internal Server Error) if the point couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/points")
    @Timed
    public ResponseEntity<Point> updatePoint(@Valid @RequestBody Point point) throws URISyntaxException {
        log.debug("REST request to update Point : {}", point);
        if (point.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Point result = pointRepository.save(point);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, point.getId().toString()))
            .body(result);
    }

    /**
     * GET  /points : get all the points.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of points in body
     */
    @GetMapping("/points")
    @Timed
    public List<Point> getAllPoints() {
        log.debug("REST request to get all Points");
        return pointRepository.findAll();
    }

    /**
     * GET  /points/:id : get the "id" point.
     *
     * @param id the id of the point to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the point, or with status 404 (Not Found)
     */
    @GetMapping("/points/{id}")
    @Timed
    public ResponseEntity<Point> getPoint(@PathVariable Long id) {
        log.debug("REST request to get Point : {}", id);
        Optional<Point> point = pointRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(point);
    }

    /**
     * DELETE  /points/:id : delete the "id" point.
     *
     * @param id the id of the point to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/points/{id}")
    @Timed
    public ResponseEntity<Void> deletePoint(@PathVariable Long id) {
        log.debug("REST request to delete Point : {}", id);

        pointRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
