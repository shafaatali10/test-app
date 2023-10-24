package com.shafaat.test.web.rest;

import com.shafaat.test.domain.RequestState;
import com.shafaat.test.repository.RequestStateRepository;
import com.shafaat.test.service.RequestStateService;
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
 * REST controller for managing {@link com.shafaat.test.domain.RequestState}.
 */
@RestController
@RequestMapping("/api")
public class RequestStateResource {

    private final Logger log = LoggerFactory.getLogger(RequestStateResource.class);

    private static final String ENTITY_NAME = "requestState";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestStateService requestStateService;

    private final RequestStateRepository requestStateRepository;

    public RequestStateResource(RequestStateService requestStateService, RequestStateRepository requestStateRepository) {
        this.requestStateService = requestStateService;
        this.requestStateRepository = requestStateRepository;
    }

    /**
     * {@code POST  /request-states} : Create a new requestState.
     *
     * @param requestState the requestState to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestState, or with status {@code 400 (Bad Request)} if the requestState has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-states")
    public ResponseEntity<RequestState> createRequestState(@RequestBody RequestState requestState) throws URISyntaxException {
        log.debug("REST request to save RequestState : {}", requestState);
        if (requestState.getId() != null) {
            throw new BadRequestAlertException("A new requestState cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestState result = requestStateService.save(requestState);
        return ResponseEntity
            .created(new URI("/api/request-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-states/:id} : Updates an existing requestState.
     *
     * @param id the id of the requestState to save.
     * @param requestState the requestState to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestState,
     * or with status {@code 400 (Bad Request)} if the requestState is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestState couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-states/{id}")
    public ResponseEntity<RequestState> updateRequestState(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestState requestState
    ) throws URISyntaxException {
        log.debug("REST request to update RequestState : {}, {}", id, requestState);
        if (requestState.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestState.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestStateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestState result = requestStateService.update(requestState);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestState.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /request-states/:id} : Partial updates given fields of an existing requestState, field will ignore if it is null
     *
     * @param id the id of the requestState to save.
     * @param requestState the requestState to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestState,
     * or with status {@code 400 (Bad Request)} if the requestState is not valid,
     * or with status {@code 404 (Not Found)} if the requestState is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestState couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/request-states/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestState> partialUpdateRequestState(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestState requestState
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestState partially : {}, {}", id, requestState);
        if (requestState.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestState.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestStateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestState> result = requestStateService.partialUpdate(requestState);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestState.getId().toString())
        );
    }

    /**
     * {@code GET  /request-states} : get all the requestStates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestStates in body.
     */
    @GetMapping("/request-states")
    public List<RequestState> getAllRequestStates() {
        log.debug("REST request to get all RequestStates");
        return requestStateService.findAll();
    }

    /**
     * {@code GET  /request-states/:id} : get the "id" requestState.
     *
     * @param id the id of the requestState to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestState, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-states/{id}")
    public ResponseEntity<RequestState> getRequestState(@PathVariable Long id) {
        log.debug("REST request to get RequestState : {}", id);
        Optional<RequestState> requestState = requestStateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestState);
    }

    /**
     * {@code DELETE  /request-states/:id} : delete the "id" requestState.
     *
     * @param id the id of the requestState to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-states/{id}")
    public ResponseEntity<Void> deleteRequestState(@PathVariable Long id) {
        log.debug("REST request to delete RequestState : {}", id);
        requestStateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
