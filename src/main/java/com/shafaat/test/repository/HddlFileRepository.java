package com.shafaat.test.repository;

import com.shafaat.test.domain.HddlFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HddlFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HddlFileRepository extends JpaRepository<HddlFile, Long> {}
