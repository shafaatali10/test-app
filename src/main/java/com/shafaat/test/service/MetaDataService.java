package com.shafaat.test.service;

import com.shafaat.test.domain.MetaData;
import com.shafaat.test.repository.MetaDataRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MetaData}.
 */
@Service
@Transactional
public class MetaDataService {

    private final Logger log = LoggerFactory.getLogger(MetaDataService.class);

    private final MetaDataRepository metaDataRepository;

    public MetaDataService(MetaDataRepository metaDataRepository) {
        this.metaDataRepository = metaDataRepository;
    }

    /**
     * Save a metaData.
     *
     * @param metaData the entity to save.
     * @return the persisted entity.
     */
    public MetaData save(MetaData metaData) {
        log.debug("Request to save MetaData : {}", metaData);
        return metaDataRepository.save(metaData);
    }

    /**
     * Update a metaData.
     *
     * @param metaData the entity to save.
     * @return the persisted entity.
     */
    public MetaData update(MetaData metaData) {
        log.debug("Request to update MetaData : {}", metaData);
        return metaDataRepository.save(metaData);
    }

    /**
     * Partially update a metaData.
     *
     * @param metaData the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MetaData> partialUpdate(MetaData metaData) {
        log.debug("Request to partially update MetaData : {}", metaData);

        return metaDataRepository
            .findById(metaData.getId())
            .map(existingMetaData -> {
                if (metaData.getStidClass() != null) {
                    existingMetaData.setStidClass(metaData.getStidClass());
                }
                if (metaData.getStidColumnName() != null) {
                    existingMetaData.setStidColumnName(metaData.getStidColumnName());
                }
                if (metaData.getDataLevel() != null) {
                    existingMetaData.setDataLevel(metaData.getDataLevel());
                }
                if (metaData.getInitialLoadType() != null) {
                    existingMetaData.setInitialLoadType(metaData.getInitialLoadType());
                }
                if (metaData.getPartitionSchema() != null) {
                    existingMetaData.setPartitionSchema(metaData.getPartitionSchema());
                }
                if (metaData.getOrderId() != null) {
                    existingMetaData.setOrderId(metaData.getOrderId());
                }

                return existingMetaData;
            })
            .map(metaDataRepository::save);
    }

    /**
     * Get all the metaData.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MetaData> findAll() {
        log.debug("Request to get all MetaData");
        return metaDataRepository.findAll();
    }

    /**
     * Get one metaData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MetaData> findOne(Long id) {
        log.debug("Request to get MetaData : {}", id);
        return metaDataRepository.findById(id);
    }

    /**
     * Delete the metaData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MetaData : {}", id);
        metaDataRepository.deleteById(id);
    }
}
