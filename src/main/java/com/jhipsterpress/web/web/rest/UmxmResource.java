package com.jhipsterpress.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jhipsterpress.web.service.UmxmService;
import com.jhipsterpress.web.web.rest.errors.BadRequestAlertException;
import com.jhipsterpress.web.web.rest.util.HeaderUtil;
import com.jhipsterpress.web.web.rest.util.PaginationUtil;
import com.jhipsterpress.web.service.dto.UmxmDTO;
import com.jhipsterpress.web.service.dto.UmxmCriteria;
import com.jhipsterpress.web.service.UmxmQueryService;
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
 * REST controller for managing Umxm.
 */
@RestController
@RequestMapping("/api")
public class UmxmResource {

    private final Logger log = LoggerFactory.getLogger(UmxmResource.class);

    private static final String ENTITY_NAME = "umxm";

    private final UmxmService umxmService;

    private final UmxmQueryService umxmQueryService;

    public UmxmResource(UmxmService umxmService, UmxmQueryService umxmQueryService) {
        this.umxmService = umxmService;
        this.umxmQueryService = umxmQueryService;
    }

    /**
     * POST  /umxms : Create a new umxm.
     *
     * @param umxmDTO the umxmDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new umxmDTO, or with status 400 (Bad Request) if the umxm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/umxms")
    @Timed
    public ResponseEntity<UmxmDTO> createUmxm(@Valid @RequestBody UmxmDTO umxmDTO) throws URISyntaxException {
        log.debug("REST request to save Umxm : {}", umxmDTO);
        if (umxmDTO.getId() != null) {
            throw new BadRequestAlertException("A new umxm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UmxmDTO result = umxmService.save(umxmDTO);
        return ResponseEntity.created(new URI("/api/umxms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /umxms : Updates an existing umxm.
     *
     * @param umxmDTO the umxmDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated umxmDTO,
     * or with status 400 (Bad Request) if the umxmDTO is not valid,
     * or with status 500 (Internal Server Error) if the umxmDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/umxms")
    @Timed
    public ResponseEntity<UmxmDTO> updateUmxm(@Valid @RequestBody UmxmDTO umxmDTO) throws URISyntaxException {
        log.debug("REST request to update Umxm : {}", umxmDTO);
        if (umxmDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UmxmDTO result = umxmService.save(umxmDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, umxmDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /umxms : get all the umxms.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of umxms in body
     */
    @GetMapping("/umxms")
    @Timed
    public ResponseEntity<List<UmxmDTO>> getAllUmxms(UmxmCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Umxms by criteria: {}", criteria);
        Page<UmxmDTO> page = umxmQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/umxms");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /umxms/count : count all the umxms.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/umxms/count")
    @Timed
    public ResponseEntity<Long> countUmxms(UmxmCriteria criteria) {
        log.debug("REST request to count Umxms by criteria: {}", criteria);
        return ResponseEntity.ok().body(umxmQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /umxms/:id : get the "id" umxm.
     *
     * @param id the id of the umxmDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the umxmDTO, or with status 404 (Not Found)
     */
    @GetMapping("/umxms/{id}")
    @Timed
    public ResponseEntity<UmxmDTO> getUmxm(@PathVariable Long id) {
        log.debug("REST request to get Umxm : {}", id);
        Optional<UmxmDTO> umxmDTO = umxmService.findOne(id);
        return ResponseUtil.wrapOrNotFound(umxmDTO);
    }

    /**
     * DELETE  /umxms/:id : delete the "id" umxm.
     *
     * @param id the id of the umxmDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/umxms/{id}")
    @Timed
    public ResponseEntity<Void> deleteUmxm(@PathVariable Long id) {
        log.debug("REST request to delete Umxm : {}", id);
        umxmService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/umxms?query=:query : search for the umxm corresponding
     * to the query.
     *
     * @param query the query of the umxm search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/umxms")
    @Timed
    public ResponseEntity<List<UmxmDTO>> searchUmxms(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Umxms for query {}", query);
        Page<UmxmDTO> page = umxmService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/umxms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
