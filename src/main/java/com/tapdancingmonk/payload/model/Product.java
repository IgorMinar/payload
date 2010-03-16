package com.tapdancingmonk.payload.model;

import com.google.appengine.api.datastore.Key;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


/**
 *
 * @author Igor Minar
 */
@PersistenceCapable
public class Product {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;


    @Persistent
    private String name;

    
    @Persistent
    private List<String> blobKeys;


    public Product(String name, List<String> blobKeys) {
        if (name == null || blobKeys == null)
            new NullPointerException("Product can't be instantiated with nulls");

        this.name = name;
        this.blobKeys = blobKeys;
    }

    public Product(String name, String...blobKeys) {
         this(name, Arrays.asList(blobKeys));
    }


    public Key getKey() {
        return key;
    }


    public String getName() {
        return name;
    }

    

    public List<String> getBlobKeys() {
        return Collections.unmodifiableList(blobKeys);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Product other = (Product) obj;
        if (this.name != other.name && (this.name == null || !this.name.equals(other.name))) {
            return false;
        }

        if (this.key != other.key && (this.key == null || !this.key.equals(other.key))) {
            return false;
        }
        return true;
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 79 * hash + (this.key != null ? this.key.hashCode() : 0);
        return hash;
    }
}
