package com.tapdancingmonk.payload.guice;

import com.google.inject.AbstractModule;
import com.tapdancingmonk.payload.JdoProductDao;
import com.tapdancingmonk.payload.ProductDao;

/**
 *
 * @author Igor Minar
 */
public class PayloadServicesModule extends AbstractModule {

    
    @Override
    protected void configure() {
        bind(ProductDao.class).to(JdoProductDao.class);
    }
}
