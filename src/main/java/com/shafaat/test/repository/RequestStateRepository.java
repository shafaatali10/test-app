package com.shafaat.test.repository;

import com.shafaat.test.domain.RequestState;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequestState entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestStateRepository extends JpaRepository<RequestState, Long> {}
