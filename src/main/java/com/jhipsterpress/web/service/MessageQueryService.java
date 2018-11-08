package com.jhipsterpress.web.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.jhipsterpress.web.domain.Message;
import com.jhipsterpress.web.domain.*; // for static metamodels
import com.jhipsterpress.web.repository.MessageRepository;
import com.jhipsterpress.web.repository.search.MessageSearchRepository;
import com.jhipsterpress.web.service.dto.MessageCriteria;
import com.jhipsterpress.web.service.dto.MessageDTO;
import com.jhipsterpress.web.service.mapper.MessageMapper;

/**
 * Service for executing complex queries for Message entities in the database.
 * The main input is a {@link MessageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MessageDTO} or a {@link Page} of {@link MessageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MessageQueryService extends QueryService<Message> {

    private final Logger log = LoggerFactory.getLogger(MessageQueryService.class);

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    private final MessageSearchRepository messageSearchRepository;

    public MessageQueryService(MessageRepository messageRepository, MessageMapper messageMapper, MessageSearchRepository messageSearchRepository) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.messageSearchRepository = messageSearchRepository;
    }

    /**
     * Return a {@link List} of {@link MessageDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MessageDTO> findByCriteria(MessageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Message> specification = createSpecification(criteria);
        return messageMapper.toDto(messageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MessageDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MessageDTO> findByCriteria(MessageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Message> specification = createSpecification(criteria);
        return messageRepository.findAll(specification, page)
            .map(messageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MessageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Message> specification = createSpecification(criteria);
        return messageRepository.count(specification);
    }

    /**
     * Function to convert MessageCriteria to a {@link Specification}
     */
    private Specification<Message> createSpecification(MessageCriteria criteria) {
        Specification<Message> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Message_.id));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Message_.creationDate));
            }
            if (criteria.getMessageText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessageText(), Message_.messageText));
            }
            if (criteria.getIsDelivered() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDelivered(), Message_.isDelivered));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Message_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getCommunityId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommunityId(),
                    root -> root.join(Message_.community, JoinType.LEFT).get(Community_.id)));
            }
        }
        return specification;
    }
}
