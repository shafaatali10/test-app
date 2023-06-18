package com.shafaat.test.web.rest;

import com.shafaat.test.domain.MetaData;
import com.shafaat.test.repository.MetaDataRepository;
import com.shafaat.test.service.MetaDataService;
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
 * REST controller for managing {@link com.shafaat.test.domain.MetaData}.
 */
@RestController
@RequestMapping("/api")
public class MetaDataResource {

    private final Logger log = LoggerFactory.getLogger(MetaDataResource.class);

    private static final String ENTITY_NAME = "metaData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MetaDataService metaDataService;

    private final MetaDataRepository metaDataRepository;

    public MetaDataResource(MetaDataService metaDataService, MetaDataRepository metaDataRepository) {
        this.metaDataService = metaDataService;
        this.metaDataRepository = metaDataRepository;
    }

    /**
     * {@code POST  /meta-data} : Create a new metaData.
     *
     * @param metaData the metaData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metaData, or with status {@code 400 (Bad Request)} if the metaData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meta-data")
    public ResponseEntity<MetaData> createMetaData(@RequestBody MetaData metaData) throws URISyntaxException {
        log.debug("REST request to save MetaData : {}", metaData);
        if (metaData.getId() != null) {
            throw new BadRequestAlertException("A new metaData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MetaData result = metaDataService.save(metaData);
        return ResponseEntity
            .created(new URI("/api/meta-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meta-data/:id} : Updates an existing metaData.
     *
     * @param id the id of the metaData to save.
     * @param metaData the metaData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaData,
     * or with status {@code 400 (Bad Request)} if the metaData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metaData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meta-data/{id}")
    public ResponseEntity<MetaData> updateMetaData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MetaData metaData
    ) throws URISyntaxException {
        log.debug("REST request to update MetaData : {}, {}", id, metaData);
        if (metaData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metaDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MetaData result = metaDataService.update(metaData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metaData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /meta-data/:id} : Partial updates given fields of an existing metaData, field will ignore if it is null
     *
     * @param id the id of the metaData to save.
     * @param metaData the metaData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaData,
     * or with status {@code 400 (Bad Request)} if the metaData is not valid,
     * or with status {@code 404 (Not Found)} if the metaData is not found,
     * or with status {@code 500 (Internal Server Error)} if the metaData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/meta-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MetaData> partialUpdateMetaData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MetaData metaData
    ) throws URISyntaxException {
        log.debug("REST request to partial update MetaData partially : {}, {}", id, metaData);
        if (metaData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metaDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MetaData> result = metaDataService.partialUpdate(metaData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metaData.getId().toString())
        );
    }

    /**
     * {@code GET  /meta-data} : get all the metaData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metaData in body.
     */
    @GetMapping("/meta-data")
    public List<MetaData> getAllMetaData() {
        log.debug("REST request to get all MetaData");
        return metaDataService.findAll();
    }

    /**
     * {@code GET  /meta-data/:id} : get the "id" metaData.
     *
     * @param id the id of the metaData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metaData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meta-data/{id}")
    public ResponseEntity<MetaData> getMetaData(@PathVariable Long id) {
        log.debug("REST request to get MetaData : {}", id);
        Optional<MetaData> metaData = metaDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metaData);
    }

    /**
     * {@code DELETE  /meta-data/:id} : delete the "id" metaData.
     *
     * @param id the id of the metaData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meta-data/{id}")
    public ResponseEntity<Void> deleteMetaData(@PathVariable Long id) {
        log.debug("REST request to delete MetaData : {}", id);
        metaDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
