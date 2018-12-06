package com.hntyhb.detection.web.rest;

import com.hntyhb.detection.WeappApp;

import com.hntyhb.detection.domain.SampleUser;
import com.hntyhb.detection.repository.SampleUserRepository;
import com.hntyhb.detection.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.hntyhb.detection.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SampleUserResource REST controller.
 *
 * @see SampleUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeappApp.class)
public class SampleUserResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SampleUserRepository sampleUserRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSampleUserMockMvc;

    private SampleUser sampleUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SampleUserResource sampleUserResource = new SampleUserResource(sampleUserRepository);
        this.restSampleUserMockMvc = MockMvcBuilders.standaloneSetup(sampleUserResource)
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
    public static SampleUser createEntity(EntityManager em) {
        SampleUser sampleUser = new SampleUser()
            .name(DEFAULT_NAME);
        return sampleUser;
    }

    @Before
    public void initTest() {
        sampleUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createSampleUser() throws Exception {
        int databaseSizeBeforeCreate = sampleUserRepository.findAll().size();

        // Create the SampleUser
        restSampleUserMockMvc.perform(post("/api/sample-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleUser)))
            .andExpect(status().isCreated());

        // Validate the SampleUser in the database
        List<SampleUser> sampleUserList = sampleUserRepository.findAll();
        assertThat(sampleUserList).hasSize(databaseSizeBeforeCreate + 1);
        SampleUser testSampleUser = sampleUserList.get(sampleUserList.size() - 1);
        assertThat(testSampleUser.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSampleUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sampleUserRepository.findAll().size();

        // Create the SampleUser with an existing ID
        sampleUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSampleUserMockMvc.perform(post("/api/sample-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleUser)))
            .andExpect(status().isBadRequest());

        // Validate the SampleUser in the database
        List<SampleUser> sampleUserList = sampleUserRepository.findAll();
        assertThat(sampleUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSampleUsers() throws Exception {
        // Initialize the database
        sampleUserRepository.saveAndFlush(sampleUser);

        // Get all the sampleUserList
        restSampleUserMockMvc.perform(get("/api/sample-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sampleUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getSampleUser() throws Exception {
        // Initialize the database
        sampleUserRepository.saveAndFlush(sampleUser);

        // Get the sampleUser
        restSampleUserMockMvc.perform(get("/api/sample-users/{id}", sampleUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sampleUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSampleUser() throws Exception {
        // Get the sampleUser
        restSampleUserMockMvc.perform(get("/api/sample-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSampleUser() throws Exception {
        // Initialize the database
        sampleUserRepository.saveAndFlush(sampleUser);

        int databaseSizeBeforeUpdate = sampleUserRepository.findAll().size();

        // Update the sampleUser
        SampleUser updatedSampleUser = sampleUserRepository.findById(sampleUser.getId()).get();
        // Disconnect from session so that the updates on updatedSampleUser are not directly saved in db
        em.detach(updatedSampleUser);
        updatedSampleUser
            .name(UPDATED_NAME);

        restSampleUserMockMvc.perform(put("/api/sample-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSampleUser)))
            .andExpect(status().isOk());

        // Validate the SampleUser in the database
        List<SampleUser> sampleUserList = sampleUserRepository.findAll();
        assertThat(sampleUserList).hasSize(databaseSizeBeforeUpdate);
        SampleUser testSampleUser = sampleUserList.get(sampleUserList.size() - 1);
        assertThat(testSampleUser.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSampleUser() throws Exception {
        int databaseSizeBeforeUpdate = sampleUserRepository.findAll().size();

        // Create the SampleUser

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSampleUserMockMvc.perform(put("/api/sample-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleUser)))
            .andExpect(status().isBadRequest());

        // Validate the SampleUser in the database
        List<SampleUser> sampleUserList = sampleUserRepository.findAll();
        assertThat(sampleUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSampleUser() throws Exception {
        // Initialize the database
        sampleUserRepository.saveAndFlush(sampleUser);

        int databaseSizeBeforeDelete = sampleUserRepository.findAll().size();

        // Get the sampleUser
        restSampleUserMockMvc.perform(delete("/api/sample-users/{id}", sampleUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SampleUser> sampleUserList = sampleUserRepository.findAll();
        assertThat(sampleUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SampleUser.class);
        SampleUser sampleUser1 = new SampleUser();
        sampleUser1.setId(1L);
        SampleUser sampleUser2 = new SampleUser();
        sampleUser2.setId(sampleUser1.getId());
        assertThat(sampleUser1).isEqualTo(sampleUser2);
        sampleUser2.setId(2L);
        assertThat(sampleUser1).isNotEqualTo(sampleUser2);
        sampleUser1.setId(null);
        assertThat(sampleUser1).isNotEqualTo(sampleUser2);
    }
}
