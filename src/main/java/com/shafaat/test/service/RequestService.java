package com.shafaat.test.service;

import com.shafaat.test.domain.Request;
import com.shafaat.test.repository.RequestRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Request}.
 */
@Service
@Transactional
public class RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestService.class);

    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    /**
     * Save a request.
     *
     * @param request the entity to save.
     * @return the persisted entity.
     */
    public Request save(Request request) {
        log.debug("Request to save Request : {}", request);
        return requestRepository.save(request);
    }

    /**
     * Update a request.
     *
     * @param request the entity to save.
     * @return the persisted entity.
     */
    public Request update(Request request) {
        log.debug("Request to update Request : {}", request);
        return requestRepository.save(request);
    }

    /**
     * Partially update a request.
     *
     * @param request the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Request> partialUpdate(Request request) {
        log.debug("Request to partially update Request : {}", request);

        return requestRepository
            .findById(request.getId())
            .map(existingRequest -> {
                if (request.getRfc() != null) {
                    existingRequest.setRfc(request.getRfc());
                }
                if (request.getSwid() != null) {
                    existingRequest.setSwid(request.getSwid());
                }
                if (request.getStatus() != null) {
                    existingRequest.setStatus(request.getStatus());
                }
                if (request.getIsDraft() != null) {
                    existingRequest.setIsDraft(request.getIsDraft());
                }
                if (request.getRequestLink() != null) {
                    existingRequest.setRequestLink(request.getRequestLink());
                }

                return existingRequest;
            })
            .map(requestRepository::save);
    }

    /**
     * Get all the requests.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Request> findAll() {
        log.debug("Request to get all Requests");
        return requestRepository.findAll();
    }

    /**
     * Get one request by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Request> findOne(Long id) {
        log.debug("Request to get Request : {}", id);
        return requestRepository.findById(id);
    }

    /**
     * Delete the request by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.deleteById(id);
    }
}
