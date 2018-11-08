package com.jhipsterpress.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jhipsterpress.web.service.UrllinkService;
import com.jhipsterpress.web.web.rest.errors.BadRequestAlertException;
import com.jhipsterpress.web.web.rest.util.HeaderUtil;
import com.jhipsterpress.web.web.rest.util.PaginationUtil;
import com.jhipsterpress.web.service.dto.UrllinkDTO;
import com.jhipsterpress.web.service.dto.UrllinkCriteria;
import com.jhipsterpress.web.service.UrllinkQueryService;
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
 * REST controller for managing Urllink.
 */
@RestController
@RequestMapping("/api")
public class UrllinkResource {

    private final Logger log = LoggerFactory.getLogger(UrllinkResource.class);

    private static final String ENTITY_NAME = "urllink";

    private final UrllinkService urllinkService;

    private final UrllinkQueryService urllinkQueryService;

    public UrllinkResource(UrllinkService urllinkService, UrllinkQueryService urllinkQueryService) {
        this.urllinkService = urllinkService;
        this.urllinkQueryService = urllinkQueryService;
    }

    /**
     * POST  /urllinks : Create a new urllink.
     *
     * @param urllinkDTO the urllinkDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new urllinkDTO, or with status 400 (Bad Request) if the urllink has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/urllinks")
    @Timed
    public ResponseEntity<UrllinkDTO> createUrllink(@Valid @RequestBody UrllinkDTO urllinkDTO) throws URISyntaxException {
        log.debug("REST request to save Urllink : {}", urllinkDTO);
        if (urllinkDTO.getId() != null) {
            throw new BadRequestAlertException("A new urllink cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UrllinkDTO result = urllinkService.save(urllinkDTO);
        return ResponseEntity.created(new URI("/api/urllinks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /urllinks : Updates an existing urllink.
     *
     * @param urllinkDTO the urllinkDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated urllinkDTO,
     * or with status 400 (Bad Request) if the urllinkDTO is not valid,
     * or with status 500 (Internal Server Error) if the urllinkDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/urllinks")
    @Timed
    public ResponseEntity<UrllinkDTO> updateUrllink(@Valid @RequestBody UrllinkDTO urllinkDTO) throws URISyntaxException {
        log.debug("REST request to update Urllink : {}", urllinkDTO);
        if (urllinkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UrllinkDTO result = urllinkService.save(urllinkDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, urllinkDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /urllinks : get all the urllinks.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of urllinks in body
     */
    @GetMapping("/urllinks")
    @Timed
    public ResponseEntity<List<UrllinkDTO>> getAllUrllinks(UrllinkCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Urllinks by criteria: {}", criteria);
        Page<UrllinkDTO> page = urllinkQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/urllinks");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /urllinks/count : count all the urllinks.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/urllinks/count")
    @Timed
    public ResponseEntity<Long> countUrllinks(UrllinkCriteria criteria) {
        log.debug("REST request to count Urllinks by criteria: {}", criteria);
        return ResponseEntity.ok().body(urllinkQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /urllinks/:id : get the "id" urllink.
     *
     * @param id the id of the urllinkDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the urllinkDTO, or with status 404 (Not Found)
     */
    @GetMapping("/urllinks/{id}")
    @Timed
    public ResponseEntity<UrllinkDTO> getUrllink(@PathVariable Long id) {
        log.debug("REST request to get Urllink : {}", id);
        Optional<UrllinkDTO> urllinkDTO = urllinkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(urllinkDTO);
    }

    /**
     * DELETE  /urllinks/:id : delete the "id" urllink.
     *
     * @param id the id of the urllinkDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/urllinks/{id}")
    @Timed
    public ResponseEntity<Void> deleteUrllink(@PathVariable Long id) {
        log.debug("REST request to delete Urllink : {}", id);
        urllinkService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/urllinks?query=:query : search for the urllink corresponding
     * to the query.
     *
     * @param query the query of the urllink search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/urllinks")
    @Timed
    public ResponseEntity<List<UrllinkDTO>> searchUrllinks(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Urllinks for query {}", query);
        Page<UrllinkDTO> page = urllinkService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/urllinks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
