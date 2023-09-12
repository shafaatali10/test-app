package com.shafaat.test.web.rest;

import com.shafaat.test.domain.RequestState;
import com.shafaat.test.service.RequestStateService;
import com.shafaat.test.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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

    public RequestStateResource(RequestStateService requestStateService) {
        this.requestStateService = requestStateService;
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
        return ResponseEntity.created(new URI("/api/request-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-states} : Updates an existing requestState.
     *
     * @param requestState the requestState to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestState,
     * or with status {@code 400 (Bad Request)} if the requestState is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestState couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-states")
    public ResponseEntity<RequestState> updateRequestState(@RequestBody RequestState requestState) throws URISyntaxException {
        log.debug("REST request to update RequestState : {}", requestState);
        if (requestState.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequestState result = requestStateService.save(requestState);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestState.getId().toString()))
            .body(result);
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
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
