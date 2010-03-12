package com.tapdancingmonk.payload;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class PayloadConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule() {
            @Override
            public void configureServlets() {
                bind(HelloWorldResource.class);
                serve("/helloworld").with(GuiceContainer.class);
            }
        });
    }

}
