package com.jhipsterpress.web.repository;

import com.jhipsterpress.web.domain.Umxm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Umxm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UmxmRepository extends JpaRepository<Umxm, Long>, JpaSpecificationExecutor<Umxm> {

}
