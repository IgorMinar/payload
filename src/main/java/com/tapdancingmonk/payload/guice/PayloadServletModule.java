package com.tapdancingmonk.payload.guice;

import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.tapdancingmonk.payload.HelloWorldResource;

/**
 *
 * @author Igor Minar
 */
public class PayloadServletModule extends ServletModule {

    @Override
    public void configureServlets() {
        bind(HelloWorldResource.class); //TODO??
        serve("/helloworld").with(GuiceContainer.class);
    }

}
