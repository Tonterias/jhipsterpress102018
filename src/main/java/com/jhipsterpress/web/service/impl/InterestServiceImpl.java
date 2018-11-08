package com.jhipsterpress.web.service.impl;

import com.jhipsterpress.web.service.InterestService;
import com.jhipsterpress.web.domain.Interest;
import com.jhipsterpress.web.repository.InterestRepository;
import com.jhipsterpress.web.repository.search.InterestSearchRepository;
import com.jhipsterpress.web.service.dto.InterestDTO;
import com.jhipsterpress.web.service.mapper.InterestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Interest.
 */
@Service
@Transactional
public class InterestServiceImpl implements InterestService {

    private final Logger log = LoggerFactory.getLogger(InterestServiceImpl.class);

    private final InterestRepository interestRepository;

    private final InterestMapper interestMapper;

    private final InterestSearchRepository interestSearchRepository;

    public InterestServiceImpl(InterestRepository interestRepository, InterestMapper interestMapper, InterestSearchRepository interestSearchRepository) {
        this.interestRepository = interestRepository;
        this.interestMapper = interestMapper;
        this.interestSearchRepository = interestSearchRepository;
    }

    /**
     * Save a interest.
     *
     * @param interestDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InterestDTO save(InterestDTO interestDTO) {
        log.debug("Request to save Interest : {}", interestDTO);

        Interest interest = interestMapper.toEntity(interestDTO);
        interest = interestRepository.save(interest);
        InterestDTO result = interestMapper.toDto(interest);
        interestSearchRepository.save(interest);
        return result;
    }

    /**
     * Get all the interests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InterestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Interests");
        return interestRepository.findAll(pageable)
            .map(interestMapper::toDto);
    }

    /**
     * Get all the Interest with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<InterestDTO> findAllWithEagerRelationships(Pageable pageable) {
        return interestRepository.findAllWithEagerRelationships(pageable).map(interestMapper::toDto);
    }
    

    /**
     * Get one interest by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InterestDTO> findOne(Long id) {
        log.debug("Request to get Interest : {}", id);
        return interestRepository.findOneWithEagerRelationships(id)
            .map(interestMapper::toDto);
    }

    /**
     * Delete the interest by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Interest : {}", id);
        interestRepository.deleteById(id);
        interestSearchRepository.deleteById(id);
    }

    /**
     * Search for the interest corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InterestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Interests for query {}", query);
        return interestSearchRepository.search(queryStringQuery(query), pageable)
            .map(interestMapper::toDto);
    }
}
