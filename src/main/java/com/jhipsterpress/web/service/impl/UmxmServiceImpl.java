package com.jhipsterpress.web.service.impl;

import com.jhipsterpress.web.service.UmxmService;
import com.jhipsterpress.web.domain.Umxm;
import com.jhipsterpress.web.repository.UmxmRepository;
import com.jhipsterpress.web.repository.search.UmxmSearchRepository;
import com.jhipsterpress.web.service.dto.UmxmDTO;
import com.jhipsterpress.web.service.mapper.UmxmMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Umxm.
 */
@Service
@Transactional
public class UmxmServiceImpl implements UmxmService {

    private final Logger log = LoggerFactory.getLogger(UmxmServiceImpl.class);

    private final UmxmRepository umxmRepository;

    private final UmxmMapper umxmMapper;

    private final UmxmSearchRepository umxmSearchRepository;

    public UmxmServiceImpl(UmxmRepository umxmRepository, UmxmMapper umxmMapper, UmxmSearchRepository umxmSearchRepository) {
        this.umxmRepository = umxmRepository;
        this.umxmMapper = umxmMapper;
        this.umxmSearchRepository = umxmSearchRepository;
    }

    /**
     * Save a umxm.
     *
     * @param umxmDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UmxmDTO save(UmxmDTO umxmDTO) {
        log.debug("Request to save Umxm : {}", umxmDTO);

        Umxm umxm = umxmMapper.toEntity(umxmDTO);
        umxm = umxmRepository.save(umxm);
        UmxmDTO result = umxmMapper.toDto(umxm);
        umxmSearchRepository.save(umxm);
        return result;
    }

    /**
     * Get all the umxms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UmxmDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Umxms");
        return umxmRepository.findAll(pageable)
            .map(umxmMapper::toDto);
    }


    /**
     * Get one umxm by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UmxmDTO> findOne(Long id) {
        log.debug("Request to get Umxm : {}", id);
        return umxmRepository.findById(id)
            .map(umxmMapper::toDto);
    }

    /**
     * Delete the umxm by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Umxm : {}", id);
        umxmRepository.deleteById(id);
        umxmSearchRepository.deleteById(id);
    }

    /**
     * Search for the umxm corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UmxmDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Umxms for query {}", query);
        return umxmSearchRepository.search(queryStringQuery(query), pageable)
            .map(umxmMapper::toDto);
    }
}
