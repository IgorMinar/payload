package com.tapdancingmonk.payload.guice;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.inject.AbstractModule;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 *
 * @author Igor Minar
 */
public class PayloadGaeModule extends AbstractModule {

    
    @Override
    protected void configure() {
        bind(PersistenceManagerFactory.class)
                .toInstance(JDOHelper.getPersistenceManagerFactory("transactions-optional"));
        bind(BlobstoreService.class)
                .toInstance(BlobstoreServiceFactory.getBlobstoreService());
    }
}
