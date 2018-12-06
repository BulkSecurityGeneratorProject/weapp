package com.hntyhb.detection.web.rest;

import com.hntyhb.detection.WeappApp;

import com.hntyhb.detection.domain.Params;
import com.hntyhb.detection.repository.ParamsRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static com.hntyhb.detection.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ParamsResource REST controller.
 *
 * @see ParamsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeappApp.class)
public class ParamsResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_JSON = "AAAAAAAAAA";
    private static final String UPDATED_JSON = "BBBBBBBBBB";

    @Autowired
    private ParamsRepository paramsRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParamsMockMvc;

    private Params params;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParamsResource paramsResource = new ParamsResource(paramsRepository);
        this.restParamsMockMvc = MockMvcBuilders.standaloneSetup(paramsResource)
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
    public static Params createEntity(EntityManager em) {
        Params params = new Params()
            .type(DEFAULT_TYPE)
            .json(DEFAULT_JSON);
        return params;
    }

    @Before
    public void initTest() {
        params = createEntity(em);
    }

    @Test
    @Transactional
    public void createParams() throws Exception {
        int databaseSizeBeforeCreate = paramsRepository.findAll().size();

        // Create the Params
        restParamsMockMvc.perform(post("/api/params")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(params)))
            .andExpect(status().isCreated());

        // Validate the Params in the database
        List<Params> paramsList = paramsRepository.findAll();
        assertThat(paramsList).hasSize(databaseSizeBeforeCreate + 1);
        Params testParams = paramsList.get(paramsList.size() - 1);
        assertThat(testParams.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testParams.getJson()).isEqualTo(DEFAULT_JSON);
    }

    @Test
    @Transactional
    public void createParamsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paramsRepository.findAll().size();

        // Create the Params with an existing ID
        params.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParamsMockMvc.perform(post("/api/params")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(params)))
            .andExpect(status().isBadRequest());

        // Validate the Params in the database
        List<Params> paramsList = paramsRepository.findAll();
        assertThat(paramsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paramsRepository.findAll().size();
        // set the field null
        params.setType(null);

        // Create the Params, which fails.

        restParamsMockMvc.perform(post("/api/params")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(params)))
            .andExpect(status().isBadRequest());

        List<Params> paramsList = paramsRepository.findAll();
        assertThat(paramsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParams() throws Exception {
        // Initialize the database
        paramsRepository.saveAndFlush(params);

        // Get all the paramsList
        restParamsMockMvc.perform(get("/api/params?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(params.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].json").value(hasItem(DEFAULT_JSON.toString())));
    }
    

    @Test
    @Transactional
    public void getParams() throws Exception {
        // Initialize the database
        paramsRepository.saveAndFlush(params);

        // Get the params
        restParamsMockMvc.perform(get("/api/params/{id}", params.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(params.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.json").value(DEFAULT_JSON.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingParams() throws Exception {
        // Get the params
        restParamsMockMvc.perform(get("/api/params/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParams() throws Exception {
        // Initialize the database
        paramsRepository.saveAndFlush(params);

        int databaseSizeBeforeUpdate = paramsRepository.findAll().size();

        // Update the params
        Params updatedParams = paramsRepository.findById(params.getId()).get();
        // Disconnect from session so that the updates on updatedParams are not directly saved in db
        em.detach(updatedParams);
        updatedParams
            .type(UPDATED_TYPE)
            .json(UPDATED_JSON);

        restParamsMockMvc.perform(put("/api/params")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParams)))
            .andExpect(status().isOk());

        // Validate the Params in the database
        List<Params> paramsList = paramsRepository.findAll();
        assertThat(paramsList).hasSize(databaseSizeBeforeUpdate);
        Params testParams = paramsList.get(paramsList.size() - 1);
        assertThat(testParams.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testParams.getJson()).isEqualTo(UPDATED_JSON);
    }

    @Test
    @Transactional
    public void updateNonExistingParams() throws Exception {
        int databaseSizeBeforeUpdate = paramsRepository.findAll().size();

        // Create the Params

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParamsMockMvc.perform(put("/api/params")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(params)))
            .andExpect(status().isBadRequest());

        // Validate the Params in the database
        List<Params> paramsList = paramsRepository.findAll();
        assertThat(paramsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParams() throws Exception {
        // Initialize the database
        paramsRepository.saveAndFlush(params);

        int databaseSizeBeforeDelete = paramsRepository.findAll().size();

        // Get the params
        restParamsMockMvc.perform(delete("/api/params/{id}", params.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Params> paramsList = paramsRepository.findAll();
        assertThat(paramsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Params.class);
        Params params1 = new Params();
        params1.setId(1L);
        Params params2 = new Params();
        params2.setId(params1.getId());
        assertThat(params1).isEqualTo(params2);
        params2.setId(2L);
        assertThat(params1).isNotEqualTo(params2);
        params1.setId(null);
        assertThat(params1).isNotEqualTo(params2);
    }
}
