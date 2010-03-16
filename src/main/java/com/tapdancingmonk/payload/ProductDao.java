package com.tapdancingmonk.payload;

import com.tapdancingmonk.payload.model.Product;

/**
 *
 * @author Igor Minar
 */
public interface ProductDao {

    Product save(Product product);

    Product find(String id) throws EntityNotFoundException;

    void delete(Product product);

}
