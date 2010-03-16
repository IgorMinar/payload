package com.tapdancingmonk.payload.guice;

import com.google.inject.AbstractModule;

/**
 *
 * @author Igor Minar
 */
public class PayloadApp extends AbstractModule {

    @Override
    protected void configure() {
        install(new PayloadServletModule());
        install(new PayloadGaeModule());
    }



}
