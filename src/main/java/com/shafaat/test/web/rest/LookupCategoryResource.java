package com.shafaat.test.web.rest;

import com.shafaat.test.domain.LookupCategory;
import com.shafaat.test.repository.LookupCategoryRepository;
import com.shafaat.test.service.LookupCategoryService;
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
 * REST controller for managing {@link com.shafaat.test.domain.LookupCategory}.
 */
@RestController
@RequestMapping("/api")
public class LookupCategoryResource {

    private final Logger log = LoggerFactory.getLogger(LookupCategoryResource.class);

    private static final String ENTITY_NAME = "lookupCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LookupCategoryService lookupCategoryService;

    private final LookupCategoryRepository lookupCategoryRepository;

    public LookupCategoryResource(LookupCategoryService lookupCategoryService, LookupCategoryRepository lookupCategoryRepository) {
        this.lookupCategoryService = lookupCategoryService;
        this.lookupCategoryRepository = lookupCategoryRepository;
    }

    /**
     * {@code POST  /lookup-categories} : Create a new lookupCategory.
     *
     * @param lookupCategory the lookupCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lookupCategory, or with status {@code 400 (Bad Request)} if the lookupCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lookup-categories")
    public ResponseEntity<LookupCategory> createLookupCategory(@RequestBody LookupCategory lookupCategory) throws URISyntaxException {
        log.debug("REST request to save LookupCategory : {}", lookupCategory);
        if (lookupCategory.getId() != null) {
            throw new BadRequestAlertException("A new lookupCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LookupCategory result = lookupCategoryService.save(lookupCategory);
        return ResponseEntity
            .created(new URI("/api/lookup-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lookup-categories/:id} : Updates an existing lookupCategory.
     *
     * @param id the id of the lookupCategory to save.
     * @param lookupCategory the lookupCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lookupCategory,
     * or with status {@code 400 (Bad Request)} if the lookupCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lookupCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lookup-categories/{id}")
    public ResponseEntity<LookupCategory> updateLookupCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LookupCategory lookupCategory
    ) throws URISyntaxException {
        log.debug("REST request to update LookupCategory : {}, {}", id, lookupCategory);
        if (lookupCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lookupCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lookupCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LookupCategory result = lookupCategoryService.update(lookupCategory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lookupCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lookup-categories/:id} : Partial updates given fields of an existing lookupCategory, field will ignore if it is null
     *
     * @param id the id of the lookupCategory to save.
     * @param lookupCategory the lookupCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lookupCategory,
     * or with status {@code 400 (Bad Request)} if the lookupCategory is not valid,
     * or with status {@code 404 (Not Found)} if the lookupCategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the lookupCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lookup-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LookupCategory> partialUpdateLookupCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LookupCategory lookupCategory
    ) throws URISyntaxException {
        log.debug("REST request to partial update LookupCategory partially : {}, {}", id, lookupCategory);
        if (lookupCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lookupCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lookupCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LookupCategory> result = lookupCategoryService.partialUpdate(lookupCategory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lookupCategory.getId().toString())
        );
    }

    /**
     * {@code GET  /lookup-categories} : get all the lookupCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lookupCategories in body.
     */
    @GetMapping("/lookup-categories")
    public List<LookupCategory> getAllLookupCategories() {
        log.debug("REST request to get all LookupCategories");
        return lookupCategoryService.findAll();
    }

    /**
     * {@code GET  /lookup-categories/:id} : get the "id" lookupCategory.
     *
     * @param id the id of the lookupCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lookupCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lookup-categories/{id}")
    public ResponseEntity<LookupCategory> getLookupCategory(@PathVariable Long id) {
        log.debug("REST request to get LookupCategory : {}", id);
        Optional<LookupCategory> lookupCategory = lookupCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lookupCategory);
    }

    /**
     * {@code DELETE  /lookup-categories/:id} : delete the "id" lookupCategory.
     *
     * @param id the id of the lookupCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lookup-categories/{id}")
    public ResponseEntity<Void> deleteLookupCategory(@PathVariable Long id) {
        log.debug("REST request to delete LookupCategory : {}", id);
        lookupCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
