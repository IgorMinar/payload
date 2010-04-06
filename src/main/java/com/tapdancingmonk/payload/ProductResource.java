package com.tapdancingmonk.payload;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import com.tapdancingmonk.payload.dao.ProductDao;
import com.tapdancingmonk.payload.model.Product;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author Igor Minar
 */
@Path("/products")
public class ProductResource {

    private final ProductDao productDao;


    @Inject
    public ProductResource(ProductDao productDao) {
        this.productDao = productDao;
    }


    @GET
    public List<Product> list() {
      return productDao.findAll();
    }

}
