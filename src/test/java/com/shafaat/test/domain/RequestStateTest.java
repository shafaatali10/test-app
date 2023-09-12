package com.shafaat.test.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.shafaat.test.web.rest.TestUtil;

public class RequestStateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestState.class);
        RequestState requestState1 = new RequestState();
        requestState1.setId(1L);
        RequestState requestState2 = new RequestState();
        requestState2.setId(requestState1.getId());
        assertThat(requestState1).isEqualTo(requestState2);
        requestState2.setId(2L);
        assertThat(requestState1).isNotEqualTo(requestState2);
        requestState1.setId(null);
        assertThat(requestState1).isNotEqualTo(requestState2);
    }
}
