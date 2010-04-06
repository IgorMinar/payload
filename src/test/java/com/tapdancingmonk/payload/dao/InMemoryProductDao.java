package com.tapdancingmonk.payload.dao;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.tapdancingmonk.payload.model.Product;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Igor Minar
 */
public class InMemoryProductDao implements ProductDao {

    private final List<Product> products;
    private final Comparator<Product> comp;


    public InMemoryProductDao() {
        products = new ArrayList<Product>();

        comp = new Comparator<Product>() {

            public int compare(Product o1, Product o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
    }


    public Product save(Product product) {
        try {
            Class pClass = Product.class;
            Key key = KeyFactory.createKey("product", System.nanoTime());
            Field kField = pClass.getDeclaredField("key");
            kField.setAccessible(true);
            kField.set(product, key);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        products.add(product);
        return product;
    }
    

    public Product find(String id) throws EntityNotFoundException {
        Key key = KeyFactory.stringToKey(id);
        for (Product p : products)
            if (p.getKey().equals(key))
                return p;
        throw new EntityNotFoundException("No entity found with id: " + id);
    }


    public List<Product> findAll() {
        Collections.sort(products, comp);
        return products;
    }

    
    public void delete(Product product) {
        products.remove(product);
    }

}
