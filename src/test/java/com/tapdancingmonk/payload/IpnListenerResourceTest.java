package com.tapdancingmonk.payload;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.tapdancingmonk.payload.dao.EntityNotFoundException;
import com.tapdancingmonk.payload.dao.JdoProductDao;
import com.tapdancingmonk.payload.dao.JdoTransactionDao;
import com.tapdancingmonk.payload.dao.ProductDao;
import com.tapdancingmonk.payload.dao.TransactionDao;
import com.tapdancingmonk.payload.model.Product;

public class IpnListenerResourceTest {

    private final LocalServiceTestHelper testEnv = 
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private static final PersistenceManagerFactory pmf = 
            JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private IpnListenerResource ipnListener;
    private TransactionDao tDao;
    private ProductDao pDao;
    private IpnMessageHandler ipnHandler;
    private PayloadProperties properties;

    @Before
    public void setUp() {
        testEnv.setUp();

        tDao = new JdoTransactionDao(pmf);
        pDao = new JdoProductDao(pmf);
        
        ipnHandler = mock(IpnMessageHandler.class);
        
        properties = new DefaultPayloadProperties();
        
        ipnListener = new IpnListenerResource(pDao, tDao, ipnHandler, properties);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void itShouldNotFailForNonexistantProduct() {
        String fakeId = new KeyFactory.Builder("Product", 1).getString();
        
        ipnListener.receiveIpnMessage(fakeId, 
                "foo", "fop", "bar", "baz", "spaz", "kaw", "wibble", "nonce");
    }
    
    @Test
    public void itShouldInvokeHandlerAndSaveTransaction() {
        Product p = new Product("foo", "bar");
        Product pp = pDao.save(p);
        
        ipnListener.receiveIpnMessage(KeyFactory.keyToString(pp.getKey()), 
                "foo", "fop", "txId", "bar", "spaz", "kaw", "baz", "nonce");
        
        ArgumentCaptor<String> arg1 = ArgumentCaptor.forClass(String.class); 
        
        verify(ipnHandler).processIpnMessage(arg1.capture());
        
        assertThat("the first argument is nonce",
                arg1.getValue(), is("nonce"));
    }
    
    @Ignore // TODO implement validation of seller email
    @Test
    public void itShouldFailIfSellerEmailIsInvalid() {
        
    }

}
