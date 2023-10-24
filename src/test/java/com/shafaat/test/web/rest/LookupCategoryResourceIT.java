package com.shafaat.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shafaat.test.IntegrationTest;
import com.shafaat.test.domain.LookupCategory;
import com.shafaat.test.repository.LookupCategoryRepository;
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
 * Integration tests for the {@link LookupCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LookupCategoryResourceIT {

    private static final String DEFAULT_CATEGORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lookup-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LookupCategoryRepository lookupCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLookupCategoryMockMvc;

    private LookupCategory lookupCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LookupCategory createEntity(EntityManager em) {
        LookupCategory lookupCategory = new LookupCategory().categoryCode(DEFAULT_CATEGORY_CODE);
        return lookupCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LookupCategory createUpdatedEntity(EntityManager em) {
        LookupCategory lookupCategory = new LookupCategory().categoryCode(UPDATED_CATEGORY_CODE);
        return lookupCategory;
    }

    @BeforeEach
    public void initTest() {
        lookupCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createLookupCategory() throws Exception {
        int databaseSizeBeforeCreate = lookupCategoryRepository.findAll().size();
        // Create the LookupCategory
        restLookupCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lookupCategory))
            )
            .andExpect(status().isCreated());

        // Validate the LookupCategory in the database
        List<LookupCategory> lookupCategoryList = lookupCategoryRepository.findAll();
        assertThat(lookupCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        LookupCategory testLookupCategory = lookupCategoryList.get(lookupCategoryList.size() - 1);
        assertThat(testLookupCategory.getCategoryCode()).isEqualTo(DEFAULT_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void createLookupCategoryWithExistingId() throws Exception {
        // Create the LookupCategory with an existing ID
        lookupCategory.setId(1L);

        int databaseSizeBeforeCreate = lookupCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLookupCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lookupCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the LookupCategory in the database
        List<LookupCategory> lookupCategoryList = lookupCategoryRepository.findAll();
        assertThat(lookupCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLookupCategories() throws Exception {
        // Initialize the database
        lookupCategoryRepository.saveAndFlush(lookupCategory);

        // Get all the lookupCategoryList
        restLookupCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookupCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryCode").value(hasItem(DEFAULT_CATEGORY_CODE)));
    }

    @Test
    @Transactional
    void getLookupCategory() throws Exception {
        // Initialize the database
        lookupCategoryRepository.saveAndFlush(lookupCategory);

        // Get the lookupCategory
        restLookupCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, lookupCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lookupCategory.getId().intValue()))
            .andExpect(jsonPath("$.categoryCode").value(DEFAULT_CATEGORY_CODE));
    }

    @Test
    @Transactional
    void getNonExistingLookupCategory() throws Exception {
        // Get the lookupCategory
        restLookupCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLookupCategory() throws Exception {
        // Initialize the database
        lookupCategoryRepository.saveAndFlush(lookupCategory);

        int databaseSizeBeforeUpdate = lookupCategoryRepository.findAll().size();

        // Update the lookupCategory
        LookupCategory updatedLookupCategory = lookupCategoryRepository.findById(lookupCategory.getId()).get();
        // Disconnect from session so that the updates on updatedLookupCategory are not directly saved in db
        em.detach(updatedLookupCategory);
        updatedLookupCategory.categoryCode(UPDATED_CATEGORY_CODE);

        restLookupCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLookupCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLookupCategory))
            )
            .andExpect(status().isOk());

        // Validate the LookupCategory in the database
        List<LookupCategory> lookupCategoryList = lookupCategoryRepository.findAll();
        assertThat(lookupCategoryList).hasSize(databaseSizeBeforeUpdate);
        LookupCategory testLookupCategory = lookupCategoryList.get(lookupCategoryList.size() - 1);
        assertThat(testLookupCategory.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void putNonExistingLookupCategory() throws Exception {
        int databaseSizeBeforeUpdate = lookupCategoryRepository.findAll().size();
        lookupCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLookupCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lookupCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lookupCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the LookupCategory in the database
        List<LookupCategory> lookupCategoryList = lookupCategoryRepository.findAll();
        assertThat(lookupCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLookupCategory() throws Exception {
        int databaseSizeBeforeUpdate = lookupCategoryRepository.findAll().size();
        lookupCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lookupCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the LookupCategory in the database
        List<LookupCategory> lookupCategoryList = lookupCategoryRepository.findAll();
        assertThat(lookupCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLookupCategory() throws Exception {
        int databaseSizeBeforeUpdate = lookupCategoryRepository.findAll().size();
        lookupCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lookupCategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LookupCategory in the database
        List<LookupCategory> lookupCategoryList = lookupCategoryRepository.findAll();
        assertThat(lookupCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLookupCategoryWithPatch() throws Exception {
        // Initialize the database
        lookupCategoryRepository.saveAndFlush(lookupCategory);

        int databaseSizeBeforeUpdate = lookupCategoryRepository.findAll().size();

        // Update the lookupCategory using partial update
        LookupCategory partialUpdatedLookupCategory = new LookupCategory();
        partialUpdatedLookupCategory.setId(lookupCategory.getId());

        partialUpdatedLookupCategory.categoryCode(UPDATED_CATEGORY_CODE);

        restLookupCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLookupCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLookupCategory))
            )
            .andExpect(status().isOk());

        // Validate the LookupCategory in the database
        List<LookupCategory> lookupCategoryList = lookupCategoryRepository.findAll();
        assertThat(lookupCategoryList).hasSize(databaseSizeBeforeUpdate);
        LookupCategory testLookupCategory = lookupCategoryList.get(lookupCategoryList.size() - 1);
        assertThat(testLookupCategory.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void fullUpdateLookupCategoryWithPatch() throws Exception {
        // Initialize the database
        lookupCategoryRepository.saveAndFlush(lookupCategory);

        int databaseSizeBeforeUpdate = lookupCategoryRepository.findAll().size();

        // Update the lookupCategory using partial update
        LookupCategory partialUpdatedLookupCategory = new LookupCategory();
        partialUpdatedLookupCategory.setId(lookupCategory.getId());

        partialUpdatedLookupCategory.categoryCode(UPDATED_CATEGORY_CODE);

        restLookupCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLookupCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLookupCategory))
            )
            .andExpect(status().isOk());

        // Validate the LookupCategory in the database
        List<LookupCategory> lookupCategoryList = lookupCategoryRepository.findAll();
        assertThat(lookupCategoryList).hasSize(databaseSizeBeforeUpdate);
        LookupCategory testLookupCategory = lookupCategoryList.get(lookupCategoryList.size() - 1);
        assertThat(testLookupCategory.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingLookupCategory() throws Exception {
        int databaseSizeBeforeUpdate = lookupCategoryRepository.findAll().size();
        lookupCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLookupCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lookupCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lookupCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the LookupCategory in the database
        List<LookupCategory> lookupCategoryList = lookupCategoryRepository.findAll();
        assertThat(lookupCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLookupCategory() throws Exception {
        int databaseSizeBeforeUpdate = lookupCategoryRepository.findAll().size();
        lookupCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lookupCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the LookupCategory in the database
        List<LookupCategory> lookupCategoryList = lookupCategoryRepository.findAll();
        assertThat(lookupCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLookupCategory() throws Exception {
        int databaseSizeBeforeUpdate = lookupCategoryRepository.findAll().size();
        lookupCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lookupCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LookupCategory in the database
        List<LookupCategory> lookupCategoryList = lookupCategoryRepository.findAll();
        assertThat(lookupCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLookupCategory() throws Exception {
        // Initialize the database
        lookupCategoryRepository.saveAndFlush(lookupCategory);

        int databaseSizeBeforeDelete = lookupCategoryRepository.findAll().size();

        // Delete the lookupCategory
        restLookupCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, lookupCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LookupCategory> lookupCategoryList = lookupCategoryRepository.findAll();
        assertThat(lookupCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
