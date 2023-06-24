package com.shafaat.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shafaat.test.IntegrationTest;
import com.shafaat.test.domain.Lookup;
import com.shafaat.test.repository.LookupRepository;
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
 * Integration tests for the {@link LookupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LookupResourceIT {

    private static final String DEFAULT_LOOKUP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LOOKUP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LOOKUP_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_LOOKUP_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DISPLAY_SEQUENCE = 1;
    private static final Integer UPDATED_DISPLAY_SEQUENCE = 2;

    private static final String DEFAULT_VIEW_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VIEW_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_DEPENDENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DEPENDENT_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/lookups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LookupRepository lookupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLookupMockMvc;

    private Lookup lookup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lookup createEntity(EntityManager em) {
        Lookup lookup = new Lookup()
            .lookupCode(DEFAULT_LOOKUP_CODE)
            .lookupValue(DEFAULT_LOOKUP_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .displaySequence(DEFAULT_DISPLAY_SEQUENCE)
            .viewName(DEFAULT_VIEW_NAME)
            .category(DEFAULT_CATEGORY)
            .dependentCode(DEFAULT_DEPENDENT_CODE)
            .isActive(DEFAULT_IS_ACTIVE);
        return lookup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lookup createUpdatedEntity(EntityManager em) {
        Lookup lookup = new Lookup()
            .lookupCode(UPDATED_LOOKUP_CODE)
            .lookupValue(UPDATED_LOOKUP_VALUE)
            .description(UPDATED_DESCRIPTION)
            .displaySequence(UPDATED_DISPLAY_SEQUENCE)
            .viewName(UPDATED_VIEW_NAME)
            .category(UPDATED_CATEGORY)
            .dependentCode(UPDATED_DEPENDENT_CODE)
            .isActive(UPDATED_IS_ACTIVE);
        return lookup;
    }

    @BeforeEach
    public void initTest() {
        lookup = createEntity(em);
    }

    @Test
    @Transactional
    void createLookup() throws Exception {
        int databaseSizeBeforeCreate = lookupRepository.findAll().size();
        // Create the Lookup
        restLookupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lookup)))
            .andExpect(status().isCreated());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeCreate + 1);
        Lookup testLookup = lookupList.get(lookupList.size() - 1);
        assertThat(testLookup.getLookupCode()).isEqualTo(DEFAULT_LOOKUP_CODE);
        assertThat(testLookup.getLookupValue()).isEqualTo(DEFAULT_LOOKUP_VALUE);
        assertThat(testLookup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLookup.getDisplaySequence()).isEqualTo(DEFAULT_DISPLAY_SEQUENCE);
        assertThat(testLookup.getViewName()).isEqualTo(DEFAULT_VIEW_NAME);
        assertThat(testLookup.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testLookup.getDependentCode()).isEqualTo(DEFAULT_DEPENDENT_CODE);
        assertThat(testLookup.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void createLookupWithExistingId() throws Exception {
        // Create the Lookup with an existing ID
        lookup.setId(1L);

        int databaseSizeBeforeCreate = lookupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLookupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lookup)))
            .andExpect(status().isBadRequest());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLookups() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList
        restLookupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookup.getId().intValue())))
            .andExpect(jsonPath("$.[*].lookupCode").value(hasItem(DEFAULT_LOOKUP_CODE)))
            .andExpect(jsonPath("$.[*].lookupValue").value(hasItem(DEFAULT_LOOKUP_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].displaySequence").value(hasItem(DEFAULT_DISPLAY_SEQUENCE)))
            .andExpect(jsonPath("$.[*].viewName").value(hasItem(DEFAULT_VIEW_NAME)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].dependentCode").value(hasItem(DEFAULT_DEPENDENT_CODE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getLookup() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get the lookup
        restLookupMockMvc
            .perform(get(ENTITY_API_URL_ID, lookup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lookup.getId().intValue()))
            .andExpect(jsonPath("$.lookupCode").value(DEFAULT_LOOKUP_CODE))
            .andExpect(jsonPath("$.lookupValue").value(DEFAULT_LOOKUP_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.displaySequence").value(DEFAULT_DISPLAY_SEQUENCE))
            .andExpect(jsonPath("$.viewName").value(DEFAULT_VIEW_NAME))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.dependentCode").value(DEFAULT_DEPENDENT_CODE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingLookup() throws Exception {
        // Get the lookup
        restLookupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLookup() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();

        // Update the lookup
        Lookup updatedLookup = lookupRepository.findById(lookup.getId()).get();
        // Disconnect from session so that the updates on updatedLookup are not directly saved in db
        em.detach(updatedLookup);
        updatedLookup
            .lookupCode(UPDATED_LOOKUP_CODE)
            .lookupValue(UPDATED_LOOKUP_VALUE)
            .description(UPDATED_DESCRIPTION)
            .displaySequence(UPDATED_DISPLAY_SEQUENCE)
            .viewName(UPDATED_VIEW_NAME)
            .category(UPDATED_CATEGORY)
            .dependentCode(UPDATED_DEPENDENT_CODE)
            .isActive(UPDATED_IS_ACTIVE);

        restLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLookup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLookup))
            )
            .andExpect(status().isOk());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
        Lookup testLookup = lookupList.get(lookupList.size() - 1);
        assertThat(testLookup.getLookupCode()).isEqualTo(UPDATED_LOOKUP_CODE);
        assertThat(testLookup.getLookupValue()).isEqualTo(UPDATED_LOOKUP_VALUE);
        assertThat(testLookup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLookup.getDisplaySequence()).isEqualTo(UPDATED_DISPLAY_SEQUENCE);
        assertThat(testLookup.getViewName()).isEqualTo(UPDATED_VIEW_NAME);
        assertThat(testLookup.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testLookup.getDependentCode()).isEqualTo(UPDATED_DEPENDENT_CODE);
        assertThat(testLookup.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingLookup() throws Exception {
        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();
        lookup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lookup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lookup))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLookup() throws Exception {
        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();
        lookup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lookup))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLookup() throws Exception {
        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();
        lookup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lookup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLookupWithPatch() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();

        // Update the lookup using partial update
        Lookup partialUpdatedLookup = new Lookup();
        partialUpdatedLookup.setId(lookup.getId());

        partialUpdatedLookup
            .lookupCode(UPDATED_LOOKUP_CODE)
            .lookupValue(UPDATED_LOOKUP_VALUE)
            .displaySequence(UPDATED_DISPLAY_SEQUENCE)
            .viewName(UPDATED_VIEW_NAME)
            .category(UPDATED_CATEGORY)
            .dependentCode(UPDATED_DEPENDENT_CODE);

        restLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLookup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLookup))
            )
            .andExpect(status().isOk());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
        Lookup testLookup = lookupList.get(lookupList.size() - 1);
        assertThat(testLookup.getLookupCode()).isEqualTo(UPDATED_LOOKUP_CODE);
        assertThat(testLookup.getLookupValue()).isEqualTo(UPDATED_LOOKUP_VALUE);
        assertThat(testLookup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLookup.getDisplaySequence()).isEqualTo(UPDATED_DISPLAY_SEQUENCE);
        assertThat(testLookup.getViewName()).isEqualTo(UPDATED_VIEW_NAME);
        assertThat(testLookup.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testLookup.getDependentCode()).isEqualTo(UPDATED_DEPENDENT_CODE);
        assertThat(testLookup.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateLookupWithPatch() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();

        // Update the lookup using partial update
        Lookup partialUpdatedLookup = new Lookup();
        partialUpdatedLookup.setId(lookup.getId());

        partialUpdatedLookup
            .lookupCode(UPDATED_LOOKUP_CODE)
            .lookupValue(UPDATED_LOOKUP_VALUE)
            .description(UPDATED_DESCRIPTION)
            .displaySequence(UPDATED_DISPLAY_SEQUENCE)
            .viewName(UPDATED_VIEW_NAME)
            .category(UPDATED_CATEGORY)
            .dependentCode(UPDATED_DEPENDENT_CODE)
            .isActive(UPDATED_IS_ACTIVE);

        restLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLookup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLookup))
            )
            .andExpect(status().isOk());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
        Lookup testLookup = lookupList.get(lookupList.size() - 1);
        assertThat(testLookup.getLookupCode()).isEqualTo(UPDATED_LOOKUP_CODE);
        assertThat(testLookup.getLookupValue()).isEqualTo(UPDATED_LOOKUP_VALUE);
        assertThat(testLookup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLookup.getDisplaySequence()).isEqualTo(UPDATED_DISPLAY_SEQUENCE);
        assertThat(testLookup.getViewName()).isEqualTo(UPDATED_VIEW_NAME);
        assertThat(testLookup.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testLookup.getDependentCode()).isEqualTo(UPDATED_DEPENDENT_CODE);
        assertThat(testLookup.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingLookup() throws Exception {
        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();
        lookup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lookup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lookup))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLookup() throws Exception {
        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();
        lookup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lookup))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLookup() throws Exception {
        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();
        lookup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLookupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lookup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLookup() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        int databaseSizeBeforeDelete = lookupRepository.findAll().size();

        // Delete the lookup
        restLookupMockMvc
            .perform(delete(ENTITY_API_URL_ID, lookup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
