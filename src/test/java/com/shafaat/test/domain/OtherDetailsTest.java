package com.shafaat.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shafaat.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OtherDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OtherDetails.class);
        OtherDetails otherDetails1 = new OtherDetails();
        otherDetails1.setId(1L);
        OtherDetails otherDetails2 = new OtherDetails();
        otherDetails2.setId(otherDetails1.getId());
        assertThat(otherDetails1).isEqualTo(otherDetails2);
        otherDetails2.setId(2L);
        assertThat(otherDetails1).isNotEqualTo(otherDetails2);
        otherDetails1.setId(null);
        assertThat(otherDetails1).isNotEqualTo(otherDetails2);
    }
}
