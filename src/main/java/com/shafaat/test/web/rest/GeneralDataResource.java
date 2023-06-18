package com.shafaat.test.web.rest;

import com.shafaat.test.domain.GeneralData;
import com.shafaat.test.repository.GeneralDataRepository;
import com.shafaat.test.service.GeneralDataService;
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
 * REST controller for managing {@link com.shafaat.test.domain.GeneralData}.
 */
@RestController
@RequestMapping("/api")
public class GeneralDataResource {

    private final Logger log = LoggerFactory.getLogger(GeneralDataResource.class);

    private static final String ENTITY_NAME = "generalData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeneralDataService generalDataService;

    private final GeneralDataRepository generalDataRepository;

    public GeneralDataResource(GeneralDataService generalDataService, GeneralDataRepository generalDataRepository) {
        this.generalDataService = generalDataService;
        this.generalDataRepository = generalDataRepository;
    }

    /**
     * {@code POST  /general-data} : Create a new generalData.
     *
     * @param generalData the generalData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new generalData, or with status {@code 400 (Bad Request)} if the generalData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/general-data")
    public ResponseEntity<GeneralData> createGeneralData(@RequestBody GeneralData generalData) throws URISyntaxException {
        log.debug("REST request to save GeneralData : {}", generalData);
        if (generalData.getId() != null) {
            throw new BadRequestAlertException("A new generalData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeneralData result = generalDataService.save(generalData);
        return ResponseEntity
            .created(new URI("/api/general-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /general-data/:id} : Updates an existing generalData.
     *
     * @param id the id of the generalData to save.
     * @param generalData the generalData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated generalData,
     * or with status {@code 400 (Bad Request)} if the generalData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the generalData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/general-data/{id}")
    public ResponseEntity<GeneralData> updateGeneralData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GeneralData generalData
    ) throws URISyntaxException {
        log.debug("REST request to update GeneralData : {}, {}", id, generalData);
        if (generalData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, generalData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!generalDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GeneralData result = generalDataService.update(generalData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, generalData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /general-data/:id} : Partial updates given fields of an existing generalData, field will ignore if it is null
     *
     * @param id the id of the generalData to save.
     * @param generalData the generalData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated generalData,
     * or with status {@code 400 (Bad Request)} if the generalData is not valid,
     * or with status {@code 404 (Not Found)} if the generalData is not found,
     * or with status {@code 500 (Internal Server Error)} if the generalData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/general-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GeneralData> partialUpdateGeneralData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GeneralData generalData
    ) throws URISyntaxException {
        log.debug("REST request to partial update GeneralData partially : {}, {}", id, generalData);
        if (generalData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, generalData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!generalDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GeneralData> result = generalDataService.partialUpdate(generalData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, generalData.getId().toString())
        );
    }

    /**
     * {@code GET  /general-data} : get all the generalData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of generalData in body.
     */
    @GetMapping("/general-data")
    public List<GeneralData> getAllGeneralData() {
        log.debug("REST request to get all GeneralData");
        return generalDataService.findAll();
    }

    /**
     * {@code GET  /general-data/:id} : get the "id" generalData.
     *
     * @param id the id of the generalData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the generalData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/general-data/{id}")
    public ResponseEntity<GeneralData> getGeneralData(@PathVariable Long id) {
        log.debug("REST request to get GeneralData : {}", id);
        Optional<GeneralData> generalData = generalDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(generalData);
    }

    /**
     * {@code DELETE  /general-data/:id} : delete the "id" generalData.
     *
     * @param id the id of the generalData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/general-data/{id}")
    public ResponseEntity<Void> deleteGeneralData(@PathVariable Long id) {
        log.debug("REST request to delete GeneralData : {}", id);
        generalDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
