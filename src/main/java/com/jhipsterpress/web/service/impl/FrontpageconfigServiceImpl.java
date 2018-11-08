package com.jhipsterpress.web.service.impl;

import com.jhipsterpress.web.service.FrontpageconfigService;
import com.jhipsterpress.web.domain.Frontpageconfig;
import com.jhipsterpress.web.repository.FrontpageconfigRepository;
import com.jhipsterpress.web.repository.search.FrontpageconfigSearchRepository;
import com.jhipsterpress.web.service.dto.FrontpageconfigDTO;
import com.jhipsterpress.web.service.mapper.FrontpageconfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Frontpageconfig.
 */
@Service
@Transactional
public class FrontpageconfigServiceImpl implements FrontpageconfigService {

    private final Logger log = LoggerFactory.getLogger(FrontpageconfigServiceImpl.class);

    private final FrontpageconfigRepository frontpageconfigRepository;

    private final FrontpageconfigMapper frontpageconfigMapper;

    private final FrontpageconfigSearchRepository frontpageconfigSearchRepository;

    public FrontpageconfigServiceImpl(FrontpageconfigRepository frontpageconfigRepository, FrontpageconfigMapper frontpageconfigMapper, FrontpageconfigSearchRepository frontpageconfigSearchRepository) {
        this.frontpageconfigRepository = frontpageconfigRepository;
        this.frontpageconfigMapper = frontpageconfigMapper;
        this.frontpageconfigSearchRepository = frontpageconfigSearchRepository;
    }

    /**
     * Save a frontpageconfig.
     *
     * @param frontpageconfigDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FrontpageconfigDTO save(FrontpageconfigDTO frontpageconfigDTO) {
        log.debug("Request to save Frontpageconfig : {}", frontpageconfigDTO);

        Frontpageconfig frontpageconfig = frontpageconfigMapper.toEntity(frontpageconfigDTO);
        frontpageconfig = frontpageconfigRepository.save(frontpageconfig);
        FrontpageconfigDTO result = frontpageconfigMapper.toDto(frontpageconfig);
        frontpageconfigSearchRepository.save(frontpageconfig);
        return result;
    }

    /**
     * Get all the frontpageconfigs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FrontpageconfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Frontpageconfigs");
        return frontpageconfigRepository.findAll(pageable)
            .map(frontpageconfigMapper::toDto);
    }


    /**
     * Get one frontpageconfig by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FrontpageconfigDTO> findOne(Long id) {
        log.debug("Request to get Frontpageconfig : {}", id);
        return frontpageconfigRepository.findById(id)
            .map(frontpageconfigMapper::toDto);
    }

    /**
     * Delete the frontpageconfig by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Frontpageconfig : {}", id);
        frontpageconfigRepository.deleteById(id);
        frontpageconfigSearchRepository.deleteById(id);
    }

    /**
     * Search for the frontpageconfig corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FrontpageconfigDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Frontpageconfigs for query {}", query);
        return frontpageconfigSearchRepository.search(queryStringQuery(query), pageable)
            .map(frontpageconfigMapper::toDto);
    }
}
