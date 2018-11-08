package com.jhipsterpress.web.service;

import com.jhipsterpress.web.service.dto.FrontpageconfigDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Frontpageconfig.
 */
public interface FrontpageconfigService {

    /**
     * Save a frontpageconfig.
     *
     * @param frontpageconfigDTO the entity to save
     * @return the persisted entity
     */
    FrontpageconfigDTO save(FrontpageconfigDTO frontpageconfigDTO);

    /**
     * Get all the frontpageconfigs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FrontpageconfigDTO> findAll(Pageable pageable);


    /**
     * Get the "id" frontpageconfig.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FrontpageconfigDTO> findOne(Long id);

    /**
     * Delete the "id" frontpageconfig.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the frontpageconfig corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FrontpageconfigDTO> search(String query, Pageable pageable);
}
