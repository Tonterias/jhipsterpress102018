package com.jhipsterpress.web.service.impl;

import com.jhipsterpress.web.service.TopicService;
import com.jhipsterpress.web.domain.Topic;
import com.jhipsterpress.web.repository.TopicRepository;
import com.jhipsterpress.web.repository.search.TopicSearchRepository;
import com.jhipsterpress.web.service.dto.TopicDTO;
import com.jhipsterpress.web.service.mapper.TopicMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Topic.
 */
@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    private final Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);

    private final TopicRepository topicRepository;

    private final TopicMapper topicMapper;

    private final TopicSearchRepository topicSearchRepository;

    public TopicServiceImpl(TopicRepository topicRepository, TopicMapper topicMapper, TopicSearchRepository topicSearchRepository) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
        this.topicSearchRepository = topicSearchRepository;
    }

    /**
     * Save a topic.
     *
     * @param topicDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TopicDTO save(TopicDTO topicDTO) {
        log.debug("Request to save Topic : {}", topicDTO);

        Topic topic = topicMapper.toEntity(topicDTO);
        topic = topicRepository.save(topic);
        TopicDTO result = topicMapper.toDto(topic);
        topicSearchRepository.save(topic);
        return result;
    }

    /**
     * Get all the topics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TopicDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Topics");
        return topicRepository.findAll(pageable)
            .map(topicMapper::toDto);
    }

    /**
     * Get all the Topic with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<TopicDTO> findAllWithEagerRelationships(Pageable pageable) {
        return topicRepository.findAllWithEagerRelationships(pageable).map(topicMapper::toDto);
    }
    

    /**
     * Get one topic by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TopicDTO> findOne(Long id) {
        log.debug("Request to get Topic : {}", id);
        return topicRepository.findOneWithEagerRelationships(id)
            .map(topicMapper::toDto);
    }

    /**
     * Delete the topic by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Topic : {}", id);
        topicRepository.deleteById(id);
        topicSearchRepository.deleteById(id);
    }

    /**
     * Search for the topic corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TopicDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Topics for query {}", query);
        return topicSearchRepository.search(queryStringQuery(query), pageable)
            .map(topicMapper::toDto);
    }
}
