package com.shafaat.test.web.rest;

import com.shafaat.test.TestAppApp;
import com.shafaat.test.domain.RequestState;
import com.shafaat.test.repository.RequestStateRepository;
import com.shafaat.test.service.RequestStateService;
import com.shafaat.test.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.shafaat.test.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RequestStateResource} REST controller.
 */
@SpringBootTest(classes = TestAppApp.class)
public class RequestStateResourceIT {

    private static final Long DEFAULT_REQUEST_ID = 1L;
    private static final Long UPDATED_REQUEST_ID = 2L;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RequestStateRepository requestStateRepository;

    @Autowired
    private RequestStateService requestStateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRequestStateMockMvc;

    private RequestState requestState;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequestStateResource requestStateResource = new RequestStateResource(requestStateService);
        this.restRequestStateMockMvc = MockMvcBuilders.standaloneSetup(requestStateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestState createEntity(EntityManager em) {
        RequestState requestState = new RequestState()
            .requestId(DEFAULT_REQUEST_ID)
            .notes(DEFAULT_NOTES)
            .status(DEFAULT_STATUS)
            .dueDate(DEFAULT_DUE_DATE);
        return requestState;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestState createUpdatedEntity(EntityManager em) {
        RequestState requestState = new RequestState()
            .requestId(UPDATED_REQUEST_ID)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS)
            .dueDate(UPDATED_DUE_DATE);
        return requestState;
    }

    @BeforeEach
    public void initTest() {
        requestState = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequestState() throws Exception {
        int databaseSizeBeforeCreate = requestStateRepository.findAll().size();

        // Create the RequestState
        restRequestStateMockMvc.perform(post("/api/request-states")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requestState)))
            .andExpect(status().isCreated());

        // Validate the RequestState in the database
        List<RequestState> requestStateList = requestStateRepository.findAll();
        assertThat(requestStateList).hasSize(databaseSizeBeforeCreate + 1);
        RequestState testRequestState = requestStateList.get(requestStateList.size() - 1);
        assertThat(testRequestState.getRequestId()).isEqualTo(DEFAULT_REQUEST_ID);
        assertThat(testRequestState.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testRequestState.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRequestState.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
    }

    @Test
    @Transactional
    public void createRequestStateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestStateRepository.findAll().size();

        // Create the RequestState with an existing ID
        requestState.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestStateMockMvc.perform(post("/api/request-states")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requestState)))
            .andExpect(status().isBadRequest());

        // Validate the RequestState in the database
        List<RequestState> requestStateList = requestStateRepository.findAll();
        assertThat(requestStateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRequestStates() throws Exception {
        // Initialize the database
        requestStateRepository.saveAndFlush(requestState);

        // Get all the requestStateList
        restRequestStateMockMvc.perform(get("/api/request-states?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestState.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestId").value(hasItem(DEFAULT_REQUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getRequestState() throws Exception {
        // Initialize the database
        requestStateRepository.saveAndFlush(requestState);

        // Get the requestState
        restRequestStateMockMvc.perform(get("/api/request-states/{id}", requestState.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestState.getId().intValue()))
            .andExpect(jsonPath("$.requestId").value(DEFAULT_REQUEST_ID.intValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRequestState() throws Exception {
        // Get the requestState
        restRequestStateMockMvc.perform(get("/api/request-states/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequestState() throws Exception {
        // Initialize the database
        requestStateService.save(requestState);

        int databaseSizeBeforeUpdate = requestStateRepository.findAll().size();

        // Update the requestState
        RequestState updatedRequestState = requestStateRepository.findById(requestState.getId()).get();
        // Disconnect from session so that the updates on updatedRequestState are not directly saved in db
        em.detach(updatedRequestState);
        updatedRequestState
            .requestId(UPDATED_REQUEST_ID)
            .notes(UPDATED_NOTES)
            .status(UPDATED_STATUS)
            .dueDate(UPDATED_DUE_DATE);

        restRequestStateMockMvc.perform(put("/api/request-states")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequestState)))
            .andExpect(status().isOk());

        // Validate the RequestState in the database
        List<RequestState> requestStateList = requestStateRepository.findAll();
        assertThat(requestStateList).hasSize(databaseSizeBeforeUpdate);
        RequestState testRequestState = requestStateList.get(requestStateList.size() - 1);
        assertThat(testRequestState.getRequestId()).isEqualTo(UPDATED_REQUEST_ID);
        assertThat(testRequestState.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testRequestState.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRequestState.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRequestState() throws Exception {
        int databaseSizeBeforeUpdate = requestStateRepository.findAll().size();

        // Create the RequestState

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestStateMockMvc.perform(put("/api/request-states")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requestState)))
            .andExpect(status().isBadRequest());

        // Validate the RequestState in the database
        List<RequestState> requestStateList = requestStateRepository.findAll();
        assertThat(requestStateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRequestState() throws Exception {
        // Initialize the database
        requestStateService.save(requestState);

        int databaseSizeBeforeDelete = requestStateRepository.findAll().size();

        // Delete the requestState
        restRequestStateMockMvc.perform(delete("/api/request-states/{id}", requestState.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequestState> requestStateList = requestStateRepository.findAll();
        assertThat(requestStateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
