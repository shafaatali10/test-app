package com.shafaat.test.repository;

import com.shafaat.test.domain.Lookup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Lookup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LookupRepository extends JpaRepository<Lookup, Long> {}
