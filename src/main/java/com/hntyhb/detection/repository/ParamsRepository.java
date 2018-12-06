package com.hntyhb.detection.repository;

import com.hntyhb.detection.domain.Params;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Params entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParamsRepository extends JpaRepository<Params, Long> {

}
