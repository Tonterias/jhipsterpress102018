package com.jhipsterpress.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jhipsterpress.web.service.InterestService;
import com.jhipsterpress.web.web.rest.errors.BadRequestAlertException;
import com.jhipsterpress.web.web.rest.util.HeaderUtil;
import com.jhipsterpress.web.web.rest.util.PaginationUtil;
import com.jhipsterpress.web.service.dto.InterestDTO;
import com.jhipsterpress.web.service.dto.InterestCriteria;
import com.jhipsterpress.web.service.InterestQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Interest.
 */
@RestController
@RequestMapping("/api")
public class InterestResource {

    private final Logger log = LoggerFactory.getLogger(InterestResource.class);

    private static final String ENTITY_NAME = "interest";

    private final InterestService interestService;

    private final InterestQueryService interestQueryService;

    public InterestResource(InterestService interestService, InterestQueryService interestQueryService) {
        this.interestService = interestService;
        this.interestQueryService = interestQueryService;
    }

    /**
     * POST  /interests : Create a new interest.
     *
     * @param interestDTO the interestDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new interestDTO, or with status 400 (Bad Request) if the interest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/interests")
    @Timed
    public ResponseEntity<InterestDTO> createInterest(@Valid @RequestBody InterestDTO interestDTO) throws URISyntaxException {
        log.debug("REST request to save Interest : {}", interestDTO);
        if (interestDTO.getId() != null) {
            throw new BadRequestAlertException("A new interest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InterestDTO result = interestService.save(interestDTO);
        return ResponseEntity.created(new URI("/api/interests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /interests : Updates an existing interest.
     *
     * @param interestDTO the interestDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated interestDTO,
     * or with status 400 (Bad Request) if the interestDTO is not valid,
     * or with status 500 (Internal Server Error) if the interestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/interests")
    @Timed
    public ResponseEntity<InterestDTO> updateInterest(@Valid @RequestBody InterestDTO interestDTO) throws URISyntaxException {
        log.debug("REST request to update Interest : {}", interestDTO);
        if (interestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InterestDTO result = interestService.save(interestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, interestDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /interests : get all the interests.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of interests in body
     */
    @GetMapping("/interests")
    @Timed
    public ResponseEntity<List<InterestDTO>> getAllInterests(InterestCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Interests by criteria: {}", criteria);
        Page<InterestDTO> page = interestQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/interests");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /interests/count : count all the interests.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/interests/count")
    @Timed
    public ResponseEntity<Long> countInterests(InterestCriteria criteria) {
        log.debug("REST request to count Interests by criteria: {}", criteria);
        return ResponseEntity.ok().body(interestQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /interests/:id : get the "id" interest.
     *
     * @param id the id of the interestDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the interestDTO, or with status 404 (Not Found)
     */
    @GetMapping("/interests/{id}")
    @Timed
    public ResponseEntity<InterestDTO> getInterest(@PathVariable Long id) {
        log.debug("REST request to get Interest : {}", id);
        Optional<InterestDTO> interestDTO = interestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interestDTO);
    }

    /**
     * DELETE  /interests/:id : delete the "id" interest.
     *
     * @param id the id of the interestDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/interests/{id}")
    @Timed
    public ResponseEntity<Void> deleteInterest(@PathVariable Long id) {
        log.debug("REST request to delete Interest : {}", id);
        interestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/interests?query=:query : search for the interest corresponding
     * to the query.
     *
     * @param query the query of the interest search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/interests")
    @Timed
    public ResponseEntity<List<InterestDTO>> searchInterests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Interests for query {}", query);
        Page<InterestDTO> page = interestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/interests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
