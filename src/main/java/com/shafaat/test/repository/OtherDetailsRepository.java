package com.shafaat.test.repository;

import com.shafaat.test.domain.OtherDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OtherDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OtherDetailsRepository extends JpaRepository<OtherDetails, Long> {}
