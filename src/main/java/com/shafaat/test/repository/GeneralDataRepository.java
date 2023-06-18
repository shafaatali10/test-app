package com.shafaat.test.repository;

import com.shafaat.test.domain.GeneralData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GeneralData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeneralDataRepository extends JpaRepository<GeneralData, Long> {}
