package com.tapdancingmonk.payload.dao;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.tapdancingmonk.payload.model.Product;
import java.util.List;
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


    @Test
    public void testFindAll() {
        Product p2 = dao.save(new Product("foo2", "bar2"));
        Product p1 = dao.save(new Product("foo1", "bar1"));

        List<Product> ps = dao.findAll();

        assertThat("two objects where found",
                ps.size(), is(2));
        assertThat("retrieved object matches the persisted one",
                p1, is(ps.get(0)));
        assertThat("retrieved object matches the persisted one",
                p2, is(ps.get(1)));

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
