package com.tapdancingmonk.payload;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.tapdancingmonk.payload.dao.InMemoryProductDao;
import com.tapdancingmonk.payload.dao.ProductDao;
import com.tapdancingmonk.payload.model.Product;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


/**
 *
 * @author Igor Minar
 */
public class ProductResourceTest {

    private ProductResource pr;
    private ProductDao pDao;


    @Before
    public void setUp() {
        pDao = new InMemoryProductDao();
        pr = new ProductResource(pDao);

        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()).setUp();
    }


    @Test
    public void testList() {
        pDao.save(new Product("foo", "bar"));
        pDao.save(new Product("bar", "baz"));
        
        List<Product> result = pr.list();

        assertThat("two products were found",
                result.size(), is(2));
        assertThat("the first product is bar",
                result.get(0).getName(), is("bar"));
        assertThat("the second product is foo",
                result.get(1).getName(), is("foo"));
    }

}
