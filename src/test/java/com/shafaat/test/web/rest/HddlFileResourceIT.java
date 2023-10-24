package com.shafaat.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shafaat.test.IntegrationTest;
import com.shafaat.test.domain.HddlFile;
import com.shafaat.test.repository.HddlFileRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link HddlFileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HddlFileResourceIT {

    private static final String DEFAULT_SWID = "AAAAAAAAAA";
    private static final String UPDATED_SWID = "BBBBBBBBBB";

    private static final String DEFAULT_DB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DB_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_HDDL = "AAAAAAAAAA";
    private static final String UPDATED_HDDL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hddl-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HddlFileRepository hddlFileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHddlFileMockMvc;

    private HddlFile hddlFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HddlFile createEntity(EntityManager em) {
        HddlFile hddlFile = new HddlFile()
            .swid(DEFAULT_SWID)
            .dbName(DEFAULT_DB_NAME)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .status(DEFAULT_STATUS)
            .hddl(DEFAULT_HDDL);
        return hddlFile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HddlFile createUpdatedEntity(EntityManager em) {
        HddlFile hddlFile = new HddlFile()
            .swid(UPDATED_SWID)
            .dbName(UPDATED_DB_NAME)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .status(UPDATED_STATUS)
            .hddl(UPDATED_HDDL);
        return hddlFile;
    }

    @BeforeEach
    public void initTest() {
        hddlFile = createEntity(em);
    }

    @Test
    @Transactional
    void createHddlFile() throws Exception {
        int databaseSizeBeforeCreate = hddlFileRepository.findAll().size();
        // Create the HddlFile
        restHddlFileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hddlFile)))
            .andExpect(status().isCreated());

        // Validate the HddlFile in the database
        List<HddlFile> hddlFileList = hddlFileRepository.findAll();
        assertThat(hddlFileList).hasSize(databaseSizeBeforeCreate + 1);
        HddlFile testHddlFile = hddlFileList.get(hddlFileList.size() - 1);
        assertThat(testHddlFile.getSwid()).isEqualTo(DEFAULT_SWID);
        assertThat(testHddlFile.getDbName()).isEqualTo(DEFAULT_DB_NAME);
        assertThat(testHddlFile.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testHddlFile.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testHddlFile.getHddl()).isEqualTo(DEFAULT_HDDL);
    }

    @Test
    @Transactional
    void createHddlFileWithExistingId() throws Exception {
        // Create the HddlFile with an existing ID
        hddlFile.setId(1L);

        int databaseSizeBeforeCreate = hddlFileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHddlFileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hddlFile)))
            .andExpect(status().isBadRequest());

        // Validate the HddlFile in the database
        List<HddlFile> hddlFileList = hddlFileRepository.findAll();
        assertThat(hddlFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHddlFiles() throws Exception {
        // Initialize the database
        hddlFileRepository.saveAndFlush(hddlFile);

        // Get all the hddlFileList
        restHddlFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hddlFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].swid").value(hasItem(DEFAULT_SWID)))
            .andExpect(jsonPath("$.[*].dbName").value(hasItem(DEFAULT_DB_NAME)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].hddl").value(hasItem(DEFAULT_HDDL.toString())));
    }

    @Test
    @Transactional
    void getHddlFile() throws Exception {
        // Initialize the database
        hddlFileRepository.saveAndFlush(hddlFile);

        // Get the hddlFile
        restHddlFileMockMvc
            .perform(get(ENTITY_API_URL_ID, hddlFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hddlFile.getId().intValue()))
            .andExpect(jsonPath("$.swid").value(DEFAULT_SWID))
            .andExpect(jsonPath("$.dbName").value(DEFAULT_DB_NAME))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.hddl").value(DEFAULT_HDDL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingHddlFile() throws Exception {
        // Get the hddlFile
        restHddlFileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHddlFile() throws Exception {
        // Initialize the database
        hddlFileRepository.saveAndFlush(hddlFile);

        int databaseSizeBeforeUpdate = hddlFileRepository.findAll().size();

        // Update the hddlFile
        HddlFile updatedHddlFile = hddlFileRepository.findById(hddlFile.getId()).get();
        // Disconnect from session so that the updates on updatedHddlFile are not directly saved in db
        em.detach(updatedHddlFile);
        updatedHddlFile
            .swid(UPDATED_SWID)
            .dbName(UPDATED_DB_NAME)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .status(UPDATED_STATUS)
            .hddl(UPDATED_HDDL);

        restHddlFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHddlFile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHddlFile))
            )
            .andExpect(status().isOk());

        // Validate the HddlFile in the database
        List<HddlFile> hddlFileList = hddlFileRepository.findAll();
        assertThat(hddlFileList).hasSize(databaseSizeBeforeUpdate);
        HddlFile testHddlFile = hddlFileList.get(hddlFileList.size() - 1);
        assertThat(testHddlFile.getSwid()).isEqualTo(UPDATED_SWID);
        assertThat(testHddlFile.getDbName()).isEqualTo(UPDATED_DB_NAME);
        assertThat(testHddlFile.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testHddlFile.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testHddlFile.getHddl()).isEqualTo(UPDATED_HDDL);
    }

    @Test
    @Transactional
    void putNonExistingHddlFile() throws Exception {
        int databaseSizeBeforeUpdate = hddlFileRepository.findAll().size();
        hddlFile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHddlFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hddlFile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hddlFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the HddlFile in the database
        List<HddlFile> hddlFileList = hddlFileRepository.findAll();
        assertThat(hddlFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHddlFile() throws Exception {
        int databaseSizeBeforeUpdate = hddlFileRepository.findAll().size();
        hddlFile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHddlFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hddlFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the HddlFile in the database
        List<HddlFile> hddlFileList = hddlFileRepository.findAll();
        assertThat(hddlFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHddlFile() throws Exception {
        int databaseSizeBeforeUpdate = hddlFileRepository.findAll().size();
        hddlFile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHddlFileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hddlFile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HddlFile in the database
        List<HddlFile> hddlFileList = hddlFileRepository.findAll();
        assertThat(hddlFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHddlFileWithPatch() throws Exception {
        // Initialize the database
        hddlFileRepository.saveAndFlush(hddlFile);

        int databaseSizeBeforeUpdate = hddlFileRepository.findAll().size();

        // Update the hddlFile using partial update
        HddlFile partialUpdatedHddlFile = new HddlFile();
        partialUpdatedHddlFile.setId(hddlFile.getId());

        partialUpdatedHddlFile.dbName(UPDATED_DB_NAME).expiryDate(UPDATED_EXPIRY_DATE).status(UPDATED_STATUS);

        restHddlFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHddlFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHddlFile))
            )
            .andExpect(status().isOk());

        // Validate the HddlFile in the database
        List<HddlFile> hddlFileList = hddlFileRepository.findAll();
        assertThat(hddlFileList).hasSize(databaseSizeBeforeUpdate);
        HddlFile testHddlFile = hddlFileList.get(hddlFileList.size() - 1);
        assertThat(testHddlFile.getSwid()).isEqualTo(DEFAULT_SWID);
        assertThat(testHddlFile.getDbName()).isEqualTo(UPDATED_DB_NAME);
        assertThat(testHddlFile.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testHddlFile.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testHddlFile.getHddl()).isEqualTo(DEFAULT_HDDL);
    }

    @Test
    @Transactional
    void fullUpdateHddlFileWithPatch() throws Exception {
        // Initialize the database
        hddlFileRepository.saveAndFlush(hddlFile);

        int databaseSizeBeforeUpdate = hddlFileRepository.findAll().size();

        // Update the hddlFile using partial update
        HddlFile partialUpdatedHddlFile = new HddlFile();
        partialUpdatedHddlFile.setId(hddlFile.getId());

        partialUpdatedHddlFile
            .swid(UPDATED_SWID)
            .dbName(UPDATED_DB_NAME)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .status(UPDATED_STATUS)
            .hddl(UPDATED_HDDL);

        restHddlFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHddlFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHddlFile))
            )
            .andExpect(status().isOk());

        // Validate the HddlFile in the database
        List<HddlFile> hddlFileList = hddlFileRepository.findAll();
        assertThat(hddlFileList).hasSize(databaseSizeBeforeUpdate);
        HddlFile testHddlFile = hddlFileList.get(hddlFileList.size() - 1);
        assertThat(testHddlFile.getSwid()).isEqualTo(UPDATED_SWID);
        assertThat(testHddlFile.getDbName()).isEqualTo(UPDATED_DB_NAME);
        assertThat(testHddlFile.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testHddlFile.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testHddlFile.getHddl()).isEqualTo(UPDATED_HDDL);
    }

    @Test
    @Transactional
    void patchNonExistingHddlFile() throws Exception {
        int databaseSizeBeforeUpdate = hddlFileRepository.findAll().size();
        hddlFile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHddlFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hddlFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hddlFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the HddlFile in the database
        List<HddlFile> hddlFileList = hddlFileRepository.findAll();
        assertThat(hddlFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHddlFile() throws Exception {
        int databaseSizeBeforeUpdate = hddlFileRepository.findAll().size();
        hddlFile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHddlFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hddlFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the HddlFile in the database
        List<HddlFile> hddlFileList = hddlFileRepository.findAll();
        assertThat(hddlFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHddlFile() throws Exception {
        int databaseSizeBeforeUpdate = hddlFileRepository.findAll().size();
        hddlFile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHddlFileMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hddlFile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HddlFile in the database
        List<HddlFile> hddlFileList = hddlFileRepository.findAll();
        assertThat(hddlFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHddlFile() throws Exception {
        // Initialize the database
        hddlFileRepository.saveAndFlush(hddlFile);

        int databaseSizeBeforeDelete = hddlFileRepository.findAll().size();

        // Delete the hddlFile
        restHddlFileMockMvc
            .perform(delete(ENTITY_API_URL_ID, hddlFile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HddlFile> hddlFileList = hddlFileRepository.findAll();
        assertThat(hddlFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
