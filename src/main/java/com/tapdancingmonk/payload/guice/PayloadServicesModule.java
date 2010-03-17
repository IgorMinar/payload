package com.tapdancingmonk.payload.guice;

import com.google.inject.AbstractModule;
import com.tapdancingmonk.payload.dao.JdoProductDao;
import com.tapdancingmonk.payload.dao.ProductDao;

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
