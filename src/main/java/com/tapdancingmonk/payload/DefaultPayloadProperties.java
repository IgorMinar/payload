package com.tapdancingmonk.payload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DefaultPayloadProperties implements PayloadProperties {
    
    private final Properties properties = new Properties();
    
    public DefaultPayloadProperties() {
        InputStream is = this.getClass().getResourceAsStream("/payload.properties");
        try {
            properties.load(is);
        } catch (IOException ex) {
            throw new FubarException(ex);
        }
    }
    
    /* (non-Javadoc)
     * @see com.tapdancingmonk.payload.PayloadProperties#getProperty(java.lang.String)
     */
    public String getReceiverEmail() {
        return properties.getProperty(RECEIVER_EMAIL_KEY);
    }
    
}
