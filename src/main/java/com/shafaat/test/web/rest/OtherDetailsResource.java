package com.shafaat.test.web.rest;

import com.shafaat.test.domain.OtherDetails;
import com.shafaat.test.repository.OtherDetailsRepository;
import com.shafaat.test.service.OtherDetailsService;
import com.shafaat.test.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.shafaat.test.domain.OtherDetails}.
 */
@RestController
@RequestMapping("/api")
public class OtherDetailsResource {

    private final Logger log = LoggerFactory.getLogger(OtherDetailsResource.class);

    private static final String ENTITY_NAME = "otherDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OtherDetailsService otherDetailsService;

    private final OtherDetailsRepository otherDetailsRepository;

    public OtherDetailsResource(OtherDetailsService otherDetailsService, OtherDetailsRepository otherDetailsRepository) {
        this.otherDetailsService = otherDetailsService;
        this.otherDetailsRepository = otherDetailsRepository;
    }

    /**
     * {@code POST  /other-details} : Create a new otherDetails.
     *
     * @param otherDetails the otherDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new otherDetails, or with status {@code 400 (Bad Request)} if the otherDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/other-details")
    public ResponseEntity<OtherDetails> createOtherDetails(@RequestBody OtherDetails otherDetails) throws URISyntaxException {
        log.debug("REST request to save OtherDetails : {}", otherDetails);
        if (otherDetails.getId() != null) {
            throw new BadRequestAlertException("A new otherDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OtherDetails result = otherDetailsService.save(otherDetails);
        return ResponseEntity
            .created(new URI("/api/other-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /other-details/:id} : Updates an existing otherDetails.
     *
     * @param id the id of the otherDetails to save.
     * @param otherDetails the otherDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated otherDetails,
     * or with status {@code 400 (Bad Request)} if the otherDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the otherDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/other-details/{id}")
    public ResponseEntity<OtherDetails> updateOtherDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OtherDetails otherDetails
    ) throws URISyntaxException {
        log.debug("REST request to update OtherDetails : {}, {}", id, otherDetails);
        if (otherDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, otherDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!otherDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OtherDetails result = otherDetailsService.update(otherDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, otherDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /other-details/:id} : Partial updates given fields of an existing otherDetails, field will ignore if it is null
     *
     * @param id the id of the otherDetails to save.
     * @param otherDetails the otherDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated otherDetails,
     * or with status {@code 400 (Bad Request)} if the otherDetails is not valid,
     * or with status {@code 404 (Not Found)} if the otherDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the otherDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/other-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OtherDetails> partialUpdateOtherDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OtherDetails otherDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update OtherDetails partially : {}, {}", id, otherDetails);
        if (otherDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, otherDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!otherDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OtherDetails> result = otherDetailsService.partialUpdate(otherDetails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, otherDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /other-details} : get all the otherDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of otherDetails in body.
     */
    @GetMapping("/other-details")
    public List<OtherDetails> getAllOtherDetails() {
        log.debug("REST request to get all OtherDetails");
        return otherDetailsService.findAll();
    }

    /**
     * {@code GET  /other-details/:id} : get the "id" otherDetails.
     *
     * @param id the id of the otherDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the otherDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/other-details/{id}")
    public ResponseEntity<OtherDetails> getOtherDetails(@PathVariable Long id) {
        log.debug("REST request to get OtherDetails : {}", id);
        Optional<OtherDetails> otherDetails = otherDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(otherDetails);
    }

    /**
     * {@code DELETE  /other-details/:id} : delete the "id" otherDetails.
     *
     * @param id the id of the otherDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/other-details/{id}")
    public ResponseEntity<Void> deleteOtherDetails(@PathVariable Long id) {
        log.debug("REST request to delete OtherDetails : {}", id);
        otherDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
