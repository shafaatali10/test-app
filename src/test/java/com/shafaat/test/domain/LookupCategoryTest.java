package com.shafaat.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shafaat.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LookupCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LookupCategory.class);
        LookupCategory lookupCategory1 = new LookupCategory();
        lookupCategory1.setId(1L);
        LookupCategory lookupCategory2 = new LookupCategory();
        lookupCategory2.setId(lookupCategory1.getId());
        assertThat(lookupCategory1).isEqualTo(lookupCategory2);
        lookupCategory2.setId(2L);
        assertThat(lookupCategory1).isNotEqualTo(lookupCategory2);
        lookupCategory1.setId(null);
        assertThat(lookupCategory1).isNotEqualTo(lookupCategory2);
    }
}
