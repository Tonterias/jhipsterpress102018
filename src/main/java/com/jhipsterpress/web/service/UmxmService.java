package com.jhipsterpress.web.service;

import com.jhipsterpress.web.service.dto.UmxmDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Umxm.
 */
public interface UmxmService {

    /**
     * Save a umxm.
     *
     * @param umxmDTO the entity to save
     * @return the persisted entity
     */
    UmxmDTO save(UmxmDTO umxmDTO);

    /**
     * Get all the umxms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UmxmDTO> findAll(Pageable pageable);


    /**
     * Get the "id" umxm.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UmxmDTO> findOne(Long id);

    /**
     * Delete the "id" umxm.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the umxm corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UmxmDTO> search(String query, Pageable pageable);
}
