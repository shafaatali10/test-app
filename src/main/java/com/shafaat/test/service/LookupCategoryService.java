package com.shafaat.test.service;

import com.shafaat.test.domain.LookupCategory;
import com.shafaat.test.repository.LookupCategoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LookupCategory}.
 */
@Service
@Transactional
public class LookupCategoryService {

    private final Logger log = LoggerFactory.getLogger(LookupCategoryService.class);

    private final LookupCategoryRepository lookupCategoryRepository;

    public LookupCategoryService(LookupCategoryRepository lookupCategoryRepository) {
        this.lookupCategoryRepository = lookupCategoryRepository;
    }

    /**
     * Save a lookupCategory.
     *
     * @param lookupCategory the entity to save.
     * @return the persisted entity.
     */
    public LookupCategory save(LookupCategory lookupCategory) {
        log.debug("Request to save LookupCategory : {}", lookupCategory);
        return lookupCategoryRepository.save(lookupCategory);
    }

    /**
     * Update a lookupCategory.
     *
     * @param lookupCategory the entity to save.
     * @return the persisted entity.
     */
    public LookupCategory update(LookupCategory lookupCategory) {
        log.debug("Request to update LookupCategory : {}", lookupCategory);
        return lookupCategoryRepository.save(lookupCategory);
    }

    /**
     * Partially update a lookupCategory.
     *
     * @param lookupCategory the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LookupCategory> partialUpdate(LookupCategory lookupCategory) {
        log.debug("Request to partially update LookupCategory : {}", lookupCategory);

        return lookupCategoryRepository
            .findById(lookupCategory.getId())
            .map(existingLookupCategory -> {
                if (lookupCategory.getCategoryCode() != null) {
                    existingLookupCategory.setCategoryCode(lookupCategory.getCategoryCode());
                }

                return existingLookupCategory;
            })
            .map(lookupCategoryRepository::save);
    }

    /**
     * Get all the lookupCategories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LookupCategory> findAll() {
        log.debug("Request to get all LookupCategories");
        return lookupCategoryRepository.findAll();
    }

    /**
     * Get one lookupCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LookupCategory> findOne(Long id) {
        log.debug("Request to get LookupCategory : {}", id);
        return lookupCategoryRepository.findById(id);
    }

    /**
     * Delete the lookupCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LookupCategory : {}", id);
        lookupCategoryRepository.deleteById(id);
    }
}
