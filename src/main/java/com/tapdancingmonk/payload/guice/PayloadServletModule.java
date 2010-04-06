package com.tapdancingmonk.payload.guice;

import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.WebComponent;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Igor Minar
 */
public class PayloadServletModule extends ServletModule {

    @Override
    public void configureServlets() {
        Map<String, String> jerseyParams = new HashMap<String, String>();
        jerseyParams.put(WebComponent.RESOURCE_CONFIG_CLASS, PayloadConfig.class.getName());
        serve("/payload/*").with(GuiceContainer.class, jerseyParams);
    }

    public static class PayloadConfig extends PackagesResourceConfig {
        public PayloadConfig() {
            super("com.tapdancingmonk.payload");
        }
    }

}
