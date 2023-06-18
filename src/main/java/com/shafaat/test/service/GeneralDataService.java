package com.shafaat.test.service;

import com.shafaat.test.domain.GeneralData;
import com.shafaat.test.repository.GeneralDataRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GeneralData}.
 */
@Service
@Transactional
public class GeneralDataService {

    private final Logger log = LoggerFactory.getLogger(GeneralDataService.class);

    private final GeneralDataRepository generalDataRepository;

    public GeneralDataService(GeneralDataRepository generalDataRepository) {
        this.generalDataRepository = generalDataRepository;
    }

    /**
     * Save a generalData.
     *
     * @param generalData the entity to save.
     * @return the persisted entity.
     */
    public GeneralData save(GeneralData generalData) {
        log.debug("Request to save GeneralData : {}", generalData);
        return generalDataRepository.save(generalData);
    }

    /**
     * Update a generalData.
     *
     * @param generalData the entity to save.
     * @return the persisted entity.
     */
    public GeneralData update(GeneralData generalData) {
        log.debug("Request to update GeneralData : {}", generalData);
        return generalDataRepository.save(generalData);
    }

    /**
     * Partially update a generalData.
     *
     * @param generalData the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GeneralData> partialUpdate(GeneralData generalData) {
        log.debug("Request to partially update GeneralData : {}", generalData);

        return generalDataRepository
            .findById(generalData.getId())
            .map(existingGeneralData -> {
                if (generalData.getTableUsage() != null) {
                    existingGeneralData.setTableUsage(generalData.getTableUsage());
                }
                if (generalData.getDbSelection() != null) {
                    existingGeneralData.setDbSelection(generalData.getDbSelection());
                }
                if (generalData.getTableName() != null) {
                    existingGeneralData.setTableName(generalData.getTableName());
                }
                if (generalData.getHasDataMoreThan5Million() != null) {
                    existingGeneralData.setHasDataMoreThan5Million(generalData.getHasDataMoreThan5Million());
                }
                if (generalData.getIsParallelizationReqd() != null) {
                    existingGeneralData.setIsParallelizationReqd(generalData.getIsParallelizationReqd());
                }
                if (generalData.getRecoveryClass() != null) {
                    existingGeneralData.setRecoveryClass(generalData.getRecoveryClass());
                }
                if (generalData.getOrderId() != null) {
                    existingGeneralData.setOrderId(generalData.getOrderId());
                }

                return existingGeneralData;
            })
            .map(generalDataRepository::save);
    }

    /**
     * Get all the generalData.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GeneralData> findAll() {
        log.debug("Request to get all GeneralData");
        return generalDataRepository.findAll();
    }

    /**
     * Get one generalData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GeneralData> findOne(Long id) {
        log.debug("Request to get GeneralData : {}", id);
        return generalDataRepository.findById(id);
    }

    /**
     * Delete the generalData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GeneralData : {}", id);
        generalDataRepository.deleteById(id);
    }
}
