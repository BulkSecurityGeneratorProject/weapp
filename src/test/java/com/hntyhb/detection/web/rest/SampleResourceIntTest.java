package com.hntyhb.detection.web.rest;

import com.hntyhb.detection.WeappApp;

import com.hntyhb.detection.domain.Sample;
import com.hntyhb.detection.repository.SampleRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.hntyhb.detection.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hntyhb.detection.domain.enumeration.Status;
/**
 * Test class for the SampleResource REST controller.
 *
 * @see SampleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeappApp.class)
public class SampleResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.PLAN;
    private static final Status UPDATED_STATUS = Status.PROCESS;

    private static final LocalDate DEFAULT_SAMPLE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SAMPLE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_DQY = 1F;
    private static final Float UPDATED_DQY = 2F;

    private static final String DEFAULT_FX = "AAAAAAAAAA";
    private static final String UPDATED_FX = "BBBBBBBBBB";

    private static final Float DEFAULT_FS = 1F;
    private static final Float UPDATED_FS = 2F;

    private static final Float DEFAULT_WD = 1F;
    private static final Float UPDATED_WD = 2F;

    private static final Float DEFAULT_XDSD = 1F;
    private static final Float UPDATED_XDSD = 2F;

    @Autowired
    private SampleRepository sampleRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSampleMockMvc;

    private Sample sample;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SampleResource sampleResource = new SampleResource(sampleRepository);
        this.restSampleMockMvc = MockMvcBuilders.standaloneSetup(sampleResource)
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
    public static Sample createEntity(EntityManager em) {
        Sample sample = new Sample()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .sampleDate(DEFAULT_SAMPLE_DATE)
            .dqy(DEFAULT_DQY)
            .fx(DEFAULT_FX)
            .fs(DEFAULT_FS)
            .wd(DEFAULT_WD)
            .xdsd(DEFAULT_XDSD);
        return sample;
    }

    @Before
    public void initTest() {
        sample = createEntity(em);
    }

    @Test
    @Transactional
    public void createSample() throws Exception {
        int databaseSizeBeforeCreate = sampleRepository.findAll().size();

        // Create the Sample
        restSampleMockMvc.perform(post("/api/samples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isCreated());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeCreate + 1);
        Sample testSample = sampleList.get(sampleList.size() - 1);
        assertThat(testSample.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSample.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSample.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSample.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSample.getSampleDate()).isEqualTo(DEFAULT_SAMPLE_DATE);
        assertThat(testSample.getDqy()).isEqualTo(DEFAULT_DQY);
        assertThat(testSample.getFx()).isEqualTo(DEFAULT_FX);
        assertThat(testSample.getFs()).isEqualTo(DEFAULT_FS);
        assertThat(testSample.getWd()).isEqualTo(DEFAULT_WD);
        assertThat(testSample.getXdsd()).isEqualTo(DEFAULT_XDSD);
    }

    @Test
    @Transactional
    public void createSampleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sampleRepository.findAll().size();

        // Create the Sample with an existing ID
        sample.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSampleMockMvc.perform(post("/api/samples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isBadRequest());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sampleRepository.findAll().size();
        // set the field null
        sample.setCode(null);

        // Create the Sample, which fails.

        restSampleMockMvc.perform(post("/api/samples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isBadRequest());

        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sampleRepository.findAll().size();
        // set the field null
        sample.setName(null);

        // Create the Sample, which fails.

        restSampleMockMvc.perform(post("/api/samples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isBadRequest());

        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sampleRepository.findAll().size();
        // set the field null
        sample.setType(null);

        // Create the Sample, which fails.

        restSampleMockMvc.perform(post("/api/samples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isBadRequest());

        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = sampleRepository.findAll().size();
        // set the field null
        sample.setStatus(null);

        // Create the Sample, which fails.

        restSampleMockMvc.perform(post("/api/samples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isBadRequest());

        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSamples() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        // Get all the sampleList
        restSampleMockMvc.perform(get("/api/samples?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sample.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].sampleDate").value(hasItem(DEFAULT_SAMPLE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dqy").value(hasItem(DEFAULT_DQY.doubleValue())))
            .andExpect(jsonPath("$.[*].fx").value(hasItem(DEFAULT_FX.toString())))
            .andExpect(jsonPath("$.[*].fs").value(hasItem(DEFAULT_FS.doubleValue())))
            .andExpect(jsonPath("$.[*].wd").value(hasItem(DEFAULT_WD.doubleValue())))
            .andExpect(jsonPath("$.[*].xdsd").value(hasItem(DEFAULT_XDSD.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getSample() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        // Get the sample
        restSampleMockMvc.perform(get("/api/samples/{id}", sample.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sample.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.sampleDate").value(DEFAULT_SAMPLE_DATE.toString()))
            .andExpect(jsonPath("$.dqy").value(DEFAULT_DQY.doubleValue()))
            .andExpect(jsonPath("$.fx").value(DEFAULT_FX.toString()))
            .andExpect(jsonPath("$.fs").value(DEFAULT_FS.doubleValue()))
            .andExpect(jsonPath("$.wd").value(DEFAULT_WD.doubleValue()))
            .andExpect(jsonPath("$.xdsd").value(DEFAULT_XDSD.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSample() throws Exception {
        // Get the sample
        restSampleMockMvc.perform(get("/api/samples/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSample() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();

        // Update the sample
        Sample updatedSample = sampleRepository.findById(sample.getId()).get();
        // Disconnect from session so that the updates on updatedSample are not directly saved in db
        em.detach(updatedSample);
        updatedSample
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .sampleDate(UPDATED_SAMPLE_DATE)
            .dqy(UPDATED_DQY)
            .fx(UPDATED_FX)
            .fs(UPDATED_FS)
            .wd(UPDATED_WD)
            .xdsd(UPDATED_XDSD);

        restSampleMockMvc.perform(put("/api/samples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSample)))
            .andExpect(status().isOk());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
        Sample testSample = sampleList.get(sampleList.size() - 1);
        assertThat(testSample.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSample.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSample.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSample.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSample.getSampleDate()).isEqualTo(UPDATED_SAMPLE_DATE);
        assertThat(testSample.getDqy()).isEqualTo(UPDATED_DQY);
        assertThat(testSample.getFx()).isEqualTo(UPDATED_FX);
        assertThat(testSample.getFs()).isEqualTo(UPDATED_FS);
        assertThat(testSample.getWd()).isEqualTo(UPDATED_WD);
        assertThat(testSample.getXdsd()).isEqualTo(UPDATED_XDSD);
    }

    @Test
    @Transactional
    public void updateNonExistingSample() throws Exception {
        int databaseSizeBeforeUpdate = sampleRepository.findAll().size();

        // Create the Sample

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSampleMockMvc.perform(put("/api/samples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sample)))
            .andExpect(status().isBadRequest());

        // Validate the Sample in the database
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSample() throws Exception {
        // Initialize the database
        sampleRepository.saveAndFlush(sample);

        int databaseSizeBeforeDelete = sampleRepository.findAll().size();

        // Get the sample
        restSampleMockMvc.perform(delete("/api/samples/{id}", sample.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sample> sampleList = sampleRepository.findAll();
        assertThat(sampleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sample.class);
        Sample sample1 = new Sample();
        sample1.setId(1L);
        Sample sample2 = new Sample();
        sample2.setId(sample1.getId());
        assertThat(sample1).isEqualTo(sample2);
        sample2.setId(2L);
        assertThat(sample1).isNotEqualTo(sample2);
        sample1.setId(null);
        assertThat(sample1).isNotEqualTo(sample2);
    }
}
