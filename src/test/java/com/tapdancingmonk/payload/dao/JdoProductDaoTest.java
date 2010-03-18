package com.tapdancingmonk.payload.dao;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.tapdancingmonk.payload.dao.EntityNotFoundException;
import com.tapdancingmonk.payload.dao.JdoProductDao;
import com.tapdancingmonk.payload.model.Product;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Igor Minar
 */
public class JdoProductDaoTest {

    private final LocalServiceTestHelper testEnv =
                new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private static final PersistenceManagerFactory pmf =
                JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private JdoProductDao dao;

    @Before
    public void setUp() {
        testEnv.setUp();

        dao = new JdoProductDao(pmf);
    }


    @Test
    public void testSave() {
        Product p = new Product("foo", "bar");
        Product pp = dao.save(p);

        assertThat("persisted product has a key",
                pp.getKey(), notNullValue());
    }


    @Test
    public void testFind() {
        Product p = new Product("foo", "bar");
        Product pp = dao.save(p);

        String id = KeyFactory.keyToString(pp.getKey());
        Product fp = dao.find(id);

        assertThat("retrieved object matches the persisted one",
                fp, is(pp));
    }


    @Test(expected=EntityNotFoundException.class)
    public void testDelete() {
        Product p = new Product("foo", "bar");
        Product pp = dao.save(p);

        assumeNotNull(pp.getKey());

        String id = KeyFactory.keyToString(pp.getKey());
        Product fp = dao.find(id);
        
        dao.delete(fp);

        dao.find(id); //throws EntityNotFoundException
    }
}
