package com.tapdancingmonk.payload;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.tools.development.testing.LocalBlobstoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.tapdancingmonk.payload.dao.InMemoryProductDao;
import com.tapdancingmonk.payload.dao.ProductDao;
import com.tapdancingmonk.payload.model.Product;
import com.tapdancingmonk.payload.model.ProductTemplate;
import java.net.URI;
import java.util.List;
import javax.ws.rs.core.UriInfo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;


/**
 *
 * @author Igor Minar
 */
public class ProductResourceTest {

    private ProductResource pr;
    private ProductDao pDao;
    private BlobstoreService blobService;


    @Before
    public void setUp() {
        pDao = new InMemoryProductDao();
        blobService = BlobstoreServiceFactory.getBlobstoreService();
        pr = new ProductResource(pDao, blobService);

        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(),
                                   new LocalBlobstoreServiceTestConfig())
                                  .setUp();
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


    @Test
    public void testNewProductTemplate() {
        UriInfo uriInfo = mock(UriInfo.class);
        when(uriInfo.getAbsolutePath())
                .thenReturn(URI.create("http://test:8080/foo"));


        ProductTemplate pt = pr.newProductTemplate(uriInfo);

        assertThat("product template has some upload uri set",
                pt.getUploadUri().matches("/.*"), is(true));
    }


    @Test
    @Ignore
    public void testCreateProduct() {
        // well, this is completely untestable without lots of utility code
        // mainly because appengine doesn't provide an easy way create fake
        // multi-part requests pre-processed by app-engine upload servlet.
    }
}
