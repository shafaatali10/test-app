package com.shafaat.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shafaat.test.IntegrationTest;
import com.shafaat.test.domain.RequestState;
import com.shafaat.test.repository.RequestStateRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RequestStateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestStateResourceIT {

    private static final Long DEFAULT_REQUEST_ID = 1L;
    private static final Long UPDATED_REQUEST_ID = 2L;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/request-states";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequestStateRepository requestStateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestStateMockMvc;

    private RequestState requestState;

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
    void createRequestState() throws Exception {
        int databaseSizeBeforeCreate = requestStateRepository.findAll().size();
        // Create the RequestState
        restRequestStateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestState)))
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
    void createRequestStateWithExistingId() throws Exception {
        // Create the RequestState with an existing ID
        requestState.setId(1L);

        int databaseSizeBeforeCreate = requestStateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestStateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestState)))
            .andExpect(status().isBadRequest());

        // Validate the RequestState in the database
        List<RequestState> requestStateList = requestStateRepository.findAll();
        assertThat(requestStateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRequestStates() throws Exception {
        // Initialize the database
        requestStateRepository.saveAndFlush(requestState);

        // Get all the requestStateList
        restRequestStateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
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
    void getRequestState() throws Exception {
        // Initialize the database
        requestStateRepository.saveAndFlush(requestState);

        // Get the requestState
        restRequestStateMockMvc
            .perform(get(ENTITY_API_URL_ID, requestState.getId()))
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
    void getNonExistingRequestState() throws Exception {
        // Get the requestState
        restRequestStateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequestState() throws Exception {
        // Initialize the database
        requestStateRepository.saveAndFlush(requestState);

        int databaseSizeBeforeUpdate = requestStateRepository.findAll().size();

        // Update the requestState
        RequestState updatedRequestState = requestStateRepository.findById(requestState.getId()).get();
        // Disconnect from session so that the updates on updatedRequestState are not directly saved in db
        em.detach(updatedRequestState);
        updatedRequestState.requestId(UPDATED_REQUEST_ID).notes(UPDATED_NOTES).status(UPDATED_STATUS).dueDate(UPDATED_DUE_DATE);

        restRequestStateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRequestState.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRequestState))
            )
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
    void putNonExistingRequestState() throws Exception {
        int databaseSizeBeforeUpdate = requestStateRepository.findAll().size();
        requestState.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestStateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestState.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestState))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestState in the database
        List<RequestState> requestStateList = requestStateRepository.findAll();
        assertThat(requestStateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestState() throws Exception {
        int databaseSizeBeforeUpdate = requestStateRepository.findAll().size();
        requestState.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestStateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestState))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestState in the database
        List<RequestState> requestStateList = requestStateRepository.findAll();
        assertThat(requestStateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestState() throws Exception {
        int databaseSizeBeforeUpdate = requestStateRepository.findAll().size();
        requestState.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestStateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestState)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestState in the database
        List<RequestState> requestStateList = requestStateRepository.findAll();
        assertThat(requestStateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestStateWithPatch() throws Exception {
        // Initialize the database
        requestStateRepository.saveAndFlush(requestState);

        int databaseSizeBeforeUpdate = requestStateRepository.findAll().size();

        // Update the requestState using partial update
        RequestState partialUpdatedRequestState = new RequestState();
        partialUpdatedRequestState.setId(requestState.getId());

        partialUpdatedRequestState.notes(UPDATED_NOTES).status(UPDATED_STATUS).dueDate(UPDATED_DUE_DATE);

        restRequestStateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestState.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestState))
            )
            .andExpect(status().isOk());

        // Validate the RequestState in the database
        List<RequestState> requestStateList = requestStateRepository.findAll();
        assertThat(requestStateList).hasSize(databaseSizeBeforeUpdate);
        RequestState testRequestState = requestStateList.get(requestStateList.size() - 1);
        assertThat(testRequestState.getRequestId()).isEqualTo(DEFAULT_REQUEST_ID);
        assertThat(testRequestState.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testRequestState.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRequestState.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateRequestStateWithPatch() throws Exception {
        // Initialize the database
        requestStateRepository.saveAndFlush(requestState);

        int databaseSizeBeforeUpdate = requestStateRepository.findAll().size();

        // Update the requestState using partial update
        RequestState partialUpdatedRequestState = new RequestState();
        partialUpdatedRequestState.setId(requestState.getId());

        partialUpdatedRequestState.requestId(UPDATED_REQUEST_ID).notes(UPDATED_NOTES).status(UPDATED_STATUS).dueDate(UPDATED_DUE_DATE);

        restRequestStateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestState.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestState))
            )
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
    void patchNonExistingRequestState() throws Exception {
        int databaseSizeBeforeUpdate = requestStateRepository.findAll().size();
        requestState.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestStateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestState.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestState))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestState in the database
        List<RequestState> requestStateList = requestStateRepository.findAll();
        assertThat(requestStateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestState() throws Exception {
        int databaseSizeBeforeUpdate = requestStateRepository.findAll().size();
        requestState.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestStateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestState))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestState in the database
        List<RequestState> requestStateList = requestStateRepository.findAll();
        assertThat(requestStateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestState() throws Exception {
        int databaseSizeBeforeUpdate = requestStateRepository.findAll().size();
        requestState.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestStateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(requestState))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestState in the database
        List<RequestState> requestStateList = requestStateRepository.findAll();
        assertThat(requestStateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestState() throws Exception {
        // Initialize the database
        requestStateRepository.saveAndFlush(requestState);

        int databaseSizeBeforeDelete = requestStateRepository.findAll().size();

        // Delete the requestState
        restRequestStateMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestState.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequestState> requestStateList = requestStateRepository.findAll();
        assertThat(requestStateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
