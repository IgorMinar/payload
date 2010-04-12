package com.tapdancingmonk.payload;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.inject.Inject;
import com.tapdancingmonk.payload.dao.ProductDao;
import com.tapdancingmonk.payload.model.Product;
import com.tapdancingmonk.payload.model.ProductTemplate;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Igor Minar
 */
@Path("/products")
public class ProductResource {

    private final ProductDao productDao;
    private final BlobstoreService blobService;


    @Inject
    public ProductResource(ProductDao productDao, BlobstoreService blobService) {
        this.productDao = productDao;
        this.blobService = blobService;
    }

    @GET
    public List<Product> list() {
      return productDao.findAll();
    }


    @GET
    @Produces("application/vnd.payload.ProductTemplate+json")
    public ProductTemplate newProductTemplate(@Context UriInfo uriInfo) {
        String uploadUri = blobService.createUploadUrl(
                                        uriInfo.getAbsolutePath().getPath());
        return new ProductTemplate(uploadUri);
    }


    @POST
    public Response createProduct(@FormParam("productName") String name,
                                 @Context HttpServletRequest req) {

        //fix for http://code.google.com/p/googleappengine/issues/detail?id=3081
        if (name == null)
            name = req.getParameter("productName");
        
        Map<String, BlobKey> blobs = blobService.getUploadedBlobs(req);
        List<String> blobKeys = new ArrayList<String>();
        
        for (int i=1; i<= blobs.size(); i++) {
            BlobKey blobKey = blobs.get("file" + i);
            blobKeys.add(blobKey.getKeyString());
        }

        Product p = new Product(name, blobKeys);
        Product pp = productDao.save(p);

        //unfortunatelly the blobstore requires a redirect to be returned
        URI uri = UriBuilder.fromResource(ProductResource.class).build(pp.getName());
        return Response.status(302).header("Location", uri).build();
    }
}
