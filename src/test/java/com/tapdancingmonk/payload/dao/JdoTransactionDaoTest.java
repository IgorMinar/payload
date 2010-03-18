package com.tapdancingmonk.payload.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeNotNull;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.tapdancingmonk.payload.model.Product;
import com.tapdancingmonk.payload.model.Transaction;

public class JdoTransactionDaoTest {

    private final LocalServiceTestHelper testEnv = 
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private static final PersistenceManagerFactory pmf = 
            JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private JdoTransactionDao tDao;
    private JdoProductDao pDao;

    @Before
    public void setUp() {
        testEnv.setUp();

        tDao = new JdoTransactionDao(pmf);
        pDao = new JdoProductDao(pmf);
    }

    @Test
    public void testSave() {
        Product p = new Product("foo", "bar");
        Product pp = pDao.save(p);
        
        Transaction t = new Transaction("foo", "bar", "baz", "spaz", pp);
        Transaction tt = tDao.save(t);

        assertThat("persisted product has a key", 
                tt.getKey(), notNullValue());
    }

    @Test
    public void testFind() {
        Product p = new Product("foo", "bar");
        Product pp = pDao.save(p);
        
        Transaction t = new Transaction("foo", "bar", "baz", "spaz", pp);
        Transaction tt = tDao.save(t);

        String id = KeyFactory.keyToString(tt.getKey());
        Transaction ft = tDao.find(id);

        assertThat("retrieved object matches the persisted one", 
                ft, is(tt));
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void testDelete() {
        Product p = new Product("foo", "bar");
        Product pp = pDao.save(p);
        
        Transaction t = new Transaction("foo", "bar", "baz", "spaz", pp);
        Transaction tt = tDao.save(t);

        assumeNotNull(tt.getKey());

        String id = KeyFactory.keyToString(tt.getKey());
        Transaction ft = tDao.find(id);
        
        tDao.delete(ft);

        tDao.find(id); //throws EntityNotFoundException
    }
    
    @Test
    public void testFindByTxId() {
        Product p = new Product("foo", "bar");
        Product pp = pDao.save(p);
        
        Transaction t = new Transaction("foo", "bar", "baz", "txId", pp);
        Transaction tt = tDao.save(t);
        
        Transaction ft = tDao.findByTxId("txId");
        
        assertThat("retrieved object matches the persisted one",
                ft, is(tt));
    }

}
