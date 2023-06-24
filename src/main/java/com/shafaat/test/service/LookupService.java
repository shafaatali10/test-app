package com.shafaat.test.service;

import com.shafaat.test.domain.Lookup;
import com.shafaat.test.repository.LookupRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Lookup}.
 */
@Service
@Transactional
public class LookupService {

    private final Logger log = LoggerFactory.getLogger(LookupService.class);

    private final LookupRepository lookupRepository;

    public LookupService(LookupRepository lookupRepository) {
        this.lookupRepository = lookupRepository;
    }

    /**
     * Save a lookup.
     *
     * @param lookup the entity to save.
     * @return the persisted entity.
     */
    public Lookup save(Lookup lookup) {
        log.debug("Request to save Lookup : {}", lookup);
        return lookupRepository.save(lookup);
    }

    /**
     * Update a lookup.
     *
     * @param lookup the entity to save.
     * @return the persisted entity.
     */
    public Lookup update(Lookup lookup) {
        log.debug("Request to update Lookup : {}", lookup);
        return lookupRepository.save(lookup);
    }

    /**
     * Partially update a lookup.
     *
     * @param lookup the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Lookup> partialUpdate(Lookup lookup) {
        log.debug("Request to partially update Lookup : {}", lookup);

        return lookupRepository
            .findById(lookup.getId())
            .map(existingLookup -> {
                if (lookup.getLookupCode() != null) {
                    existingLookup.setLookupCode(lookup.getLookupCode());
                }
                if (lookup.getLookupValue() != null) {
                    existingLookup.setLookupValue(lookup.getLookupValue());
                }
                if (lookup.getDescription() != null) {
                    existingLookup.setDescription(lookup.getDescription());
                }
                if (lookup.getDisplaySequence() != null) {
                    existingLookup.setDisplaySequence(lookup.getDisplaySequence());
                }
                if (lookup.getViewName() != null) {
                    existingLookup.setViewName(lookup.getViewName());
                }
                if (lookup.getCategory() != null) {
                    existingLookup.setCategory(lookup.getCategory());
                }
                if (lookup.getDependentCode() != null) {
                    existingLookup.setDependentCode(lookup.getDependentCode());
                }
                if (lookup.getIsActive() != null) {
                    existingLookup.setIsActive(lookup.getIsActive());
                }

                return existingLookup;
            })
            .map(lookupRepository::save);
    }

    /**
     * Get all the lookups.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Lookup> findAll() {
        log.debug("Request to get all Lookups");
        return lookupRepository.findAll();
    }

    /**
     * Get one lookup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Lookup> findOne(Long id) {
        log.debug("Request to get Lookup : {}", id);
        return lookupRepository.findById(id);
    }

    /**
     * Delete the lookup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Lookup : {}", id);
        lookupRepository.deleteById(id);
    }
}
