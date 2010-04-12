package com.tapdancingmonk.payload.model;

/**
 *
 * @author Igor Minar
 */
public class ProductTemplate {

    private final String uploadUri;


    public ProductTemplate(String uploadUri) {
        this.uploadUri = uploadUri;
    }


    public String getUploadUri() {
       return uploadUri;
    }
}
