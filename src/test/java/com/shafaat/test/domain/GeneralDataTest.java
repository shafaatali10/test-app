package com.shafaat.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shafaat.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GeneralDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeneralData.class);
        GeneralData generalData1 = new GeneralData();
        generalData1.setId(1L);
        GeneralData generalData2 = new GeneralData();
        generalData2.setId(generalData1.getId());
        assertThat(generalData1).isEqualTo(generalData2);
        generalData2.setId(2L);
        assertThat(generalData1).isNotEqualTo(generalData2);
        generalData1.setId(null);
        assertThat(generalData1).isNotEqualTo(generalData2);
    }
}
