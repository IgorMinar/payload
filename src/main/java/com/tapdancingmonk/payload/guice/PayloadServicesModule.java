package com.tapdancingmonk.payload.guice;

import com.google.inject.AbstractModule;
import com.tapdancingmonk.payload.DefaultIpnMessageHandler;
import com.tapdancingmonk.payload.DefaultPayloadProperties;
import com.tapdancingmonk.payload.IpnMessageHandler;
import com.tapdancingmonk.payload.PayloadProperties;
import com.tapdancingmonk.payload.dao.JdoProductDao;
import com.tapdancingmonk.payload.dao.JdoTransactionDao;
import com.tapdancingmonk.payload.dao.ProductDao;
import com.tapdancingmonk.payload.dao.TransactionDao;

/**
 *
 * @author Igor Minar
 */
public class PayloadServicesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ProductDao.class).to(JdoProductDao.class);
        bind(TransactionDao.class).to(JdoTransactionDao.class);
        bind(IpnMessageHandler.class).to(DefaultIpnMessageHandler.class);
        bind(PayloadProperties.class).to(DefaultPayloadProperties.class);
    }
    
}
