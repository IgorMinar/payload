package com.tapdancingmonk.payload.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class PayloadBooter extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new PayloadApp());
    }

}
