package com.shafaat.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shafaat.test.IntegrationTest;
import com.shafaat.test.domain.GeneralData;
import com.shafaat.test.repository.GeneralDataRepository;
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
 * Integration tests for the {@link GeneralDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GeneralDataResourceIT {

    private static final String DEFAULT_TABLE_USAGE = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_USAGE = "BBBBBBBBBB";

    private static final String DEFAULT_DB_SELECTION = "AAAAAAAAAA";
    private static final String UPDATED_DB_SELECTION = "BBBBBBBBBB";

    private static final String DEFAULT_TABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_DATA_MORE_THAN_5_MILLION = false;
    private static final Boolean UPDATED_HAS_DATA_MORE_THAN_5_MILLION = true;

    private static final Boolean DEFAULT_IS_PARALLELIZATION_REQD = false;
    private static final Boolean UPDATED_IS_PARALLELIZATION_REQD = true;

    private static final String DEFAULT_RECOVERY_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_RECOVERY_CLASS = "BBBBBBBBBB";

    private static final Long DEFAULT_ORDER_ID = 1L;
    private static final Long UPDATED_ORDER_ID = 2L;

    private static final String ENTITY_API_URL = "/api/general-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GeneralDataRepository generalDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeneralDataMockMvc;

    private GeneralData generalData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeneralData createEntity(EntityManager em) {
        GeneralData generalData = new GeneralData()
            .tableUsage(DEFAULT_TABLE_USAGE)
            .dbSelection(DEFAULT_DB_SELECTION)
            .tableName(DEFAULT_TABLE_NAME)
            .hasDataMoreThan5Million(DEFAULT_HAS_DATA_MORE_THAN_5_MILLION)
            .isParallelizationReqd(DEFAULT_IS_PARALLELIZATION_REQD)
            .recoveryClass(DEFAULT_RECOVERY_CLASS)
            .orderId(DEFAULT_ORDER_ID);
        return generalData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeneralData createUpdatedEntity(EntityManager em) {
        GeneralData generalData = new GeneralData()
            .tableUsage(UPDATED_TABLE_USAGE)
            .dbSelection(UPDATED_DB_SELECTION)
            .tableName(UPDATED_TABLE_NAME)
            .hasDataMoreThan5Million(UPDATED_HAS_DATA_MORE_THAN_5_MILLION)
            .isParallelizationReqd(UPDATED_IS_PARALLELIZATION_REQD)
            .recoveryClass(UPDATED_RECOVERY_CLASS)
            .orderId(UPDATED_ORDER_ID);
        return generalData;
    }

    @BeforeEach
    public void initTest() {
        generalData = createEntity(em);
    }

    @Test
    @Transactional
    void createGeneralData() throws Exception {
        int databaseSizeBeforeCreate = generalDataRepository.findAll().size();
        // Create the GeneralData
        restGeneralDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generalData)))
            .andExpect(status().isCreated());

        // Validate the GeneralData in the database
        List<GeneralData> generalDataList = generalDataRepository.findAll();
        assertThat(generalDataList).hasSize(databaseSizeBeforeCreate + 1);
        GeneralData testGeneralData = generalDataList.get(generalDataList.size() - 1);
        assertThat(testGeneralData.getTableUsage()).isEqualTo(DEFAULT_TABLE_USAGE);
        assertThat(testGeneralData.getDbSelection()).isEqualTo(DEFAULT_DB_SELECTION);
        assertThat(testGeneralData.getTableName()).isEqualTo(DEFAULT_TABLE_NAME);
        assertThat(testGeneralData.getHasDataMoreThan5Million()).isEqualTo(DEFAULT_HAS_DATA_MORE_THAN_5_MILLION);
        assertThat(testGeneralData.getIsParallelizationReqd()).isEqualTo(DEFAULT_IS_PARALLELIZATION_REQD);
        assertThat(testGeneralData.getRecoveryClass()).isEqualTo(DEFAULT_RECOVERY_CLASS);
        assertThat(testGeneralData.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
    }

    @Test
    @Transactional
    void createGeneralDataWithExistingId() throws Exception {
        // Create the GeneralData with an existing ID
        generalData.setId(1L);

        int databaseSizeBeforeCreate = generalDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeneralDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generalData)))
            .andExpect(status().isBadRequest());

        // Validate the GeneralData in the database
        List<GeneralData> generalDataList = generalDataRepository.findAll();
        assertThat(generalDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGeneralData() throws Exception {
        // Initialize the database
        generalDataRepository.saveAndFlush(generalData);

        // Get all the generalDataList
        restGeneralDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(generalData.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableUsage").value(hasItem(DEFAULT_TABLE_USAGE)))
            .andExpect(jsonPath("$.[*].dbSelection").value(hasItem(DEFAULT_DB_SELECTION)))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME)))
            .andExpect(jsonPath("$.[*].hasDataMoreThan5Million").value(hasItem(DEFAULT_HAS_DATA_MORE_THAN_5_MILLION.booleanValue())))
            .andExpect(jsonPath("$.[*].isParallelizationReqd").value(hasItem(DEFAULT_IS_PARALLELIZATION_REQD.booleanValue())))
            .andExpect(jsonPath("$.[*].recoveryClass").value(hasItem(DEFAULT_RECOVERY_CLASS)))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID.intValue())));
    }

    @Test
    @Transactional
    void getGeneralData() throws Exception {
        // Initialize the database
        generalDataRepository.saveAndFlush(generalData);

        // Get the generalData
        restGeneralDataMockMvc
            .perform(get(ENTITY_API_URL_ID, generalData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(generalData.getId().intValue()))
            .andExpect(jsonPath("$.tableUsage").value(DEFAULT_TABLE_USAGE))
            .andExpect(jsonPath("$.dbSelection").value(DEFAULT_DB_SELECTION))
            .andExpect(jsonPath("$.tableName").value(DEFAULT_TABLE_NAME))
            .andExpect(jsonPath("$.hasDataMoreThan5Million").value(DEFAULT_HAS_DATA_MORE_THAN_5_MILLION.booleanValue()))
            .andExpect(jsonPath("$.isParallelizationReqd").value(DEFAULT_IS_PARALLELIZATION_REQD.booleanValue()))
            .andExpect(jsonPath("$.recoveryClass").value(DEFAULT_RECOVERY_CLASS))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingGeneralData() throws Exception {
        // Get the generalData
        restGeneralDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGeneralData() throws Exception {
        // Initialize the database
        generalDataRepository.saveAndFlush(generalData);

        int databaseSizeBeforeUpdate = generalDataRepository.findAll().size();

        // Update the generalData
        GeneralData updatedGeneralData = generalDataRepository.findById(generalData.getId()).get();
        // Disconnect from session so that the updates on updatedGeneralData are not directly saved in db
        em.detach(updatedGeneralData);
        updatedGeneralData
            .tableUsage(UPDATED_TABLE_USAGE)
            .dbSelection(UPDATED_DB_SELECTION)
            .tableName(UPDATED_TABLE_NAME)
            .hasDataMoreThan5Million(UPDATED_HAS_DATA_MORE_THAN_5_MILLION)
            .isParallelizationReqd(UPDATED_IS_PARALLELIZATION_REQD)
            .recoveryClass(UPDATED_RECOVERY_CLASS)
            .orderId(UPDATED_ORDER_ID);

        restGeneralDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGeneralData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGeneralData))
            )
            .andExpect(status().isOk());

        // Validate the GeneralData in the database
        List<GeneralData> generalDataList = generalDataRepository.findAll();
        assertThat(generalDataList).hasSize(databaseSizeBeforeUpdate);
        GeneralData testGeneralData = generalDataList.get(generalDataList.size() - 1);
        assertThat(testGeneralData.getTableUsage()).isEqualTo(UPDATED_TABLE_USAGE);
        assertThat(testGeneralData.getDbSelection()).isEqualTo(UPDATED_DB_SELECTION);
        assertThat(testGeneralData.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testGeneralData.getHasDataMoreThan5Million()).isEqualTo(UPDATED_HAS_DATA_MORE_THAN_5_MILLION);
        assertThat(testGeneralData.getIsParallelizationReqd()).isEqualTo(UPDATED_IS_PARALLELIZATION_REQD);
        assertThat(testGeneralData.getRecoveryClass()).isEqualTo(UPDATED_RECOVERY_CLASS);
        assertThat(testGeneralData.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void putNonExistingGeneralData() throws Exception {
        int databaseSizeBeforeUpdate = generalDataRepository.findAll().size();
        generalData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeneralDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, generalData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(generalData))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeneralData in the database
        List<GeneralData> generalDataList = generalDataRepository.findAll();
        assertThat(generalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGeneralData() throws Exception {
        int databaseSizeBeforeUpdate = generalDataRepository.findAll().size();
        generalData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(generalData))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeneralData in the database
        List<GeneralData> generalDataList = generalDataRepository.findAll();
        assertThat(generalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGeneralData() throws Exception {
        int databaseSizeBeforeUpdate = generalDataRepository.findAll().size();
        generalData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generalData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GeneralData in the database
        List<GeneralData> generalDataList = generalDataRepository.findAll();
        assertThat(generalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGeneralDataWithPatch() throws Exception {
        // Initialize the database
        generalDataRepository.saveAndFlush(generalData);

        int databaseSizeBeforeUpdate = generalDataRepository.findAll().size();

        // Update the generalData using partial update
        GeneralData partialUpdatedGeneralData = new GeneralData();
        partialUpdatedGeneralData.setId(generalData.getId());

        partialUpdatedGeneralData
            .tableUsage(UPDATED_TABLE_USAGE)
            .dbSelection(UPDATED_DB_SELECTION)
            .tableName(UPDATED_TABLE_NAME)
            .isParallelizationReqd(UPDATED_IS_PARALLELIZATION_REQD)
            .recoveryClass(UPDATED_RECOVERY_CLASS)
            .orderId(UPDATED_ORDER_ID);

        restGeneralDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeneralData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGeneralData))
            )
            .andExpect(status().isOk());

        // Validate the GeneralData in the database
        List<GeneralData> generalDataList = generalDataRepository.findAll();
        assertThat(generalDataList).hasSize(databaseSizeBeforeUpdate);
        GeneralData testGeneralData = generalDataList.get(generalDataList.size() - 1);
        assertThat(testGeneralData.getTableUsage()).isEqualTo(UPDATED_TABLE_USAGE);
        assertThat(testGeneralData.getDbSelection()).isEqualTo(UPDATED_DB_SELECTION);
        assertThat(testGeneralData.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testGeneralData.getHasDataMoreThan5Million()).isEqualTo(DEFAULT_HAS_DATA_MORE_THAN_5_MILLION);
        assertThat(testGeneralData.getIsParallelizationReqd()).isEqualTo(UPDATED_IS_PARALLELIZATION_REQD);
        assertThat(testGeneralData.getRecoveryClass()).isEqualTo(UPDATED_RECOVERY_CLASS);
        assertThat(testGeneralData.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void fullUpdateGeneralDataWithPatch() throws Exception {
        // Initialize the database
        generalDataRepository.saveAndFlush(generalData);

        int databaseSizeBeforeUpdate = generalDataRepository.findAll().size();

        // Update the generalData using partial update
        GeneralData partialUpdatedGeneralData = new GeneralData();
        partialUpdatedGeneralData.setId(generalData.getId());

        partialUpdatedGeneralData
            .tableUsage(UPDATED_TABLE_USAGE)
            .dbSelection(UPDATED_DB_SELECTION)
            .tableName(UPDATED_TABLE_NAME)
            .hasDataMoreThan5Million(UPDATED_HAS_DATA_MORE_THAN_5_MILLION)
            .isParallelizationReqd(UPDATED_IS_PARALLELIZATION_REQD)
            .recoveryClass(UPDATED_RECOVERY_CLASS)
            .orderId(UPDATED_ORDER_ID);

        restGeneralDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeneralData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGeneralData))
            )
            .andExpect(status().isOk());

        // Validate the GeneralData in the database
        List<GeneralData> generalDataList = generalDataRepository.findAll();
        assertThat(generalDataList).hasSize(databaseSizeBeforeUpdate);
        GeneralData testGeneralData = generalDataList.get(generalDataList.size() - 1);
        assertThat(testGeneralData.getTableUsage()).isEqualTo(UPDATED_TABLE_USAGE);
        assertThat(testGeneralData.getDbSelection()).isEqualTo(UPDATED_DB_SELECTION);
        assertThat(testGeneralData.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testGeneralData.getHasDataMoreThan5Million()).isEqualTo(UPDATED_HAS_DATA_MORE_THAN_5_MILLION);
        assertThat(testGeneralData.getIsParallelizationReqd()).isEqualTo(UPDATED_IS_PARALLELIZATION_REQD);
        assertThat(testGeneralData.getRecoveryClass()).isEqualTo(UPDATED_RECOVERY_CLASS);
        assertThat(testGeneralData.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingGeneralData() throws Exception {
        int databaseSizeBeforeUpdate = generalDataRepository.findAll().size();
        generalData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeneralDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, generalData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(generalData))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeneralData in the database
        List<GeneralData> generalDataList = generalDataRepository.findAll();
        assertThat(generalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGeneralData() throws Exception {
        int databaseSizeBeforeUpdate = generalDataRepository.findAll().size();
        generalData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(generalData))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeneralData in the database
        List<GeneralData> generalDataList = generalDataRepository.findAll();
        assertThat(generalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGeneralData() throws Exception {
        int databaseSizeBeforeUpdate = generalDataRepository.findAll().size();
        generalData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralDataMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(generalData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GeneralData in the database
        List<GeneralData> generalDataList = generalDataRepository.findAll();
        assertThat(generalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGeneralData() throws Exception {
        // Initialize the database
        generalDataRepository.saveAndFlush(generalData);

        int databaseSizeBeforeDelete = generalDataRepository.findAll().size();

        // Delete the generalData
        restGeneralDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, generalData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GeneralData> generalDataList = generalDataRepository.findAll();
        assertThat(generalDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
