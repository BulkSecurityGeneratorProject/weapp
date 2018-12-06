package com.hntyhb.detection.repository;

import com.hntyhb.detection.domain.SampleUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SampleUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SampleUserRepository extends JpaRepository<SampleUser, Long> {

}
