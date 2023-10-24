package com.shafaat.test.repository;

import com.shafaat.test.domain.LookupCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LookupCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LookupCategoryRepository extends JpaRepository<LookupCategory, Long> {}
