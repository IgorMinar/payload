package com.tapdancingmonk.payload;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.matchers.Equals;
import org.mockito.internal.matchers.InstanceOf;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.tapdancingmonk.payload.dao.EntityNotFoundException;
import com.tapdancingmonk.payload.dao.JdoProductDao;
import com.tapdancingmonk.payload.dao.JdoTransactionDao;
import com.tapdancingmonk.payload.dao.ProductDao;
import com.tapdancingmonk.payload.dao.TransactionDao;
import com.tapdancingmonk.payload.model.Product;
import com.tapdancingmonk.payload.model.Transaction;

import static org.mockito.Mockito.*; 
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class IpnListenerResourceTest {

    private final LocalServiceTestHelper testEnv = 
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private static final PersistenceManagerFactory pmf = 
            JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private IpnListenerResource ipnListener;
    private TransactionDao tDao;
    private ProductDao pDao;
    private IpnMessageHandler ipnHandler;

    @Before
    public void setUp() {
        testEnv.setUp();

        tDao = new JdoTransactionDao(pmf);
        pDao = new JdoProductDao(pmf);
        
        ipnHandler = mock(IpnMessageHandler.class);
        
        ipnListener = new IpnListenerResource(pDao, tDao, ipnHandler);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void itShouldNotFailForNonexistantProduct() {
        String fakeId = new KeyFactory.Builder("Product", 1).getString();
        
        ipnListener.receiveIpnMessage(fakeId, 
                "foo", "bar", "baz", "spaz", "kaw", "wibble", "nonce");
    }
    
    @Test
    public void itShouldInvokeHandlerAndSaveTransaction() {
        Product p = new Product("foo", "bar");
        Product pp = pDao.save(p);
        
        ipnListener.receiveIpnMessage(KeyFactory.keyToString(pp.getKey()), 
                "foo", "txId", "bar", "spaz", "kaw", "baz", "nonce");
        
        ArgumentCaptor<String> arg1 = ArgumentCaptor.forClass(String.class); 
        ArgumentCaptor<Transaction> arg2 = ArgumentCaptor.forClass(Transaction.class); 
        
        verify(ipnHandler).processIpnMessage(arg1.capture(), arg2.capture());
        
        assertThat("the first argument is nonce",
                arg1.getValue(), is("nonce"));
        assertThat("the second argument is our transaction",
                arg2.getValue().getTransactionId(), is("txId"));
    }
    
    @Ignore // TODO implement validation of seller email
    @Test
    public void itShouldFailIfSellerEmailIsInvalid() {
        
    }

}
