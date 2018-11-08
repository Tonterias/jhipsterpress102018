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

import com.jhipsterpress.web.domain.Umxm;
import com.jhipsterpress.web.domain.*; // for static metamodels
import com.jhipsterpress.web.repository.UmxmRepository;
import com.jhipsterpress.web.repository.search.UmxmSearchRepository;
import com.jhipsterpress.web.service.dto.UmxmCriteria;
import com.jhipsterpress.web.service.dto.UmxmDTO;
import com.jhipsterpress.web.service.mapper.UmxmMapper;

/**
 * Service for executing complex queries for Umxm entities in the database.
 * The main input is a {@link UmxmCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UmxmDTO} or a {@link Page} of {@link UmxmDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UmxmQueryService extends QueryService<Umxm> {

    private final Logger log = LoggerFactory.getLogger(UmxmQueryService.class);

    private final UmxmRepository umxmRepository;

    private final UmxmMapper umxmMapper;

    private final UmxmSearchRepository umxmSearchRepository;

    public UmxmQueryService(UmxmRepository umxmRepository, UmxmMapper umxmMapper, UmxmSearchRepository umxmSearchRepository) {
        this.umxmRepository = umxmRepository;
        this.umxmMapper = umxmMapper;
        this.umxmSearchRepository = umxmSearchRepository;
    }

    /**
     * Return a {@link List} of {@link UmxmDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UmxmDTO> findByCriteria(UmxmCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Umxm> specification = createSpecification(criteria);
        return umxmMapper.toDto(umxmRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UmxmDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UmxmDTO> findByCriteria(UmxmCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Umxm> specification = createSpecification(criteria);
        return umxmRepository.findAll(specification, page)
            .map(umxmMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UmxmCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Umxm> specification = createSpecification(criteria);
        return umxmRepository.count(specification);
    }

    /**
     * Function to convert UmxmCriteria to a {@link Specification}
     */
    private Specification<Umxm> createSpecification(UmxmCriteria criteria) {
        Specification<Umxm> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Umxm_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Umxm_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getInterestId() != null) {
                specification = specification.and(buildSpecification(criteria.getInterestId(),
                    root -> root.join(Umxm_.interests, JoinType.LEFT).get(Interest_.id)));
            }
            if (criteria.getActivityId() != null) {
                specification = specification.and(buildSpecification(criteria.getActivityId(),
                    root -> root.join(Umxm_.activities, JoinType.LEFT).get(Activity_.id)));
            }
            if (criteria.getCelebId() != null) {
                specification = specification.and(buildSpecification(criteria.getCelebId(),
                    root -> root.join(Umxm_.celebs, JoinType.LEFT).get(Celeb_.id)));
            }
        }
        return specification;
    }
}
