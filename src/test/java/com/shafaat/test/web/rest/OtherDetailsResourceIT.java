package com.shafaat.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shafaat.test.IntegrationTest;
import com.shafaat.test.domain.OtherDetails;
import com.shafaat.test.repository.OtherDetailsRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link OtherDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OtherDetailsResourceIT {

    private static final String DEFAULT_MANDATOR_COLUMN = "AAAAAAAAAA";
    private static final String UPDATED_MANDATOR_COLUMN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_HUB_USAGE_REQD = false;
    private static final Boolean UPDATED_IS_HUB_USAGE_REQD = true;

    private static final String DEFAULT_INSERT_CHARS = "AAAAAAAAAA";
    private static final String UPDATED_INSERT_CHARS = "BBBBBBBBBB";

    private static final String DEFAULT_TABLE_ACCESS_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_ACCESS_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_ONE_WMP_VIEW = "AAAAAAAAAA";
    private static final String UPDATED_ONE_WMP_VIEW = "BBBBBBBBBB";

    private static final Long DEFAULT_ORDER_ID = 1L;
    private static final Long UPDATED_ORDER_ID = 2L;

    private static final String ENTITY_API_URL = "/api/other-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OtherDetailsRepository otherDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOtherDetailsMockMvc;

    private OtherDetails otherDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtherDetails createEntity(EntityManager em) {
        OtherDetails otherDetails = new OtherDetails()
            .mandatorColumn(DEFAULT_MANDATOR_COLUMN)
            .isHubUsageReqd(DEFAULT_IS_HUB_USAGE_REQD)
            .insertChars(DEFAULT_INSERT_CHARS)
            .tableAccessMethod(DEFAULT_TABLE_ACCESS_METHOD)
            .oneWmpView(DEFAULT_ONE_WMP_VIEW)
            .orderId(DEFAULT_ORDER_ID);
        return otherDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtherDetails createUpdatedEntity(EntityManager em) {
        OtherDetails otherDetails = new OtherDetails()
            .mandatorColumn(UPDATED_MANDATOR_COLUMN)
            .isHubUsageReqd(UPDATED_IS_HUB_USAGE_REQD)
            .insertChars(UPDATED_INSERT_CHARS)
            .tableAccessMethod(UPDATED_TABLE_ACCESS_METHOD)
            .oneWmpView(UPDATED_ONE_WMP_VIEW)
            .orderId(UPDATED_ORDER_ID);
        return otherDetails;
    }

    @BeforeEach
    public void initTest() {
        otherDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createOtherDetails() throws Exception {
        int databaseSizeBeforeCreate = otherDetailsRepository.findAll().size();
        // Create the OtherDetails
        restOtherDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(otherDetails)))
            .andExpect(status().isCreated());

        // Validate the OtherDetails in the database
        List<OtherDetails> otherDetailsList = otherDetailsRepository.findAll();
        assertThat(otherDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        OtherDetails testOtherDetails = otherDetailsList.get(otherDetailsList.size() - 1);
        assertThat(testOtherDetails.getMandatorColumn()).isEqualTo(DEFAULT_MANDATOR_COLUMN);
        assertThat(testOtherDetails.getIsHubUsageReqd()).isEqualTo(DEFAULT_IS_HUB_USAGE_REQD);
        assertThat(testOtherDetails.getInsertChars()).isEqualTo(DEFAULT_INSERT_CHARS);
        assertThat(testOtherDetails.getTableAccessMethod()).isEqualTo(DEFAULT_TABLE_ACCESS_METHOD);
        assertThat(testOtherDetails.getOneWmpView()).isEqualTo(DEFAULT_ONE_WMP_VIEW);
        assertThat(testOtherDetails.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
    }

    @Test
    @Transactional
    void createOtherDetailsWithExistingId() throws Exception {
        // Create the OtherDetails with an existing ID
        otherDetails.setId(1L);

        int databaseSizeBeforeCreate = otherDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOtherDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(otherDetails)))
            .andExpect(status().isBadRequest());

        // Validate the OtherDetails in the database
        List<OtherDetails> otherDetailsList = otherDetailsRepository.findAll();
        assertThat(otherDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOtherDetails() throws Exception {
        // Initialize the database
        otherDetailsRepository.saveAndFlush(otherDetails);

        // Get all the otherDetailsList
        restOtherDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(otherDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].mandatorColumn").value(hasItem(DEFAULT_MANDATOR_COLUMN)))
            .andExpect(jsonPath("$.[*].isHubUsageReqd").value(hasItem(DEFAULT_IS_HUB_USAGE_REQD.booleanValue())))
            .andExpect(jsonPath("$.[*].insertChars").value(hasItem(DEFAULT_INSERT_CHARS)))
            .andExpect(jsonPath("$.[*].tableAccessMethod").value(hasItem(DEFAULT_TABLE_ACCESS_METHOD)))
            .andExpect(jsonPath("$.[*].oneWmpView").value(hasItem(DEFAULT_ONE_WMP_VIEW)))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID.intValue())));
    }

    @Test
    @Transactional
    void getOtherDetails() throws Exception {
        // Initialize the database
        otherDetailsRepository.saveAndFlush(otherDetails);

        // Get the otherDetails
        restOtherDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, otherDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(otherDetails.getId().intValue()))
            .andExpect(jsonPath("$.mandatorColumn").value(DEFAULT_MANDATOR_COLUMN))
            .andExpect(jsonPath("$.isHubUsageReqd").value(DEFAULT_IS_HUB_USAGE_REQD.booleanValue()))
            .andExpect(jsonPath("$.insertChars").value(DEFAULT_INSERT_CHARS))
            .andExpect(jsonPath("$.tableAccessMethod").value(DEFAULT_TABLE_ACCESS_METHOD))
            .andExpect(jsonPath("$.oneWmpView").value(DEFAULT_ONE_WMP_VIEW))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOtherDetails() throws Exception {
        // Get the otherDetails
        restOtherDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOtherDetails() throws Exception {
        // Initialize the database
        otherDetailsRepository.saveAndFlush(otherDetails);

        int databaseSizeBeforeUpdate = otherDetailsRepository.findAll().size();

        // Update the otherDetails
        OtherDetails updatedOtherDetails = otherDetailsRepository.findById(otherDetails.getId()).get();
        // Disconnect from session so that the updates on updatedOtherDetails are not directly saved in db
        em.detach(updatedOtherDetails);
        updatedOtherDetails
            .mandatorColumn(UPDATED_MANDATOR_COLUMN)
            .isHubUsageReqd(UPDATED_IS_HUB_USAGE_REQD)
            .insertChars(UPDATED_INSERT_CHARS)
            .tableAccessMethod(UPDATED_TABLE_ACCESS_METHOD)
            .oneWmpView(UPDATED_ONE_WMP_VIEW)
            .orderId(UPDATED_ORDER_ID);

        restOtherDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOtherDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOtherDetails))
            )
            .andExpect(status().isOk());

        // Validate the OtherDetails in the database
        List<OtherDetails> otherDetailsList = otherDetailsRepository.findAll();
        assertThat(otherDetailsList).hasSize(databaseSizeBeforeUpdate);
        OtherDetails testOtherDetails = otherDetailsList.get(otherDetailsList.size() - 1);
        assertThat(testOtherDetails.getMandatorColumn()).isEqualTo(UPDATED_MANDATOR_COLUMN);
        assertThat(testOtherDetails.getIsHubUsageReqd()).isEqualTo(UPDATED_IS_HUB_USAGE_REQD);
        assertThat(testOtherDetails.getInsertChars()).isEqualTo(UPDATED_INSERT_CHARS);
        assertThat(testOtherDetails.getTableAccessMethod()).isEqualTo(UPDATED_TABLE_ACCESS_METHOD);
        assertThat(testOtherDetails.getOneWmpView()).isEqualTo(UPDATED_ONE_WMP_VIEW);
        assertThat(testOtherDetails.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void putNonExistingOtherDetails() throws Exception {
        int databaseSizeBeforeUpdate = otherDetailsRepository.findAll().size();
        otherDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtherDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, otherDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otherDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtherDetails in the database
        List<OtherDetails> otherDetailsList = otherDetailsRepository.findAll();
        assertThat(otherDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOtherDetails() throws Exception {
        int databaseSizeBeforeUpdate = otherDetailsRepository.findAll().size();
        otherDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtherDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otherDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtherDetails in the database
        List<OtherDetails> otherDetailsList = otherDetailsRepository.findAll();
        assertThat(otherDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOtherDetails() throws Exception {
        int databaseSizeBeforeUpdate = otherDetailsRepository.findAll().size();
        otherDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtherDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(otherDetails)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OtherDetails in the database
        List<OtherDetails> otherDetailsList = otherDetailsRepository.findAll();
        assertThat(otherDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOtherDetailsWithPatch() throws Exception {
        // Initialize the database
        otherDetailsRepository.saveAndFlush(otherDetails);

        int databaseSizeBeforeUpdate = otherDetailsRepository.findAll().size();

        // Update the otherDetails using partial update
        OtherDetails partialUpdatedOtherDetails = new OtherDetails();
        partialUpdatedOtherDetails.setId(otherDetails.getId());

        partialUpdatedOtherDetails.isHubUsageReqd(UPDATED_IS_HUB_USAGE_REQD).orderId(UPDATED_ORDER_ID);

        restOtherDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOtherDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOtherDetails))
            )
            .andExpect(status().isOk());

        // Validate the OtherDetails in the database
        List<OtherDetails> otherDetailsList = otherDetailsRepository.findAll();
        assertThat(otherDetailsList).hasSize(databaseSizeBeforeUpdate);
        OtherDetails testOtherDetails = otherDetailsList.get(otherDetailsList.size() - 1);
        assertThat(testOtherDetails.getMandatorColumn()).isEqualTo(DEFAULT_MANDATOR_COLUMN);
        assertThat(testOtherDetails.getIsHubUsageReqd()).isEqualTo(UPDATED_IS_HUB_USAGE_REQD);
        assertThat(testOtherDetails.getInsertChars()).isEqualTo(DEFAULT_INSERT_CHARS);
        assertThat(testOtherDetails.getTableAccessMethod()).isEqualTo(DEFAULT_TABLE_ACCESS_METHOD);
        assertThat(testOtherDetails.getOneWmpView()).isEqualTo(DEFAULT_ONE_WMP_VIEW);
        assertThat(testOtherDetails.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void fullUpdateOtherDetailsWithPatch() throws Exception {
        // Initialize the database
        otherDetailsRepository.saveAndFlush(otherDetails);

        int databaseSizeBeforeUpdate = otherDetailsRepository.findAll().size();

        // Update the otherDetails using partial update
        OtherDetails partialUpdatedOtherDetails = new OtherDetails();
        partialUpdatedOtherDetails.setId(otherDetails.getId());

        partialUpdatedOtherDetails
            .mandatorColumn(UPDATED_MANDATOR_COLUMN)
            .isHubUsageReqd(UPDATED_IS_HUB_USAGE_REQD)
            .insertChars(UPDATED_INSERT_CHARS)
            .tableAccessMethod(UPDATED_TABLE_ACCESS_METHOD)
            .oneWmpView(UPDATED_ONE_WMP_VIEW)
            .orderId(UPDATED_ORDER_ID);

        restOtherDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOtherDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOtherDetails))
            )
            .andExpect(status().isOk());

        // Validate the OtherDetails in the database
        List<OtherDetails> otherDetailsList = otherDetailsRepository.findAll();
        assertThat(otherDetailsList).hasSize(databaseSizeBeforeUpdate);
        OtherDetails testOtherDetails = otherDetailsList.get(otherDetailsList.size() - 1);
        assertThat(testOtherDetails.getMandatorColumn()).isEqualTo(UPDATED_MANDATOR_COLUMN);
        assertThat(testOtherDetails.getIsHubUsageReqd()).isEqualTo(UPDATED_IS_HUB_USAGE_REQD);
        assertThat(testOtherDetails.getInsertChars()).isEqualTo(UPDATED_INSERT_CHARS);
        assertThat(testOtherDetails.getTableAccessMethod()).isEqualTo(UPDATED_TABLE_ACCESS_METHOD);
        assertThat(testOtherDetails.getOneWmpView()).isEqualTo(UPDATED_ONE_WMP_VIEW);
        assertThat(testOtherDetails.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingOtherDetails() throws Exception {
        int databaseSizeBeforeUpdate = otherDetailsRepository.findAll().size();
        otherDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtherDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, otherDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(otherDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtherDetails in the database
        List<OtherDetails> otherDetailsList = otherDetailsRepository.findAll();
        assertThat(otherDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOtherDetails() throws Exception {
        int databaseSizeBeforeUpdate = otherDetailsRepository.findAll().size();
        otherDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtherDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(otherDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtherDetails in the database
        List<OtherDetails> otherDetailsList = otherDetailsRepository.findAll();
        assertThat(otherDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOtherDetails() throws Exception {
        int databaseSizeBeforeUpdate = otherDetailsRepository.findAll().size();
        otherDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtherDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(otherDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OtherDetails in the database
        List<OtherDetails> otherDetailsList = otherDetailsRepository.findAll();
        assertThat(otherDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOtherDetails() throws Exception {
        // Initialize the database
        otherDetailsRepository.saveAndFlush(otherDetails);

        int databaseSizeBeforeDelete = otherDetailsRepository.findAll().size();

        // Delete the otherDetails
        restOtherDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, otherDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OtherDetails> otherDetailsList = otherDetailsRepository.findAll();
        assertThat(otherDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
