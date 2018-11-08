package com.jhipsterpress.web.service.impl;

import com.jhipsterpress.web.service.AlbumService;
import com.jhipsterpress.web.domain.Album;
import com.jhipsterpress.web.repository.AlbumRepository;
import com.jhipsterpress.web.repository.search.AlbumSearchRepository;
import com.jhipsterpress.web.service.dto.AlbumDTO;
import com.jhipsterpress.web.service.mapper.AlbumMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Album.
 */
@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    private final Logger log = LoggerFactory.getLogger(AlbumServiceImpl.class);

    private final AlbumRepository albumRepository;

    private final AlbumMapper albumMapper;

    private final AlbumSearchRepository albumSearchRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository, AlbumMapper albumMapper, AlbumSearchRepository albumSearchRepository) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
        this.albumSearchRepository = albumSearchRepository;
    }

    /**
     * Save a album.
     *
     * @param albumDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AlbumDTO save(AlbumDTO albumDTO) {
        log.debug("Request to save Album : {}", albumDTO);

        Album album = albumMapper.toEntity(albumDTO);
        album = albumRepository.save(album);
        AlbumDTO result = albumMapper.toDto(album);
        albumSearchRepository.save(album);
        return result;
    }

    /**
     * Get all the albums.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AlbumDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Albums");
        return albumRepository.findAll(pageable)
            .map(albumMapper::toDto);
    }


    /**
     * Get one album by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AlbumDTO> findOne(Long id) {
        log.debug("Request to get Album : {}", id);
        return albumRepository.findById(id)
            .map(albumMapper::toDto);
    }

    /**
     * Delete the album by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Album : {}", id);
        albumRepository.deleteById(id);
        albumSearchRepository.deleteById(id);
    }

    /**
     * Search for the album corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AlbumDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Albums for query {}", query);
        return albumSearchRepository.search(queryStringQuery(query), pageable)
            .map(albumMapper::toDto);
    }
}
