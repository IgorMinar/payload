package com.tapdancingmonk.payload;

import com.google.gson.Gson;
import com.sun.jersey.spi.resource.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Igor Minar
 */
@Provider
@Singleton
public class GsonReader<T> implements MessageBodyReader<T> {

    private final Gson gson;

    
    public GsonReader() {
        gson = new Gson();
    }


    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        return mediaType.toString().endsWith("json");
    }


    public T readFrom(Class<T> type, Type genericType,
                           Annotation[] annotations, MediaType mediaType,
                           MultivaluedMap<String, String> httpHeaders,
                           InputStream entityStream) throws IOException, WebApplicationException {
        
        return gson.fromJson(new InputStreamReader(entityStream), type);
    }
}
