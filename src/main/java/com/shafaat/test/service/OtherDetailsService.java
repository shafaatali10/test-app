package com.shafaat.test.service;

import com.shafaat.test.domain.OtherDetails;
import com.shafaat.test.repository.OtherDetailsRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OtherDetails}.
 */
@Service
@Transactional
public class OtherDetailsService {

    private final Logger log = LoggerFactory.getLogger(OtherDetailsService.class);

    private final OtherDetailsRepository otherDetailsRepository;

    public OtherDetailsService(OtherDetailsRepository otherDetailsRepository) {
        this.otherDetailsRepository = otherDetailsRepository;
    }

    /**
     * Save a otherDetails.
     *
     * @param otherDetails the entity to save.
     * @return the persisted entity.
     */
    public OtherDetails save(OtherDetails otherDetails) {
        log.debug("Request to save OtherDetails : {}", otherDetails);
        return otherDetailsRepository.save(otherDetails);
    }

    /**
     * Update a otherDetails.
     *
     * @param otherDetails the entity to save.
     * @return the persisted entity.
     */
    public OtherDetails update(OtherDetails otherDetails) {
        log.debug("Request to update OtherDetails : {}", otherDetails);
        return otherDetailsRepository.save(otherDetails);
    }

    /**
     * Partially update a otherDetails.
     *
     * @param otherDetails the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OtherDetails> partialUpdate(OtherDetails otherDetails) {
        log.debug("Request to partially update OtherDetails : {}", otherDetails);

        return otherDetailsRepository
            .findById(otherDetails.getId())
            .map(existingOtherDetails -> {
                if (otherDetails.getMandatorColumn() != null) {
                    existingOtherDetails.setMandatorColumn(otherDetails.getMandatorColumn());
                }
                if (otherDetails.getIsHubUsageReqd() != null) {
                    existingOtherDetails.setIsHubUsageReqd(otherDetails.getIsHubUsageReqd());
                }
                if (otherDetails.getInsertChars() != null) {
                    existingOtherDetails.setInsertChars(otherDetails.getInsertChars());
                }
                if (otherDetails.getTableAccessMethod() != null) {
                    existingOtherDetails.setTableAccessMethod(otherDetails.getTableAccessMethod());
                }
                if (otherDetails.getOneWmpView() != null) {
                    existingOtherDetails.setOneWmpView(otherDetails.getOneWmpView());
                }
                if (otherDetails.getOrderId() != null) {
                    existingOtherDetails.setOrderId(otherDetails.getOrderId());
                }

                return existingOtherDetails;
            })
            .map(otherDetailsRepository::save);
    }

    /**
     * Get all the otherDetails.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OtherDetails> findAll() {
        log.debug("Request to get all OtherDetails");
        return otherDetailsRepository.findAll();
    }

    /**
     * Get one otherDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OtherDetails> findOne(Long id) {
        log.debug("Request to get OtherDetails : {}", id);
        return otherDetailsRepository.findById(id);
    }

    /**
     * Delete the otherDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OtherDetails : {}", id);
        otherDetailsRepository.deleteById(id);
    }
}
