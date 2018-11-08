package com.jhipsterpress.web.web.rest;

import com.jhipsterpress.web.JhipsterpressApp;

import com.jhipsterpress.web.domain.Umxm;
import com.jhipsterpress.web.domain.User;
import com.jhipsterpress.web.domain.Interest;
import com.jhipsterpress.web.domain.Activity;
import com.jhipsterpress.web.domain.Celeb;
import com.jhipsterpress.web.repository.UmxmRepository;
import com.jhipsterpress.web.repository.search.UmxmSearchRepository;
import com.jhipsterpress.web.service.UmxmService;
import com.jhipsterpress.web.service.dto.UmxmDTO;
import com.jhipsterpress.web.service.mapper.UmxmMapper;
import com.jhipsterpress.web.web.rest.errors.ExceptionTranslator;
import com.jhipsterpress.web.service.dto.UmxmCriteria;
import com.jhipsterpress.web.service.UmxmQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.jhipsterpress.web.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UmxmResource REST controller.
 *
 * @see UmxmResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterpressApp.class)
public class UmxmResourceIntTest {

    @Autowired
    private UmxmRepository umxmRepository;

    @Autowired
    private UmxmMapper umxmMapper;

    @Autowired
    private UmxmService umxmService;

    /**
     * This repository is mocked in the com.jhipsterpress.web.repository.search test package.
     *
     * @see com.jhipsterpress.web.repository.search.UmxmSearchRepositoryMockConfiguration
     */
    @Autowired
    private UmxmSearchRepository mockUmxmSearchRepository;

    @Autowired
    private UmxmQueryService umxmQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUmxmMockMvc;

    private Umxm umxm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UmxmResource umxmResource = new UmxmResource(umxmService, umxmQueryService);
        this.restUmxmMockMvc = MockMvcBuilders.standaloneSetup(umxmResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Umxm createEntity(EntityManager em) {
        Umxm umxm = new Umxm();
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        umxm.setUser(user);
        return umxm;
    }

    @Before
    public void initTest() {
        umxm = createEntity(em);
    }

    @Test
    @Transactional
    public void createUmxm() throws Exception {
        int databaseSizeBeforeCreate = umxmRepository.findAll().size();

        // Create the Umxm
        UmxmDTO umxmDTO = umxmMapper.toDto(umxm);
        restUmxmMockMvc.perform(post("/api/umxms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(umxmDTO)))
            .andExpect(status().isCreated());

        // Validate the Umxm in the database
        List<Umxm> umxmList = umxmRepository.findAll();
        assertThat(umxmList).hasSize(databaseSizeBeforeCreate + 1);
        Umxm testUmxm = umxmList.get(umxmList.size() - 1);

        // Validate the Umxm in Elasticsearch
        verify(mockUmxmSearchRepository, times(1)).save(testUmxm);
    }

    @Test
    @Transactional
    public void createUmxmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = umxmRepository.findAll().size();

        // Create the Umxm with an existing ID
        umxm.setId(1L);
        UmxmDTO umxmDTO = umxmMapper.toDto(umxm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUmxmMockMvc.perform(post("/api/umxms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(umxmDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Umxm in the database
        List<Umxm> umxmList = umxmRepository.findAll();
        assertThat(umxmList).hasSize(databaseSizeBeforeCreate);

        // Validate the Umxm in Elasticsearch
        verify(mockUmxmSearchRepository, times(0)).save(umxm);
    }

    @Test
    @Transactional
    public void getAllUmxms() throws Exception {
        // Initialize the database
        umxmRepository.saveAndFlush(umxm);

        // Get all the umxmList
        restUmxmMockMvc.perform(get("/api/umxms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(umxm.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getUmxm() throws Exception {
        // Initialize the database
        umxmRepository.saveAndFlush(umxm);

        // Get the umxm
        restUmxmMockMvc.perform(get("/api/umxms/{id}", umxm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(umxm.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllUmxmsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        umxm.setUser(user);
        umxmRepository.saveAndFlush(umxm);
        Long userId = user.getId();

        // Get all the umxmList where user equals to userId
        defaultUmxmShouldBeFound("userId.equals=" + userId);

        // Get all the umxmList where user equals to userId + 1
        defaultUmxmShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllUmxmsByInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        Interest interest = InterestResourceIntTest.createEntity(em);
        em.persist(interest);
        em.flush();
        umxm.addInterest(interest);
        umxmRepository.saveAndFlush(umxm);
        Long interestId = interest.getId();

        // Get all the umxmList where interest equals to interestId
        defaultUmxmShouldBeFound("interestId.equals=" + interestId);

        // Get all the umxmList where interest equals to interestId + 1
        defaultUmxmShouldNotBeFound("interestId.equals=" + (interestId + 1));
    }


    @Test
    @Transactional
    public void getAllUmxmsByActivityIsEqualToSomething() throws Exception {
        // Initialize the database
        Activity activity = ActivityResourceIntTest.createEntity(em);
        em.persist(activity);
        em.flush();
        umxm.addActivity(activity);
        umxmRepository.saveAndFlush(umxm);
        Long activityId = activity.getId();

        // Get all the umxmList where activity equals to activityId
        defaultUmxmShouldBeFound("activityId.equals=" + activityId);

        // Get all the umxmList where activity equals to activityId + 1
        defaultUmxmShouldNotBeFound("activityId.equals=" + (activityId + 1));
    }


    @Test
    @Transactional
    public void getAllUmxmsByCelebIsEqualToSomething() throws Exception {
        // Initialize the database
        Celeb celeb = CelebResourceIntTest.createEntity(em);
        em.persist(celeb);
        em.flush();
        umxm.addCeleb(celeb);
        umxmRepository.saveAndFlush(umxm);
        Long celebId = celeb.getId();

        // Get all the umxmList where celeb equals to celebId
        defaultUmxmShouldBeFound("celebId.equals=" + celebId);

        // Get all the umxmList where celeb equals to celebId + 1
        defaultUmxmShouldNotBeFound("celebId.equals=" + (celebId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultUmxmShouldBeFound(String filter) throws Exception {
        restUmxmMockMvc.perform(get("/api/umxms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(umxm.getId().intValue())));

        // Check, that the count call also returns 1
        restUmxmMockMvc.perform(get("/api/umxms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultUmxmShouldNotBeFound(String filter) throws Exception {
        restUmxmMockMvc.perform(get("/api/umxms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUmxmMockMvc.perform(get("/api/umxms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUmxm() throws Exception {
        // Get the umxm
        restUmxmMockMvc.perform(get("/api/umxms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUmxm() throws Exception {
        // Initialize the database
        umxmRepository.saveAndFlush(umxm);

        int databaseSizeBeforeUpdate = umxmRepository.findAll().size();

        // Update the umxm
        Umxm updatedUmxm = umxmRepository.findById(umxm.getId()).get();
        // Disconnect from session so that the updates on updatedUmxm are not directly saved in db
        em.detach(updatedUmxm);
        UmxmDTO umxmDTO = umxmMapper.toDto(updatedUmxm);

        restUmxmMockMvc.perform(put("/api/umxms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(umxmDTO)))
            .andExpect(status().isOk());

        // Validate the Umxm in the database
        List<Umxm> umxmList = umxmRepository.findAll();
        assertThat(umxmList).hasSize(databaseSizeBeforeUpdate);
        Umxm testUmxm = umxmList.get(umxmList.size() - 1);

        // Validate the Umxm in Elasticsearch
        verify(mockUmxmSearchRepository, times(1)).save(testUmxm);
    }

    @Test
    @Transactional
    public void updateNonExistingUmxm() throws Exception {
        int databaseSizeBeforeUpdate = umxmRepository.findAll().size();

        // Create the Umxm
        UmxmDTO umxmDTO = umxmMapper.toDto(umxm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUmxmMockMvc.perform(put("/api/umxms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(umxmDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Umxm in the database
        List<Umxm> umxmList = umxmRepository.findAll();
        assertThat(umxmList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Umxm in Elasticsearch
        verify(mockUmxmSearchRepository, times(0)).save(umxm);
    }

    @Test
    @Transactional
    public void deleteUmxm() throws Exception {
        // Initialize the database
        umxmRepository.saveAndFlush(umxm);

        int databaseSizeBeforeDelete = umxmRepository.findAll().size();

        // Get the umxm
        restUmxmMockMvc.perform(delete("/api/umxms/{id}", umxm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Umxm> umxmList = umxmRepository.findAll();
        assertThat(umxmList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Umxm in Elasticsearch
        verify(mockUmxmSearchRepository, times(1)).deleteById(umxm.getId());
    }

    @Test
    @Transactional
    public void searchUmxm() throws Exception {
        // Initialize the database
        umxmRepository.saveAndFlush(umxm);
        when(mockUmxmSearchRepository.search(queryStringQuery("id:" + umxm.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(umxm), PageRequest.of(0, 1), 1));
        // Search the umxm
        restUmxmMockMvc.perform(get("/api/_search/umxms?query=id:" + umxm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(umxm.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Umxm.class);
        Umxm umxm1 = new Umxm();
        umxm1.setId(1L);
        Umxm umxm2 = new Umxm();
        umxm2.setId(umxm1.getId());
        assertThat(umxm1).isEqualTo(umxm2);
        umxm2.setId(2L);
        assertThat(umxm1).isNotEqualTo(umxm2);
        umxm1.setId(null);
        assertThat(umxm1).isNotEqualTo(umxm2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UmxmDTO.class);
        UmxmDTO umxmDTO1 = new UmxmDTO();
        umxmDTO1.setId(1L);
        UmxmDTO umxmDTO2 = new UmxmDTO();
        assertThat(umxmDTO1).isNotEqualTo(umxmDTO2);
        umxmDTO2.setId(umxmDTO1.getId());
        assertThat(umxmDTO1).isEqualTo(umxmDTO2);
        umxmDTO2.setId(2L);
        assertThat(umxmDTO1).isNotEqualTo(umxmDTO2);
        umxmDTO1.setId(null);
        assertThat(umxmDTO1).isNotEqualTo(umxmDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(umxmMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(umxmMapper.fromId(null)).isNull();
    }
}
