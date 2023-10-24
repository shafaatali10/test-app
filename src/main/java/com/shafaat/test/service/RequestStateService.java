package com.shafaat.test.service;

import com.shafaat.test.domain.RequestState;
import com.shafaat.test.repository.RequestStateRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RequestState}.
 */
@Service
@Transactional
public class RequestStateService {

    private final Logger log = LoggerFactory.getLogger(RequestStateService.class);

    private final RequestStateRepository requestStateRepository;

    public RequestStateService(RequestStateRepository requestStateRepository) {
        this.requestStateRepository = requestStateRepository;
    }

    /**
     * Save a requestState.
     *
     * @param requestState the entity to save.
     * @return the persisted entity.
     */
    public RequestState save(RequestState requestState) {
        log.debug("Request to save RequestState : {}", requestState);
        return requestStateRepository.save(requestState);
    }

    /**
     * Update a requestState.
     *
     * @param requestState the entity to save.
     * @return the persisted entity.
     */
    public RequestState update(RequestState requestState) {
        log.debug("Request to update RequestState : {}", requestState);
        return requestStateRepository.save(requestState);
    }

    /**
     * Partially update a requestState.
     *
     * @param requestState the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RequestState> partialUpdate(RequestState requestState) {
        log.debug("Request to partially update RequestState : {}", requestState);

        return requestStateRepository
            .findById(requestState.getId())
            .map(existingRequestState -> {
                if (requestState.getRequestId() != null) {
                    existingRequestState.setRequestId(requestState.getRequestId());
                }
                if (requestState.getNotes() != null) {
                    existingRequestState.setNotes(requestState.getNotes());
                }
                if (requestState.getStatus() != null) {
                    existingRequestState.setStatus(requestState.getStatus());
                }
                if (requestState.getDueDate() != null) {
                    existingRequestState.setDueDate(requestState.getDueDate());
                }

                return existingRequestState;
            })
            .map(requestStateRepository::save);
    }

    /**
     * Get all the requestStates.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RequestState> findAll() {
        log.debug("Request to get all RequestStates");
        return requestStateRepository.findAll();
    }

    /**
     * Get one requestState by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RequestState> findOne(Long id) {
        log.debug("Request to get RequestState : {}", id);
        return requestStateRepository.findById(id);
    }

    /**
     * Delete the requestState by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RequestState : {}", id);
        requestStateRepository.deleteById(id);
    }
}
