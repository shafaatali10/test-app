package com.shafaat.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shafaat.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HddlFileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HddlFile.class);
        HddlFile hddlFile1 = new HddlFile();
        hddlFile1.setId(1L);
        HddlFile hddlFile2 = new HddlFile();
        hddlFile2.setId(hddlFile1.getId());
        assertThat(hddlFile1).isEqualTo(hddlFile2);
        hddlFile2.setId(2L);
        assertThat(hddlFile1).isNotEqualTo(hddlFile2);
        hddlFile1.setId(null);
        assertThat(hddlFile1).isNotEqualTo(hddlFile2);
    }
}
