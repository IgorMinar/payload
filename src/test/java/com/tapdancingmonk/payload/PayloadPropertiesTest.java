package com.tapdancingmonk.payload;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;


public class PayloadPropertiesTest {

    @Test
    public void itShouldLoadPropertiesWithoutBarfing() {
        new DefaultPayloadProperties();
    }
    
    @Test
    public void itShouldReturnAStringForReceiverEmail() {
        String address = new DefaultPayloadProperties().getReceiverEmail();
        assertNotNull(address);
        assertFalse(address.isEmpty());
    }
    
}
