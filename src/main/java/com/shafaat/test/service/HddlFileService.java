package com.shafaat.test.service;

import com.shafaat.test.domain.HddlFile;
import com.shafaat.test.repository.HddlFileRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HddlFile}.
 */
@Service
@Transactional
public class HddlFileService {

    private final Logger log = LoggerFactory.getLogger(HddlFileService.class);

    private final HddlFileRepository hddlFileRepository;

    public HddlFileService(HddlFileRepository hddlFileRepository) {
        this.hddlFileRepository = hddlFileRepository;
    }

    /**
     * Save a hddlFile.
     *
     * @param hddlFile the entity to save.
     * @return the persisted entity.
     */
    public HddlFile save(HddlFile hddlFile) {
        log.debug("Request to save HddlFile : {}", hddlFile);
        return hddlFileRepository.save(hddlFile);
    }

    /**
     * Update a hddlFile.
     *
     * @param hddlFile the entity to save.
     * @return the persisted entity.
     */
    public HddlFile update(HddlFile hddlFile) {
        log.debug("Request to update HddlFile : {}", hddlFile);
        return hddlFileRepository.save(hddlFile);
    }

    /**
     * Partially update a hddlFile.
     *
     * @param hddlFile the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HddlFile> partialUpdate(HddlFile hddlFile) {
        log.debug("Request to partially update HddlFile : {}", hddlFile);

        return hddlFileRepository
            .findById(hddlFile.getId())
            .map(existingHddlFile -> {
                if (hddlFile.getSwid() != null) {
                    existingHddlFile.setSwid(hddlFile.getSwid());
                }
                if (hddlFile.getDbName() != null) {
                    existingHddlFile.setDbName(hddlFile.getDbName());
                }
                if (hddlFile.getExpiryDate() != null) {
                    existingHddlFile.setExpiryDate(hddlFile.getExpiryDate());
                }
                if (hddlFile.getStatus() != null) {
                    existingHddlFile.setStatus(hddlFile.getStatus());
                }
                if (hddlFile.getHddl() != null) {
                    existingHddlFile.setHddl(hddlFile.getHddl());
                }

                return existingHddlFile;
            })
            .map(hddlFileRepository::save);
    }

    /**
     * Get all the hddlFiles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<HddlFile> findAll() {
        log.debug("Request to get all HddlFiles");
        return hddlFileRepository.findAll();
    }

    /**
     * Get one hddlFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HddlFile> findOne(Long id) {
        log.debug("Request to get HddlFile : {}", id);
        return hddlFileRepository.findById(id);
    }

    /**
     * Delete the hddlFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HddlFile : {}", id);
        hddlFileRepository.deleteById(id);
    }
}
