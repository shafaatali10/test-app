package com.shafaat.test.web.rest;

import com.shafaat.test.domain.HddlFile;
import com.shafaat.test.repository.HddlFileRepository;
import com.shafaat.test.service.HddlFileService;
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
 * REST controller for managing {@link com.shafaat.test.domain.HddlFile}.
 */
@RestController
@RequestMapping("/api")
public class HddlFileResource {

    private final Logger log = LoggerFactory.getLogger(HddlFileResource.class);

    private static final String ENTITY_NAME = "hddlFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HddlFileService hddlFileService;

    private final HddlFileRepository hddlFileRepository;

    public HddlFileResource(HddlFileService hddlFileService, HddlFileRepository hddlFileRepository) {
        this.hddlFileService = hddlFileService;
        this.hddlFileRepository = hddlFileRepository;
    }

    /**
     * {@code POST  /hddl-files} : Create a new hddlFile.
     *
     * @param hddlFile the hddlFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hddlFile, or with status {@code 400 (Bad Request)} if the hddlFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hddl-files")
    public ResponseEntity<HddlFile> createHddlFile(@RequestBody HddlFile hddlFile) throws URISyntaxException {
        log.debug("REST request to save HddlFile : {}", hddlFile);
        if (hddlFile.getId() != null) {
            throw new BadRequestAlertException("A new hddlFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HddlFile result = hddlFileService.save(hddlFile);
        return ResponseEntity
            .created(new URI("/api/hddl-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hddl-files/:id} : Updates an existing hddlFile.
     *
     * @param id the id of the hddlFile to save.
     * @param hddlFile the hddlFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hddlFile,
     * or with status {@code 400 (Bad Request)} if the hddlFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hddlFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hddl-files/{id}")
    public ResponseEntity<HddlFile> updateHddlFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HddlFile hddlFile
    ) throws URISyntaxException {
        log.debug("REST request to update HddlFile : {}, {}", id, hddlFile);
        if (hddlFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hddlFile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hddlFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HddlFile result = hddlFileService.update(hddlFile);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hddlFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hddl-files/:id} : Partial updates given fields of an existing hddlFile, field will ignore if it is null
     *
     * @param id the id of the hddlFile to save.
     * @param hddlFile the hddlFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hddlFile,
     * or with status {@code 400 (Bad Request)} if the hddlFile is not valid,
     * or with status {@code 404 (Not Found)} if the hddlFile is not found,
     * or with status {@code 500 (Internal Server Error)} if the hddlFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hddl-files/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HddlFile> partialUpdateHddlFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HddlFile hddlFile
    ) throws URISyntaxException {
        log.debug("REST request to partial update HddlFile partially : {}, {}", id, hddlFile);
        if (hddlFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hddlFile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hddlFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HddlFile> result = hddlFileService.partialUpdate(hddlFile);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hddlFile.getId().toString())
        );
    }

    /**
     * {@code GET  /hddl-files} : get all the hddlFiles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hddlFiles in body.
     */
    @GetMapping("/hddl-files")
    public List<HddlFile> getAllHddlFiles() {
        log.debug("REST request to get all HddlFiles");
        return hddlFileService.findAll();
    }

    /**
     * {@code GET  /hddl-files/:id} : get the "id" hddlFile.
     *
     * @param id the id of the hddlFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hddlFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hddl-files/{id}")
    public ResponseEntity<HddlFile> getHddlFile(@PathVariable Long id) {
        log.debug("REST request to get HddlFile : {}", id);
        Optional<HddlFile> hddlFile = hddlFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hddlFile);
    }

    /**
     * {@code DELETE  /hddl-files/:id} : delete the "id" hddlFile.
     *
     * @param id the id of the hddlFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hddl-files/{id}")
    public ResponseEntity<Void> deleteHddlFile(@PathVariable Long id) {
        log.debug("REST request to delete HddlFile : {}", id);
        hddlFileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
