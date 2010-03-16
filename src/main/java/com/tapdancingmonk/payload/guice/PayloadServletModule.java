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
        Map<String, String> guiceContainerParams = new HashMap<String, String>();
        guiceContainerParams.put(WebComponent.RESOURCE_CONFIG_CLASS, PayloadConfig.class.getName());
        serve("/helloworld").with(GuiceContainer.class, guiceContainerParams);
    }

    public static class PayloadConfig extends PackagesResourceConfig {
        public PayloadConfig() {
            super("com.tapdancingmonk.payload");
        }
    }

}
