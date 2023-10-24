package com.shafaat.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shafaat.test.IntegrationTest;
import com.shafaat.test.domain.MetaData;
import com.shafaat.test.repository.MetaDataRepository;
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
 * Integration tests for the {@link MetaDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MetaDataResourceIT {

    private static final String DEFAULT_STID_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_STID_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_STID_COLUMN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STID_COLUMN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_DATA_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_INITIAL_LOAD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_INITIAL_LOAD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PARTITION_SCHEMA = "AAAAAAAAAA";
    private static final String UPDATED_PARTITION_SCHEMA = "BBBBBBBBBB";

    private static final Long DEFAULT_ORDER_ID = 1L;
    private static final Long UPDATED_ORDER_ID = 2L;

    private static final String ENTITY_API_URL = "/api/meta-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MetaDataRepository metaDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMetaDataMockMvc;

    private MetaData metaData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetaData createEntity(EntityManager em) {
        MetaData metaData = new MetaData()
            .stidClass(DEFAULT_STID_CLASS)
            .stidColumnName(DEFAULT_STID_COLUMN_NAME)
            .dataLevel(DEFAULT_DATA_LEVEL)
            .initialLoadType(DEFAULT_INITIAL_LOAD_TYPE)
            .partitionSchema(DEFAULT_PARTITION_SCHEMA)
            .orderId(DEFAULT_ORDER_ID);
        return metaData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetaData createUpdatedEntity(EntityManager em) {
        MetaData metaData = new MetaData()
            .stidClass(UPDATED_STID_CLASS)
            .stidColumnName(UPDATED_STID_COLUMN_NAME)
            .dataLevel(UPDATED_DATA_LEVEL)
            .initialLoadType(UPDATED_INITIAL_LOAD_TYPE)
            .partitionSchema(UPDATED_PARTITION_SCHEMA)
            .orderId(UPDATED_ORDER_ID);
        return metaData;
    }

    @BeforeEach
    public void initTest() {
        metaData = createEntity(em);
    }

    @Test
    @Transactional
    void createMetaData() throws Exception {
        int databaseSizeBeforeCreate = metaDataRepository.findAll().size();
        // Create the MetaData
        restMetaDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaData)))
            .andExpect(status().isCreated());

        // Validate the MetaData in the database
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeCreate + 1);
        MetaData testMetaData = metaDataList.get(metaDataList.size() - 1);
        assertThat(testMetaData.getStidClass()).isEqualTo(DEFAULT_STID_CLASS);
        assertThat(testMetaData.getStidColumnName()).isEqualTo(DEFAULT_STID_COLUMN_NAME);
        assertThat(testMetaData.getDataLevel()).isEqualTo(DEFAULT_DATA_LEVEL);
        assertThat(testMetaData.getInitialLoadType()).isEqualTo(DEFAULT_INITIAL_LOAD_TYPE);
        assertThat(testMetaData.getPartitionSchema()).isEqualTo(DEFAULT_PARTITION_SCHEMA);
        assertThat(testMetaData.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
    }

    @Test
    @Transactional
    void createMetaDataWithExistingId() throws Exception {
        // Create the MetaData with an existing ID
        metaData.setId(1L);

        int databaseSizeBeforeCreate = metaDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetaDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaData)))
            .andExpect(status().isBadRequest());

        // Validate the MetaData in the database
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMetaData() throws Exception {
        // Initialize the database
        metaDataRepository.saveAndFlush(metaData);

        // Get all the metaDataList
        restMetaDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metaData.getId().intValue())))
            .andExpect(jsonPath("$.[*].stidClass").value(hasItem(DEFAULT_STID_CLASS)))
            .andExpect(jsonPath("$.[*].stidColumnName").value(hasItem(DEFAULT_STID_COLUMN_NAME)))
            .andExpect(jsonPath("$.[*].dataLevel").value(hasItem(DEFAULT_DATA_LEVEL)))
            .andExpect(jsonPath("$.[*].initialLoadType").value(hasItem(DEFAULT_INITIAL_LOAD_TYPE)))
            .andExpect(jsonPath("$.[*].partitionSchema").value(hasItem(DEFAULT_PARTITION_SCHEMA)))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID.intValue())));
    }

    @Test
    @Transactional
    void getMetaData() throws Exception {
        // Initialize the database
        metaDataRepository.saveAndFlush(metaData);

        // Get the metaData
        restMetaDataMockMvc
            .perform(get(ENTITY_API_URL_ID, metaData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(metaData.getId().intValue()))
            .andExpect(jsonPath("$.stidClass").value(DEFAULT_STID_CLASS))
            .andExpect(jsonPath("$.stidColumnName").value(DEFAULT_STID_COLUMN_NAME))
            .andExpect(jsonPath("$.dataLevel").value(DEFAULT_DATA_LEVEL))
            .andExpect(jsonPath("$.initialLoadType").value(DEFAULT_INITIAL_LOAD_TYPE))
            .andExpect(jsonPath("$.partitionSchema").value(DEFAULT_PARTITION_SCHEMA))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMetaData() throws Exception {
        // Get the metaData
        restMetaDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMetaData() throws Exception {
        // Initialize the database
        metaDataRepository.saveAndFlush(metaData);

        int databaseSizeBeforeUpdate = metaDataRepository.findAll().size();

        // Update the metaData
        MetaData updatedMetaData = metaDataRepository.findById(metaData.getId()).get();
        // Disconnect from session so that the updates on updatedMetaData are not directly saved in db
        em.detach(updatedMetaData);
        updatedMetaData
            .stidClass(UPDATED_STID_CLASS)
            .stidColumnName(UPDATED_STID_COLUMN_NAME)
            .dataLevel(UPDATED_DATA_LEVEL)
            .initialLoadType(UPDATED_INITIAL_LOAD_TYPE)
            .partitionSchema(UPDATED_PARTITION_SCHEMA)
            .orderId(UPDATED_ORDER_ID);

        restMetaDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMetaData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMetaData))
            )
            .andExpect(status().isOk());

        // Validate the MetaData in the database
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeUpdate);
        MetaData testMetaData = metaDataList.get(metaDataList.size() - 1);
        assertThat(testMetaData.getStidClass()).isEqualTo(UPDATED_STID_CLASS);
        assertThat(testMetaData.getStidColumnName()).isEqualTo(UPDATED_STID_COLUMN_NAME);
        assertThat(testMetaData.getDataLevel()).isEqualTo(UPDATED_DATA_LEVEL);
        assertThat(testMetaData.getInitialLoadType()).isEqualTo(UPDATED_INITIAL_LOAD_TYPE);
        assertThat(testMetaData.getPartitionSchema()).isEqualTo(UPDATED_PARTITION_SCHEMA);
        assertThat(testMetaData.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void putNonExistingMetaData() throws Exception {
        int databaseSizeBeforeUpdate = metaDataRepository.findAll().size();
        metaData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaData))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaData in the database
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMetaData() throws Exception {
        int databaseSizeBeforeUpdate = metaDataRepository.findAll().size();
        metaData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaData))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaData in the database
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMetaData() throws Exception {
        int databaseSizeBeforeUpdate = metaDataRepository.findAll().size();
        metaData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaData in the database
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMetaDataWithPatch() throws Exception {
        // Initialize the database
        metaDataRepository.saveAndFlush(metaData);

        int databaseSizeBeforeUpdate = metaDataRepository.findAll().size();

        // Update the metaData using partial update
        MetaData partialUpdatedMetaData = new MetaData();
        partialUpdatedMetaData.setId(metaData.getId());

        partialUpdatedMetaData
            .stidClass(UPDATED_STID_CLASS)
            .stidColumnName(UPDATED_STID_COLUMN_NAME)
            .initialLoadType(UPDATED_INITIAL_LOAD_TYPE)
            .partitionSchema(UPDATED_PARTITION_SCHEMA)
            .orderId(UPDATED_ORDER_ID);

        restMetaDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetaData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetaData))
            )
            .andExpect(status().isOk());

        // Validate the MetaData in the database
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeUpdate);
        MetaData testMetaData = metaDataList.get(metaDataList.size() - 1);
        assertThat(testMetaData.getStidClass()).isEqualTo(UPDATED_STID_CLASS);
        assertThat(testMetaData.getStidColumnName()).isEqualTo(UPDATED_STID_COLUMN_NAME);
        assertThat(testMetaData.getDataLevel()).isEqualTo(DEFAULT_DATA_LEVEL);
        assertThat(testMetaData.getInitialLoadType()).isEqualTo(UPDATED_INITIAL_LOAD_TYPE);
        assertThat(testMetaData.getPartitionSchema()).isEqualTo(UPDATED_PARTITION_SCHEMA);
        assertThat(testMetaData.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void fullUpdateMetaDataWithPatch() throws Exception {
        // Initialize the database
        metaDataRepository.saveAndFlush(metaData);

        int databaseSizeBeforeUpdate = metaDataRepository.findAll().size();

        // Update the metaData using partial update
        MetaData partialUpdatedMetaData = new MetaData();
        partialUpdatedMetaData.setId(metaData.getId());

        partialUpdatedMetaData
            .stidClass(UPDATED_STID_CLASS)
            .stidColumnName(UPDATED_STID_COLUMN_NAME)
            .dataLevel(UPDATED_DATA_LEVEL)
            .initialLoadType(UPDATED_INITIAL_LOAD_TYPE)
            .partitionSchema(UPDATED_PARTITION_SCHEMA)
            .orderId(UPDATED_ORDER_ID);

        restMetaDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetaData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetaData))
            )
            .andExpect(status().isOk());

        // Validate the MetaData in the database
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeUpdate);
        MetaData testMetaData = metaDataList.get(metaDataList.size() - 1);
        assertThat(testMetaData.getStidClass()).isEqualTo(UPDATED_STID_CLASS);
        assertThat(testMetaData.getStidColumnName()).isEqualTo(UPDATED_STID_COLUMN_NAME);
        assertThat(testMetaData.getDataLevel()).isEqualTo(UPDATED_DATA_LEVEL);
        assertThat(testMetaData.getInitialLoadType()).isEqualTo(UPDATED_INITIAL_LOAD_TYPE);
        assertThat(testMetaData.getPartitionSchema()).isEqualTo(UPDATED_PARTITION_SCHEMA);
        assertThat(testMetaData.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingMetaData() throws Exception {
        int databaseSizeBeforeUpdate = metaDataRepository.findAll().size();
        metaData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metaData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaData))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaData in the database
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMetaData() throws Exception {
        int databaseSizeBeforeUpdate = metaDataRepository.findAll().size();
        metaData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaData))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaData in the database
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMetaData() throws Exception {
        int databaseSizeBeforeUpdate = metaDataRepository.findAll().size();
        metaData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaDataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(metaData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaData in the database
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMetaData() throws Exception {
        // Initialize the database
        metaDataRepository.saveAndFlush(metaData);

        int databaseSizeBeforeDelete = metaDataRepository.findAll().size();

        // Delete the metaData
        restMetaDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, metaData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
