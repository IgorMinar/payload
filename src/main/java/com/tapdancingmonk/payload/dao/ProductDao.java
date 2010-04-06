package com.tapdancingmonk.payload.dao;

import com.tapdancingmonk.payload.model.Product;
import java.util.List;

/**
 *
 * @author Igor Minar
 */
public interface ProductDao {

    Product save(Product product);

    Product find(String id) throws EntityNotFoundException;

    List<Product> findAll();

    void delete(Product product);

}
